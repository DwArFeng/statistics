package com.dwarfeng.statistics.impl.handler.bridge.redis.service;

import com.dwarfeng.statistics.impl.handler.bridge.redis.bean.RedisBridgeBridgeData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;

/**
 * Redis 桥接器数据维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface RedisBridgeBridgeDataMaintainService extends BatchCrudService<LongIdKey, RedisBridgeBridgeData> {
}
