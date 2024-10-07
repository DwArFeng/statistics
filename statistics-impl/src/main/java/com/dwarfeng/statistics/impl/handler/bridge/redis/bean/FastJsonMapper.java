package com.dwarfeng.statistics.impl.handler.bridge.redis.bean;

import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
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

    RedisBridgeFastJsonBridgeData redisBridgeBridgeDataToFastJson(
            RedisBridgeBridgeData redisBridgeBridgeData
    );

    @InheritInverseConfiguration
    RedisBridgeBridgeData redisBridgeBridgeDataFromFastJson(
            RedisBridgeFastJsonBridgeData redisBridgeFastJsonBridgeData
    );
}
