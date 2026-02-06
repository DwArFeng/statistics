package com.dwarfeng.statistics.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.statistics.stack.bean.key.TagDefinitionKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * JSFixed FastJson 标签定义键。
 *
 * @author zhaofz
 * @since 1.4.0
 */
public class JSFixedFastJsonTagDefinitionKey implements Key {

    private static final long serialVersionUID = 8964051711403484446L;

    public static JSFixedFastJsonTagDefinitionKey of(TagDefinitionKey tagDefinitionKey) {
        if (Objects.isNull(tagDefinitionKey)) {
            return null;
        } else {
            return new JSFixedFastJsonTagDefinitionKey(
                    tagDefinitionKey.getStatisticsSettingLongId(),
                    tagDefinitionKey.getTag()
            );
        }
    }

    @JSONField(name = "statistics_setting_long_id", ordinal = 1, serializeUsing = ToStringSerializer.class)
    private Long statisticsSettingLongId;

    @JSONField(name = "tag", ordinal = 2)
    private String tag;

    public JSFixedFastJsonTagDefinitionKey() {
    }

    public JSFixedFastJsonTagDefinitionKey(Long statisticsSettingLongId, String tag) {
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

        JSFixedFastJsonTagDefinitionKey that = (JSFixedFastJsonTagDefinitionKey) o;
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
        return "JSFixedFastJsonTagDefinitionKey{" +
                "statisticsSettingLongId=" + statisticsSettingLongId +
                ", tag='" + tag + '\'' +
                '}';
    }
}
