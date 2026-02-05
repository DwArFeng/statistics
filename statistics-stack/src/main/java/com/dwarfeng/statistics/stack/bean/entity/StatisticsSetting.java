package com.dwarfeng.statistics.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 统计设置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class StatisticsSetting implements Entity<LongIdKey> {

    private static final long serialVersionUID = 4570130463402998013L;

    private LongIdKey key;
    private boolean enabled;
    private String name;
    private String description;
    private String remark;

    public StatisticsSetting() {
    }

    public StatisticsSetting(LongIdKey key, boolean enabled, String name, String description, String remark) {
        this.key = key;
        this.enabled = enabled;
        this.name = name;
        this.description = description;
        this.remark = remark;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
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
        return "StatisticsSetting{" +
                "key=" + key +
                ", enabled=" + enabled +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
