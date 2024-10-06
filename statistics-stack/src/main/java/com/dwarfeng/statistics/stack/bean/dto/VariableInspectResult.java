package com.dwarfeng.statistics.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 变量查看结果。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class VariableInspectResult implements Dto {

    private static final long serialVersionUID = -8287080732301709609L;

    private int valueType;
    private Object value;

    public VariableInspectResult() {
    }

    public VariableInspectResult(int valueType, Object value) {
        this.valueType = valueType;
        this.value = value;
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
        return "VariableInspectResult{" +
                "valueType=" + valueType +
                ", value=" + value +
                '}';
    }
}
