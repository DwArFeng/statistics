package com.dwarfeng.statistics.stack.handler;

import com.dwarfeng.statistics.stack.struct.ExecuteInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.handler.LocalCacheHandler;

/**
 * 执行用本地缓存处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExecuteLocalCacheHandler extends LocalCacheHandler<LongIdKey, ExecuteInfo> {
}
