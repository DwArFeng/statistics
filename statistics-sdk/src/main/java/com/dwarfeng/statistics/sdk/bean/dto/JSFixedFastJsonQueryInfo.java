package com.dwarfeng.statistics.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.stack.bean.dto.QueryInfo.MapInfo;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * JSFixed FastJson查询信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonQueryInfo implements Dto {

    private static final long serialVersionUID = -3671257908402787620L;

    @JSONField(name = "preset", ordinal = 1)
    private String preset;

    @JSONField(name = "params", ordinal = 2)
    private String[] params;

    @JSONField(name = "statistics_setting_keys", ordinal = 3)
    private List<JSFixedFastJsonLongIdKey> statisticsSettingKeys;

    @JSONField(name = "start_date", ordinal = 4)
    private Date startDate;

    @JSONField(name = "end_date", ordinal = 5)
    private Date endDate;

    @JSONField(name = "include_start_date", ordinal = 6)
    private boolean includeStartDate;

    @JSONField(name = "include_end_date", ordinal = 7)
    private boolean includeEndDate;

    @JSONField(name = "map_infos", ordinal = 8)
    private List<JSFixedFastJsonMapInfo> mapInfos;

    public JSFixedFastJsonQueryInfo() {
    }

    public JSFixedFastJsonQueryInfo(
            String preset, String[] params, List<JSFixedFastJsonLongIdKey> statisticsSettingKeys, Date startDate,
            Date endDate, boolean includeStartDate, boolean includeEndDate, List<JSFixedFastJsonMapInfo> mapInfos
    ) {
        this.preset = preset;
        this.params = params;
        this.statisticsSettingKeys = statisticsSettingKeys;
        this.startDate = startDate;
        this.endDate = endDate;
        this.includeStartDate = includeStartDate;
        this.includeEndDate = includeEndDate;
        this.mapInfos = mapInfos;
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

    public List<JSFixedFastJsonLongIdKey> getStatisticsSettingKeys() {
        return statisticsSettingKeys;
    }

    public void setStatisticsSettingKeys(List<JSFixedFastJsonLongIdKey> statisticsSettingKeys) {
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

    public List<JSFixedFastJsonMapInfo> getMapInfos() {
        return mapInfos;
    }

    public void setMapInfos(List<JSFixedFastJsonMapInfo> mapInfos) {
        this.mapInfos = mapInfos;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonQueryInfo{" +
                "preset='" + preset + '\'' +
                ", params=" + Arrays.toString(params) +
                ", statisticsSettingKeys=" + statisticsSettingKeys +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", includeStartDate=" + includeStartDate +
                ", includeEndDate=" + includeEndDate +
                ", mapInfos=" + mapInfos +
                '}';
    }

    /**
     * JSFixed FastJson查询映射信息。
     *
     * <p>
     * 该实体表示映射信息，包含了映射的类型和映射的参数。
     *
     * <p>
     * 该实体包含了查看过程中的第二步，也就是映射，所需要的信息。
     *
     * <p>
     * 有关查看的详细信息，请参阅术语。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    public static class JSFixedFastJsonMapInfo implements Dto {

        private static final long serialVersionUID = 3697433068409955567L;

        public static JSFixedFastJsonMapInfo of(MapInfo mapInfo) {
            if (Objects.isNull(mapInfo)) {
                return null;
            } else {
                return new JSFixedFastJsonMapInfo(
                        mapInfo.getType(),
                        mapInfo.getParam()
                );
            }
        }

        @JSONField(name = "type", ordinal = 1)
        private String type;

        @JSONField(name = "param", ordinal = 2)
        private String param;

        public JSFixedFastJsonMapInfo() {
        }

        public JSFixedFastJsonMapInfo(String type, String param) {
            this.type = type;
            this.param = param;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        @Override
        public String toString() {
            return "JSFixedFastJsonMapInfo{" +
                    "type='" + type + '\'' +
                    ", param='" + param + '\'' +
                    '}';
        }
    }
}
