package com.dwarfeng.statistics.stack.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * 桥接器数据键。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class BridgeDataKey implements Key {

    private static final long serialVersionUID = -8508159012768923886L;

    private Long statisticsSettingLongId;
    private String tag;

    public BridgeDataKey() {
    }

    public BridgeDataKey(Long statisticsSettingLongId, String tag) {
        this.statisticsSettingLongId = statisticsSettingLongId;
        this.tag = tag;
    }

    public Long getStatisticsSettingLongId() {
        return statisticsSettingLongId;
    }

    public void setStatisticsSettingLongId(Long statisticsSettingLongId) {
        this.statisticsSettingLongId = statisticsSettingLongId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BridgeDataKey that = (BridgeDataKey) o;
        return Objects.equals(statisticsSettingLongId, that.statisticsSettingLongId) && Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(statisticsSettingLongId);
        result = 31 * result + Objects.hashCode(tag);
        return result;
    }

    @Override
    public String toString() {
        return "BridgeDataKey{" +
                "statisticsSettingLongId=" + statisticsSettingLongId +
                ", tag='" + tag + '\'' +
                '}';
    }
}
