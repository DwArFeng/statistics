package com.dwarfeng.statistics.stack.cache;

import com.dwarfeng.statistics.stack.bean.entity.TaskEvent;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 任务事件缓存。
 *
 * @author DwArFeng
 * @since beta-1.0.0
 */
public interface TaskEventCache extends BatchBaseCache<LongIdKey, TaskEvent> {
}
