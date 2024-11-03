package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.sdk.util.Constants;
import com.dwarfeng.statistics.stack.bean.dto.*;
import com.dwarfeng.statistics.stack.bean.entity.Task;
import com.dwarfeng.statistics.stack.bean.key.BridgeDataKey;
import com.dwarfeng.statistics.stack.exception.ProviderDataExceededException;
import com.dwarfeng.statistics.stack.handler.*;
import com.dwarfeng.statistics.stack.service.TaskMaintainService;
import com.dwarfeng.statistics.stack.struct.ExecuteInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Component
public class ExecuteHandlerImpl implements ExecuteHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteHandlerImpl.class);

    private final ApplicationContext ctx;

    private final TaskMaintainService taskMaintainService;

    private final ExecuteLocalCacheHandler executeLocalCacheHandler;
    private final TaskOperateHandler taskOperateHandler;
    private final TaskEventOperateHandler taskEventOperateHandler;
    private final VariableOperateHandler variableOperateHandler;
    private final KeepHandler keepHandler;
    private final PersistHandler persistHandler;

    private final ThreadPoolTaskExecutor executor;
    private final ThreadPoolTaskScheduler scheduler;

    @Value("${task.beat_interval}")
    private long beatInterval;

    @Value("${task.max_provider_data_size}")
    private int maxProviderDataSize;

    public ExecuteHandlerImpl(
            ApplicationContext ctx,
            TaskMaintainService taskMaintainService,
            ExecuteLocalCacheHandler executeLocalCacheHandler,
            TaskOperateHandler taskOperateHandler,
            TaskEventOperateHandler taskEventOperateHandler,
            VariableOperateHandler variableOperateHandler,
            KeepHandler keepHandler,
            PersistHandler persistHandler,
            ThreadPoolTaskExecutor executor,
            ThreadPoolTaskScheduler scheduler
    ) {
        this.ctx = ctx;
        this.taskMaintainService = taskMaintainService;
        this.executeLocalCacheHandler = executeLocalCacheHandler;
        this.taskOperateHandler = taskOperateHandler;
        this.taskEventOperateHandler = taskEventOperateHandler;
        this.variableOperateHandler = variableOperateHandler;
        this.keepHandler = keepHandler;
        this.persistHandler = persistHandler;
        this.executor = executor;
        this.scheduler = scheduler;
    }

    @Override
    public TaskCreateResult create(TaskCreateInfo info) throws HandlerException {
        try {
            return taskOperateHandler.create(info);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public void execute(TaskExecuteInfo info) throws HandlerException {
        try {
            doExecuteTask(info);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    private void doExecuteTask(TaskExecuteInfo info) throws Exception {
        // 预定义参数。
        Future<?> beatTaskFuture = null;

        // 展开参数。
        LongIdKey taskKey = info.getTaskKey();

        try {
            // 调用处理器启动任务。
            taskOperateHandler.start(new TaskStartInfo(taskKey));

            // 启动心跳任务。
            beatTaskFuture = startBeatTask(taskKey);

            // 获取任务本身，及其关联的任务设置主键。
            Task task = taskMaintainService.get(taskKey);
            LongIdKey statisticsSettingKey = task.getStatisticsSettingKey();

            // 根据统计设置主键获取执行信息。
            ExecuteInfo executeInfo = executeLocalCacheHandler.get(statisticsSettingKey);

            // 构造读取器上下文。
            Provider.Context providerContext = ctx.getBean(
                    InternalProviderContext.class,
                    statisticsSettingKey, taskKey, taskOperateHandler, taskEventOperateHandler, variableOperateHandler
            );
            // 构造过滤器上下文。
            Filter.Context filterContext = ctx.getBean(
                    InternalFilterContext.class,
                    statisticsSettingKey, taskKey, taskOperateHandler, taskEventOperateHandler, variableOperateHandler
            );
            // 构造读取器执行器。
            Map<LongIdKey, Provider.Executor> providerExecutorMap = makeProviderExecutorMap(
                    executeInfo, providerContext
            );
            // 构造提供器执行器。
            Map<LongIdKey, Filter.Executor> filterExecutorMap = makeFilterExecutorMap(
                    executeInfo, filterContext
            );

            // 定义提供器数据列表，及其相关锁。
            List<ProviderData> providerDatas = new ArrayList<>();
            Lock providerDatasLock = new ReentrantLock();

            // 定义桥接器数据列表。
            List<BridgeData> bridgeDatas;

            // 定义 CompletableFuture 集合。
            Set<CompletableFuture<?>> completableFutureSet = new HashSet<>();

            for (Map.Entry<LongIdKey, Provider.Executor> entry : providerExecutorMap.entrySet()) {
                LongIdKey providerKey = entry.getKey();
                Provider.Executor providerExecutor = entry.getValue();
                ProviderExecutorTask providerExecutorTask = ctx.getBean(
                        ProviderExecutorTask.class,
                        statisticsSettingKey, taskKey, providerKey, providerExecutor,
                        providerDatas, providerDatasLock, maxProviderDataSize
                );
                CompletableFuture<Void> future = CompletableFuture.runAsync(providerExecutorTask, executor);
                completableFutureSet.add(future);
            }

            // 等待所有任务完成。
            try {
                CompletableFuture.allOf(completableFutureSet.toArray(new CompletableFuture[0])).join();
            } catch (CompletionException e) {
                throw (Exception) e.getCause();
            }

            // 记录日志。
            LOGGER.debug("任务 {} 提供器执行完成, 共获取提供器数据 {} 条", taskKey, providerDatas.size());

            // 判断任务状态。
            boolean skipProcessFlag = false;
            task = taskMaintainService.get(taskKey);
            if (!Objects.equals(task.getStatus(), Constants.TASK_STATUS_PROCESSING)) {
                // 记录日志。
                LOGGER.debug("任务 {} 状态被设置为 {}, 跳过处理器执行", taskKey, task.getStatus());
                // 置位跳过处理标识。
                skipProcessFlag = true;
            }

            // 如果 skipProcessFlag 为 true，则跳过处理器执行。
            if (skipProcessFlag) {
                return;
            }

            // 调用过滤器过滤提供器数据。
            providerDatas = filterProviderDatas(
                    statisticsSettingKey, taskKey, executeInfo.getFilterChainKeys(), filterExecutorMap, providerDatas
            );

            // 将提供器数据转换为桥接器数据，并按发生日期排序。
            bridgeDatas = providerDatas.stream().map(pd -> new BridgeData(
                    new BridgeDataKey(statisticsSettingKey.getLongId(), pd.getTag()),
                    pd.getValue(), pd.getHappenedDate()
            )).sorted(Comparator.comparing(BridgeData::getHappenedDate)).collect(Collectors.toList());

            // 计算不同的 BridgeDataKey 对应的最新数据。
            Map<BridgeDataKey, BridgeData> latestBridgeDataMap = new HashMap<>();
            // 由于 bridgeDatas 已经按照 happenedDate 正序排序，因此倒序遍历即可。
            for (int i = bridgeDatas.size() - 1; i >= 0; i--) {
                BridgeData bridgeData = bridgeDatas.get(i);
                BridgeDataKey bridgeDataKey = bridgeData.getKey();
                if (!latestBridgeDataMap.containsKey(bridgeDataKey)) {
                    latestBridgeDataMap.put(bridgeDataKey, bridgeData);
                }
            }

            // 记录日志。
            LOGGER.debug("任务 {} 过滤器执行完成, 共获取桥接器数据 {} 条", taskKey, bridgeDatas.size());

            // 判断任务状态。
            task = taskMaintainService.get(taskKey);
            if (!Objects.equals(task.getStatus(), Constants.TASK_STATUS_PROCESSING)) {
                // 记录日志。
                LOGGER.debug("任务 {} 状态被设置为 {}, 跳过处理器执行", taskKey, task.getStatus());
                // 置位跳过处理标识。
                skipProcessFlag = true;
            }

            // 如果 skipProcessFlag 为 true，则跳过处理器执行。
            if (skipProcessFlag) {
                return;
            }

            // 如果 bridgeDatas 不为空，则处理数据。
            if (!bridgeDatas.isEmpty()) {
                // 取最新的数据，使用保持处理器更新数据。
                keepHandler.update(new ArrayList<>(latestBridgeDataMap.values()));
                // 使用持久化处理器持久化数据。
                persistHandler.record(bridgeDatas);
            }

            // 记录日志。
            LOGGER.debug("任务 {} 桥接器数据处理完毕", taskKey);

            // 判断任务状态。
            task = taskMaintainService.get(taskKey);
            if (!Objects.equals(task.getStatus(), Constants.TASK_STATUS_PROCESSING)) {
                // 记录日志。
                LOGGER.debug("任务 {} 状态被设置为 {}, 跳过处理器执行", taskKey, task.getStatus());
                // 置位跳过处理标识。
                skipProcessFlag = true;
            }

            // 如果 skipProcessFlag 为 true，则跳过处理器执行。
            if (skipProcessFlag) {
                return;
            }

            // 将任务状态置为完成。
            taskOperateHandler.finish(new TaskFinishInfo(taskKey));
        } catch (Exception e) {
            // 记录日志。
            String logMessage = "导出任务执行失败, 任务主键: " + taskKey + ", 异常信息如下: ";
            LOGGER.warn(logMessage, e);

            // 将任务状态置为失败。
            taskOperateHandler.fail(new TaskFailInfo(taskKey));
        } finally {
            // 清理心跳任务。
            if (Objects.nonNull(beatTaskFuture)) {
                beatTaskFuture.cancel(true);
            }
        }
    }

    @Override
    public CompletableFuture<Void> executeAsync(TaskExecuteInfo info) {
        return CompletableFuture.supplyAsync(
                () -> {
                    wrappedDoExecuteTask(info);
                    return null;
                },
                executor
        );
    }

    private void wrappedDoExecuteTask(TaskExecuteInfo info) throws CompletionException {
        try {
            doExecuteTask(info);
        } catch (Exception e) {
            throw new CompletionException(e);
        }
    }

    private Future<?> startBeatTask(LongIdKey taskKey) {
        BeatSchedulerTask beatSchedulerTask = ctx.getBean(BeatSchedulerTask.class, taskOperateHandler, taskKey);
        return scheduler.scheduleAtFixedRate(
                beatSchedulerTask,
                new Date(System.currentTimeMillis() + beatInterval),
                beatInterval
        );
    }

    private Map<LongIdKey, Provider.Executor> makeProviderExecutorMap(
            ExecuteInfo executeInfo, Provider.Context providerContext
    ) throws Exception {
        Map<LongIdKey, Provider.Executor> result = new HashMap<>();
        for (Map.Entry<LongIdKey, Provider> entry : executeInfo.getProviderMap().entrySet()) {
            LongIdKey providerKey = entry.getKey();
            Provider provider = entry.getValue();
            Provider.Executor providerExecutor = provider.newExecutor();
            providerExecutor.init(providerContext);
            result.put(providerKey, providerExecutor);
        }
        return result;
    }

    private Map<LongIdKey, Filter.Executor> makeFilterExecutorMap(
            ExecuteInfo executeInfo, Filter.Context filterContext
    ) throws Exception {
        Map<LongIdKey, Filter.Executor> result = new HashMap<>();
        for (Map.Entry<LongIdKey, Filter> entry : executeInfo.getFilterMap().entrySet()) {
            LongIdKey filterKey = entry.getKey();
            Filter filter = entry.getValue();
            Filter.Executor filterExecutor = filter.newExecutor();
            filterExecutor.init(filterContext);
            result.put(filterKey, filterExecutor);
        }
        return result;
    }

    private List<ProviderData> filterProviderDatas(
            LongIdKey statisticsSettingKey, LongIdKey taskKey,
            List<LongIdKey> filterChainKeys, Map<LongIdKey, Filter.Executor> filterExecutorMap,
            List<ProviderData> providerDatas
    ) throws Exception {
        // 如果 providerDatas 为空，则直接返回。
        if (providerDatas.isEmpty()) {
            return Collections.emptyList();
        }
        // 否则，依次调用过滤器过滤提供器数据。
        List<ProviderData> result = new ArrayList<>(providerDatas);
        for (LongIdKey filterKey : filterChainKeys) {
            try {
                Filter.Executor filterExecutor = filterExecutorMap.get(filterKey);
                FilterExecutorTask filterExecutorTask = ctx.getBean(
                        FilterExecutorTask.class,
                        statisticsSettingKey, taskKey, filterKey, filterExecutor, result
                );
                filterExecutorTask.run();
                result = filterExecutorTask.getResult();
            } catch (CompletionException e) {
                throw (Exception) e.getCause();
            }
        }
        // 返回结果。
        return result;
    }

    /**
     * 心跳定时器任务。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class BeatSchedulerTask implements Runnable {

        private static final Logger LOGGER = LoggerFactory.getLogger(BeatSchedulerTask.class);

        private final TaskOperateHandler taskOperateHandler;

        private final LongIdKey taskKey;

        public BeatSchedulerTask(TaskOperateHandler taskOperateHandler, LongIdKey taskKey) {
            this.taskOperateHandler = taskOperateHandler;
            this.taskKey = taskKey;
        }

        @Override
        public void run() {
            try {
                taskOperateHandler.beat(new TaskBeatInfo(taskKey));
            } catch (Exception e) {
                LOGGER.warn("心跳任务执行失败, 任务主键为 {}, 异常信息如下: ", taskKey, e);
            }
        }

        @Override
        public String toString() {
            return "BeatSchedulerTask{" +
                    "taskOperateHandler=" + taskOperateHandler +
                    ", taskKey=" + taskKey +
                    '}';
        }
    }

    /**
     * 提供器上下文的内部实现。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class InternalProviderContext implements Provider.Context {

        private final LongIdKey statisticsSettingKey;
        private final LongIdKey taskKey;

        private final TaskOperateHandler taskOperateHandler;
        private final TaskEventOperateHandler taskEventOperateHandler;
        private final VariableOperateHandler variableOperateHandler;

        public InternalProviderContext(
                LongIdKey statisticsSettingKey, LongIdKey taskKey, TaskOperateHandler taskOperateHandler,
                TaskEventOperateHandler taskEventOperateHandler, VariableOperateHandler variableOperateHandler
        ) {
            this.statisticsSettingKey = statisticsSettingKey;
            this.taskKey = taskKey;
            this.taskOperateHandler = taskOperateHandler;
            this.taskEventOperateHandler = taskEventOperateHandler;
            this.variableOperateHandler = variableOperateHandler;
        }

        @Override
        public LongIdKey getStatisticsSettingKey() {
            return statisticsSettingKey;
        }

        @Override
        public LongIdKey getTaskKey() {
            return taskKey;
        }

        @Override
        public void updateTaskModal(TaskUpdateModalInfo info) throws Exception {
            taskOperateHandler.updateModal(info);
        }

        @Override
        public void createTaskEvent(TaskEventCreateInfo info) throws Exception {
            taskEventOperateHandler.create(info);
        }

        @Override
        public VariableInspectResult inspectVariable(VariableInspectInfo info) throws Exception {
            return variableOperateHandler.inspect(info);
        }

        @Override
        public void upsertVariable(VariableUpsertInfo info) throws Exception {
            variableOperateHandler.upsert(info);
        }

        @Override
        public void removeVariable(VariableRemoveInfo info) throws Exception {
            variableOperateHandler.remove(info);
        }

        @Override
        public String toString() {
            return "InternalProviderContext{" +
                    "statisticsSettingKey=" + statisticsSettingKey +
                    ", taskKey=" + taskKey +
                    ", taskOperateHandler=" + taskOperateHandler +
                    ", taskEventOperateHandler=" + taskEventOperateHandler +
                    ", variableOperateHandler=" + variableOperateHandler +
                    '}';
        }
    }

    /**
     * 过滤器上下文的内部实现。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class InternalFilterContext implements Filter.Context {

        private final LongIdKey statisticsSettingKey;
        private final LongIdKey taskKey;

        private final TaskOperateHandler taskOperateHandler;
        private final TaskEventOperateHandler taskEventOperateHandler;
        private final VariableOperateHandler variableOperateHandler;

        public InternalFilterContext(
                LongIdKey statisticsSettingKey, LongIdKey taskKey, TaskOperateHandler taskOperateHandler,
                TaskEventOperateHandler taskEventOperateHandler, VariableOperateHandler variableOperateHandler
        ) {
            this.statisticsSettingKey = statisticsSettingKey;
            this.taskKey = taskKey;
            this.taskOperateHandler = taskOperateHandler;
            this.taskEventOperateHandler = taskEventOperateHandler;
            this.variableOperateHandler = variableOperateHandler;
        }

        @Override
        public LongIdKey getStatisticsSettingKey() {
            return statisticsSettingKey;
        }

        @Override
        public LongIdKey getTaskKey() {
            return taskKey;
        }

        @Override
        public void updateTaskModal(TaskUpdateModalInfo info) throws Exception {
            taskOperateHandler.updateModal(info);
        }

        @Override
        public void createTaskEvent(TaskEventCreateInfo info) throws Exception {
            taskEventOperateHandler.create(info);
        }

        @Override
        public VariableInspectResult inspectVariable(VariableInspectInfo info) throws Exception {
            return variableOperateHandler.inspect(info);
        }

        @Override
        public void upsertVariable(VariableUpsertInfo info) throws Exception {
            variableOperateHandler.upsert(info);
        }

        @Override
        public void removeVariable(VariableRemoveInfo info) throws Exception {
            variableOperateHandler.remove(info);
        }

        @Override
        public String toString() {
            return "InternalFilterContext{" +
                    "statisticsSettingKey=" + statisticsSettingKey +
                    ", taskKey=" + taskKey +
                    ", taskOperateHandler=" + taskOperateHandler +
                    ", taskEventOperateHandler=" + taskEventOperateHandler +
                    ", variableOperateHandler=" + variableOperateHandler +
                    '}';
        }
    }

    /**
     * 提供器执行器任务。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class ProviderExecutorTask implements Runnable {

        private static final Logger LOGGER = LoggerFactory.getLogger(ProviderExecutorTask.class);

        private static final int EXECUTOR_STATUS_STARTED = 1;
        private static final int EXECUTOR_STATUS_EXCEPTION = 2;

        private final LongIdKey statisticsSettingKey;
        private final LongIdKey taskKey;
        private final LongIdKey providerKey;

        private final Provider.Executor providerExecutor;

        private final List<ProviderData> providerDatas;
        private final Lock providerDatasLock;

        private final int maxProviderDataSize;

        public ProviderExecutorTask(
                LongIdKey statisticsSettingKey,
                LongIdKey taskKey,
                LongIdKey providerKey,
                Provider.Executor providerExecutor,
                List<ProviderData> providerDatas,
                Lock providerDatasLock,
                int maxProviderDataSize
        ) {
            this.statisticsSettingKey = statisticsSettingKey;
            this.taskKey = taskKey;
            this.providerKey = providerKey;
            this.providerExecutor = providerExecutor;
            this.providerDatas = providerDatas;
            this.providerDatasLock = providerDatasLock;
            this.maxProviderDataSize = maxProviderDataSize;
        }

        @Override
        public void run() {
            // 定义异常。
            Exception occurredException = null;

            // 定义执行标识。
            int beforeExecuteExecuteStatus;

            // 执行启动调度。
            try {
                // 执行启动方法。
                providerExecutor.start();
                // 置位状态标识。
                beforeExecuteExecuteStatus = EXECUTOR_STATUS_STARTED;
            } catch (Exception e) {
                // 记录日志。
                String message = "提供器执行器执启动调度时发生异常, 统计设置键: " + statisticsSettingKey + ", 任务键: " +
                        taskKey + ", 提供器键: " + providerKey + ", 异常信息如下: ";
                LOGGER.warn(message, e);
                // 置位异常。
                occurredException = e;
                // 置位状态标识。
                beforeExecuteExecuteStatus = EXECUTOR_STATUS_EXCEPTION;
            }

            // 如果执启动调度方法被执行，则执行执行调度。
            if (Objects.equals(beforeExecuteExecuteStatus, EXECUTOR_STATUS_STARTED)) {
                // 定义提供器数据列表。
                List<ProviderData> innerProviderDatas = null;

                // 调用执行器的提供方法，获取提供器提供的数据。
                try {
                    innerProviderDatas = Optional.ofNullable(providerExecutor.provide())
                            .orElse(Collections.emptyList());
                } catch (Exception e) {
                    // 记录日志。
                    String message = "提供器执行器执行调度时发生异常, 统计设置键: " + statisticsSettingKey + ", 任务键: " +
                            taskKey + ", 提供器键: " + providerKey + ", 异常信息如下: ";
                    LOGGER.warn(message, e);
                    // 置位异常。
                    if (Objects.isNull(occurredException)) {
                        occurredException = e;
                    }
                }

                // 提供器提供的数据超限判断。
                // 根据上文的逻辑，可以保证此处的 innerProviderDatas 一定不为 null。
                assert innerProviderDatas != null;
                if (maxProviderDataSize < innerProviderDatas.size()) {
                    // 记录日志。
                    String message = "提供器提供的数据超限, 统计设置键: " + statisticsSettingKey + ", 任务键: " +
                            taskKey + ", 提供器键: " + providerKey + ", 数据量: " + innerProviderDatas.size() +
                            ", 最大数据量: " + maxProviderDataSize;
                    LOGGER.warn(message);
                    // 置位异常。
                    occurredException = new ProviderDataExceededException(
                            maxProviderDataSize, innerProviderDatas.size()
                    );
                }

                // 将提供器数据添加到列表中。
                providerDatasLock.lock();
                try {
                    this.providerDatas.addAll(innerProviderDatas);
                } catch (Exception e) {
                    // 记录日志。
                    String message = "提供器执行器执行调度时发生异常, 统计设置键: " + statisticsSettingKey + ", 任务键: " +
                            taskKey + ", 提供器键: " + providerKey + ", 异常信息如下: ";
                    LOGGER.warn(message, e);
                    // 置位异常。
                    if (Objects.isNull(occurredException)) {
                        occurredException = e;
                    }
                } finally {
                    providerDatasLock.unlock();
                }
            }

            // 只要执启动调度和执行调度被执行（无论成功还是失败），都执行执行后执行调度。
            // 只要代码执行到此处，一定符合执启动调度被执行的条件。
            try {
                providerExecutor.stop();
            } catch (Exception e) {
                // 记录日志。
                String message = "提供器执行器执行后执行调度时发生异常, 统计设置键: " + statisticsSettingKey +
                        ", 任务键: " + taskKey + ", 提供器键: " + providerKey + ", 异常信息如下: ";
                LOGGER.warn(message, e);
                // 置位异常。
                if (Objects.isNull(occurredException)) {
                    occurredException = e;
                }
            }

            // 如果发生异常，则抛出。
            if (Objects.nonNull(occurredException)) {
                throw new CompletionException(occurredException);
            }
        }

        @Override
        public String toString() {
            return "ProviderExecutorTask{" +
                    "statisticsSettingKey=" + statisticsSettingKey +
                    ", taskKey=" + taskKey +
                    ", providerKey=" + providerKey +
                    ", providerExecutor=" + providerExecutor +
                    ", providerDatas=" + providerDatas +
                    ", providerDatasLock=" + providerDatasLock +
                    ", maxProviderDataSize=" + maxProviderDataSize +
                    '}';
        }
    }

    /**
     * 过滤器执行器任务。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class FilterExecutorTask implements Runnable {

        private static final Logger LOGGER = LoggerFactory.getLogger(FilterExecutorTask.class);

        private static final int EXECUTOR_STATUS_STARTED = 1;
        private static final int EXECUTOR_STATUS_EXCEPTION = 2;

        private final LongIdKey statisticsSettingKey;
        private final LongIdKey taskKey;
        private final LongIdKey filterKey;

        private final Filter.Executor filterExecutor;

        private final List<ProviderData> providerDatas;

        private List<ProviderData> result;

        public FilterExecutorTask(
                LongIdKey statisticsSettingKey,
                LongIdKey taskKey,
                LongIdKey filterKey,
                Filter.Executor filterExecutor,
                List<ProviderData> providerDatas
        ) {
            this.statisticsSettingKey = statisticsSettingKey;
            this.taskKey = taskKey;
            this.filterKey = filterKey;
            this.filterExecutor = filterExecutor;
            this.providerDatas = providerDatas;
        }

        @Override
        public void run() {
            // 定义异常。
            Exception occurredException = null;

            // 定义执行标识。
            int beforeExecuteExecuteStatus;

            // 执行启动调度。
            try {
                // 执行启动方法。
                filterExecutor.start();
                // 置位状态标识。
                beforeExecuteExecuteStatus = EXECUTOR_STATUS_STARTED;
            } catch (Exception e) {
                // 记录日志。
                String message = "过滤器执行器执启动调度时发生异常, 统计设置键: " + statisticsSettingKey + ", 任务键: " +
                        taskKey + ", 过滤器键: " + filterKey + ", 异常信息如下: ";
                LOGGER.warn(message, e);
                // 置位异常。
                occurredException = e;
                // 置位状态标识。
                beforeExecuteExecuteStatus = EXECUTOR_STATUS_EXCEPTION;
            }

            // 如果执启动调度方法被执行，则执行执行调度。
            if (Objects.equals(beforeExecuteExecuteStatus, EXECUTOR_STATUS_STARTED)) {
                // 调用执行器的过滤方法，将过滤的结果存储到 result 中。
                try {
                    result = filterExecutor.filter(providerDatas);
                } catch (Exception e) {
                    // 记录日志。
                    String message = "过滤器执行器执行调度时发生异常, 统计设置键: " + statisticsSettingKey + ", 任务键: " +
                            taskKey + ", 过滤器键: " + filterKey + ", 异常信息如下: ";
                    LOGGER.warn(message, e);
                    // 置位异常。
                    if (Objects.isNull(occurredException)) {
                        occurredException = e;
                    }
                }
            }

            // 只要执启动调度和执行调度被执行（无论成功还是失败），都执行执行后执行调度。
            // 只要代码执行到此处，一定符合执启动调度被执行的条件。
            try {
                filterExecutor.stop();
            } catch (Exception e) {
                // 记录日志。
                String message = "过滤器执行器执行后执行调度时发生异常, 统计设置键: " + statisticsSettingKey +
                        ", 任务键: " + taskKey + ", 过滤器键: " + filterKey + ", 异常信息如下: ";
                LOGGER.warn(message, e);
                // 置位异常。
                if (Objects.isNull(occurredException)) {
                    occurredException = e;
                }
            }

            // 如果发生异常，则抛出。
            if (Objects.nonNull(occurredException)) {
                throw new CompletionException(occurredException);
            }
        }

        public List<ProviderData> getResult() {
            return result;
        }

        @Override
        public String toString() {
            return "FilterExecutorTask{" +
                    "statisticsSettingKey=" + statisticsSettingKey +
                    ", taskKey=" + taskKey +
                    ", filterKey=" + filterKey +
                    ", filterExecutor=" + filterExecutor +
                    ", providerDatas=" + providerDatas +
                    ", result=" + result +
                    '}';
        }
    }
}
