package com.dwarfeng.statistics.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.stack.bean.entity.StatisticsSetting;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 统计设置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonStatisticsSetting implements Bean {

    private static final long serialVersionUID = -1396483905066726218L;

    public static FastJsonStatisticsSetting of(StatisticsSetting statisticsSetting) {
        if (Objects.isNull(statisticsSetting)) {
            return null;
        } else {
            return new FastJsonStatisticsSetting(
                    FastJsonLongIdKey.of(statisticsSetting.getKey()),
                    statisticsSetting.isEnabled(),
                    statisticsSetting.getName(),
                    statisticsSetting.getDescription(),
                    statisticsSetting.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "enabled", ordinal = 2)
    private boolean enabled;

    @JSONField(name = "name", ordinal = 3)
    private String name;

    @JSONField(name = "description", ordinal = 4)
    private String description;

    @JSONField(name = "remark", ordinal = 5)
    private String remark;

    public FastJsonStatisticsSetting() {
    }

    public FastJsonStatisticsSetting(FastJsonLongIdKey key, boolean enabled, String name, String description, String remark) {
        this.key = key;
        this.enabled = enabled;
        this.name = name;
        this.description = description;
        this.remark = remark;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "FastJsonStatisticsSetting{" +
                "key=" + key +
                ", enabled=" + enabled +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
