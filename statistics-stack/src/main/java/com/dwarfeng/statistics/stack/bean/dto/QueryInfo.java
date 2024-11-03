package com.dwarfeng.statistics.stack.bean.dto;

import com.dwarfeng.statistics.stack.bean.key.BridgeDataKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 查询信息。
 *
 * <p>
 * 该实体表示查询信息，包含了序列的查看信息和序列的映射信息。
 *
 * <p>
 * 有关查看的详细信息，请参阅术语。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class QueryInfo implements Dto {

    private static final long serialVersionUID = 6414545103577681499L;

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
    private List<MapInfo> mapInfos;

    public QueryInfo() {
    }

    public QueryInfo(
            String preset, String[] params, List<BridgeDataKey> bridgeDataKeys, Date startDate, Date endDate,
            boolean includeStartDate, boolean includeEndDate, List<MapInfo> mapInfos
    ) {
        this.preset = preset;
        this.params = params;
        this.bridgeDataKeys = bridgeDataKeys;
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

    public List<MapInfo> getMapInfos() {
        return mapInfos;
    }

    public void setMapInfos(List<MapInfo> mapInfos) {
        this.mapInfos = mapInfos;
    }

    @Override
    public String toString() {
        return "QueryInfo{" +
                "preset='" + preset + '\'' +
                ", params=" + Arrays.toString(params) +
                ", bridgeDataKeys=" + bridgeDataKeys +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", includeStartDate=" + includeStartDate +
                ", includeEndDate=" + includeEndDate +
                ", mapInfos=" + mapInfos +
                '}';
    }

    /**
     * 查询映射信息。
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
    public static class MapInfo implements Dto {

        private static final long serialVersionUID = 1834641890530517907L;

        private String type;
        private String param;

        public MapInfo() {
        }

        public MapInfo(String type, String param) {
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
            return "MapInfo{" +
                    "type='" + type + '\'' +
                    ", param='" + param + '\'' +
                    '}';
        }
    }
}
