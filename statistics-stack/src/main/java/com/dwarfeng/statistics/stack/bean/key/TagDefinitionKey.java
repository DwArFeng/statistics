package com.dwarfeng.statistics.stack.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * 标记定义键。
 *
 * @author zhaofz
 * @since 1.4.0
 */
public class TagDefinitionKey implements Key {

    private static final long serialVersionUID = -9172626425184260354L;

    private Long statisticsSettingLongId;
    private String tag;

    public TagDefinitionKey() {
    }

    public TagDefinitionKey(Long statisticsSettingLongId, String tag) {
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

        TagDefinitionKey that = (TagDefinitionKey) o;
        return Objects.equals(statisticsSettingLongId, that.statisticsSettingLongId) &&
                Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(statisticsSettingLongId);
        result = 31 * result + Objects.hashCode(tag);
        return result;
    }

    @Override
    public String toString() {
        return "TagDefinitionKey{" +
                "statisticsSettingLongId=" + statisticsSettingLongId +
                ", tag='" + tag + '\'' +
                '}';
    }
}
