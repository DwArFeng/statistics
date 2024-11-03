package com.dwarfeng.statistics.impl.handler.bridge.influxdb.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * Influxdb 桥接器数据分组。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InfluxdbBridgeDataGroup implements Dto {

    private static final long serialVersionUID = -5373882811551285190L;

    private String measurement;
    private String tag;

    public InfluxdbBridgeDataGroup() {
    }

    public InfluxdbBridgeDataGroup(String measurement, String tag) {
        this.measurement = measurement;
        this.tag = tag;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "InfluxdbBridgeDataGroup{" +
                "measurement='" + measurement + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
