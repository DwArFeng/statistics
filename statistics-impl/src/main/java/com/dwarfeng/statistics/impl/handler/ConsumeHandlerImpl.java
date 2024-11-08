package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.dutil.basic.mea.TimeMeasurer;
import com.dwarfeng.dutil.develop.backgr.AbstractTask;
import com.dwarfeng.statistics.sdk.util.Constants;
import com.dwarfeng.statistics.stack.bean.dto.TaskCreateInfo;
import com.dwarfeng.statistics.stack.bean.dto.TaskCreateResult;
import com.dwarfeng.statistics.stack.bean.dto.TaskExecuteInfo;
import com.dwarfeng.statistics.stack.handler.ConsumeHandler;
import com.dwarfeng.statistics.stack.handler.ExecuteHandler;
import com.dwarfeng.statistics.stack.struct.ConsumeInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class ConsumeHandlerImpl implements ConsumeHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumeHandlerImpl.class);

    private final ApplicationContext ctx;

    private final ThreadPoolTaskExecutor executor;
    private final ThreadPoolTaskScheduler scheduler;

    private final ConsumeProcessor consumeProcessor;
    private final ConsumeBuffer consumeBuffer;

    private final List<ConsumeTask> processingConsumeTasks = new ArrayList<>();
    private final List<ConsumeTask> endingConsumeTasks = new ArrayList<>();

    @Value("${consume.consumer_thread}")
    private int consumerThread;
    @Value("${consume.buffer_size}")
    private int bufferSize;
    @Value("${consume.threshold.warn}")
    private double warnThreshold;

    private final Lock lock = new ReentrantLock();

    private boolean startFlag = false;
    private ScheduledFuture<?> capacityCheckFuture = null;
    private int thread;

    public ConsumeHandlerImpl(
            ApplicationContext ctx,
            ThreadPoolTaskExecutor executor,
            ThreadPoolTaskScheduler scheduler,
            ConsumeProcessor consumeProcessor,
            ConsumeBuffer consumeBuffer
    ) {
        this.ctx = ctx;
        this.executor = executor;
        this.scheduler = scheduler;
        this.consumeProcessor = consumeProcessor;
        this.consumeBuffer = consumeBuffer;
    }

    @PostConstruct
    public void init() {
        thread = Math.max(1, consumerThread);
        setBufferSize(bufferSize);
    }

    @Override
    public boolean isStart() {
        lock.lock();
        try {
            return startFlag;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void start() {
        lock.lock();
        try {
            if (startFlag) {
                return;
            }

            LOGGER.info("Consumer handler 开启消费线程...");
            consumeBuffer.block();
            for (int i = 0; i < thread; i++) {
                ConsumeTask consumeTask = ctx.getBean(ConsumeTask.class);
                executor.execute(consumeTask);
                processingConsumeTasks.add(consumeTask);
            }
            capacityCheckFuture = scheduler.scheduleAtFixedRate(
                    () -> {
                        double ratio = (double) consumeBuffer.bufferedSize() / (double) consumeBuffer.getBufferSize();
                        if (ratio >= warnThreshold) {
                            LOGGER.warn("消费者的待消费元素占用缓存比例为 {}, 超过报警值 {}, 请检查", ratio, warnThreshold);
                        }
                    },
                    Constants.CONSUMER_HANDLER_CHECK_INTERVAL
            );
            startFlag = true;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void stop() {
        lock.lock();
        try {
            if (!startFlag) {
                return;
            }

            LOGGER.info("Consume handler 结束消费线程...");
            if (Objects.nonNull(capacityCheckFuture)) {
                capacityCheckFuture.cancel(true);
                capacityCheckFuture = null;
            }
            processingConsumeTasks.forEach(ConsumeTask::shutdown);
            endingConsumeTasks.addAll(processingConsumeTasks);
            processingConsumeTasks.clear();
            consumeBuffer.unblock();
            processRemainingElement();
            endingConsumeTasks.removeIf(AbstractTask::isFinished);
            if (!endingConsumeTasks.isEmpty()) {
                LOGGER.info("Consume handler 中的线程还未完全结束, 等待线程结束...");
                endingConsumeTasks.forEach(
                        task -> {
                            try {
                                task.awaitFinish();
                            } catch (InterruptedException ignored) {
                            }
                        }
                );
            }
            processingConsumeTasks.clear();
            endingConsumeTasks.clear();
            LOGGER.info("Consume handler 已经妥善处理数据, 消费线程结束");
            startFlag = false;
        } finally {
            lock.unlock();
        }
    }

    private void processRemainingElement() {
        // 如果没有剩余元素，直接跳过。
        if (consumeBuffer.bufferedSize() <= 0) return;
        LOGGER.info("消费 consume handler 中剩余的元素 {} 个...", consumeBuffer.bufferedSize());
        LOGGER.info("Consume handler 中剩余的元素过多时，需要较长时间消费，请耐心等待...");
        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(
                () -> {
                    String message;
                    if (consumeBuffer.bufferedSize() > 0) {
                        message = "消费 consume handler 中剩余的元素" + consumeBuffer.bufferedSize() + "个，请耐心等待...";
                    } else {
                        message = "Consume handler 中剩余的元素已经全部消费完毕, 等待 consume processor 中的元素消费完毕...";
                    }
                    LOGGER.info(message);
                },
                new Date(System.currentTimeMillis() + Constants.CONSUMER_HANDLER_CHECK_INTERVAL),
                Constants.CONSUMER_HANDLER_CHECK_INTERVAL
        );
        ConsumeInfo consumeInfo2Consume;
        while (Objects.nonNull(consumeInfo2Consume = consumeBuffer.poll())) {
            try {
                consumeProcessor.consume(consumeInfo2Consume);
            } catch (Exception e) {
                LOGGER.warn("消费元素时发生异常, 抛弃 DataInfo: {}", consumeInfo2Consume, e);
            }
        }
        scheduledFuture.cancel(true);
    }

    @Override
    public void accept(ConsumeInfo consumeInfo) {
        lock.lock();
        try {
            consumeBuffer.accept(consumeInfo);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int bufferedSize() {
        lock.lock();
        try {
            return consumeBuffer.bufferedSize();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getBufferSize() {
        lock.lock();
        try {
            return consumeBuffer.getBufferSize();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setBufferSize(int size) {
        lock.lock();
        try {
            consumeBuffer.setBufferSize(size);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getThread() {
        lock.lock();
        try {
            return thread;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setThread(int thread) {
        lock.lock();
        try {
            thread = Math.max(thread, 1);
            int delta = thread - this.thread;
            this.thread = thread;
            if (startFlag) {
                if (delta > 0) {
                    for (int i = 0; i < delta; i++) {
                        ConsumeTask consumeTask = ctx.getBean(ConsumeTask.class);
                        executor.execute(consumeTask);
                        processingConsumeTasks.add(consumeTask);
                    }
                } else if (delta < 0) {
                    endingConsumeTasks.removeIf(AbstractTask::isFinished);
                    for (int i = 0; i < -delta; i++) {
                        ConsumeTask consumeTask = processingConsumeTasks.remove(0);
                        consumeTask.shutdown();
                        endingConsumeTasks.add(consumeTask);
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isIdle() {
        lock.lock();
        try {
            if (consumeBuffer.bufferedSize() > 0) {
                return false;
            }
            if (!processingConsumeTasks.isEmpty()) {
                return false;
            }
            endingConsumeTasks.removeIf(AbstractTask::isFinished);
            return endingConsumeTasks.isEmpty();
        } finally {
            lock.unlock();
        }
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class ConsumeTask extends AbstractTask {

        private static final Logger LOGGER = LoggerFactory.getLogger(ConsumeTask.class);

        private final ConsumeBuffer consumeBuffer;
        private final ConsumeProcessor consumeProcessor;

        private final AtomicBoolean runningFlag = new AtomicBoolean(true);

        public ConsumeTask(ConsumeBuffer consumeBuffer, ConsumeProcessor consumeProcessor) {
            this.consumeBuffer = consumeBuffer;
            this.consumeProcessor = consumeProcessor;
        }

        @Override
        protected void todo() {
            while (runningFlag.get()) {
                ConsumeInfo consumeInfo = null;
                try {
                    consumeInfo = consumeBuffer.poll();
                    if (Objects.nonNull(consumeInfo)) {
                        consumeProcessor.consume(consumeInfo);
                    }
                } catch (Exception e) {
                    if (Objects.nonNull(consumeInfo)) {
                        LOGGER.warn("进行统计工作时发生异常, 抛弃 1 次统计", e);
                    }
                }
            }
            LOGGER.info("消费线程退出...");
        }

        public void shutdown() {
            runningFlag.set(false);
        }

        @Override
        public String toString() {
            return "ConsumeTask{" +
                    "consumeBuffer=" + consumeBuffer +
                    ", consumeProcessor=" + consumeProcessor +
                    ", runningFlag=" + runningFlag +
                    ", observers=" + observers +
                    ", lock=" + lock +
                    '}';
        }
    }

    @Component
    public static class ConsumeBuffer {

        private final Lock lock = new ReentrantLock();
        private final Condition provideCondition = lock.newCondition();
        private final Condition consumeCondition = lock.newCondition();
        private final List<ConsumeInfo> buffer = new ArrayList<>();

        private int bufferSize;
        private boolean blockEnabled = true;

        public void accept(ConsumeInfo consumeInfo) {
            lock.lock();
            try {
                while (buffer.size() >= bufferSize) {
                    try {
                        provideCondition.await();
                    } catch (InterruptedException ignored) {
                    }
                }

                buffer.add(consumeInfo);
                consumeCondition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public ConsumeInfo poll() {
            lock.lock();
            try {
                while (buffer.isEmpty() && blockEnabled) {
                    try {
                        consumeCondition.await();
                    } catch (InterruptedException ignored) {
                    }
                }

                // 取出缓冲器中的第一个元素。
                if (buffer.isEmpty()) {
                    return null;
                } else {
                    ConsumeInfo consumeInfo = buffer.remove(0);
                    provideCondition.signalAll();
                    return consumeInfo;
                }
            } finally {
                lock.unlock();
            }
        }

        public int bufferedSize() {
            lock.lock();
            try {
                return buffer.size();
            } finally {
                lock.unlock();
            }
        }

        public int getBufferSize() {
            lock.lock();
            try {
                return bufferSize;
            } finally {
                lock.unlock();
            }
        }

        public void setBufferSize(int bufferSize) {
            lock.lock();
            try {
                this.bufferSize = Math.max(1, bufferSize);
                provideCondition.signalAll();
                consumeCondition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public void block() {
            lock.lock();
            try {
                this.blockEnabled = true;
                this.provideCondition.signalAll();
                this.consumeCondition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public void unblock() {
            lock.lock();
            try {
                this.blockEnabled = false;
                this.provideCondition.signalAll();
                this.consumeCondition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        @Override
        public String toString() {
            return "Buffer{" +
                    "lock=" + lock +
                    ", provideCondition=" + provideCondition +
                    ", consumeCondition=" + consumeCondition +
                    ", buffer=" + buffer +
                    ", bufferSize=" + bufferSize +
                    ", blockEnabled=" + blockEnabled +
                    '}';
        }
    }

    @Component
    public static class ConsumeProcessor {

        private final ExecuteHandler executeHandler;

        public ConsumeProcessor(ExecuteHandler executeHandler) {
            this.executeHandler = executeHandler;
        }

        public void consume(ConsumeInfo consumeInfo) throws Exception {
            // 展开参数。
            LongIdKey statisticsSettingKey = consumeInfo.getStatisticsSettingKey();

            // 定义并开启计时器。
            TimeMeasurer tm = new TimeMeasurer();
            tm.start();

            // 创建任务。
            TaskCreateInfo taskCreateInfo = new TaskCreateInfo(statisticsSettingKey);
            TaskCreateResult taskCreateResult = executeHandler.createTask(taskCreateInfo);
            LongIdKey taskKey = taskCreateResult.getTaskKey();

            // 日志记录。
            LOGGER.debug("创建任务, 统计设置主键为 {}, 任务主键为 {}", statisticsSettingKey, taskKey);

            // 执行任务。
            TaskExecuteInfo taskExecuteInfo = new TaskExecuteInfo(taskKey);
            executeHandler.executeTask(taskExecuteInfo);

            // 停止计时器。
            tm.stop();

            // 输出日志。
            LOGGER.debug("消费者完成消费, 统计设置主键为 {}, 用时 {} 毫秒", statisticsSettingKey, tm.getTimeMs());
        }

        @Override
        public String toString() {
            return "ConsumeProcessor{" +
                    "executeHandler=" + executeHandler +
                    '}';
        }
    }
}
