package com.dwarfeng.statistics.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 变量插入/更新信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class VariableUpsertInfo implements Dto {

    private static final long serialVersionUID = -3668700763076564561L;
    
    private LongIdKey statisticsSettingKey;
    private String variableId;
    private int valueType;
    private Object value;

    public VariableUpsertInfo() {
    }

    public VariableUpsertInfo(LongIdKey statisticsSettingKey, String variableId, int valueType, Object value) {
        this.statisticsSettingKey = statisticsSettingKey;
        this.variableId = variableId;
        this.valueType = valueType;
        this.value = value;
    }

    public LongIdKey getStatisticsSettingKey() {
        return statisticsSettingKey;
    }

    public void setStatisticsSettingKey(LongIdKey statisticsSettingKey) {
        this.statisticsSettingKey = statisticsSettingKey;
    }

    public String getVariableId() {
        return variableId;
    }

    public void setVariableId(String variableId) {
        this.variableId = variableId;
    }

    public int getValueType() {
        return valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "VariableUpsertInfo{" +
                "statisticsSettingKey=" + statisticsSettingKey +
                ", variableId='" + variableId + '\'' +
                ", valueType=" + valueType +
                ", value=" + value +
                '}';
    }
}
