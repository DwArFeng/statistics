package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.sdk.util.Constants;
import com.dwarfeng.statistics.stack.bean.entity.Task;
import com.dwarfeng.statistics.stack.exception.StatisticsSettingNotExistsException;
import com.dwarfeng.statistics.stack.exception.TaskNotExistsException;
import com.dwarfeng.statistics.stack.exception.TaskStatusMismatchException;
import com.dwarfeng.statistics.stack.service.StatisticsSettingMaintainService;
import com.dwarfeng.statistics.stack.service.TaskMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

/**
 * 处理器验证器。
 *
 * <p>
 * 为处理器提供公共的验证方法。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class HandlerValidator {

    private final StatisticsSettingMaintainService statisticsSettingMaintainService;
    private final TaskMaintainService taskMaintainService;

    public HandlerValidator(
            StatisticsSettingMaintainService statisticsSettingMaintainService,
            TaskMaintainService taskMaintainService
    ) {
        this.statisticsSettingMaintainService = statisticsSettingMaintainService;
        this.taskMaintainService = taskMaintainService;
    }

    public void makeSureStatisticsSettingExists(LongIdKey statisticsSettingKey) throws HandlerException {
        try {
            if (!statisticsSettingMaintainService.exists(statisticsSettingKey)) {
                throw new StatisticsSettingNotExistsException(statisticsSettingKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureTaskExists(LongIdKey taskKey) throws HandlerException {
        try {
            if (!taskMaintainService.exists(taskKey)) {
                throw new TaskNotExistsException(taskKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureTaskStatusValid(LongIdKey taskKey, Set<Integer> validStatusSet)
            throws HandlerException {
        try {
            Task task = taskMaintainService.getIfExists(taskKey);
            if (Objects.isNull(task)) {
                throw new TaskNotExistsException(taskKey);
            }
            int status = task.getStatus();
            if (!Constants.taskStatusSpace().contains(status)) {
                throw new TaskStatusMismatchException(validStatusSet, status);
            }
            if (!validStatusSet.contains(status)) {
                throw new TaskStatusMismatchException(validStatusSet, status);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }
}
