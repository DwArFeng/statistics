package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.stack.bean.entity.FilterInfo;
import com.dwarfeng.statistics.stack.bean.entity.ProviderInfo;
import com.dwarfeng.statistics.stack.bean.entity.StatisticsSetting;
import com.dwarfeng.statistics.stack.handler.*;
import com.dwarfeng.statistics.stack.service.FilterInfoMaintainService;
import com.dwarfeng.statistics.stack.service.ProviderInfoMaintainService;
import com.dwarfeng.statistics.stack.service.StatisticsSettingMaintainService;
import com.dwarfeng.statistics.stack.struct.ExecuteInfo;
import com.dwarfeng.subgrade.impl.handler.Fetcher;
import com.dwarfeng.subgrade.impl.handler.GeneralLocalCacheHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExecuteLocalCacheHandlerImpl implements ExecuteLocalCacheHandler {

    private final GeneralLocalCacheHandler<LongIdKey, ExecuteInfo> handler;

    public ExecuteLocalCacheHandlerImpl(ExecuteInfoFetcher ExecuteInfoFetcher) {
        this.handler = new GeneralLocalCacheHandler<>(ExecuteInfoFetcher);
    }

    @BehaviorAnalyse
    @Override
    public boolean exists(LongIdKey key) throws HandlerException {
        return handler.exists(key);
    }

    @BehaviorAnalyse
    @Override
    public ExecuteInfo get(LongIdKey key) throws HandlerException {
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
    public static class ExecuteInfoFetcher implements Fetcher<LongIdKey, ExecuteInfo> {

        private final StatisticsSettingMaintainService statisticsSettingMaintainService;
        private final ProviderInfoMaintainService providerInfoMaintainService;
        private final FilterInfoMaintainService filterInfoMaintainService;

        private final ProviderHandler providerHandler;
        private final FilterHandler filterHandler;

        public ExecuteInfoFetcher(
                StatisticsSettingMaintainService statisticsSettingMaintainService,
                ProviderInfoMaintainService providerInfoMaintainService,
                FilterInfoMaintainService filterInfoMaintainService,
                ProviderHandler providerHandler,
                FilterHandler filterHandler
        ) {
            this.statisticsSettingMaintainService = statisticsSettingMaintainService;
            this.providerInfoMaintainService = providerInfoMaintainService;
            this.filterInfoMaintainService = filterInfoMaintainService;
            this.providerHandler = providerHandler;
            this.filterHandler = filterHandler;
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
        public ExecuteInfo fetch(LongIdKey key) throws Exception {
            StatisticsSetting statisticsSetting = statisticsSettingMaintainService.get(key);
            Map<LongIdKey, ProviderInfo> providerInfoMap = new HashMap<>();
            Map<LongIdKey, Provider> providerMap = new HashMap<>();
            List<ProviderInfo> providerInfos = providerInfoMaintainService.lookupAsList(
                    ProviderInfoMaintainService.CHILD_FOR_STATISTICS_SETTING_ENABLED, new Object[]{key}
            );
            for (ProviderInfo providerInfo : providerInfos) {
                providerInfoMap.put(providerInfo.getKey(), providerInfo);
                Provider provider = providerHandler.make(providerInfo.getType(), providerInfo.getParam());
                providerMap.put(providerInfo.getKey(), provider);
            }
            Map<LongIdKey, FilterInfo> filterInfoMap = new HashMap<>();
            Map<LongIdKey, Filter> filterMap = new HashMap<>();
            List<LongIdKey> filterChainKeys = new ArrayList<>();
            List<FilterInfo> filterInfos = filterInfoMaintainService.lookupAsList(
                    FilterInfoMaintainService.CHILD_FOR_STATISTICS_SETTING_ENABLED_INDEX_ASC, new Object[]{key}
            );
            for (FilterInfo filterInfo : filterInfos) {
                filterInfoMap.put(filterInfo.getKey(), filterInfo);
                Filter filter = filterHandler.make(filterInfo.getType(), filterInfo.getParam());
                filterMap.put(filterInfo.getKey(), filter);
                filterChainKeys.add(filterInfo.getKey());
            }
            return new ExecuteInfo(
                    statisticsSetting, providerInfoMap, providerMap, filterInfoMap, filterMap, filterChainKeys
            );
        }
    }
}
