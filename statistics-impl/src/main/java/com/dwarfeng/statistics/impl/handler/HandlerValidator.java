package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.dutil.basic.cls.ClassUtil;
import com.dwarfeng.statistics.sdk.util.Constants;
import com.dwarfeng.statistics.stack.bean.entity.Task;
import com.dwarfeng.statistics.stack.bean.key.VariableKey;
import com.dwarfeng.statistics.stack.exception.*;
import com.dwarfeng.statistics.stack.service.StatisticsSettingMaintainService;
import com.dwarfeng.statistics.stack.service.TaskMaintainService;
import com.dwarfeng.statistics.stack.service.VariableMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.stereotype.Component;

import java.util.Date;
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
    private final VariableMaintainService variableMaintainService;

    public HandlerValidator(
            StatisticsSettingMaintainService statisticsSettingMaintainService,
            TaskMaintainService taskMaintainService,
            VariableMaintainService variableMaintainService
    ) {
        this.statisticsSettingMaintainService = statisticsSettingMaintainService;
        this.taskMaintainService = taskMaintainService;
        this.variableMaintainService = variableMaintainService;
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

    public void makeSureVariableExists(VariableKey variableKey) throws HandlerException {
        try {
            if (!variableMaintainService.exists(variableKey)) {
                throw new VariableNotExistsException(variableKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureVariableTypeValid(int valueType, Object value) throws HandlerException {
        if (!Constants.variableValueTypeSpace().contains(valueType)) {
            throw new InvalidVariableValueTypeException(valueType);
        }
        if (Objects.isNull(value)) {
            return;
        }
        Class<?> expectedValueClazz;
        // 由于在上文中已经验证了 valueType 的合法性，因此可以保证 valueType 的合法性。
        switch (valueType) {
            case Constants.VARIABLE_VALUE_TYPE_STRING:
                expectedValueClazz = String.class;
                break;
            case Constants.VARIABLE_VALUE_TYPE_LONG:
                expectedValueClazz = Long.class;
                break;
            case Constants.VARIABLE_VALUE_TYPE_DOUBLE:
                expectedValueClazz = Double.class;
                break;
            case Constants.VARIABLE_VALUE_TYPE_BOOLEAN:
                expectedValueClazz = Boolean.class;
                break;
            case Constants.VARIABLE_VALUE_TYPE_DATE:
                expectedValueClazz = Date.class;
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }
        // 获取 value 的实际类型，特别地，如果 actualValueClazz 是基本类型，则转换为对应的包装类型。
        Class<?> actualValueClazz = ClassUtil.getPackedClass(value.getClass());
        // 如果 actualValueClazz 不是 expectedValueClazz 或 expectedValueClazz 的子类，则抛出异常。
        if (!expectedValueClazz.isAssignableFrom(actualValueClazz)) {
            throw new VariableValueTypeMismatchException(valueType, expectedValueClazz, actualValueClazz);
        }
    }
}
