package com.dwarfeng.statistics.impl.handler.bridge.redis.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * Redis 桥接器桥接 FastJson 数据。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class RedisBridgeFastJsonBridgeData implements Bean {

    private static final long serialVersionUID = -1140046515812865886L;

    public static RedisBridgeFastJsonBridgeData of(RedisBridgeBridgeData redisBridgeBridgeData) {
        if (Objects.isNull(redisBridgeBridgeData)) {
            return null;
        } else {
            return new RedisBridgeFastJsonBridgeData(
                    RedisBridgeFastJsonBridgeDataKey.of(redisBridgeBridgeData.getKey()),
                    redisBridgeBridgeData.getValue(),
                    redisBridgeBridgeData.getHappenedDate()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private RedisBridgeFastJsonBridgeDataKey key;

    @JSONField(name = "value", ordinal = 2)
    private String value;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    public RedisBridgeFastJsonBridgeData() {
    }

    public RedisBridgeFastJsonBridgeData(RedisBridgeFastJsonBridgeDataKey key, String value, Date happenedDate) {
        this.key = key;
        this.value = value;
        this.happenedDate = happenedDate;
    }

    public RedisBridgeFastJsonBridgeDataKey getKey() {
        return key;
    }

    public void setKey(RedisBridgeFastJsonBridgeDataKey key) {
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
        return "RedisBridgeFastJsonBridgeData{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
