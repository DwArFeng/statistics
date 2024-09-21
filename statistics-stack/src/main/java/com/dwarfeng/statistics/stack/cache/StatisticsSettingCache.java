package com.dwarfeng.statistics.stack.cache;

import com.dwarfeng.statistics.stack.bean.entity.StatisticsSetting;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 统计设置缓存。
 *
 * @author DwArFeng
 * @since beta-1.0.0
 */
public interface StatisticsSettingCache extends BatchBaseCache<LongIdKey, StatisticsSetting> {
}
