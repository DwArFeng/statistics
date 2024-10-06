package com.dwarfeng.statistics.stack.struct;

import com.dwarfeng.statistics.stack.bean.entity.FilterInfo;
import com.dwarfeng.statistics.stack.bean.entity.ProviderInfo;
import com.dwarfeng.statistics.stack.bean.entity.StatisticsSetting;
import com.dwarfeng.statistics.stack.handler.Filter;
import com.dwarfeng.statistics.stack.handler.Provider;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.List;
import java.util.Map;

/**
 * 运算信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ExecuteInfo {

    private final StatisticsSetting statisticsSetting;
    private final Map<LongIdKey, ProviderInfo> providerInfoMap;
    private final Map<LongIdKey, Provider> providerMap;
    private final Map<LongIdKey, FilterInfo> filterInfoMap;
    private final Map<LongIdKey, Filter> filterMap;
    private final List<LongIdKey> filterChainKeys;

    public ExecuteInfo(
            StatisticsSetting statisticsSetting,
            Map<LongIdKey, ProviderInfo> providerInfoMap,
            Map<LongIdKey, Provider> providerMap,
            Map<LongIdKey, FilterInfo> filterInfoMap,
            Map<LongIdKey, Filter> filterMap,
            List<LongIdKey> filterChainKeys
    ) {
        this.statisticsSetting = statisticsSetting;
        this.providerInfoMap = providerInfoMap;
        this.providerMap = providerMap;
        this.filterInfoMap = filterInfoMap;
        this.filterMap = filterMap;
        this.filterChainKeys = filterChainKeys;
    }

    public StatisticsSetting getStatisticsSetting() {
        return statisticsSetting;
    }

    public Map<LongIdKey, ProviderInfo> getProviderInfoMap() {
        return providerInfoMap;
    }

    public Map<LongIdKey, Provider> getProviderMap() {
        return providerMap;
    }

    public Map<LongIdKey, FilterInfo> getFilterInfoMap() {
        return filterInfoMap;
    }

    public Map<LongIdKey, Filter> getFilterMap() {
        return filterMap;
    }

    public List<LongIdKey> getFilterChainKeys() {
        return filterChainKeys;
    }

    @Override
    public String toString() {
        return "ExecuteInfo{" +
                "statisticsSetting=" + statisticsSetting +
                ", providerInfoMap=" + providerInfoMap +
                ", providerMap=" + providerMap +
                ", filterInfoMap=" + filterInfoMap +
                ", filterMap=" + filterMap +
                ", filterChainKeys=" + filterChainKeys +
                '}';
    }
}
