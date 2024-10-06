package com.dwarfeng.statistics.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.stack.bean.dto.LookupInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
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

    private static final long serialVersionUID = -5801428563027333858L;

    public static LookupInfo toStackBean(WebInputLookupInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new LookupInfo(
                    webInput.getPreset(),
                    webInput.getParams(),
                    WebInputLongIdKey.toStackBean(webInput.getStatisticsSettingKey()),
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

    @JSONField(name = "statistics_setting_key")
    @NotNull
    @Valid
    private WebInputLongIdKey statisticsSettingKey;

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

    public WebInputLongIdKey getStatisticsSettingKey() {
        return statisticsSettingKey;
    }

    public void setStatisticsSettingKey(WebInputLongIdKey statisticsSettingKey) {
        this.statisticsSettingKey = statisticsSettingKey;
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
                ", statisticsSettingKey=" + statisticsSettingKey +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", includeStartDate=" + includeStartDate +
                ", includeEndDate=" + includeEndDate +
                ", page=" + page +
                ", rows=" + rows +
                '}';
    }
}
