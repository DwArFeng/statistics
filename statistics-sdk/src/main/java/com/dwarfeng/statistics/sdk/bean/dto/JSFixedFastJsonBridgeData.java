package com.dwarfeng.statistics.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.sdk.bean.key.JSFixedFastJsonBridgeDataKey;
import com.dwarfeng.statistics.stack.bean.dto.BridgeData;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 桥接器数据。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonBridgeData implements Dto {

    private static final long serialVersionUID = 8632517700135303087L;

    public static JSFixedFastJsonBridgeData of(BridgeData bridgeData) {
        if (Objects.isNull(bridgeData)) {
            return null;
        } else {
            return new JSFixedFastJsonBridgeData(
                    JSFixedFastJsonBridgeDataKey.of(bridgeData.getKey()),
                    bridgeData.getValue(),
                    bridgeData.getHappenedDate()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonBridgeDataKey key;

    @JSONField(name = "value", ordinal = 2)
    private Object value;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    public JSFixedFastJsonBridgeData() {
    }

    public JSFixedFastJsonBridgeData(JSFixedFastJsonBridgeDataKey key, Object value, Date happenedDate) {
        this.key = key;
        this.value = value;
        this.happenedDate = happenedDate;
    }

    public JSFixedFastJsonBridgeDataKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonBridgeDataKey key) {
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
        return "JSFixedFastJsonBridgeData{" +
                "key=" + key +
                ", value=" + value +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
