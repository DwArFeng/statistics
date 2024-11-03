package com.dwarfeng.statistics.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.sdk.bean.key.FastJsonBridgeDataKey;
import com.dwarfeng.statistics.stack.bean.dto.BridgeData;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 桥接器数据。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonBridgeData implements Dto {

    private static final long serialVersionUID = 3176404747682609827L;

    public static FastJsonBridgeData of(BridgeData bridgeData) {
        if (Objects.isNull(bridgeData)) {
            return null;
        } else {
            return new FastJsonBridgeData(
                    FastJsonBridgeDataKey.of(bridgeData.getKey()),
                    bridgeData.getValue(),
                    bridgeData.getHappenedDate()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonBridgeDataKey key;

    @JSONField(name = "value", ordinal = 2)
    private Object value;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    public FastJsonBridgeData() {
    }

    public FastJsonBridgeData(FastJsonBridgeDataKey key, Object value, Date happenedDate) {
        this.key = key;
        this.value = value;
        this.happenedDate = happenedDate;
    }

    public FastJsonBridgeDataKey getKey() {
        return key;
    }

    public void setKey(FastJsonBridgeDataKey key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
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
        return "FastJsonBridgeData{" +
                "key=" + key +
                ", value=" + value +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
