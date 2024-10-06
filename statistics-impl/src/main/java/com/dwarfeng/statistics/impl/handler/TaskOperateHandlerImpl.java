package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.sdk.util.Constants;
import com.dwarfeng.statistics.stack.bean.dto.*;
import com.dwarfeng.statistics.stack.bean.entity.HistoryTask;
import com.dwarfeng.statistics.stack.bean.entity.HistoryTaskEvent;
import com.dwarfeng.statistics.stack.bean.entity.Task;
import com.dwarfeng.statistics.stack.bean.entity.TaskEvent;
import com.dwarfeng.statistics.stack.handler.TaskOperateHandler;
import com.dwarfeng.statistics.stack.service.HistoryTaskEventMaintainService;
import com.dwarfeng.statistics.stack.service.HistoryTaskMaintainService;
import com.dwarfeng.statistics.stack.service.TaskEventMaintainService;
import com.dwarfeng.statistics.stack.service.TaskMaintainService;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.generation.KeyGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class TaskOperateHandlerImpl implements TaskOperateHandler {

    private static final Set<Integer> VALID_TASK_STATUS_SET_START;
    private static final Set<Integer> VALID_TASK_STATUS_SET_FINISH;
    private static final Set<Integer> VALID_TASK_STATUS_SET_FAIL;
    private static final Set<Integer> VALID_TASK_STATUS_SET_EXPIRE;
    private static final Set<Integer> VALID_TASK_STATUS_SET_DEAD;
    private static final Set<Integer> VALID_TASK_STATUS_SET_UPDATE_MODAL;
    private static final Set<Integer> VALID_TASK_STATUS_SET_BEAT;

    static {
        Set<Integer> VALID_TASK_STATUS_SET_START_DEJA_VU = new HashSet<>();
        VALID_TASK_STATUS_SET_START_DEJA_VU.add(Constants.TASK_STATUS_CREATED);
        VALID_TASK_STATUS_SET_START = Collections.unmodifiableSet(VALID_TASK_STATUS_SET_START_DEJA_VU);

        Set<Integer> VALID_TASK_STATUS_SET_FINISH_DEJA_VU = new HashSet<>();
        VALID_TASK_STATUS_SET_FINISH_DEJA_VU.add(Constants.TASK_STATUS_CREATED);
        VALID_TASK_STATUS_SET_FINISH_DEJA_VU.add(Constants.TASK_STATUS_PROCESSING);
        VALID_TASK_STATUS_SET_FINISH = Collections.unmodifiableSet(VALID_TASK_STATUS_SET_FINISH_DEJA_VU);

        Set<Integer> VALID_TASK_STATUS_SET_FAIL_DEJA_VU = new HashSet<>();
        VALID_TASK_STATUS_SET_FAIL_DEJA_VU.add(Constants.TASK_STATUS_CREATED);
        VALID_TASK_STATUS_SET_FAIL_DEJA_VU.add(Constants.TASK_STATUS_PROCESSING);
        VALID_TASK_STATUS_SET_FAIL = Collections.unmodifiableSet(VALID_TASK_STATUS_SET_FAIL_DEJA_VU);

        Set<Integer> VALID_TASK_STATUS_SET_EXPIRE_DEJA_VU = new HashSet<>();
        VALID_TASK_STATUS_SET_EXPIRE_DEJA_VU.add(Constants.TASK_STATUS_CREATED);
        VALID_TASK_STATUS_SET_EXPIRE_DEJA_VU.add(Constants.TASK_STATUS_PROCESSING);
        VALID_TASK_STATUS_SET_EXPIRE = Collections.unmodifiableSet(VALID_TASK_STATUS_SET_EXPIRE_DEJA_VU);

        Set<Integer> VALID_TASK_STATUS_SET_DEAD_DEJA_VU = new HashSet<>();
        VALID_TASK_STATUS_SET_DEAD_DEJA_VU.add(Constants.TASK_STATUS_PROCESSING);
        VALID_TASK_STATUS_SET_DEAD = Collections.unmodifiableSet(VALID_TASK_STATUS_SET_DEAD_DEJA_VU);

        Set<Integer> VALID_TASK_STATUS_SET_UPDATE_MODAL_DEJA_VU = new HashSet<>();
        VALID_TASK_STATUS_SET_UPDATE_MODAL_DEJA_VU.add(Constants.TASK_STATUS_PROCESSING);
        VALID_TASK_STATUS_SET_UPDATE_MODAL = Collections.unmodifiableSet(VALID_TASK_STATUS_SET_UPDATE_MODAL_DEJA_VU);

        Set<Integer> VALID_TASK_STATUS_SET_BEAT_DEJA_VU = new HashSet<>();
        VALID_TASK_STATUS_SET_BEAT_DEJA_VU.add(Constants.TASK_STATUS_PROCESSING);
        VALID_TASK_STATUS_SET_BEAT = Collections.unmodifiableSet(VALID_TASK_STATUS_SET_BEAT_DEJA_VU);
    }

    private final TaskMaintainService taskMaintainService;
    private final HistoryTaskMaintainService historyTaskMaintainService;
    private final TaskEventMaintainService taskEventMaintainService;
    private final HistoryTaskEventMaintainService historyTaskEventMaintainService;

    private final KeyGenerator<LongIdKey> keyGenerator;

    private final HandlerValidator handlerValidator;

    @Value("${task.node_id}")
    private int nodeId;
    @Value("${task.expire_timeout}")
    private long expireTimeout;
    @Value("${task.die_timeout}")
    private long dieTimeout;

    public TaskOperateHandlerImpl(
            TaskMaintainService taskMaintainService,
            HistoryTaskMaintainService historyTaskMaintainService,
            TaskEventMaintainService taskEventMaintainService,
            HistoryTaskEventMaintainService historyTaskEventMaintainService,
            @Qualifier("snowflakeLongIdKeyGenerator") KeyGenerator<LongIdKey> keyGenerator,
            HandlerValidator handlerValidator
    ) {
        this.taskMaintainService = taskMaintainService;
        this.historyTaskMaintainService = historyTaskMaintainService;
        this.taskEventMaintainService = taskEventMaintainService;
        this.historyTaskEventMaintainService = historyTaskEventMaintainService;
        this.keyGenerator = keyGenerator;
        this.handlerValidator = handlerValidator;
    }

    @Override
    public TaskCreateResult create(TaskCreateInfo createInfo) throws HandlerException {
        try {
            // 展开参数。
            LongIdKey statisticsSettingKey = createInfo.getStatisticsSettingKey();

            // 确认统计设置存在。
            handlerValidator.makeSureStatisticsSettingExists(statisticsSettingKey);

            // 创建任务实体。
            LongIdKey taskKey = keyGenerator.generate();
            Date currentDate = new Date();
            Task task = new Task(
                    taskKey,
                    statisticsSettingKey,
                    Constants.TASK_STATUS_CREATED,
                    currentDate,
                    null,
                    new Date(currentDate.getTime() + expireTimeout),
                    null,
                    nodeId,
                    null,
                    "通过 statistics 操作处理器创建"
            );

            // 调用维护服务插入任务实体。
            taskMaintainService.insert(task);

            // 生成结果并返回。
            return new TaskCreateResult(taskKey);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public void start(TaskStartInfo startInfo) throws HandlerException {
        try {
            // 展开参数。
            LongIdKey taskKey = startInfo.getTaskKey();

            // 确认任务存在。
            handlerValidator.makeSureTaskExists(taskKey);
            // 确认任务状态合法。
            handlerValidator.makeSureTaskStatusValid(taskKey, VALID_TASK_STATUS_SET_START);

            // 获取任务。
            Task task = taskMaintainService.get(taskKey);

            // 更新任务字段。
            Date currentDate = new Date();
            task.setStatus(Constants.TASK_STATUS_PROCESSING);
            task.setStartDate(currentDate);
            task.setShouldDieDate(new Date(currentDate.getTime() + dieTimeout));

            // 调用维护服务更新任务实体。
            taskMaintainService.update(task);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    // 为了代码的可读性，此处代码不做简化。
    @SuppressWarnings("ExtractMethodRecommender")
    @Override
    public void finish(TaskFinishInfo finishInfo) throws HandlerException {
        try {
            // 展开参数。
            LongIdKey taskKey = finishInfo.getTaskKey();

            // 确认任务存在。
            handlerValidator.makeSureTaskExists(taskKey);
            // 确认任务状态合法。
            handlerValidator.makeSureTaskStatusValid(taskKey, VALID_TASK_STATUS_SET_FINISH);

            // 获取任务。
            Task task = taskMaintainService.get(taskKey);

            // 根据任务生成历史任务。
            Date currentDate = new Date();
            HistoryTask historyTask = new HistoryTask(
                    task.getKey(),
                    task.getStatisticsSettingKey(),
                    Constants.TASK_STATUS_FINISHED,
                    task.getCreateDate(),
                    task.getStartDate(),
                    currentDate,
                    currentDate.getTime() - task.getStartDate().getTime(),
                    null,
                    null,
                    task.getExecutionNodeId(),
                    task.getFrontMessage(),
                    task.getRemark()
            );

            // 调用维护服务插入历史任务实体。
            historyTaskMaintainService.insert(historyTask);

            // 复制任务事件。
            copyTaskEvent(taskKey, historyTask.getKey());

            // 删除任务。
            taskMaintainService.delete(taskKey);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    // 为了代码的可读性，此处代码不做简化。
    @SuppressWarnings("ExtractMethodRecommender")
    @Override
    public void fail(TaskFailInfo failInfo) throws HandlerException {
        try {
            // 展开参数。
            LongIdKey taskKey = failInfo.getTaskKey();

            // 确认任务存在。
            handlerValidator.makeSureTaskExists(taskKey);
            // 确认任务状态合法。
            handlerValidator.makeSureTaskStatusValid(taskKey, VALID_TASK_STATUS_SET_FAIL);

            // 获取任务。
            Task task = taskMaintainService.get(taskKey);

            // 根据任务生成历史任务。
            Date currentDate = new Date();
            HistoryTask historyTask = new HistoryTask(
                    task.getKey(),
                    task.getStatisticsSettingKey(),
                    Constants.TASK_STATUS_FAILED,
                    task.getCreateDate(),
                    task.getStartDate(),
                    currentDate,
                    currentDate.getTime() - task.getStartDate().getTime(),
                    null,
                    null,
                    task.getExecutionNodeId(),
                    task.getFrontMessage(),
                    task.getRemark()
            );

            // 调用维护服务插入历史任务实体。
            historyTaskMaintainService.insert(historyTask);

            // 复制任务事件。
            copyTaskEvent(taskKey, historyTask.getKey());

            // 删除任务。
            taskMaintainService.delete(taskKey);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    // 为了代码的可读性，此处代码不做简化。
    @SuppressWarnings("ExtractMethodRecommender")
    @Override
    public void expire(TaskExpireInfo expireInfo) throws HandlerException {
        try {
            // 展开参数。
            LongIdKey taskKey = expireInfo.getTaskKey();

            // 确认任务存在。
            handlerValidator.makeSureTaskExists(taskKey);
            // 确认任务状态合法。
            handlerValidator.makeSureTaskStatusValid(taskKey, VALID_TASK_STATUS_SET_EXPIRE);

            // 获取任务。
            Task task = taskMaintainService.get(taskKey);

            // 根据任务生成历史任务。
            Date currentDate = new Date();
            HistoryTask historyTask = new HistoryTask(
                    task.getKey(),
                    task.getStatisticsSettingKey(),
                    Constants.TASK_STATUS_EXPIRED,
                    task.getCreateDate(),
                    task.getStartDate(),
                    currentDate,
                    currentDate.getTime() - task.getStartDate().getTime(),
                    currentDate,
                    null,
                    task.getExecutionNodeId(),
                    task.getFrontMessage(),
                    task.getRemark()
            );

            // 调用维护服务插入历史任务实体。
            historyTaskMaintainService.insert(historyTask);

            // 复制任务事件。
            copyTaskEvent(taskKey, historyTask.getKey());

            // 删除任务。
            taskMaintainService.delete(taskKey);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    // 为了代码的可读性，此处代码不做简化。
    @SuppressWarnings("ExtractMethodRecommender")
    @Override
    public void die(TaskDieInfo dieInfo) throws HandlerException {
        try {
            // 展开参数。
            LongIdKey taskKey = dieInfo.getTaskKey();

            // 确认任务存在。
            handlerValidator.makeSureTaskExists(taskKey);
            // 确认任务状态合法。
            handlerValidator.makeSureTaskStatusValid(taskKey, VALID_TASK_STATUS_SET_DEAD);

            // 获取任务。
            Task task = taskMaintainService.get(taskKey);

            // 根据任务生成历史任务。
            Date currentDate = new Date();
            HistoryTask historyTask = new HistoryTask(
                    task.getKey(),
                    task.getStatisticsSettingKey(),
                    Constants.TASK_STATUS_DIED,
                    task.getCreateDate(),
                    task.getStartDate(),
                    currentDate,
                    currentDate.getTime() - task.getStartDate().getTime(),
                    null,
                    currentDate,
                    task.getExecutionNodeId(),
                    task.getFrontMessage(),
                    task.getRemark()
            );

            // 调用维护服务插入历史任务实体。
            historyTaskMaintainService.insert(historyTask);

            // 复制任务事件。
            copyTaskEvent(taskKey, historyTask.getKey());

            // 删除任务。
            taskMaintainService.delete(taskKey);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void copyTaskEvent(LongIdKey taskKey, LongIdKey historyTaskKey) throws Exception {
        // 查询任务所属的所有事件。
        List<TaskEvent> taskEvents = taskEventMaintainService.lookupAsList(
                TaskEventMaintainService.CHILD_FOR_TASK, new Object[]{taskKey}
        );

        // 将所有的任务事件转换为历史任务事件。
        List<HistoryTaskEvent> historyTaskEvents = taskEvents.stream().map(
                f -> new HistoryTaskEvent(
                        f.getKey(),
                        historyTaskKey,
                        f.getHappenedDate(),
                        f.getMessage(),
                        f.getRemark()
                )
        ).collect(Collectors.toList());

        // 批量插入历史任务事件。
        historyTaskEventMaintainService.batchInsert(historyTaskEvents);
    }

    @Override
    public void updateModal(TaskUpdateModalInfo updateModalInfo) throws HandlerException {
        try {
            // 展开参数。
            LongIdKey taskKey = updateModalInfo.getTaskKey();
            String frontMessage = updateModalInfo.getFrontMessage();

            // 确认任务存在。
            handlerValidator.makeSureTaskExists(taskKey);
            // 确认任务状态合法。
            handlerValidator.makeSureTaskStatusValid(taskKey, VALID_TASK_STATUS_SET_UPDATE_MODAL);

            // 获取任务。
            Task task = taskMaintainService.get(taskKey);

            // 更新任务字段。
            task.setFrontMessage(frontMessage);

            // 调用维护服务更新任务实体。
            taskMaintainService.update(task);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public void beat(TaskBeatInfo beatInfo) throws HandlerException {
        try {
            // 展开参数。
            LongIdKey taskKey = beatInfo.getTaskKey();

            // 确认任务存在。
            handlerValidator.makeSureTaskExists(taskKey);
            // 确认任务状态合法。
            handlerValidator.makeSureTaskStatusValid(taskKey, VALID_TASK_STATUS_SET_BEAT);

            // 获取任务。
            Task task = taskMaintainService.get(taskKey);

            // 更新任务字段。
            Date currentDate = new Date();
            task.setShouldDieDate(new Date(currentDate.getTime() + dieTimeout));

            // 调用维护服务更新任务实体。
            taskMaintainService.update(task);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }
}
