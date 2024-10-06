package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.sdk.util.Constants;
import com.dwarfeng.statistics.stack.bean.dto.VariableInspectInfo;
import com.dwarfeng.statistics.stack.bean.dto.VariableInspectResult;
import com.dwarfeng.statistics.stack.bean.dto.VariableRemoveInfo;
import com.dwarfeng.statistics.stack.bean.dto.VariableUpsertInfo;
import com.dwarfeng.statistics.stack.bean.entity.Variable;
import com.dwarfeng.statistics.stack.bean.key.VariableKey;
import com.dwarfeng.statistics.stack.handler.VariableOperateHandler;
import com.dwarfeng.statistics.stack.service.VariableMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class VariableOperateHandlerImpl implements VariableOperateHandler {

    private final VariableMaintainService variableMaintainService;

    private final HandlerValidator handlerValidator;

    public VariableOperateHandlerImpl(
            VariableMaintainService variableMaintainService,
            HandlerValidator handlerValidator
    ) {
        this.variableMaintainService = variableMaintainService;
        this.handlerValidator = handlerValidator;
    }

    @Override
    public VariableInspectResult inspect(VariableInspectInfo info) throws HandlerException {
        try {
            // 展开参数。
            LongIdKey statisticsSettingKey = info.getStatisticsSettingKey();
            String variableId = info.getVariableId();

            // 确认统计设置存在。
            handlerValidator.makeSureStatisticsSettingExists(statisticsSettingKey);

            // 调用维护服务获取变量。
            VariableKey variableKey = new VariableKey(statisticsSettingKey.getLongId(), variableId);
            Variable variable = variableMaintainService.getIfExists(variableKey);

            // 如果变量不存在，则返回 null。
            if (Objects.isNull(variable)) {
                return null;
            }

            // 构建返回值并返回。
            int valueType = variable.getValueType();
            Object value;
            switch (valueType) {
                case Constants.VARIABLE_VALUE_TYPE_STRING:
                    value = variable.getStringValue();
                    break;
                case Constants.VARIABLE_VALUE_TYPE_LONG:
                    value = variable.getLongValue();
                    break;
                case Constants.VARIABLE_VALUE_TYPE_DOUBLE:
                    value = variable.getDoubleValue();
                    break;
                case Constants.VARIABLE_VALUE_TYPE_BOOLEAN:
                    value = variable.getBooleanValue();
                    break;
                case Constants.VARIABLE_VALUE_TYPE_DATE:
                    value = variable.getDateValue();
                    break;
                default:
                    throw new IllegalStateException("非法的 valueType 值: " + valueType);
            }
            return new VariableInspectResult(valueType, value);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public void upsert(VariableUpsertInfo info) throws HandlerException {
        try {
            // 展开参数。
            LongIdKey statisticsSettingKey = info.getStatisticsSettingKey();
            String variableId = info.getVariableId();
            int valueType = info.getValueType();
            Object value = info.getValue();

            // 确认统计设置存在。
            handlerValidator.makeSureStatisticsSettingExists(statisticsSettingKey);
            // 确认变量类型有效。
            handlerValidator.makeSureVariableTypeValid(valueType, value);

            // 构建变量。
            Variable variable = new Variable(
                    new VariableKey(statisticsSettingKey.getLongId(), variableId),
                    valueType, null, null, null, null, null
            );
            if (Objects.nonNull(value)) {
                // 由于在校验过程中已经确认了 valueType 的有效性，因此此处不再进行判断。
                switch (valueType) {
                    case Constants.VARIABLE_VALUE_TYPE_STRING:
                        variable.setStringValue((String) value);
                        break;
                    case Constants.VARIABLE_VALUE_TYPE_LONG:
                        variable.setLongValue((Long) value);
                        break;
                    case Constants.VARIABLE_VALUE_TYPE_DOUBLE:
                        variable.setDoubleValue((Double) value);
                        break;
                    case Constants.VARIABLE_VALUE_TYPE_BOOLEAN:
                        variable.setBooleanValue((Boolean) value);
                        break;
                    case Constants.VARIABLE_VALUE_TYPE_DATE:
                        variable.setDateValue((Date) value);
                        break;
                    default:
                        throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
                }
            }

            // 调用维护服务插入/更新变量。
            variableMaintainService.insertOrUpdate(variable);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public void remove(VariableRemoveInfo info) throws HandlerException {
        try {
            // 展开参数。
            LongIdKey statisticsSettingKey = info.getStatisticsSettingKey();
            String variableId = info.getVariableId();

            // 确认统计设置存在。
            handlerValidator.makeSureStatisticsSettingExists(statisticsSettingKey);
            // 确认变量存在。
            VariableKey variableKey = new VariableKey(statisticsSettingKey.getLongId(), variableId);
            handlerValidator.makeSureVariableExists(variableKey);

            // 调用维护服务删除变量。
            variableMaintainService.delete(variableKey);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }
}
