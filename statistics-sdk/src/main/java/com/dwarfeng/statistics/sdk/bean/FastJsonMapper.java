package com.dwarfeng.statistics.sdk.bean;

import com.dwarfeng.statistics.sdk.bean.entity.*;
import com.dwarfeng.statistics.sdk.bean.key.FastJsonVariableKey;
import com.dwarfeng.statistics.stack.bean.entity.*;
import com.dwarfeng.statistics.stack.bean.key.VariableKey;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * FastJson Bean 映射器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Mapper
public interface FastJsonMapper {

    FastJsonLongIdKey longIdKeyToFastJson(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromFastJson(FastJsonLongIdKey fastJsonLongIdKey);

    FastJsonStringIdKey stringIdKeyToFastJson(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromFastJson(FastJsonStringIdKey fastJsonStringIdKey);

    FastJsonVariableKey variableKeyToFastJson(VariableKey variableKey);

    @InheritInverseConfiguration
    VariableKey variableKeyFromFastJson(FastJsonVariableKey fastJsonVariableKey);

    FastJsonStatisticsSetting statisticsSettingToFastJson(StatisticsSetting statisticsSetting);

    @InheritInverseConfiguration
    StatisticsSetting statisticsSettingFromFastJson(FastJsonStatisticsSetting fastJsonStatisticsSetting);

    FastJsonStatisticsExecutionProfile statisticsExecutionProfileToFastJson(
            StatisticsExecutionProfile statisticsExecutionProfile
    );

    @InheritInverseConfiguration
    StatisticsExecutionProfile statisticsExecutionProfileFromFastJson(
            FastJsonStatisticsExecutionProfile fastJsonStatisticsExecutionProfile
    );

    FastJsonVariable variableToFastJson(Variable variable);

    @InheritInverseConfiguration
    Variable variableFromFastJson(FastJsonVariable fastJsonVariable);

    FastJsonDriverInfo driverInfoToFastJson(DriverInfo driverInfo);

    @InheritInverseConfiguration
    DriverInfo driverInfoFromFastJson(FastJsonDriverInfo fastJsonDriverInfo);

    FastJsonFilterInfo filterInfoToFastJson(FilterInfo filterInfo);

    @InheritInverseConfiguration
    FilterInfo filterInfoFromFastJson(FastJsonFilterInfo fastJsonFilterInfo);

    FastJsonProviderInfo providerInfoToFastJson(ProviderInfo providerInfo);

    @InheritInverseConfiguration
    ProviderInfo providerInfoFromFastJson(FastJsonProviderInfo fastJsonProviderInfo);

    FastJsonDriverSupport driverSupportToFastJson(DriverSupport driverSupport);

    @InheritInverseConfiguration
    DriverSupport driverSupportFromFastJson(FastJsonDriverSupport fastJsonDriverSupport);

    FastJsonFilterSupport filterSupportToFastJson(FilterSupport filterSupport);

    @InheritInverseConfiguration
    FilterSupport filterSupportFromFastJson(FastJsonFilterSupport fastJsonFilterSupport);

    FastJsonProviderSupport providerSupportToFastJson(ProviderSupport providerSupport);

    @InheritInverseConfiguration
    ProviderSupport providerSupportFromFastJson(FastJsonProviderSupport fastJsonProviderSupport);

    FastJsonMapperSupport mapperSupportToFastJson(MapperSupport mapperSupport);

    @InheritInverseConfiguration
    MapperSupport mapperSupportFromFastJson(FastJsonMapperSupport fastJsonMapperSupport);

    FastJsonTask taskToFastJson(Task task);

    @InheritInverseConfiguration
    Task taskFromFastJson(FastJsonTask fastJsonTask);

    FastJsonTaskEvent taskEventToFastJson(TaskEvent taskEvent);

    @InheritInverseConfiguration
    TaskEvent taskEventFromFastJson(FastJsonTaskEvent fastJsonTaskEvent);

    FastJsonHistoryTask historyTaskToFastJson(HistoryTask historyTask);

    @InheritInverseConfiguration
    HistoryTask historyTaskFromFastJson(FastJsonHistoryTask fastJsonHistoryTask);

    FastJsonHistoryTaskEvent historyTaskEventToFastJson(HistoryTaskEvent historyTaskEvent);

    @InheritInverseConfiguration
    HistoryTaskEvent historyTaskEventFromFastJson(FastJsonHistoryTaskEvent fastJsonHistoryTaskEvent);
}
