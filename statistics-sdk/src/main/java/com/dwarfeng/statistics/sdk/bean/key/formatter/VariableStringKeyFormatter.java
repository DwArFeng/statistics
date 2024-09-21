package com.dwarfeng.statistics.sdk.bean.key.formatter;

import com.dwarfeng.statistics.stack.bean.key.VariableKey;
import com.dwarfeng.subgrade.sdk.common.Constants;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringKeyFormatter;

import java.util.Objects;

/**
 * VariableKey 的文本格式化转换器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class VariableStringKeyFormatter implements StringKeyFormatter<VariableKey> {

    private String prefix;

    public VariableStringKeyFormatter(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String format(VariableKey key) {
        Objects.requireNonNull(key);
        return prefix + key.getStatisticsSettingLongId() + "_" + key.getVariableStringId();
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
        return "VariableStringKeyFormatter{" +
                "prefix='" + prefix + '\'' +
                '}';
    }
}
