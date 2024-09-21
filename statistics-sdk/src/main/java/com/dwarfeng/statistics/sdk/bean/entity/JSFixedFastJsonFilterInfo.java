package com.dwarfeng.statistics.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.stack.bean.entity.FilterInfo;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * JSFixed FastJson 过滤器信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonFilterInfo implements Bean {

    private static final long serialVersionUID = 2868310839351336272L;

    public static JSFixedFastJsonFilterInfo of(FilterInfo filterInfo) {
        if (Objects.isNull(filterInfo)) {
            return null;
        } else {
            return new JSFixedFastJsonFilterInfo(
                    JSFixedFastJsonLongIdKey.of(filterInfo.getKey()),
                    JSFixedFastJsonLongIdKey.of(filterInfo.getStatisticsSettingKey()),
                    filterInfo.isEnabled(),
                    filterInfo.getType(),
                    filterInfo.getParam(),
                    filterInfo.getRemark(),
                    filterInfo.getIndex()
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

    @JSONField(name = "index", ordinal = 7)
    private int index;

    public JSFixedFastJsonFilterInfo() {
    }

    public JSFixedFastJsonFilterInfo(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey statisticsSettingKey, boolean enabled, String type,
            String param, String remark, int index
    ) {
        this.key = key;
        this.statisticsSettingKey = statisticsSettingKey;
        this.enabled = enabled;
        this.type = type;
        this.param = param;
        this.remark = remark;
        this.index = index;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonFilterInfo{" +
                "key=" + key +
                ", statisticsSettingKey=" + statisticsSettingKey +
                ", enabled=" + enabled +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                ", index=" + index +
                '}';
    }
}
