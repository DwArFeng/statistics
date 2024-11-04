package com.dwarfeng.statistics.stack.cache;

import com.dwarfeng.statistics.stack.bean.entity.ProviderInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 提供器信息缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ProviderInfoCache extends BatchBaseCache<LongIdKey, ProviderInfo> {
}
