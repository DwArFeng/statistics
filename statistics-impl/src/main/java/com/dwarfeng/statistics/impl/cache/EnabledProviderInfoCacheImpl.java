package com.dwarfeng.statistics.impl.cache;

import com.dwarfeng.statistics.sdk.bean.entity.FastJsonProviderInfo;
import com.dwarfeng.statistics.stack.bean.entity.ProviderInfo;
import com.dwarfeng.statistics.stack.cache.EnabledProviderInfoCache;
import com.dwarfeng.subgrade.impl.cache.RedisKeyListCache;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.CacheException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
public class EnabledProviderInfoCacheImpl implements EnabledProviderInfoCache {

    private final RedisKeyListCache<LongIdKey, ProviderInfo, FastJsonProviderInfo> listCache;

    public EnabledProviderInfoCacheImpl(
            RedisKeyListCache<LongIdKey, ProviderInfo, FastJsonProviderInfo> listCache
    ) {
        this.listCache = listCache;
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean exists(LongIdKey key) throws CacheException {
        return listCache.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public int size(LongIdKey key) throws CacheException {
        return listCache.size(key);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<ProviderInfo> get(LongIdKey key) throws CacheException {
        return listCache.get(key);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<ProviderInfo> get(LongIdKey key, int beginIndex, int maxEntity) throws CacheException {
        return listCache.get(key, beginIndex, maxEntity);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<ProviderInfo> get(LongIdKey key, PagingInfo pagingInfo) throws CacheException {
        return listCache.get(key, pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void set(LongIdKey key, @SkipRecord Collection<ProviderInfo> entities, long timeout) throws CacheException {
        listCache.set(key, entities, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void leftPush(LongIdKey key, @SkipRecord Collection<ProviderInfo> entities, long timeout) throws CacheException {
        listCache.leftPush(key, entities, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void rightPush(LongIdKey key, @SkipRecord Collection<ProviderInfo> entities, long timeout) throws CacheException {
        listCache.rightPush(key, entities, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void delete(LongIdKey key) throws CacheException {
        listCache.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void clear() throws CacheException {
        listCache.clear();
    }
}
