package com.dwarfeng.statistics.stack.cache;

import com.dwarfeng.statistics.stack.bean.entity.HistoryTaskEvent;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 历史任务事件缓存。
 *
 * @author DwArFeng
 * @since beta-1.0.0
 */
public interface HistoryTaskEventCache extends BatchBaseCache<LongIdKey, HistoryTaskEvent> {
}
