package com.dwarfeng.statistics.stack.cache;

import com.dwarfeng.statistics.stack.bean.entity.FilterInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.KeyListCache;

/**
 * 部件持有使能的过滤器子项的缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface EnabledFilterInfoCache extends KeyListCache<LongIdKey, FilterInfo> {
}
