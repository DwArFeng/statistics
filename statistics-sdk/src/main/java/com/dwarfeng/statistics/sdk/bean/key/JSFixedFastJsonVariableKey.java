package com.dwarfeng.statistics.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.statistics.stack.bean.key.VariableKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * JSFixed FastJson 变量键。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonVariableKey implements Key {

    private static final long serialVersionUID = -4191864701290181012L;

    public static JSFixedFastJsonVariableKey of(VariableKey variableKey) {
        if (Objects.isNull(variableKey)) {
            return null;
        } else {
            return new JSFixedFastJsonVariableKey(
                    variableKey.getStatisticsSettingLongId(),
                    variableKey.getVariableStringId()
            );
        }
    }

    @JSONField(name = "statistics_setting_long_id", ordinal = 1, serializeUsing = ToStringSerializer.class)
    private Long statisticsSettingLongId;

    @JSONField(name = "variable_string_id", ordinal = 2)
    private String variableStringId;

    public JSFixedFastJsonVariableKey() {
    }

    public JSFixedFastJsonVariableKey(Long statisticsSettingLongId, String variableStringId) {
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

        JSFixedFastJsonVariableKey that = (JSFixedFastJsonVariableKey) o;
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
        return "JSFixedFastJsonVariableKey{" +
                "statisticsSettingLongId=" + statisticsSettingLongId +
                ", variableStringId='" + variableStringId + '\'' +
                '}';
    }
}
