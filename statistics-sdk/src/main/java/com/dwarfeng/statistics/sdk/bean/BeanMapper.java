package com.dwarfeng.statistics.sdk.bean;

import com.dwarfeng.statistics.sdk.bean.entity.*;
import com.dwarfeng.statistics.sdk.bean.key.*;
import com.dwarfeng.statistics.stack.bean.entity.*;
import com.dwarfeng.statistics.stack.bean.key.BridgeDataKey;
import com.dwarfeng.statistics.stack.bean.key.VariableKey;
import com.dwarfeng.subgrade.sdk.bean.key.*;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * Bean 映射器。
 *
 * <p>
 * 该映射器中包含了 <code>sdk</code> 模块中所有实体与 <code>stack</code> 模块中对应实体的映射方法。
 *
 * @author wusn
 * @since 1.2.1.a
 */
@Mapper
public interface BeanMapper {

    // -----------------------------------------------------------Subgrade Key-----------------------------------------------------------
    FastJsonLongIdKey longIdKeyToFastJson(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromFastJson(FastJsonLongIdKey fastJsonLongIdKey);

    FastJsonStringIdKey stringIdKeyToFastJson(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromFastJson(FastJsonStringIdKey fastJsonStringIdKey);

    JSFixedFastJsonLongIdKey longIdKeyToJSFixedFastJson(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromJSFixedFastJson(JSFixedFastJsonLongIdKey jSFixedFastJsonLongIdKey);

    WebInputLongIdKey longIdKeyToWebInput(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromWebInput(WebInputLongIdKey webInputLongIdKey);

    WebInputStringIdKey stringIdKeyToWebInput(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromWebInput(WebInputStringIdKey webInputStringIdKey);

    // -----------------------------------------------------------Statistics Key-----------------------------------------------------------
    FastJsonBridgeDataKey bridgeDataKeyToFastJson(BridgeDataKey bridgeDataKey);

    @InheritInverseConfiguration
    BridgeDataKey bridgeDataKeyFromFastJson(FastJsonBridgeDataKey fastJsonBridgeDataKey);

    FastJsonVariableKey variableKeyToFastJson(VariableKey variableKey);

    @InheritInverseConfiguration
    VariableKey variableKeyFromFastJson(FastJsonVariableKey fastJsonVariableKey);

    JSFixedFastJsonBridgeDataKey bridgeDataKeyToJSFixedFastJson(BridgeDataKey bridgeDataKey);

    @InheritInverseConfiguration
    BridgeDataKey bridgeDataKeyFromJSFixedFastJson(JSFixedFastJsonBridgeDataKey jsFixedFastJsonBridgeDataKey);

    JSFixedFastJsonVariableKey variableKeyToJSFixedFastJson(VariableKey variableKey);

    @InheritInverseConfiguration
    VariableKey variableKeyFromJSFixedFastJson(JSFixedFastJsonVariableKey jSFixedFastJsonVariableKey);

    WebInputBridgeDataKey bridgeDataKeyToWebInput(BridgeDataKey bridgeDataKey);

    @InheritInverseConfiguration
    BridgeDataKey bridgeDataKeyFromWebInput(WebInputBridgeDataKey webInputBridgeDataKey);

    // -----------------------------------------------------------Statistics Entity-----------------------------------------------------------
    FastJsonDriverInfo driverInfoToFastJson(DriverInfo driverInfo);

    @InheritInverseConfiguration
    DriverInfo driverInfoFromFastJson(FastJsonDriverInfo fastJsonDriverInfo);

    JSFixedFastJsonDriverInfo driverInfoToJSFixedFastJson(DriverInfo driverInfo);

    @InheritInverseConfiguration
    DriverInfo driverInfoFromJSFixedFastJson(JSFixedFastJsonDriverInfo jsFixedFastJsonDriverInfo);

    WebInputDriverInfo driverInfoToWebInput(DriverInfo driverInfo);

    @InheritInverseConfiguration
    DriverInfo driverInfoFromWebInput(WebInputDriverInfo webInputDriverInfo);

    FastJsonDriverSupport driverSupportToFastJson(DriverSupport driverSupport);

    @InheritInverseConfiguration
    DriverSupport driverSupportFromFastJson(FastJsonDriverSupport fastJsonDriverSupport);

    FastJsonFilterInfo filterInfoToFastJson(FilterInfo filterInfo);

    @InheritInverseConfiguration
    FilterInfo filterInfoFromFastJson(FastJsonFilterInfo fastJsonFilterInfo);

    JSFixedFastJsonFilterInfo filterInfoToJSFixedFastJson(FilterInfo filterInfo);

    @InheritInverseConfiguration
    FilterInfo filterInfoFromJSFixedFastJson(JSFixedFastJsonFilterInfo jsFixedFastJsonFilterInfo);

    WebInputFilterInfo filterInfoToWebInput(FilterInfo filterInfo);

    @InheritInverseConfiguration
    FilterInfo filterInfoFromWebInput(WebInputFilterInfo webInputFilterInfo);

    FastJsonFilterSupport filterSupportToFastJson(FilterSupport filterSupport);

    @InheritInverseConfiguration
    FilterSupport filterSupportFromFastJson(FastJsonFilterSupport fastJsonFilterSupport);

    FastJsonHistoryTask historyTaskToFastJson(HistoryTask historyTask);

    @InheritInverseConfiguration
    HistoryTask historyTaskFromFastJson(FastJsonHistoryTask fastJsonHistoryTask);

    JSFixedFastJsonHistoryTask historyTaskToJSFixedFastJson(HistoryTask historyTask);

    @InheritInverseConfiguration
    HistoryTask historyTaskFromJSFixedFastJson(JSFixedFastJsonHistoryTask jsFixedFastJsonHistoryTask);

    FastJsonHistoryTaskEvent historyTaskEventToFastJson(HistoryTaskEvent historyTaskEvent);

    @InheritInverseConfiguration
    HistoryTaskEvent historyTaskEventFromFastJson(FastJsonHistoryTaskEvent fastJsonHistoryTaskEvent);

    JSFixedFastJsonHistoryTaskEvent historyTaskEventToJSFixedFastJson(HistoryTaskEvent historyTaskEvent);

    @InheritInverseConfiguration
    HistoryTaskEvent historyTaskEventFromJSFixedFastJson(JSFixedFastJsonHistoryTaskEvent jsFixedFastJsonHistoryTaskEvent);

    FastJsonMapperSupport mapperSupportToFastJson(MapperSupport mapperSupport);

    @InheritInverseConfiguration
    MapperSupport mapperSupportFromFastJson(FastJsonMapperSupport fastJsonMapperSupport);

    FastJsonProviderInfo providerInfoToFastJson(ProviderInfo providerInfo);

    @InheritInverseConfiguration
    ProviderInfo providerInfoFromFastJson(FastJsonProviderInfo fastJsonProviderInfo);

    JSFixedFastJsonProviderInfo providerInfoToJSFixedFastJson(ProviderInfo providerInfo);

    @InheritInverseConfiguration
    ProviderInfo providerInfoFromJSFixedFastJson(JSFixedFastJsonProviderInfo jsFixedFastJsonProviderInfo);

    WebInputProviderInfo providerInfoToWebInput(ProviderInfo providerInfo);

    @InheritInverseConfiguration
    ProviderInfo providerInfoFromWebInput(WebInputProviderInfo webInputProviderInfo);

    FastJsonProviderSupport providerSupportToFastJson(ProviderSupport providerSupport);

    @InheritInverseConfiguration
    ProviderSupport providerSupportFromFastJson(FastJsonProviderSupport fastJsonProviderSupport);

    FastJsonStatisticsExecutionProfile statisticsExecutionProfileToFastJson(StatisticsExecutionProfile statisticsExecutionProfile);

    @InheritInverseConfiguration
    StatisticsExecutionProfile statisticsExecutionProfileFromFastJson(FastJsonStatisticsExecutionProfile fastJsonStatisticsExecutionProfile);

    JSFixedFastJsonStatisticsExecutionProfile statisticsExecutionProfileToJSFixedFastJson(StatisticsExecutionProfile statisticsExecutionProfile);

    @InheritInverseConfiguration
    StatisticsExecutionProfile statisticsExecutionProfileFromJSFixedFastJson(JSFixedFastJsonStatisticsExecutionProfile jsFixedFastJsonStatisticsExecutionProfile);

    FastJsonStatisticsSetting statisticsSettingToFastJson(StatisticsSetting statisticsSetting);

    @InheritInverseConfiguration
    StatisticsSetting statisticsSettingFromFastJson(FastJsonStatisticsSetting fastJsonStatisticsSetting);

    JSFixedFastJsonStatisticsSetting statisticsSettingToJSFixedFastJson(StatisticsSetting statisticsSetting);

    @InheritInverseConfiguration
    StatisticsSetting statisticsSettingFromJSFixedFastJson(JSFixedFastJsonStatisticsSetting jsFixedFastJsonStatisticsSetting);

    WebInputStatisticsSetting statisticsSettingToWebInput(StatisticsSetting statisticsSetting);

    @InheritInverseConfiguration
    StatisticsSetting statisticsSettingFromWebInput(WebInputStatisticsSetting webInputStatisticsSetting);

    FastJsonTask taskToFastJson(Task task);

    @InheritInverseConfiguration
    Task taskFromFastJson(FastJsonTask fastJsonTask);

    JSFixedFastJsonTask taskToJSFixedFastJson(Task task);

    @InheritInverseConfiguration
    Task taskFromJSFixedFastJson(JSFixedFastJsonTask jsFixedFastJsonTask);

    FastJsonTaskEvent taskEventToFastJson(TaskEvent taskEvent);

    @InheritInverseConfiguration
    TaskEvent taskEventFromFastJson(FastJsonTaskEvent fastJsonTaskEvent);

    JSFixedFastJsonTaskEvent taskEventToJSFixedFastJson(TaskEvent taskEvent);

    @InheritInverseConfiguration
    TaskEvent taskEventFromJSFixedFastJson(JSFixedFastJsonTaskEvent jsFixedFastJsonTaskEvent);

    FastJsonVariable variableToFastJson(Variable variable);

    @InheritInverseConfiguration
    Variable variableFromFastJson(FastJsonVariable fastJsonVariable);

    JSFixedFastJsonVariable variableToJSFixedFastJson(Variable variable);

    @InheritInverseConfiguration
    Variable variableFromJSFixedFastJson(JSFixedFastJsonVariable jsFixedFastJsonVariable);

}
