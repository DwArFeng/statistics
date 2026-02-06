package com.dwarfeng.statistics.stack.cache;

import com.dwarfeng.statistics.stack.bean.entity.TagDefinition;
import com.dwarfeng.statistics.stack.bean.key.TagDefinitionKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 标签定义。
 *
 * @author zhaofz
 * @since 1.4.0
 */
public interface TagDefinitionCache extends BatchBaseCache<TagDefinitionKey, TagDefinition> {
}
