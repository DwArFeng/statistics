package com.dwarfeng.statistics.sdk.bean;

import com.dwarfeng.statistics.sdk.bean.dto.*;
import com.dwarfeng.statistics.sdk.bean.entity.*;
import com.dwarfeng.statistics.sdk.bean.key.*;
import com.dwarfeng.statistics.stack.bean.dto.*;
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
    BridgeDataKey bridgeDataKeyFromJSFixedFastJson(JSFixedFastJsonBridgeDataKey jSFixedFastJsonBridgeDataKey);

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

    FastJsonDriverSupport driverSupportToFastJson(DriverSupport driverSupport);

    @InheritInverseConfiguration
    DriverSupport driverSupportFromFastJson(FastJsonDriverSupport fastJsonDriverSupport);

    FastJsonFilterInfo filterInfoToFastJson(FilterInfo filterInfo);

    @InheritInverseConfiguration
    FilterInfo filterInfoFromFastJson(FastJsonFilterInfo fastJsonFilterInfo);

    FastJsonFilterSupport filterSupportToFastJson(FilterSupport filterSupport);

    @InheritInverseConfiguration
    FilterSupport filterSupportFromFastJson(FastJsonFilterSupport fastJsonFilterSupport);

    FastJsonHistoryTask historyTaskToFastJson(HistoryTask historyTask);

    @InheritInverseConfiguration
    HistoryTask historyTaskFromFastJson(FastJsonHistoryTask fastJsonHistoryTask);

    FastJsonHistoryTaskEvent historyTaskEventToFastJson(HistoryTaskEvent historyTaskEvent);

    @InheritInverseConfiguration
    HistoryTaskEvent historyTaskEventFromFastJson(FastJsonHistoryTaskEvent fastJsonHistoryTaskEvent);

    FastJsonMapperSupport mapperSupportToFastJson(MapperSupport mapperSupport);

    @InheritInverseConfiguration
    MapperSupport mapperSupportFromFastJson(FastJsonMapperSupport fastJsonMapperSupport);

    FastJsonProviderInfo providerInfoToFastJson(ProviderInfo providerInfo);

    @InheritInverseConfiguration
    ProviderInfo providerInfoFromFastJson(FastJsonProviderInfo fastJsonProviderInfo);

    FastJsonProviderSupport providerSupportToFastJson(ProviderSupport providerSupport);

    @InheritInverseConfiguration
    ProviderSupport providerSupportFromFastJson(FastJsonProviderSupport fastJsonProviderSupport);

    FastJsonStatisticsExecutionProfile statisticsExecutionProfileToFastJson(
            StatisticsExecutionProfile statisticsExecutionProfile
    );

    @InheritInverseConfiguration
    StatisticsExecutionProfile statisticsExecutionProfileFromFastJson(
            FastJsonStatisticsExecutionProfile fastJsonStatisticsExecutionProfile
    );

    FastJsonStatisticsSetting statisticsSettingToFastJson(StatisticsSetting statisticsSetting);

    @InheritInverseConfiguration
    StatisticsSetting statisticsSettingFromFastJson(FastJsonStatisticsSetting fastJsonStatisticsSetting);

    FastJsonTask taskToFastJson(Task task);

    @InheritInverseConfiguration
    Task taskFromFastJson(FastJsonTask fastJsonTask);

    FastJsonTaskEvent taskEventToFastJson(TaskEvent taskEvent);

    @InheritInverseConfiguration
    TaskEvent taskEventFromFastJson(FastJsonTaskEvent fastJsonTaskEvent);

    FastJsonVariable variableToFastJson(Variable variable);

    @InheritInverseConfiguration
    Variable variableFromFastJson(FastJsonVariable fastJsonVariable);

    JSFixedFastJsonDriverInfo driverInfoToJSFixedFastJson(DriverInfo driverInfo);

    @InheritInverseConfiguration
    DriverInfo driverInfoFromJSFixedFastJson(JSFixedFastJsonDriverInfo jSFixedFastJsonDriverInfo);

    JSFixedFastJsonFilterInfo filterInfoToJSFixedFastJson(FilterInfo filterInfo);

    @InheritInverseConfiguration
    FilterInfo filterInfoFromJSFixedFastJson(JSFixedFastJsonFilterInfo jSFixedFastJsonFilterInfo);

    JSFixedFastJsonHistoryTask historyTaskToJSFixedFastJson(HistoryTask historyTask);

    @InheritInverseConfiguration
    HistoryTask historyTaskFromJSFixedFastJson(JSFixedFastJsonHistoryTask jSFixedFastJsonHistoryTask);

    JSFixedFastJsonHistoryTaskEvent historyTaskEventToJSFixedFastJson(HistoryTaskEvent historyTaskEvent);

    @InheritInverseConfiguration
    HistoryTaskEvent historyTaskEventFromJSFixedFastJson(
            JSFixedFastJsonHistoryTaskEvent jSFixedFastJsonHistoryTaskEvent
    );

    JSFixedFastJsonProviderInfo providerInfoToJSFixedFastJson(ProviderInfo providerInfo);

    @InheritInverseConfiguration
    ProviderInfo providerInfoFromJSFixedFastJson(JSFixedFastJsonProviderInfo jSFixedFastJsonProviderInfo);

    JSFixedFastJsonStatisticsExecutionProfile statisticsExecutionProfileToJSFixedFastJson(
            StatisticsExecutionProfile statisticsExecutionProfile
    );

    @InheritInverseConfiguration
    StatisticsExecutionProfile statisticsExecutionProfileFromJSFixedFastJson(
            JSFixedFastJsonStatisticsExecutionProfile jSFixedFastJsonStatisticsExecutionProfile
    );

    JSFixedFastJsonStatisticsSetting statisticsSettingToJSFixedFastJson(StatisticsSetting statisticsSetting);

    @InheritInverseConfiguration
    StatisticsSetting statisticsSettingFromJSFixedFastJson(
            JSFixedFastJsonStatisticsSetting jSFixedFastJsonStatisticsSetting
    );

    JSFixedFastJsonTask taskToJSFixedFastJson(Task task);

    @InheritInverseConfiguration
    Task taskFromJSFixedFastJson(JSFixedFastJsonTask jSFixedFastJsonTask);

    JSFixedFastJsonTaskEvent taskEventToJSFixedFastJson(TaskEvent taskEvent);

    @InheritInverseConfiguration
    TaskEvent taskEventFromJSFixedFastJson(JSFixedFastJsonTaskEvent jSFixedFastJsonTaskEvent);

    JSFixedFastJsonVariable variableToJSFixedFastJson(Variable variable);

    @InheritInverseConfiguration
    Variable variableFromJSFixedFastJson(JSFixedFastJsonVariable jSFixedFastJsonVariable);

    WebInputDriverInfo driverInfoToWebInput(DriverInfo driverInfo);

    @InheritInverseConfiguration
    DriverInfo driverInfoFromWebInput(WebInputDriverInfo webInputDriverInfo);

    WebInputFilterInfo filterInfoToWebInput(FilterInfo filterInfo);

    @InheritInverseConfiguration
    FilterInfo filterInfoFromWebInput(WebInputFilterInfo webInputFilterInfo);

    WebInputProviderInfo providerInfoToWebInput(ProviderInfo providerInfo);

    @InheritInverseConfiguration
    ProviderInfo providerInfoFromWebInput(WebInputProviderInfo webInputProviderInfo);

    WebInputStatisticsSetting statisticsSettingToWebInput(StatisticsSetting statisticsSetting);

    @InheritInverseConfiguration
    StatisticsSetting statisticsSettingFromWebInput(WebInputStatisticsSetting webInputStatisticsSetting);

    // -----------------------------------------------------------Statistics DTO-----------------------------------------------------------
    FastJsonBridgeData bridgeDataToFastJson(BridgeData bridgeData);

    @InheritInverseConfiguration
    BridgeData bridgeDataFromFastJson(FastJsonBridgeData fastJsonBridgeData);

    FastJsonLookupInfo lookupInfoToFastJson(LookupInfo lookupInfo);

    @InheritInverseConfiguration
    LookupInfo lookupInfoFromFastJson(FastJsonLookupInfo fastJsonLookupInfo);

    FastJsonLookupResult lookupResultToFastJson(LookupResult lookupResult);

    @InheritInverseConfiguration
    LookupResult lookupResultFromFastJson(FastJsonLookupResult fastJsonLookupResult);

    FastJsonNativeQueryInfo nativeQueryInfoToFastJson(NativeQueryInfo nativeQueryInfo);

    @InheritInverseConfiguration
    NativeQueryInfo nativeQueryInfoFromFastJson(FastJsonNativeQueryInfo fastJsonNativeQueryInfo);

    FastJsonQueryInfo queryInfoToFastJson(QueryInfo queryInfo);

    @InheritInverseConfiguration
    QueryInfo queryInfoFromFastJson(FastJsonQueryInfo fastJsonQueryInfo);

    FastJsonQueryResult queryResultToFastJson(QueryResult queryResult);

    @InheritInverseConfiguration
    QueryResult queryResultFromFastJson(FastJsonQueryResult fastJsonQueryResult);

    JSFixedFastJsonBridgeData bridgeDataToJSFixedFastJson(BridgeData bridgeData);

    @InheritInverseConfiguration
    BridgeData bridgeDataFromJSFixedFastJson(JSFixedFastJsonBridgeData jSFixedFastJsonBridgeData);

    JSFixedFastJsonLookupInfo lookupInfoToJSFixedFastJson(LookupInfo lookupInfo);

    @InheritInverseConfiguration
    LookupInfo lookupInfoFromJSFixedFastJson(JSFixedFastJsonLookupInfo jSFixedFastJsonLookupInfo);

    JSFixedFastJsonLookupResult lookupResultToJSFixedFastJson(LookupResult lookupResult);

    @InheritInverseConfiguration
    LookupResult lookupResultFromJSFixedFastJson(JSFixedFastJsonLookupResult jSFixedFastJsonLookupResult);

    JSFixedFastJsonNativeQueryInfo nativeQueryInfoToJSFixedFastJson(NativeQueryInfo nativeQueryInfo);

    @InheritInverseConfiguration
    NativeQueryInfo nativeQueryInfoFromJSFixedFastJson(JSFixedFastJsonNativeQueryInfo jSFixedFastJsonNativeQueryInfo);

    JSFixedFastJsonQueryInfo queryInfoToJSFixedFastJson(QueryInfo queryInfo);

    @InheritInverseConfiguration
    QueryInfo queryInfoFromJSFixedFastJson(JSFixedFastJsonQueryInfo jSFixedFastJsonQueryInfo);

    JSFixedFastJsonQueryResult queryResultToJSFixedFastJson(QueryResult queryResult);

    @InheritInverseConfiguration
    QueryResult queryResultFromJSFixedFastJson(JSFixedFastJsonQueryResult jSFixedFastJsonQueryResult);

    WebInputLookupInfo lookupInfoToWebInput(LookupInfo lookupInfo);

    @InheritInverseConfiguration
    LookupInfo lookupInfoFromWebInput(WebInputLookupInfo webInputLookupInfo);

    WebInputNativeQueryInfo nativeQueryInfoToWebInput(NativeQueryInfo nativeQueryInfo);

    @InheritInverseConfiguration
    NativeQueryInfo nativeQueryInfoFromWebInput(WebInputNativeQueryInfo webInputNativeQueryInfo);

    WebInputQueryInfo queryInfoToWebInput(QueryInfo queryInfo);

    @InheritInverseConfiguration
    QueryInfo queryInfoFromWebInput(WebInputQueryInfo webInputQueryInfo);
}
