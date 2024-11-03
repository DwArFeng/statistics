package com.dwarfeng.statistics.stack.bean.dto;

import com.dwarfeng.statistics.stack.bean.key.BridgeDataKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 原生查询信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class NativeQueryInfo implements Dto {

    private static final long serialVersionUID = -8557709180553535175L;

    private String preset;
    private String[] params;

    /**
     * @since 1.1.0
     */
    private List<BridgeDataKey> bridgeDataKeys;

    private Date startDate;
    private Date endDate;
    private boolean includeStartDate;
    private boolean includeEndDate;

    public NativeQueryInfo() {
    }

    public NativeQueryInfo(
            String preset, String[] params, List<BridgeDataKey> bridgeDataKeys, Date startDate, Date endDate,
            boolean includeStartDate, boolean includeEndDate
    ) {
        this.preset = preset;
        this.params = params;
        this.bridgeDataKeys = bridgeDataKeys;
        this.startDate = startDate;
        this.endDate = endDate;
        this.includeStartDate = includeStartDate;
        this.includeEndDate = includeEndDate;
    }

    public String getPreset() {
        return preset;
    }

    public void setPreset(String preset) {
        this.preset = preset;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public List<BridgeDataKey> getBridgeDataKeys() {
        return bridgeDataKeys;
    }

    public void setBridgeDataKeys(List<BridgeDataKey> bridgeDataKeys) {
        this.bridgeDataKeys = bridgeDataKeys;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isIncludeStartDate() {
        return includeStartDate;
    }

    public void setIncludeStartDate(boolean includeStartDate) {
        this.includeStartDate = includeStartDate;
    }

    public boolean isIncludeEndDate() {
        return includeEndDate;
    }

    public void setIncludeEndDate(boolean includeEndDate) {
        this.includeEndDate = includeEndDate;
    }

    @Override
    public String toString() {
        return "NativeQueryInfo{" +
                "preset='" + preset + '\'' +
                ", params=" + Arrays.toString(params) +
                ", bridgeDataKeys=" + bridgeDataKeys +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", includeStartDate=" + includeStartDate +
                ", includeEndDate=" + includeEndDate +
                '}';
    }
}
