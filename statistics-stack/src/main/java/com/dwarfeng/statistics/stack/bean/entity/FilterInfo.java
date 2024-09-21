package com.dwarfeng.statistics.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 过滤器信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FilterInfo implements Entity<LongIdKey> {

    private static final long serialVersionUID = -3055597746420488816L;

    private LongIdKey key;
    private LongIdKey statisticsSettingKey;
    private boolean enabled;
    private String type;
    private String param;
    private String remark;

    /**
     * 索引。
     *
     * <p>
     * 该值用于表示过滤器在统计设置对应的过滤器链中的位置。
     */
    private int index;

    public FilterInfo() {
    }

    public FilterInfo(
            LongIdKey key, LongIdKey statisticsSettingKey, boolean enabled, String type, String param, String remark,
            int index
    ) {
        this.key = key;
        this.statisticsSettingKey = statisticsSettingKey;
        this.enabled = enabled;
        this.type = type;
        this.param = param;
        this.remark = remark;
        this.index = index;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getStatisticsSettingKey() {
        return statisticsSettingKey;
    }

    public void setStatisticsSettingKey(LongIdKey statisticsSettingKey) {
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
        return "FilterInfo{" +
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
