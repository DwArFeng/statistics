package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.stack.bean.entity.DriverInfo;
import com.dwarfeng.statistics.stack.bean.entity.StatisticsSetting;
import com.dwarfeng.statistics.stack.handler.DriveLocalCacheHandler;
import com.dwarfeng.statistics.stack.handler.Driver;
import com.dwarfeng.statistics.stack.handler.DriverHandler;
import com.dwarfeng.statistics.stack.service.EnabledDriverInfoLookupService;
import com.dwarfeng.statistics.stack.service.StatisticsSettingMaintainService;
import com.dwarfeng.statistics.stack.struct.DriveInfo;
import com.dwarfeng.subgrade.impl.handler.Fetcher;
import com.dwarfeng.subgrade.impl.handler.GeneralLocalCacheHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DriveLocalCacheHandlerImpl implements DriveLocalCacheHandler {

    private final GeneralLocalCacheHandler<LongIdKey, DriveInfo> handler;

    public DriveLocalCacheHandlerImpl(DriveInfoFetcher driveInfoFetcher) {
        handler = new GeneralLocalCacheHandler<>(driveInfoFetcher);
    }

    @BehaviorAnalyse
    @Override
    public boolean exists(LongIdKey key) throws HandlerException {
        return handler.exists(key);
    }

    @BehaviorAnalyse
    @Override
    public DriveInfo get(LongIdKey key) throws HandlerException {
        return handler.get(key);
    }

    @BehaviorAnalyse
    @Override
    public boolean remove(LongIdKey key) {
        return handler.remove(key);
    }

    @BehaviorAnalyse
    @Override
    public void clear() {
        handler.clear();
    }

    @Component
    public static class DriveInfoFetcher implements Fetcher<LongIdKey, DriveInfo> {

        private final StatisticsSettingMaintainService statisticsSettingMaintainService;
        private final EnabledDriverInfoLookupService enabledDriverInfoLookupService;

        private final DriverHandler driverHandler;

        public DriveInfoFetcher(
                StatisticsSettingMaintainService statisticsSettingMaintainService,
                EnabledDriverInfoLookupService enabledDriverInfoLookupService,
                DriverHandler driverHandler
        ) {
            this.statisticsSettingMaintainService = statisticsSettingMaintainService;
            this.enabledDriverInfoLookupService = enabledDriverInfoLookupService;
            this.driverHandler = driverHandler;
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public boolean exists(LongIdKey key) throws Exception {
            return statisticsSettingMaintainService.exists(key);
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public DriveInfo fetch(LongIdKey key) throws Exception {
            StatisticsSetting statisticsSetting = statisticsSettingMaintainService.get(key);
            List<DriverInfo> driverInfos = enabledDriverInfoLookupService.getEnabledDriverInfos(key);

            Map<DriverInfo, Driver> driverMap = new HashMap<>();

            for (DriverInfo driverInfo : driverInfos) {
                driverMap.put(driverInfo, driverHandler.find(driverInfo.getType()));
            }

            return new DriveInfo(statisticsSetting, driverMap);
        }
    }
}
