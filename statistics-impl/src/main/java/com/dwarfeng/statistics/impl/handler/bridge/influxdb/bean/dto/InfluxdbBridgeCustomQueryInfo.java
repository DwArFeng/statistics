package com.dwarfeng.statistics.impl.handler.bridge.influxdb.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.List;

/**
 * Influxdb 桥接器自定义查询信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class InfluxdbBridgeCustomQueryInfo implements Dto {

    private static final long serialVersionUID = 9185226162755069975L;

    private List<String> measurements;
    private Date rangeStart;
    private Date rangeStop;
    private String fluxFragment;

    public InfluxdbBridgeCustomQueryInfo() {
    }

    public InfluxdbBridgeCustomQueryInfo(
            List<String> measurements, Date rangeStart, Date rangeStop, String fluxFragment
    ) {
        this.measurements = measurements;
        this.rangeStart = rangeStart;
        this.rangeStop = rangeStop;
        this.fluxFragment = fluxFragment;
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

    public String getFluxFragment() {
        return fluxFragment;
    }

    public void setFluxFragment(String fluxFragment) {
        this.fluxFragment = fluxFragment;
    }

    @Override
    public String toString() {
        return "InfluxdbBridgeCustomQueryInfo{" +
                "measurements=" + measurements +
                ", rangeStart=" + rangeStart +
                ", rangeStop=" + rangeStop +
                ", fluxFragment='" + fluxFragment + '\'' +
                '}';
    }
}
