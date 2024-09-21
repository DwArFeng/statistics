package com.dwarfeng.statistics.stack.cache;

import com.dwarfeng.statistics.stack.bean.entity.FilterSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 过滤器支持缓存。
 *
 * @author DwArFeng
 * @since beta-1.0.0
 */
public interface FilterSupportCache extends BatchBaseCache<StringIdKey, FilterSupport> {
}
