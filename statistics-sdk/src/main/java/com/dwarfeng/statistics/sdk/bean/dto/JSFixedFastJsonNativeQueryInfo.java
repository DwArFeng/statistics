package com.dwarfeng.statistics.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.sdk.bean.key.JSFixedFastJsonBridgeDataKey;
import com.dwarfeng.statistics.stack.bean.dto.NativeQueryInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.*;
import java.util.stream.Collectors;

/**
 * JSFixed FastJson原生查询信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonNativeQueryInfo implements Dto {

    private static final long serialVersionUID = 7784072944310746392L;

    public static JSFixedFastJsonNativeQueryInfo of(NativeQueryInfo nativeQueryInfo) {
        if (Objects.isNull(nativeQueryInfo)) {
            return null;
        } else {
            return new JSFixedFastJsonNativeQueryInfo(
                    nativeQueryInfo.getPreset(),
                    nativeQueryInfo.getParams(),
                    Optional.ofNullable(nativeQueryInfo.getBridgeDataKeys()).map(
                            f -> f.stream().map(JSFixedFastJsonBridgeDataKey::of).collect(Collectors.toList())
                    ).orElse(null),
                    nativeQueryInfo.getStartDate(),
                    nativeQueryInfo.getEndDate(),
                    nativeQueryInfo.isIncludeStartDate(),
                    nativeQueryInfo.isIncludeEndDate()
            );
        }
    }

    @JSONField(name = "preset", ordinal = 1)
    private String preset;

    @JSONField(name = "params", ordinal = 2)
    private String[] params;

    @JSONField(name = "bridge_data_keys", ordinal = 3)
    private List<JSFixedFastJsonBridgeDataKey> bridgeDataKeys;

    @JSONField(name = "start_date", ordinal = 4)
    private Date startDate;

    @JSONField(name = "end_date", ordinal = 5)
    private Date endDate;

    @JSONField(name = "include_start_date", ordinal = 6)
    private boolean includeStartDate;

    @JSONField(name = "include_end_date", ordinal = 7)
    private boolean includeEndDate;

    public JSFixedFastJsonNativeQueryInfo() {
    }

    public JSFixedFastJsonNativeQueryInfo(
            String preset, String[] params, List<JSFixedFastJsonBridgeDataKey> bridgeDataKeys, Date startDate,
            Date endDate, boolean includeStartDate, boolean includeEndDate
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

    public List<JSFixedFastJsonBridgeDataKey> getBridgeDataKeys() {
        return bridgeDataKeys;
    }

    public void setBridgeDataKeys(List<JSFixedFastJsonBridgeDataKey> bridgeDataKeys) {
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
        return "JSFixedFastJsonNativeQueryInfo{" +
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
