package com.dwarfeng.statistics.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.stack.bean.dto.NativeQueryInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * WebInput 原生查询信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputNativeQueryInfo implements Dto {

    private static final long serialVersionUID = -1883624258295539049L;

    public static NativeQueryInfo toStackBean(WebInputNativeQueryInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new NativeQueryInfo(
                    webInput.getPreset(),
                    webInput.getParams(),
                    Optional.ofNullable(webInput.getStatisticsSettingKeys()).map(
                            f -> f.stream().map(WebInputLongIdKey::toStackBean).collect(Collectors.toList())
                    ).orElse(null),
                    webInput.getStartDate(),
                    webInput.getEndDate(),
                    webInput.isIncludeStartDate(),
                    webInput.isIncludeEndDate()
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

    @JSONField(name = "statistics_setting_keys")
    @NotNull
    @Valid
    private List<WebInputLongIdKey> statisticsSettingKeys;

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

    public WebInputNativeQueryInfo() {
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

    public List<WebInputLongIdKey> getStatisticsSettingKeys() {
        return statisticsSettingKeys;
    }

    public void setStatisticsSettingKeys(List<WebInputLongIdKey> statisticsSettingKeys) {
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
        return "WebInputNativeQueryInfo{" +
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
