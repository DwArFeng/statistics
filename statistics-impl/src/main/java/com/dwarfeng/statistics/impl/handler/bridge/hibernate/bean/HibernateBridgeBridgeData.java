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

    private static final long serialVersionUID = 1013102787655189235L;

    private LongIdKey key;
    private LongIdKey statisticsSettingKey;
    private String value;
    private Date happenedDate;

    public HibernateBridgeBridgeData() {
    }

    public HibernateBridgeBridgeData(LongIdKey key, LongIdKey statisticsSettingKey, String value, Date happenedDate) {
        this.key = key;
        this.statisticsSettingKey = statisticsSettingKey;
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

    public LongIdKey getStatisticsSettingKey() {
        return statisticsSettingKey;
    }

    public void setStatisticsSettingKey(LongIdKey statisticsSettingKey) {
        this.statisticsSettingKey = statisticsSettingKey;
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
        return "HibernateBridgeNormalData{" +
                "key=" + key +
                ", statisticsSettingKey=" + statisticsSettingKey +
                ", value='" + value + '\'' +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
