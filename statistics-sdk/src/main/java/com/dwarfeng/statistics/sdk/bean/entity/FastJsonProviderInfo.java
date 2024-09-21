package com.dwarfeng.statistics.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.stack.bean.entity.ProviderInfo;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 提供器信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonProviderInfo implements Bean {

    private static final long serialVersionUID = -4885097185030755539L;

    public static FastJsonProviderInfo of(ProviderInfo providerInfo) {
        if (Objects.isNull(providerInfo)) {
            return null;
        } else {
            return new FastJsonProviderInfo(
                    FastJsonLongIdKey.of(providerInfo.getKey()),
                    FastJsonLongIdKey.of(providerInfo.getStatisticsSettingKey()),
                    providerInfo.isEnabled(),
                    providerInfo.getType(),
                    providerInfo.getParam(),
                    providerInfo.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "statistics_setting_key", ordinal = 2)
    private FastJsonLongIdKey statisticsSettingKey;

    @JSONField(name = "enabled", ordinal = 3)
    private boolean enabled;

    @JSONField(name = "type", ordinal = 4)
    private String type;

    @JSONField(name = "param", ordinal = 5)
    private String param;

    @JSONField(name = "remark", ordinal = 6)
    private String remark;

    public FastJsonProviderInfo() {
    }

    public FastJsonProviderInfo(
            FastJsonLongIdKey key, FastJsonLongIdKey statisticsSettingKey, boolean enabled, String type, String param,
            String remark
    ) {
        this.key = key;
        this.statisticsSettingKey = statisticsSettingKey;
        this.enabled = enabled;
        this.type = type;
        this.param = param;
        this.remark = remark;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
        this.key = key;
    }

    public FastJsonLongIdKey getStatisticsSettingKey() {
        return statisticsSettingKey;
    }

    public void setStatisticsSettingKey(FastJsonLongIdKey statisticsSettingKey) {
        this.statisticsSettingKey = statisticsSettingKey;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "FastJsonProviderInfo{" +
                "key=" + key +
                ", statisticsSettingKey=" + statisticsSettingKey +
                ", enabled=" + enabled +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
