package com.dwarfeng.statistics.stack.cache;

import com.dwarfeng.statistics.stack.bean.entity.Variable;
import com.dwarfeng.statistics.stack.bean.key.VariableKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 变量缓存。
 *
 * @author DwArFeng
 * @since beta-1.0.0
 */
public interface VariableCache extends BatchBaseCache<VariableKey, Variable> {
}
