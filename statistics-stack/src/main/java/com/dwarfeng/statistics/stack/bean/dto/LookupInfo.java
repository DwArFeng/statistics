package com.dwarfeng.statistics.stack.bean.dto;

import com.dwarfeng.statistics.stack.bean.key.BridgeDataKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;
import java.util.Date;

/**
 * 查看信息。
 *
 * <p>
 * 该实体表示查看信息，包含了查看的预设、参数、开始时间、结束时间、是否包含开始时间、是否包含结束时间、发生时间的排序、
 * 限制、偏移。
 *
 * <p>
 * 有关查看的详细信息，请参阅术语。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class LookupInfo implements Dto {

    private static final long serialVersionUID = -6043573280384973464L;

    private String preset;
    private String[] params;

    /**
     * @since 1.1.0
     */
    private BridgeDataKey bridgeDataKey;

    private Date startDate;
    private Date endDate;
    private boolean includeStartDate;
    private boolean includeEndDate;
    private Integer page;
    private Integer rows;

    public LookupInfo() {
    }

    public LookupInfo(
            String preset, String[] params, BridgeDataKey bridgeDataKey, Date startDate, Date endDate,
            boolean includeStartDate, boolean includeEndDate, Integer page, Integer rows
    ) {
        this.preset = preset;
        this.params = params;
        this.bridgeDataKey = bridgeDataKey;
        this.startDate = startDate;
        this.endDate = endDate;
        this.includeStartDate = includeStartDate;
        this.includeEndDate = includeEndDate;
        this.page = page;
        this.rows = rows;
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

    public BridgeDataKey getBridgeDataKey() {
        return bridgeDataKey;
    }

    public void setBridgeDataKey(BridgeDataKey bridgeDataKey) {
        this.bridgeDataKey = bridgeDataKey;
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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "LookupInfo{" +
                "preset='" + preset + '\'' +
                ", params=" + Arrays.toString(params) +
                ", bridgeDataKey=" + bridgeDataKey +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", includeStartDate=" + includeStartDate +
                ", includeEndDate=" + includeEndDate +
                ", page=" + page +
                ", rows=" + rows +
                '}';
    }
}
