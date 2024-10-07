package com.dwarfeng.statistics.impl.handler.bridge.hibernate.dao;

import com.dwarfeng.statistics.impl.handler.bridge.hibernate.bean.HibernateBridgeBridgeData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.BatchWriteDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * Hibernate 桥接器数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface HibernateBridgeBridgeDataDao extends BatchBaseDao<LongIdKey, HibernateBridgeBridgeData>,
        EntireLookupDao<HibernateBridgeBridgeData>, PresetLookupDao<HibernateBridgeBridgeData>,
        BatchWriteDao<HibernateBridgeBridgeData> {
}
