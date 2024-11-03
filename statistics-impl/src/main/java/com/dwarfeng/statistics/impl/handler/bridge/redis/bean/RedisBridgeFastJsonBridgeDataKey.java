package com.dwarfeng.statistics.impl.handler.bridge.redis.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * Redis 桥接器 FastJson 桥接数据键。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class RedisBridgeFastJsonBridgeDataKey implements Key {

    private static final long serialVersionUID = -4392624430495175709L;

    public static RedisBridgeFastJsonBridgeDataKey of(RedisBridgeBridgeDataKey redisBridgeBridgeDataKey) {
        if (Objects.isNull(redisBridgeBridgeDataKey)) {
            return null;
        } else {
            return new RedisBridgeFastJsonBridgeDataKey(
                    redisBridgeBridgeDataKey.getStatisticsSettingLongId(),
                    redisBridgeBridgeDataKey.getTag()
            );
        }
    }

    @JSONField(name = "statistics_setting_long_id", ordinal = 1)
    private Long statisticsSettingLongId;

    @JSONField(name = "tag", ordinal = 2)
    private String tag;

    public RedisBridgeFastJsonBridgeDataKey() {
    }

    public RedisBridgeFastJsonBridgeDataKey(Long statisticsSettingLongId, String tag) {
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

        RedisBridgeFastJsonBridgeDataKey that = (RedisBridgeFastJsonBridgeDataKey) o;
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
        return "RedisBridgeFastJsonBridgeDataKey{" +
                "statisticsSettingLongId=" + statisticsSettingLongId +
                ", tag='" + tag + '\'' +
                '}';
    }
}
