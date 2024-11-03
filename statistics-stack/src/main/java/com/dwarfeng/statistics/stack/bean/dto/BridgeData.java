package com.dwarfeng.statistics.stack.bean.dto;

import com.dwarfeng.statistics.stack.bean.key.BridgeDataKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;

/**
 * 桥接器数据。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class BridgeData implements Dto {

    private static final long serialVersionUID = -5743363583632129139L;

    /**
     * @since 1.1.0
     */
    private BridgeDataKey key;
    private Object value;
    private Date happenedDate;

    public BridgeData() {
    }

    public BridgeData(BridgeDataKey key, Object value, Date happenedDate) {
        this.key = key;
        this.value = value;
        this.happenedDate = happenedDate;
    }

    public BridgeDataKey getKey() {
        return key;
    }

    public void setKey(BridgeDataKey key) {
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
        return "BridgeData{" +
                "key=" + key +
                ", value=" + value +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
