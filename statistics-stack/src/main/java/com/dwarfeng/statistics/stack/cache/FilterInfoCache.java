package com.dwarfeng.statistics.stack.cache;

import com.dwarfeng.statistics.stack.bean.entity.FilterInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 过滤器信息缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface FilterInfoCache extends BatchBaseCache<LongIdKey, FilterInfo> {
}
