package com.dwarfeng.statistics.impl.handler.bridge.redis.bean;

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

    RedisBridgeFastJsonBridgeDataKey redisBridgeBridgeDataKeyToFastJson(
            RedisBridgeBridgeDataKey redisBridgeBridgeDataKey
    );

    @InheritInverseConfiguration
    RedisBridgeBridgeDataKey redisBridgeBridgeDataKeyFromFastJson(
            RedisBridgeFastJsonBridgeDataKey redisBridgeFastJsonBridgeDataKey
    );

    RedisBridgeFastJsonBridgeData redisBridgeBridgeDataToFastJson(
            RedisBridgeBridgeData redisBridgeBridgeData
    );

    @InheritInverseConfiguration
    RedisBridgeBridgeData redisBridgeBridgeDataFromFastJson(
            RedisBridgeFastJsonBridgeData redisBridgeFastJsonBridgeData
    );
}
