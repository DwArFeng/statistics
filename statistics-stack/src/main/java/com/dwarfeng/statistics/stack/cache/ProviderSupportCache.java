package com.dwarfeng.statistics.stack.cache;

import com.dwarfeng.statistics.stack.bean.entity.ProviderSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 提供器支持缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ProviderSupportCache extends BatchBaseCache<StringIdKey, ProviderSupport> {
}
