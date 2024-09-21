package com.dwarfeng.statistics.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.stack.bean.entity.ProviderInfo;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * JSFixed FastJson 提供器信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonProviderInfo implements Bean {

    private static final long serialVersionUID = 5885996913900775594L;

    public static JSFixedFastJsonProviderInfo of(ProviderInfo providerInfo) {
        if (Objects.isNull(providerInfo)) {
            return null;
        } else {
            return new JSFixedFastJsonProviderInfo(
                    JSFixedFastJsonLongIdKey.of(providerInfo.getKey()),
                    JSFixedFastJsonLongIdKey.of(providerInfo.getStatisticsSettingKey()),
                    providerInfo.isEnabled(),
                    providerInfo.getType(),
                    providerInfo.getParam(),
                    providerInfo.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "statistics_setting_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey statisticsSettingKey;

    @JSONField(name = "enabled", ordinal = 3)
    private boolean enabled;

    @JSONField(name = "type", ordinal = 4)
    private String type;

    @JSONField(name = "param", ordinal = 5)
    private String param;

    @JSONField(name = "remark", ordinal = 6)
    private String remark;

    public JSFixedFastJsonProviderInfo() {
    }

    public JSFixedFastJsonProviderInfo(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey statisticsSettingKey, boolean enabled, String type,
            String param, String remark
    ) {
        this.key = key;
        this.statisticsSettingKey = statisticsSettingKey;
        this.enabled = enabled;
        this.type = type;
        this.param = param;
        this.remark = remark;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getStatisticsSettingKey() {
        return statisticsSettingKey;
    }

    public void setStatisticsSettingKey(JSFixedFastJsonLongIdKey statisticsSettingKey) {
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
        return "JSFixedFastJsonProviderInfo{" +
                "key=" + key +
                ", statisticsSettingKey=" + statisticsSettingKey +
                ", enabled=" + enabled +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
