package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.bean.entity.DriverInfo;
import com.dwarfeng.statistics.stack.cache.EnabledDriverInfoCache;
import com.dwarfeng.statistics.stack.dao.DriverInfoDao;
import com.dwarfeng.statistics.stack.service.EnabledDriverInfoLookupService;
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
public class EnabledDriverInfoLookupServiceImpl implements EnabledDriverInfoLookupService {

    private final DriverInfoDao dao;

    private final EnabledDriverInfoCache cache;

    private final ServiceExceptionMapper sem;

    @Value("${cache.timeout.key_list.enabled_driver_info}")
    private long timeout;

    public EnabledDriverInfoLookupServiceImpl(
            DriverInfoDao dao, EnabledDriverInfoCache cache, ServiceExceptionMapper sem
    ) {
        this.dao = dao;
        this.cache = cache;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<DriverInfo> getEnabledDriverInfos(LongIdKey statisticsSettingKey) throws ServiceException {
        try {
            if (cache.exists(statisticsSettingKey)) {
                return cache.get(statisticsSettingKey);
            }
            List<DriverInfo> lookup = dao.lookup(
                    DriverInfoMaintainServiceImpl.CHILD_FOR_STATISTICS_SETTING_ENABLED,
                    new Object[]{statisticsSettingKey}
            );
            cache.set(statisticsSettingKey, lookup, timeout);
            return lookup;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询有效的驱动器信息时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
