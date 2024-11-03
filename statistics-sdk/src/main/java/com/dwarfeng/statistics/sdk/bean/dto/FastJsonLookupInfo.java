package com.dwarfeng.statistics.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.sdk.bean.key.FastJsonBridgeDataKey;
import com.dwarfeng.statistics.stack.bean.dto.LookupInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * FastJson 查看信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonLookupInfo implements Dto {

    private static final long serialVersionUID = -266524889455711464L;

    public static FastJsonLookupInfo of(LookupInfo lookupInfo) {
        if (Objects.isNull(lookupInfo)) {
            return null;
        } else {
            return new FastJsonLookupInfo(
                    lookupInfo.getPreset(),
                    lookupInfo.getParams(),
                    FastJsonBridgeDataKey.of(lookupInfo.getBridgeDataKey()),
                    lookupInfo.getStartDate(),
                    lookupInfo.getEndDate(),
                    lookupInfo.isIncludeStartDate(),
                    lookupInfo.isIncludeEndDate(),
                    lookupInfo.getPage(),
                    lookupInfo.getRows()
            );
        }
    }

    @JSONField(name = "preset", ordinal = 1)
    private String preset;

    @JSONField(name = "params", ordinal = 2)
    private String[] params;

    @JSONField(name = "bridge_data_key", ordinal = 3)
    private FastJsonBridgeDataKey bridgeDataKey;

    @JSONField(name = "start_date", ordinal = 4)
    private Date startDate;

    @JSONField(name = "end_date", ordinal = 5)
    private Date endDate;

    @JSONField(name = "include_start_date", ordinal = 6)
    private boolean includeStartDate;

    @JSONField(name = "include_end_date", ordinal = 7)
    private boolean includeEndDate;

    @JSONField(name = "page", ordinal = 8)
    private Integer page;

    @JSONField(name = "rows", ordinal = 9)
    private Integer rows;

    public FastJsonLookupInfo() {
    }

    public FastJsonLookupInfo(
            String preset, String[] params, FastJsonBridgeDataKey bridgeDataKey, Date startDate, Date endDate,
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

    public FastJsonBridgeDataKey getBridgeDataKey() {
        return bridgeDataKey;
    }

    public void setBridgeDataKey(FastJsonBridgeDataKey bridgeDataKey) {
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
        return "FastJsonLookupInfo{" +
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
