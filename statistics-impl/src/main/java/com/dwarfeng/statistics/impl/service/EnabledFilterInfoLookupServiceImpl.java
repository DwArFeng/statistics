package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.bean.entity.FilterInfo;
import com.dwarfeng.statistics.stack.cache.EnabledFilterInfoCache;
import com.dwarfeng.statistics.stack.dao.FilterInfoDao;
import com.dwarfeng.statistics.stack.service.EnabledFilterInfoLookupService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnabledFilterInfoLookupServiceImpl implements EnabledFilterInfoLookupService {

    private final FilterInfoDao dao;

    private final EnabledFilterInfoCache cache;

    private final ServiceExceptionMapper sem;

    @Value("${cache.timeout.key_list.enabled_filter_info}")
    private long timeout;

    public EnabledFilterInfoLookupServiceImpl(
            FilterInfoDao dao, EnabledFilterInfoCache cache, ServiceExceptionMapper sem
    ) {
        this.dao = dao;
        this.cache = cache;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<FilterInfo> getEnabledFilterInfos(LongIdKey statisticsSettingKey) throws ServiceException {
        try {
            if (cache.exists(statisticsSettingKey)) {
                return cache.get(statisticsSettingKey);
            }
            List<FilterInfo> lookup = dao.lookup(
                    FilterInfoMaintainServiceImpl.CHILD_FOR_STATISTICS_SETTING_ENABLED_INDEX_ASC,
                    new Object[]{statisticsSettingKey}
            );
            cache.set(statisticsSettingKey, lookup, timeout);
            return lookup;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询有效的过滤器信息时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
