package com.dwarfeng.statistics.impl.handler.bridge.redis.bean;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;

import java.util.Date;

/**
 * Redis 桥接器桥接数据。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class RedisBridgeBridgeData implements Entity<RedisBridgeBridgeDataKey> {

    private static final long serialVersionUID = -8765287830542819017L;

    private RedisBridgeBridgeDataKey key;
    private String value;
    private Date happenedDate;

    public RedisBridgeBridgeData() {
    }

    public RedisBridgeBridgeData(RedisBridgeBridgeDataKey key, String value, Date happenedDate) {
        this.key = key;
        this.value = value;
        this.happenedDate = happenedDate;
    }

    @Override
    public RedisBridgeBridgeDataKey getKey() {
        return key;
    }

    @Override
    public void setKey(RedisBridgeBridgeDataKey key) {
        this.key = key;
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
        return "RedisBridgeBridgeData{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
