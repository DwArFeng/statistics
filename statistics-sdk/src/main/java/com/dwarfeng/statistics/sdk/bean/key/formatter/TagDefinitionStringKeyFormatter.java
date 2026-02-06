package com.dwarfeng.statistics.sdk.bean.key.formatter;

import com.dwarfeng.statistics.stack.bean.key.TagDefinitionKey;
import com.dwarfeng.subgrade.sdk.common.Constants;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringKeyFormatter;

import java.util.Objects;

/**
 * TagDefinitionKey 的文本格式化转换器。
 *
 * @author zhaofz
 * @since 1.4.0
 */
public class TagDefinitionStringKeyFormatter implements StringKeyFormatter<TagDefinitionKey> {

    private String prefix;

    public TagDefinitionStringKeyFormatter(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String format(TagDefinitionKey key) {
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
        return "TagDefinitionStringKeyFormatter{" +
                "prefix='" + prefix + '\'' +
                '}';
    }
}
