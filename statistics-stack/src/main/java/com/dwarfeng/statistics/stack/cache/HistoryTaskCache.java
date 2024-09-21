package com.dwarfeng.statistics.stack.cache;

import com.dwarfeng.statistics.stack.bean.entity.HistoryTask;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 历史任务缓存。
 *
 * @author DwArFeng
 * @since beta-1.0.0
 */
public interface HistoryTaskCache extends BatchBaseCache<LongIdKey, HistoryTask> {
}
