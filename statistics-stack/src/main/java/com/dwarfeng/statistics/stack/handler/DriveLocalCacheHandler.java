package com.dwarfeng.statistics.stack.handler;

import com.dwarfeng.statistics.stack.struct.DriveInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.handler.LocalCacheHandler;

/**
 * 驱动用本地缓存处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface DriveLocalCacheHandler extends LocalCacheHandler<LongIdKey, DriveInfo> {
}
