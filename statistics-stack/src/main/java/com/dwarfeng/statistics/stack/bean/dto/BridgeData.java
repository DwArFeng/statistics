package com.dwarfeng.statistics.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * 桥接器数据。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class BridgeData implements Dto {

    private static final long serialVersionUID = 5625081273188456236L;

    private LongIdKey statisticsSettingKey;
    private Object value;
    private Date happenedDate;

    public BridgeData() {
    }

    public BridgeData(LongIdKey statisticsSettingKey, Object value, Date happenedDate) {
        this.statisticsSettingKey = statisticsSettingKey;
        this.value = value;
        this.happenedDate = happenedDate;
    }

    public LongIdKey getStatisticsSettingKey() {
        return statisticsSettingKey;
    }

    public void setStatisticsSettingKey(LongIdKey statisticsSettingKey) {
        this.statisticsSettingKey = statisticsSettingKey;
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
                "statisticsSettingKey=" + statisticsSettingKey +
                ", value=" + value +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
