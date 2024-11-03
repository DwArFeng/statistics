package com.dwarfeng.statistics.impl.handler.bridge.redis.service;

import com.dwarfeng.statistics.impl.handler.bridge.redis.bean.RedisBridgeBridgeData;
import com.dwarfeng.statistics.impl.handler.bridge.redis.bean.RedisBridgeBridgeDataKey;
import com.dwarfeng.subgrade.impl.service.DaoOnlyBatchCrudService;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisBridgeBridgeDataMaintainServiceImpl implements RedisBridgeBridgeDataMaintainService {

    private final DaoOnlyBatchCrudService<RedisBridgeBridgeDataKey, RedisBridgeBridgeData> batchCrudService;

    public RedisBridgeBridgeDataMaintainServiceImpl(
            DaoOnlyBatchCrudService<RedisBridgeBridgeDataKey, RedisBridgeBridgeData> batchCrudService
    ) {
        this.batchCrudService = batchCrudService;
    }

    @Override
    @BehaviorAnalyse
    public boolean exists(RedisBridgeBridgeDataKey key) throws ServiceException {
        return batchCrudService.exists(key);
    }

    @Override
    @BehaviorAnalyse
    public RedisBridgeBridgeData get(RedisBridgeBridgeDataKey key) throws ServiceException {
        return batchCrudService.get(key);
    }

    @Override
    @BehaviorAnalyse
    public RedisBridgeBridgeDataKey insert(RedisBridgeBridgeData element) throws ServiceException {
        return batchCrudService.insert(element);
    }

    @Override
    @BehaviorAnalyse
    public void update(RedisBridgeBridgeData element) throws ServiceException {
        batchCrudService.update(element);
    }

    @Override
    @BehaviorAnalyse
    public void delete(RedisBridgeBridgeDataKey key) throws ServiceException {
        batchCrudService.delete(key);
    }

    @Override
    @BehaviorAnalyse
    public RedisBridgeBridgeData getIfExists(RedisBridgeBridgeDataKey key) throws ServiceException {
        return batchCrudService.getIfExists(key);
    }

    @Override
    @BehaviorAnalyse
    public RedisBridgeBridgeDataKey insertIfNotExists(RedisBridgeBridgeData element) throws ServiceException {
        return batchCrudService.insertIfNotExists(element);
    }

    @Override
    @BehaviorAnalyse
    public void updateIfExists(RedisBridgeBridgeData element) throws ServiceException {
        batchCrudService.updateIfExists(element);
    }

    @Override
    @BehaviorAnalyse
    public void deleteIfExists(RedisBridgeBridgeDataKey key) throws ServiceException {
        batchCrudService.deleteIfExists(key);
    }

    @Override
    @BehaviorAnalyse
    public RedisBridgeBridgeDataKey insertOrUpdate(RedisBridgeBridgeData element) throws ServiceException {
        return batchCrudService.insertOrUpdate(element);
    }

    @Override
    @BehaviorAnalyse
    public boolean allExists(@SkipRecord List<RedisBridgeBridgeDataKey> keys) throws ServiceException {
        return batchCrudService.allExists(keys);
    }

    @Override
    @BehaviorAnalyse
    public boolean nonExists(@SkipRecord List<RedisBridgeBridgeDataKey> keys) throws ServiceException {
        return batchCrudService.nonExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<RedisBridgeBridgeData> batchGet(
            @SkipRecord List<RedisBridgeBridgeDataKey> keys
    ) throws ServiceException {
        return batchCrudService.batchGet(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<RedisBridgeBridgeDataKey> batchInsert(
            @SkipRecord List<RedisBridgeBridgeData> elements
    ) throws ServiceException {
        return batchCrudService.batchInsert(elements);
    }

    @Override
    @BehaviorAnalyse
    public void batchUpdate(@SkipRecord List<RedisBridgeBridgeData> elements) throws ServiceException {
        batchCrudService.batchUpdate(elements);
    }

    @Override
    @BehaviorAnalyse
    public void batchDelete(@SkipRecord List<RedisBridgeBridgeDataKey> keys) throws ServiceException {
        batchCrudService.batchDelete(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<RedisBridgeBridgeData> batchGetIfExists(
            @SkipRecord List<RedisBridgeBridgeDataKey> keys
    ) throws ServiceException {
        return batchCrudService.batchGetIfExists(keys);
    }

    @Deprecated
    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<RedisBridgeBridgeDataKey> batchInsertIfExists(
            @SkipRecord List<RedisBridgeBridgeData> elements
    ) throws ServiceException {
        return batchCrudService.batchInsertIfExists(elements);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<RedisBridgeBridgeDataKey> batchInsertIfNotExists(
            @SkipRecord List<RedisBridgeBridgeData> elements
    ) throws ServiceException {
        return batchCrudService.batchInsertIfNotExists(elements);
    }

    @Override
    @BehaviorAnalyse
    public void batchUpdateIfExists(@SkipRecord List<RedisBridgeBridgeData> elements) throws ServiceException {
        batchCrudService.batchUpdateIfExists(elements);
    }

    @Override
    @BehaviorAnalyse
    public void batchDeleteIfExists(@SkipRecord List<RedisBridgeBridgeDataKey> keys) throws ServiceException {
        batchCrudService.batchDeleteIfExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<RedisBridgeBridgeDataKey> batchInsertOrUpdate(
            @SkipRecord List<RedisBridgeBridgeData> elements
    ) throws ServiceException {
        return batchCrudService.batchInsertOrUpdate(elements);
    }
}
