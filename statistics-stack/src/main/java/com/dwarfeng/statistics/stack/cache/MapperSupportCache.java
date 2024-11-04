package com.dwarfeng.statistics.stack.cache;

import com.dwarfeng.statistics.stack.bean.entity.MapperSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 映射器支持缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface MapperSupportCache extends BatchBaseCache<StringIdKey, MapperSupport> {
}
