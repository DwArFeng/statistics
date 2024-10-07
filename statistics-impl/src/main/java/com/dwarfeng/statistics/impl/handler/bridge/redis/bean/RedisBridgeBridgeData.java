package com.dwarfeng.statistics.impl.handler.bridge.redis.bean;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * Redis 桥接数据。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class RedisBridgeBridgeData implements Entity<LongIdKey> {

    private static final long serialVersionUID = -1066247773168086530L;

    private LongIdKey key;
    private String value;
    private Date happenedDate;

    public RedisBridgeBridgeData() {
    }

    public RedisBridgeBridgeData(LongIdKey key, String value, Date happenedDate) {
        this.key = key;
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
