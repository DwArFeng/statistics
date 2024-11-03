package com.dwarfeng.statistics.impl.handler.bridge.redis.dao;

import com.dwarfeng.statistics.impl.handler.bridge.redis.bean.RedisBridgeBridgeData;
import com.dwarfeng.statistics.impl.handler.bridge.redis.bean.RedisBridgeBridgeDataKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;

/**
 * Redis 桥接器数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface RedisBridgeBridgeDataDao extends BatchBaseDao<RedisBridgeBridgeDataKey, RedisBridgeBridgeData> {
}
