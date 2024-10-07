package com.dwarfeng.statistics.impl.handler.bridge.influxdb.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;
import java.util.Date;

/**
 * Influxdb 桥接器查询信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class InfluxdbBridgeLookupInfo implements Dto {

    private static final long serialVersionUID = 4347181345446316239L;

    private String measurement;
    private Date rangeStart;
    private Date rangeStop;
    private int page;
    private int rows;
    private String[] params;

    public InfluxdbBridgeLookupInfo() {
    }

    public InfluxdbBridgeLookupInfo(
            String measurement, Date rangeStart, Date rangeStop, int page, int rows, String[] params
    ) {
        this.measurement = measurement;
        this.rangeStart = rangeStart;
        this.rangeStop = rangeStop;
        this.page = page;
        this.rows = rows;
        this.params = params;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public Date getRangeStart() {
        return rangeStart;
    }

    public void setRangeStart(Date rangeStart) {
        this.rangeStart = rangeStart;
    }

    public Date getRangeStop() {
        return rangeStop;
    }

    public void setRangeStop(Date rangeStop) {
        this.rangeStop = rangeStop;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "InfluxdbBridgeLookupInfo{" +
                "measurement='" + measurement + '\'' +
                ", rangeStart=" + rangeStart +
                ", rangeStop=" + rangeStop +
                ", page=" + page +
                ", rows=" + rows +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
