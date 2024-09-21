package com.dwarfeng.statistics.stack.cache;

import com.dwarfeng.statistics.stack.bean.entity.StatisticsExecutionProfile;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 统计执行简报缓存。
 *
 * @author DwArFeng
 * @since beta-1.0.0
 */
public interface StatisticsExecutionProfileCache extends BatchBaseCache<LongIdKey, StatisticsExecutionProfile> {
}
