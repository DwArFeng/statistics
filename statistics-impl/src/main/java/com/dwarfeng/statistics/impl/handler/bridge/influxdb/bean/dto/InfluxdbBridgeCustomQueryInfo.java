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

    private static final long serialVersionUID = 8291762583684592180L;

    private List<InfluxdbBridgeDataGroup> dataGroups;
    private Date rangeStart;
    private Date rangeStop;
    private String fluxFragment;

    public InfluxdbBridgeCustomQueryInfo() {
    }

    public InfluxdbBridgeCustomQueryInfo(
            List<InfluxdbBridgeDataGroup> dataGroups, Date rangeStart, Date rangeStop, String fluxFragment
    ) {
        this.dataGroups = dataGroups;
        this.rangeStart = rangeStart;
        this.rangeStop = rangeStop;
        this.fluxFragment = fluxFragment;
    }

    public List<InfluxdbBridgeDataGroup> getDataGroups() {
        return dataGroups;
    }

    public void setDataGroups(List<InfluxdbBridgeDataGroup> dataGroups) {
        this.dataGroups = dataGroups;
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
                "dataGroups=" + dataGroups +
                ", rangeStart=" + rangeStart +
                ", rangeStop=" + rangeStop +
                ", fluxFragment='" + fluxFragment + '\'' +
                '}';
    }
}
