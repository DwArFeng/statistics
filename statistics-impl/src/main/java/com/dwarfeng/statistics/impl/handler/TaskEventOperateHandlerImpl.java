package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.sdk.util.Constants;
import com.dwarfeng.statistics.stack.bean.dto.TaskEventCreateInfo;
import com.dwarfeng.statistics.stack.bean.dto.TaskEventCreateResult;
import com.dwarfeng.statistics.stack.bean.entity.TaskEvent;
import com.dwarfeng.statistics.stack.handler.TaskEventOperateHandler;
import com.dwarfeng.statistics.stack.service.TaskEventMaintainService;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.generation.KeyGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class TaskEventOperateHandlerImpl implements TaskEventOperateHandler {

    private static final Set<Integer> VALID_TASK_STATUS_SET_CREATE;

    static {
        Set<Integer> VALID_TASK_STATUS_SET_CREATE_DEJA_VU = new HashSet<>();
        VALID_TASK_STATUS_SET_CREATE_DEJA_VU.add(Constants.TASK_STATUS_PROCESSING);
        VALID_TASK_STATUS_SET_CREATE = Collections.unmodifiableSet(VALID_TASK_STATUS_SET_CREATE_DEJA_VU);
    }

    private final TaskEventMaintainService taskEventMaintainService;

    private final KeyGenerator<LongIdKey> keyGenerator;

    private final HandlerValidator handlerValidator;

    public TaskEventOperateHandlerImpl(
            TaskEventMaintainService taskEventMaintainService,
            @Qualifier("snowflakeLongIdKeyGenerator") KeyGenerator<LongIdKey> keyGenerator,
            HandlerValidator handlerValidator
    ) {
        this.taskEventMaintainService = taskEventMaintainService;
        this.keyGenerator = keyGenerator;
        this.handlerValidator = handlerValidator;
    }

    @Override
    public TaskEventCreateResult create(TaskEventCreateInfo info) throws HandlerException {
        try {
            // 展开参数。
            LongIdKey taskKey = info.getTaskKey();
            String message = info.getMessage();

            // 确认任务存在。
            handlerValidator.makeSureTaskExists(taskKey);
            // 确认任务状态合法。
            handlerValidator.makeSureTaskStatusValid(taskKey, VALID_TASK_STATUS_SET_CREATE);

            // 创建任务事件。
            LongIdKey taskEventKey = keyGenerator.generate();
            TaskEvent taskEvent = new TaskEvent(
                    taskEventKey, taskKey, new Date(), message, "通过 statistics 操作处理器创建"
            );

            // 调用维护服务创建任务事件。
            taskEventMaintainService.insert(taskEvent);

            // 生成结果对象并返回。
            return new TaskEventCreateResult(taskEventKey);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }
}
