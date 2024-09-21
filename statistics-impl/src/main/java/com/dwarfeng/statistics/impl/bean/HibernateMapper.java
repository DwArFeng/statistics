package com.dwarfeng.statistics.impl.bean;

import com.dwarfeng.statistics.impl.bean.entity.*;
import com.dwarfeng.statistics.impl.bean.key.HibernateVariableKey;
import com.dwarfeng.statistics.stack.bean.entity.*;
import com.dwarfeng.statistics.stack.bean.key.VariableKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Hibernate Bean 映射器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Mapper
public interface HibernateMapper {

    HibernateLongIdKey longIdKeyToHibernate(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromHibernate(HibernateLongIdKey hibernateLongIdKey);

    HibernateStringIdKey stringIdKeyToHibernate(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromHibernate(HibernateStringIdKey hibernateStringIdKey);

    HibernateVariableKey variableKeyToHibernate(VariableKey variableKey);

    @InheritInverseConfiguration
    VariableKey variableKeyFromHibernate(HibernateVariableKey hibernateVariableKey);

    @Mapping(target = "variables", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "statisticsExecutionProfile", ignore = true)
    @Mapping(target = "providerInfos", ignore = true)
    @Mapping(target = "longId", ignore = true)
    @Mapping(target = "historyTasks", ignore = true)
    @Mapping(target = "filterInfos", ignore = true)
    @Mapping(target = "driverInfos", ignore = true)
    HibernateStatisticsSetting statisticsSettingToHibernate(StatisticsSetting statisticsSetting);

    @InheritInverseConfiguration
    StatisticsSetting statisticsSettingFromHibernate(HibernateStatisticsSetting hibernateStatisticsSetting);

    @Mapping(target = "statisticsSetting", ignore = true)
    @Mapping(target = "longId", ignore = true)
    HibernateStatisticsExecutionProfile statisticsExecutionProfileToHibernate(
            StatisticsExecutionProfile statisticsExecutionProfile
    );

    @InheritInverseConfiguration
    StatisticsExecutionProfile statisticsExecutionProfileFromHibernate(
            HibernateStatisticsExecutionProfile hibernateStatisticsExecutionProfile
    );

    @Mapping(target = "variableStringId", ignore = true)
    @Mapping(target = "statisticsSettingLongId", ignore = true)
    @Mapping(target = "statisticsSetting", ignore = true)
    HibernateVariable variableToHibernate(Variable variable);

    @InheritInverseConfiguration
    Variable variableFromHibernate(HibernateVariable hibernateVariable);

    @Mapping(target = "statisticsSettingLongId", ignore = true)
    @Mapping(target = "statisticsSetting", ignore = true)
    @Mapping(target = "longId", ignore = true)
    HibernateDriverInfo driverInfoToHibernate(DriverInfo driverInfo);

    @InheritInverseConfiguration
    DriverInfo driverInfoFromHibernate(HibernateDriverInfo hibernateDriverInfo);

    @Mapping(target = "statisticsSettingLongId", ignore = true)
    @Mapping(target = "statisticsSetting", ignore = true)
    @Mapping(target = "longId", ignore = true)
    HibernateFilterInfo filterInfoToHibernate(FilterInfo filterInfo);

    @InheritInverseConfiguration
    FilterInfo filterInfoFromHibernate(HibernateFilterInfo hibernateFilterInfo);

    @Mapping(target = "statisticsSettingLongId", ignore = true)
    @Mapping(target = "statisticsSetting", ignore = true)
    @Mapping(target = "longId", ignore = true)
    HibernateProviderInfo providerInfoToHibernate(ProviderInfo providerInfo);

    @InheritInverseConfiguration
    ProviderInfo providerInfoFromHibernate(HibernateProviderInfo hibernateProviderInfo);

    @Mapping(target = "stringId", ignore = true)
    HibernateDriverSupport driverSupportToHibernate(DriverSupport driverSupport);

    @InheritInverseConfiguration
    DriverSupport driverSupportFromHibernate(HibernateDriverSupport hibernateDriverSupport);

    @Mapping(target = "stringId", ignore = true)
    HibernateFilterSupport filterSupportToHibernate(FilterSupport filterSupport);

    @InheritInverseConfiguration
    FilterSupport filterSupportFromHibernate(HibernateFilterSupport hibernateFilterSupport);

    @Mapping(target = "stringId", ignore = true)
    HibernateProviderSupport providerSupportToHibernate(ProviderSupport providerSupport);

    @InheritInverseConfiguration
    ProviderSupport providerSupportFromHibernate(HibernateProviderSupport hibernateProviderSupport);

    @Mapping(target = "stringId", ignore = true)
    HibernateMapperSupport mapperSupportToHibernate(MapperSupport mapperSupport);

    @InheritInverseConfiguration
    MapperSupport mapperSupportFromHibernate(HibernateMapperSupport hibernateMapperSupport);

    @Mapping(target = "taskEvents", ignore = true)
    @Mapping(target = "statisticsSettingLongId", ignore = true)
    @Mapping(target = "statisticsSetting", ignore = true)
    @Mapping(target = "longId", ignore = true)
    HibernateTask taskToHibernate(Task task);

    @InheritInverseConfiguration
    Task taskFromHibernate(HibernateTask hibernateTask);

    @Mapping(target = "taskLongId", ignore = true)
    @Mapping(target = "task", ignore = true)
    @Mapping(target = "longId", ignore = true)
    HibernateTaskEvent taskEventToHibernate(TaskEvent taskEvent);

    @InheritInverseConfiguration
    TaskEvent taskEventFromHibernate(HibernateTaskEvent hibernateTaskEvent);

    @Mapping(target = "taskEventHistories", ignore = true)
    @Mapping(target = "statisticsSettingLongId", ignore = true)
    @Mapping(target = "statisticsSetting", ignore = true)
    @Mapping(target = "longId", ignore = true)
    HibernateHistoryTask historyTaskToHibernate(HistoryTask historyTask);

    @InheritInverseConfiguration
    HistoryTask historyTaskFromHibernate(HibernateHistoryTask hibernateHistoryTask);

    @Mapping(target = "longId", ignore = true)
    @Mapping(target = "historyTaskLongId", ignore = true)
    @Mapping(target = "historyTask", ignore = true)
    HibernateHistoryTaskEvent historyTaskEventToHibernate(HistoryTaskEvent historyTaskEvent);

    @InheritInverseConfiguration
    HistoryTaskEvent historyTaskEventFromHibernate(HibernateHistoryTaskEvent hibernateHistoryTaskEvent);
}
