package com.dwarfeng.statistics.impl.handler.bridge.redis.dao;

import com.dwarfeng.statistics.impl.handler.bridge.redis.bean.RedisBridgeBridgeData;
import com.dwarfeng.statistics.impl.handler.bridge.redis.bean.RedisBridgeBridgeDataKey;
import com.dwarfeng.statistics.impl.handler.bridge.redis.bean.RedisBridgeFastJsonBridgeData;
import com.dwarfeng.subgrade.impl.dao.RedisBatchBaseDao;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RedisBridgeBridgeDataDaoImpl implements RedisBridgeBridgeDataDao {

    private final RedisBatchBaseDao<RedisBridgeBridgeDataKey, RedisBridgeBridgeData, RedisBridgeFastJsonBridgeData>
            batchBaseDao;

    public RedisBridgeBridgeDataDaoImpl(
            RedisBatchBaseDao<RedisBridgeBridgeDataKey, RedisBridgeBridgeData, RedisBridgeFastJsonBridgeData>
                    batchBaseDao
    ) {
        this.batchBaseDao = batchBaseDao;
    }

    @Override
    @BehaviorAnalyse
    public RedisBridgeBridgeDataKey insert(RedisBridgeBridgeData element) throws DaoException {
        return batchBaseDao.insert(element);
    }

    @Override
    @BehaviorAnalyse
    public void update(RedisBridgeBridgeData element) throws DaoException {
        batchBaseDao.update(element);
    }

    @Override
    @BehaviorAnalyse
    public void delete(RedisBridgeBridgeDataKey key) throws DaoException {
        batchBaseDao.delete(key);
    }

    @Override
    @BehaviorAnalyse
    public boolean exists(RedisBridgeBridgeDataKey key) throws DaoException {
        return batchBaseDao.exists(key);
    }

    @Override
    @BehaviorAnalyse
    public RedisBridgeBridgeData get(RedisBridgeBridgeDataKey key) throws DaoException {
        return batchBaseDao.get(key);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<RedisBridgeBridgeDataKey> batchInsert(@SkipRecord List<RedisBridgeBridgeData> elements)
            throws DaoException {
        return batchBaseDao.batchInsert(elements);
    }

    @Override
    @BehaviorAnalyse
    public void batchUpdate(@SkipRecord List<RedisBridgeBridgeData> elements) throws DaoException {
        batchBaseDao.batchUpdate(elements);
    }

    @Override
    @BehaviorAnalyse
    public void batchDelete(@SkipRecord List<RedisBridgeBridgeDataKey> keys) throws DaoException {
        batchBaseDao.batchDelete(keys);
    }

    @Override
    @BehaviorAnalyse
    public boolean allExists(@SkipRecord List<RedisBridgeBridgeDataKey> keys) throws DaoException {
        return batchBaseDao.allExists(keys);
    }

    @Override
    @BehaviorAnalyse
    public boolean nonExists(@SkipRecord List<RedisBridgeBridgeDataKey> keys) throws DaoException {
        return batchBaseDao.nonExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<RedisBridgeBridgeData> batchGet(@SkipRecord List<RedisBridgeBridgeDataKey> keys) throws DaoException {
        return batchBaseDao.batchGet(keys);
    }
}
