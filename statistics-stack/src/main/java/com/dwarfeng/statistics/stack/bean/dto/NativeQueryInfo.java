package com.dwarfeng.statistics.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

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

    private static final long serialVersionUID = 1717814260755238709L;

    private String preset;
    private String[] params;
    private List<LongIdKey> statisticsSettingKeys;
    private Date startDate;
    private Date endDate;
    private boolean includeStartDate;
    private boolean includeEndDate;

    public NativeQueryInfo() {
    }

    public NativeQueryInfo(
            String preset, String[] params, List<LongIdKey> statisticsSettingKeys, Date startDate, Date endDate,
            boolean includeStartDate, boolean includeEndDate
    ) {
        this.preset = preset;
        this.params = params;
        this.statisticsSettingKeys = statisticsSettingKeys;
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

    public List<LongIdKey> getStatisticsSettingKeys() {
        return statisticsSettingKeys;
    }

    public void setStatisticsSettingKeys(List<LongIdKey> statisticsSettingKeys) {
        this.statisticsSettingKeys = statisticsSettingKeys;
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
                ", statisticsSettingKeys=" + statisticsSettingKeys +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", includeStartDate=" + includeStartDate +
                ", includeEndDate=" + includeEndDate +
                '}';
    }
}
