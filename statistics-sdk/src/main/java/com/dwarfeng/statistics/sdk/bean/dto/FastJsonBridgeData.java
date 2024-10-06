package com.dwarfeng.statistics.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.stack.bean.dto.BridgeData;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
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

    private static final long serialVersionUID = -8789023333673672621L;

    public static FastJsonBridgeData of(BridgeData bridgeData) {
        if (Objects.isNull(bridgeData)) {
            return null;
        } else {
            return new FastJsonBridgeData(
                    FastJsonLongIdKey.of(bridgeData.getStatisticsSettingKey()),
                    bridgeData.getValue(),
                    bridgeData.getHappenedDate()
            );
        }
    }

    @JSONField(name = "statistics_setting_key", ordinal = 1)
    private FastJsonLongIdKey statisticsSettingKey;

    @JSONField(name = "value", ordinal = 2)
    private Object value;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    public FastJsonBridgeData() {
    }

    public FastJsonBridgeData(FastJsonLongIdKey statisticsSettingKey, Object value, Date happenedDate) {
        this.statisticsSettingKey = statisticsSettingKey;
        this.value = value;
        this.happenedDate = happenedDate;
    }

    public FastJsonLongIdKey getStatisticsSettingKey() {
        return statisticsSettingKey;
    }

    public void setStatisticsSettingKey(FastJsonLongIdKey statisticsSettingKey) {
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
        return "FastJsonBridgeData{" +
                "statisticsSettingKey=" + statisticsSettingKey +
                ", value=" + value +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
