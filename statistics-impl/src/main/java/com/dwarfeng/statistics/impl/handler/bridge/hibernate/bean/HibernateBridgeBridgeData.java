package com.dwarfeng.statistics.impl.handler.bridge.hibernate.bean;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * Hibernate 桥接数据。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class HibernateBridgeBridgeData implements Entity<LongIdKey> {

    private static final long serialVersionUID = -6060752430501776506L;

    private LongIdKey key;
    private Long statisticsSettingLongId;
    private String tag;
    private String value;
    private Date happenedDate;

    public HibernateBridgeBridgeData() {
    }

    public HibernateBridgeBridgeData(
            LongIdKey key, Long statisticsSettingLongId, String tag, String value, Date happenedDate
    ) {
        this.key = key;
        this.statisticsSettingLongId = statisticsSettingLongId;
        this.tag = tag;
        this.value = value;
        this.happenedDate = happenedDate;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public Long getStatisticsSettingLongId() {
        return statisticsSettingLongId;
    }

    public void setStatisticsSettingLongId(Long statisticsSettingLongId) {
        this.statisticsSettingLongId = statisticsSettingLongId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    @Override
    public String toString() {
        return "HibernateBridgeBridgeData{" +
                "key=" + key +
                ", statisticsSettingLongId=" + statisticsSettingLongId +
                ", tag='" + tag + '\'' +
                ", value='" + value + '\'' +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
