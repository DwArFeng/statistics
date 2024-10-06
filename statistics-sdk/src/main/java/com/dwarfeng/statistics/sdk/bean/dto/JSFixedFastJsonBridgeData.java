package com.dwarfeng.statistics.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.stack.bean.dto.BridgeData;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
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

    private static final long serialVersionUID = -7235434929751966419L;

    public static JSFixedFastJsonBridgeData of(BridgeData bridgeData) {
        if (Objects.isNull(bridgeData)) {
            return null;
        } else {
            return new JSFixedFastJsonBridgeData(
                    JSFixedFastJsonLongIdKey.of(bridgeData.getStatisticsSettingKey()),
                    bridgeData.getValue(),
                    bridgeData.getHappenedDate()
            );
        }
    }

    @JSONField(name = "statistics_setting_key", ordinal = 1)
    private JSFixedFastJsonLongIdKey statisticsSettingKey;

    @JSONField(name = "value", ordinal = 2)
    private Object value;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    public JSFixedFastJsonBridgeData() {
    }

    public JSFixedFastJsonBridgeData(JSFixedFastJsonLongIdKey statisticsSettingKey, Object value, Date happenedDate) {
        this.statisticsSettingKey = statisticsSettingKey;
        this.value = value;
        this.happenedDate = happenedDate;
    }

    public JSFixedFastJsonLongIdKey getStatisticsSettingKey() {
        return statisticsSettingKey;
    }

    public void setStatisticsSettingKey(JSFixedFastJsonLongIdKey statisticsSettingKey) {
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
        return "JSFixedFastJsonBridgeData{" +
                "statisticsSettingKey=" + statisticsSettingKey +
                ", value=" + value +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
