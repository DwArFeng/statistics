package com.dwarfeng.statistics.impl.handler.bridge.influxdb.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.List;

/**
 * Influxdb 桥接器查询信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class InfluxdbBridgeDefaultQueryInfo implements Dto {

    private static final long serialVersionUID = 7536593546171703366L;

    private List<String> measurements;
    private Date rangeStart;
    private Date rangeStop;
    private long aggregateWindowEvery;
    private long aggregateWindowOffset;
    private String aggregateWindowFn;

    public InfluxdbBridgeDefaultQueryInfo() {
    }

    public InfluxdbBridgeDefaultQueryInfo(
            List<String> measurements, Date rangeStart, Date rangeStop, long aggregateWindowEvery,
            long aggregateWindowOffset, String aggregateWindowFn
    ) {
        this.measurements = measurements;
        this.rangeStart = rangeStart;
        this.rangeStop = rangeStop;
        this.aggregateWindowEvery = aggregateWindowEvery;
        this.aggregateWindowOffset = aggregateWindowOffset;
        this.aggregateWindowFn = aggregateWindowFn;
    }

    public List<String> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<String> measurements) {
        this.measurements = measurements;
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

    public long getAggregateWindowEvery() {
        return aggregateWindowEvery;
    }

    public void setAggregateWindowEvery(long aggregateWindowEvery) {
        this.aggregateWindowEvery = aggregateWindowEvery;
    }

    public long getAggregateWindowOffset() {
        return aggregateWindowOffset;
    }

    public void setAggregateWindowOffset(long aggregateWindowOffset) {
        this.aggregateWindowOffset = aggregateWindowOffset;
    }

    public String getAggregateWindowFn() {
        return aggregateWindowFn;
    }

    public void setAggregateWindowFn(String aggregateWindowFn) {
        this.aggregateWindowFn = aggregateWindowFn;
    }

    @Override
    public String toString() {
        return "InfluxdbBridgeDefaultQueryInfo{" +
                "measurements=" + measurements +
                ", rangeStart=" + rangeStart +
                ", rangeStop=" + rangeStop +
                ", aggregateWindowEvery=" + aggregateWindowEvery +
                ", aggregateWindowOffset=" + aggregateWindowOffset +
                ", aggregateWindowFn='" + aggregateWindowFn + '\'' +
                '}';
    }
}
