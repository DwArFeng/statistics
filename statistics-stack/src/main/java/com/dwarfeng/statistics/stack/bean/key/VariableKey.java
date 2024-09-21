package com.dwarfeng.statistics.stack.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * 变量键。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class VariableKey implements Key {

    private static final long serialVersionUID = -7589978228177738106L;

    private Long statisticsSettingLongId;
    private String variableStringId;

    public VariableKey() {
    }

    public VariableKey(Long statisticsSettingLongId, String variableStringId) {
        this.statisticsSettingLongId = statisticsSettingLongId;
        this.variableStringId = variableStringId;
    }

    public Long getStatisticsSettingLongId() {
        return statisticsSettingLongId;
    }

    public void setStatisticsSettingLongId(Long statisticsSettingLongId) {
        this.statisticsSettingLongId = statisticsSettingLongId;
    }

    public String getVariableStringId() {
        return variableStringId;
    }

    public void setVariableStringId(String variableStringId) {
        this.variableStringId = variableStringId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VariableKey that = (VariableKey) o;
        return Objects.equals(statisticsSettingLongId, that.statisticsSettingLongId) &&
                Objects.equals(variableStringId, that.variableStringId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(statisticsSettingLongId);
        result = 31 * result + Objects.hashCode(variableStringId);
        return result;
    }

    @Override
    public String toString() {
        return "VariableKey{" +
                "statisticsSettingLongId=" + statisticsSettingLongId +
                ", variableStringId='" + variableStringId + '\'' +
                '}';
    }
}
