package com.dwarfeng.statistics.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.stack.bean.key.VariableKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * FastJson 变量键。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonVariableKey implements Key {

    private static final long serialVersionUID = -1441588608803648980L;

    public static FastJsonVariableKey of(VariableKey variableKey) {
        if (Objects.isNull(variableKey)) {
            return null;
        } else {
            return new FastJsonVariableKey(
                    variableKey.getStatisticsSettingLongId(),
                    variableKey.getVariableStringId()
            );
        }
    }

    @JSONField(name = "statistics_setting_long_id", ordinal = 1)
    private Long statisticsSettingLongId;

    @JSONField(name = "variable_string_id", ordinal = 2)
    private String variableStringId;

    public FastJsonVariableKey() {
    }

    public FastJsonVariableKey(Long statisticsSettingLongId, String variableStringId) {
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

        FastJsonVariableKey that = (FastJsonVariableKey) o;
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
        return "FastJsonVariableKey{" +
                "statisticsSettingLongId=" + statisticsSettingLongId +
                ", variableStringId='" + variableStringId + '\'' +
                '}';
    }
}
