package com.dwarfeng.statistics.impl.handler.bridge.hibernate.dao;

import com.dwarfeng.statistics.impl.handler.bridge.hibernate.bean.HibernateBridgeBridgeData;
import com.dwarfeng.statistics.impl.handler.bridge.hibernate.bean.HibernateBridgeHibernateBridgeData;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchBaseDao;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchWriteDao;
import com.dwarfeng.subgrade.impl.dao.HibernateEntireLookupDao;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class HibernateBridgeBridgeDataDaoImpl implements HibernateBridgeBridgeDataDao {

    private final HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, HibernateBridgeBridgeData,
            HibernateBridgeHibernateBridgeData> batchBaseDao;
    private final HibernateEntireLookupDao<HibernateBridgeBridgeData, HibernateBridgeHibernateBridgeData>
            entireLookupDao;
    private final PresetLookupDao<HibernateBridgeBridgeData> presetLookupDao;
    private final HibernateBatchWriteDao<HibernateBridgeBridgeData, HibernateBridgeHibernateBridgeData> batchWriteDao;

    public HibernateBridgeBridgeDataDaoImpl(
            HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, HibernateBridgeBridgeData,
                    HibernateBridgeHibernateBridgeData> batchBaseDao,
            HibernateEntireLookupDao<HibernateBridgeBridgeData, HibernateBridgeHibernateBridgeData> entireLookupDao,
            PresetLookupDao<HibernateBridgeBridgeData> presetLookupDao,
            HibernateBatchWriteDao<HibernateBridgeBridgeData, HibernateBridgeHibernateBridgeData> batchWriteDao
    ) {
        this.batchBaseDao = batchBaseDao;
        this.entireLookupDao = entireLookupDao;
        this.presetLookupDao = presetLookupDao;
        this.batchWriteDao = batchWriteDao;
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public LongIdKey insert(HibernateBridgeBridgeData element) throws DaoException {
        return batchBaseDao.insert(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public void update(HibernateBridgeBridgeData element) throws DaoException {
        batchBaseDao.update(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public void delete(LongIdKey key) throws DaoException {
        batchBaseDao.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public boolean exists(LongIdKey key) throws DaoException {
        return batchBaseDao.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public HibernateBridgeBridgeData get(LongIdKey key) throws DaoException {
        return batchBaseDao.get(key);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public List<LongIdKey> batchInsert(@SkipRecord List<HibernateBridgeBridgeData> elements) throws DaoException {
        return batchBaseDao.batchInsert(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public void batchUpdate(@SkipRecord List<HibernateBridgeBridgeData> elements) throws DaoException {
        batchBaseDao.batchUpdate(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public void batchDelete(@SkipRecord List<LongIdKey> keys) throws DaoException {
        batchBaseDao.batchDelete(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public boolean allExists(@SkipRecord List<LongIdKey> keys) throws DaoException {
        return batchBaseDao.allExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public boolean nonExists(@SkipRecord List<LongIdKey> keys) throws DaoException {
        return batchBaseDao.nonExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public List<HibernateBridgeBridgeData> batchGet(@SkipRecord List<LongIdKey> keys) throws DaoException {
        return batchBaseDao.batchGet(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public List<HibernateBridgeBridgeData> lookup() throws DaoException {
        return entireLookupDao.lookup();
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public List<HibernateBridgeBridgeData> lookup(PagingInfo pagingInfo) throws DaoException {
        return entireLookupDao.lookup(pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public int lookupCount() throws DaoException {
        return entireLookupDao.lookupCount();
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public List<HibernateBridgeBridgeData> lookup(String preset, Object[] objs) throws DaoException {
        return presetLookupDao.lookup(preset, objs);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public List<HibernateBridgeBridgeData> lookup(
            String preset, Object[] objs, PagingInfo pagingInfo
    ) throws DaoException {
        return presetLookupDao.lookup(preset, objs, pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public int lookupCount(String preset, Object[] objs) throws DaoException {
        return presetLookupDao.lookupCount(preset, objs);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public void write(HibernateBridgeBridgeData element) throws DaoException {
        batchWriteDao.write(element);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public void batchWrite(List<HibernateBridgeBridgeData> elements) throws DaoException {
        batchWriteDao.batchWrite(elements);
    }
}
