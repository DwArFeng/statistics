package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.bean.entity.ProviderInfo;
import com.dwarfeng.statistics.stack.cache.EnabledProviderInfoCache;
import com.dwarfeng.statistics.stack.dao.ProviderInfoDao;
import com.dwarfeng.statistics.stack.service.EnabledProviderInfoLookupService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnabledProviderInfoLookupServiceImpl implements EnabledProviderInfoLookupService {

    private final ProviderInfoDao dao;

    private final EnabledProviderInfoCache cache;

    private final ServiceExceptionMapper sem;

    @Value("${cache.timeout.key_list.enabled_provider_info}")
    private long timeout;

    public EnabledProviderInfoLookupServiceImpl(
            ProviderInfoDao dao, EnabledProviderInfoCache cache, ServiceExceptionMapper sem
    ) {
        this.dao = dao;
        this.cache = cache;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<ProviderInfo> getEnabledProviderInfos(LongIdKey statisticsSettingKey) throws ServiceException {
        try {
            if (cache.exists(statisticsSettingKey)) {
                return cache.get(statisticsSettingKey);
            }
            List<ProviderInfo> lookup = dao.lookup(
                    ProviderInfoMaintainServiceImpl.CHILD_FOR_STATISTICS_SETTING_ENABLED,
                    new Object[]{statisticsSettingKey}
            );
            cache.set(statisticsSettingKey, lookup, timeout);
            return lookup;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询有效的提供器信息时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
