package com.dwarfeng.statistics.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.stack.bean.entity.StatisticsSetting;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * JSFixed FastJson 统计设置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonStatisticsSetting implements Bean {

    private static final long serialVersionUID = 7750851857627378995L;

    public static JSFixedFastJsonStatisticsSetting of(StatisticsSetting statisticsSetting) {
        if (Objects.isNull(statisticsSetting)) {
            return null;
        } else {
            return new JSFixedFastJsonStatisticsSetting(
                    JSFixedFastJsonLongIdKey.of(statisticsSetting.getKey()),
                    statisticsSetting.isEnabled(),
                    statisticsSetting.getName(),
                    statisticsSetting.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "enabled", ordinal = 2)
    private boolean enabled;

    @JSONField(name = "name", ordinal = 3)
    private String name;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    public JSFixedFastJsonStatisticsSetting() {
    }

    public JSFixedFastJsonStatisticsSetting(JSFixedFastJsonLongIdKey key, boolean enabled, String name, String remark) {
        this.key = key;
        this.enabled = enabled;
        this.name = name;
        this.remark = remark;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonStatisticsSetting{" +
                "key=" + key +
                ", enabled=" + enabled +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
