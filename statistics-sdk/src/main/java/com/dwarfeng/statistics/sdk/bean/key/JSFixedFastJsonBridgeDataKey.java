package com.dwarfeng.statistics.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.statistics.stack.bean.key.BridgeDataKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * JSFixed FastJson 桥接器数据键。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class JSFixedFastJsonBridgeDataKey implements Key {

    private static final long serialVersionUID = 3955638852933036222L;

    public static JSFixedFastJsonBridgeDataKey of(BridgeDataKey bridgeDataKey) {
        if (Objects.isNull(bridgeDataKey)) {
            return null;
        } else {
            return new JSFixedFastJsonBridgeDataKey(
                    bridgeDataKey.getStatisticsSettingLongId(),
                    bridgeDataKey.getTag()
            );
        }
    }

    @JSONField(name = "statistics_setting_long_id", ordinal = 1, serializeUsing = ToStringSerializer.class)
    private Long statisticsSettingLongId;

    @JSONField(name = "tag", ordinal = 2)
    private String tag;

    public JSFixedFastJsonBridgeDataKey() {
    }

    public JSFixedFastJsonBridgeDataKey(Long statisticsSettingLongId, String tag) {
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

        JSFixedFastJsonBridgeDataKey that = (JSFixedFastJsonBridgeDataKey) o;
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
        return "JSFixedFastJsonBridgeDataKey{" +
                "statisticsSettingLongId=" + statisticsSettingLongId +
                ", tag='" + tag + '\'' +
                '}';
    }
}
