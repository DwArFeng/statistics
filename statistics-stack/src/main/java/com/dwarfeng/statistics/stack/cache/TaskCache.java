package com.dwarfeng.statistics.stack.cache;

import com.dwarfeng.statistics.stack.bean.entity.Task;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 任务缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface TaskCache extends BatchBaseCache<LongIdKey, Task> {
}
