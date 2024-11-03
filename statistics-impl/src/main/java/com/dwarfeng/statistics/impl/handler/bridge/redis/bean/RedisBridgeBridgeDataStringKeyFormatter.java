package com.dwarfeng.statistics.impl.handler.bridge.redis.bean;

import com.dwarfeng.subgrade.sdk.common.Constants;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringKeyFormatter;

import java.util.Objects;

/**
 * Redis 桥接器 BridgeDataKey 的文本格式化转换器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class RedisBridgeBridgeDataStringKeyFormatter implements StringKeyFormatter<RedisBridgeBridgeDataKey> {

    private String prefix;

    public RedisBridgeBridgeDataStringKeyFormatter(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String format(RedisBridgeBridgeDataKey key) {
        Objects.requireNonNull(key);
        return prefix + key.getStatisticsSettingLongId() + "_" + key.getTag();
    }

    @Override
    public String generalFormat() {
        return prefix + Constants.REDIS_KEY_WILDCARD_CHARACTER;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return "RedisBridgeBridgeDataStringKeyFormatter{" +
                "prefix='" + prefix + '\'' +
                '}';
    }
}
