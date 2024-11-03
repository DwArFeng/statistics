package com.dwarfeng.statistics.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.sdk.bean.key.WebInputBridgeDataKey;
import com.dwarfeng.statistics.stack.bean.dto.LookupInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * WebInput 查看信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputLookupInfo implements Dto {

    private static final long serialVersionUID = -7391074361391559318L;

    public static LookupInfo toStackBean(WebInputLookupInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new LookupInfo(
                    webInput.getPreset(),
                    webInput.getParams(),
                    WebInputBridgeDataKey.toStackBean(webInput.getBridgeDataKey()),
                    webInput.getStartDate(),
                    webInput.getEndDate(),
                    webInput.isIncludeStartDate(),
                    webInput.isIncludeEndDate(),
                    webInput.getPage(),
                    webInput.getRows()
            );
        }
    }

    @JSONField(name = "preset")
    @NotNull
    @NotEmpty
    private String preset;

    @JSONField(name = "params")
    @NotNull
    private String[] params;

    @JSONField(name = "bridge_data_key")
    @NotNull
    @Valid
    private WebInputBridgeDataKey bridgeDataKey;

    @JSONField(name = "start_date")
    @NotNull
    private Date startDate;

    @JSONField(name = "end_date")
    @NotNull
    private Date endDate;

    @JSONField(name = "include_start_date")
    private boolean includeStartDate;

    @JSONField(name = "include_end_date")
    private boolean includeEndDate;

    @JSONField(name = "page")
    @PositiveOrZero
    private Integer page;

    @JSONField(name = "rows")
    @PositiveOrZero
    private Integer rows;

    public WebInputLookupInfo() {
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

    public WebInputBridgeDataKey getBridgeDataKey() {
        return bridgeDataKey;
    }

    public void setBridgeDataKey(WebInputBridgeDataKey bridgeDataKey) {
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
        return "WebInputLookupInfo{" +
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
