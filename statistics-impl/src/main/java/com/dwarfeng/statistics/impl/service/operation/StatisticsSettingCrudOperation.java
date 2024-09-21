package com.dwarfeng.statistics.impl.service.operation;

import com.dwarfeng.statistics.stack.bean.entity.*;
import com.dwarfeng.statistics.stack.bean.key.VariableKey;
import com.dwarfeng.statistics.stack.cache.*;
import com.dwarfeng.statistics.stack.dao.*;
import com.dwarfeng.statistics.stack.service.*;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StatisticsSettingCrudOperation implements BatchCrudOperation<LongIdKey, StatisticsSetting> {

    private final StatisticsSettingDao statisticsSettingDao;
    private final StatisticsSettingCache statisticsSettingCache;

    private final StatisticsExecutionProfileDao statisticsExecutionProfileDao;
    private final StatisticsExecutionProfileCache statisticsExecutionProfileCache;

    private final DriverInfoDao driverInfoDao;
    private final DriverInfoCache driverInfoCache;

    private final ProviderInfoDao providerInfoDao;
    private final ProviderInfoCache providerInfoCache;

    private final FilterInfoDao filterInfoDao;
    private final FilterInfoCache filterInfoCache;

    private final VariableDao variableDao;
    private final VariableCache variableCache;

    private final TaskDao taskDao;
    private final TaskCache taskCache;

    private final TaskEventDao taskEventDao;
    private final TaskEventCache taskEventCache;

    private final HistoryTaskDao historyTaskDao;
    private final HistoryTaskCache historyTaskCache;

    private final HistoryTaskEventDao historyTaskEventDao;
    private final HistoryTaskEventCache historyTaskEventCache;

    @Value("${cache.timeout.entity.statistics_setting}")
    private long statisticsSettingTimeout;

    public StatisticsSettingCrudOperation(
            StatisticsSettingDao statisticsSettingDao,
            StatisticsSettingCache statisticsSettingCache,
            StatisticsExecutionProfileDao statisticsExecutionProfileDao,
            StatisticsExecutionProfileCache statisticsExecutionProfileCache,
            DriverInfoDao driverInfoDao,
            DriverInfoCache driverInfoCache,
            ProviderInfoDao providerInfoDao,
            ProviderInfoCache providerInfoCache,
            FilterInfoDao filterInfoDao,
            FilterInfoCache filterInfoCache,
            VariableDao variableDao,
            VariableCache variableCache,
            TaskDao taskDao,
            TaskCache taskCache,
            TaskEventDao taskEventDao,
            TaskEventCache taskEventCache,
            HistoryTaskDao historyTaskDao,
            HistoryTaskCache historyTaskCache,
            HistoryTaskEventDao historyTaskEventDao,
            HistoryTaskEventCache historyTaskEventCache
    ) {
        this.statisticsSettingDao = statisticsSettingDao;
        this.statisticsSettingCache = statisticsSettingCache;
        this.statisticsExecutionProfileDao = statisticsExecutionProfileDao;
        this.statisticsExecutionProfileCache = statisticsExecutionProfileCache;
        this.driverInfoDao = driverInfoDao;
        this.driverInfoCache = driverInfoCache;
        this.providerInfoDao = providerInfoDao;
        this.providerInfoCache = providerInfoCache;
        this.filterInfoDao = filterInfoDao;
        this.filterInfoCache = filterInfoCache;
        this.variableDao = variableDao;
        this.variableCache = variableCache;
        this.taskDao = taskDao;
        this.taskCache = taskCache;
        this.taskEventDao = taskEventDao;
        this.taskEventCache = taskEventCache;
        this.historyTaskDao = historyTaskDao;
        this.historyTaskCache = historyTaskCache;
        this.historyTaskEventDao = historyTaskEventDao;
        this.historyTaskEventCache = historyTaskEventCache;
    }

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return statisticsSettingCache.exists(key) || statisticsSettingDao.exists(key);
    }

    @Override
    public StatisticsSetting get(LongIdKey key) throws Exception {
        if (statisticsSettingCache.exists(key)) {
            return statisticsSettingCache.get(key);
        } else {
            if (!statisticsSettingDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            StatisticsSetting statisticsSetting = statisticsSettingDao.get(key);
            statisticsSettingCache.push(statisticsSetting, statisticsSettingTimeout);
            return statisticsSetting;
        }
    }

    @Override
    public LongIdKey insert(StatisticsSetting statisticsSetting) throws Exception {
        statisticsSettingCache.push(statisticsSetting, statisticsSettingTimeout);
        return statisticsSettingDao.insert(statisticsSetting);
    }

    @Override
    public void update(StatisticsSetting statisticsSetting) throws Exception {
        statisticsSettingCache.push(statisticsSetting, statisticsSettingTimeout);
        statisticsSettingDao.update(statisticsSetting);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        // 删除与 统计设置 相关的 统计执行简报。
        if (statisticsExecutionProfileDao.exists(key)) {
            statisticsExecutionProfileDao.delete(key);
            statisticsExecutionProfileCache.delete(key);
        }

        // 删除与 统计设置 相关的 驱动器信息。
        List<LongIdKey> driverInfoKeys = driverInfoDao.lookup(
                DriverInfoMaintainService.CHILD_FOR_STATISTICS_SETTING, new Object[]{key}
        ).stream().map(DriverInfo::getKey).collect(Collectors.toList());
        driverInfoDao.batchDelete(driverInfoKeys);
        driverInfoCache.batchDelete(driverInfoKeys);

        // 删除与 统计设置 相关的 提供器信息。
        List<LongIdKey> providerInfoKeys = providerInfoDao.lookup(
                ProviderInfoMaintainService.CHILD_FOR_STATISTICS_SETTING, new Object[]{key}
        ).stream().map(ProviderInfo::getKey).collect(Collectors.toList());
        providerInfoDao.batchDelete(providerInfoKeys);
        providerInfoCache.batchDelete(providerInfoKeys);

        // 删除与 统计设置 相关的 过滤器信息。
        List<LongIdKey> filterInfoKeys = filterInfoDao.lookup(
                FilterInfoMaintainService.CHILD_FOR_STATISTICS_SETTING, new Object[]{key}
        ).stream().map(FilterInfo::getKey).collect(Collectors.toList());
        filterInfoDao.batchDelete(filterInfoKeys);
        filterInfoCache.batchDelete(filterInfoKeys);

        // 删除与 统计设置 相关的 变量。
        List<VariableKey> variableKeys = variableDao.lookup(
                VariableMaintainService.CHILD_FOR_STATISTICS_SETTING, new Object[]{key}
        ).stream().map(Variable::getKey).collect(Collectors.toList());
        variableDao.batchDelete(variableKeys);
        variableCache.batchDelete(variableKeys);

        // 删除与 统计设置 相关的 任务事件。
        List<LongIdKey> taskEventKeys = taskEventDao.lookup(
                TaskEventMaintainService.CHILD_FOR_STATISTICS_SETTING, new Object[]{key}
        ).stream().map(TaskEvent::getKey).collect(Collectors.toList());
        taskEventDao.batchDelete(taskEventKeys);
        taskEventCache.batchDelete(taskEventKeys);

        // 删除与 统计设置 相关的 任务。
        List<LongIdKey> taskKeys = taskDao.lookup(
                TaskMaintainService.CHILD_FOR_STATISTICS_SETTING, new Object[]{key}
        ).stream().map(Task::getKey).collect(Collectors.toList());
        taskDao.batchDelete(taskKeys);
        taskCache.batchDelete(taskKeys);

        // 删除与 统计设置 相关的 历史任务事件。
        List<LongIdKey> historyTaskEventKeys = historyTaskEventDao.lookup(
                HistoryTaskEventMaintainService.CHILD_FOR_STATISTICS_SETTING, new Object[]{key}
        ).stream().map(HistoryTaskEvent::getKey).collect(Collectors.toList());
        historyTaskEventDao.batchDelete(historyTaskEventKeys);
        historyTaskEventCache.batchDelete(historyTaskEventKeys);

        // 删除与 统计设置 相关的 历史任务。
        List<LongIdKey> historyTaskKeys = historyTaskDao.lookup(
                HistoryTaskMaintainService.CHILD_FOR_STATISTICS_SETTING, new Object[]{key}
        ).stream().map(HistoryTask::getKey).collect(Collectors.toList());
        historyTaskDao.batchDelete(historyTaskKeys);
        historyTaskCache.batchDelete(historyTaskKeys);

        // 删除 统计设置 自身。
        statisticsSettingDao.delete(key);
        statisticsSettingCache.delete(key);
    }

    @Override
    public boolean allExists(List<LongIdKey> keys) throws Exception {
        return statisticsSettingCache.allExists(keys) || statisticsSettingDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<LongIdKey> keys) throws Exception {
        return statisticsSettingCache.nonExists(keys) && statisticsSettingDao.nonExists(keys);
    }

    @Override
    public List<StatisticsSetting> batchGet(List<LongIdKey> keys) throws Exception {
        if (statisticsSettingCache.allExists(keys)) {
            return statisticsSettingCache.batchGet(keys);
        } else {
            if (!statisticsSettingDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<StatisticsSetting> statisticsSettings = statisticsSettingDao.batchGet(keys);
            statisticsSettingCache.batchPush(statisticsSettings, statisticsSettingTimeout);
            return statisticsSettings;
        }
    }

    @Override
    public List<LongIdKey> batchInsert(List<StatisticsSetting> statisticsSettings) throws Exception {
        statisticsSettingCache.batchPush(statisticsSettings, statisticsSettingTimeout);
        return statisticsSettingDao.batchInsert(statisticsSettings);
    }

    @Override
    public void batchUpdate(List<StatisticsSetting> statisticsSettings) throws Exception {
        statisticsSettingCache.batchPush(statisticsSettings, statisticsSettingTimeout);
        statisticsSettingDao.batchUpdate(statisticsSettings);
    }

    @Override
    public void batchDelete(List<LongIdKey> keys) throws Exception {
        for (LongIdKey key : keys) {
            delete(key);
        }
    }
}
