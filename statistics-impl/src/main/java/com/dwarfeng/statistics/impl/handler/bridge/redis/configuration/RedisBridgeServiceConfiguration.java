package com.dwarfeng.statistics.impl.handler.bridge.redis.configuration;

import com.dwarfeng.sfds.api.integration.subgrade.SnowflakeLongIdKeyGenerator;
import com.dwarfeng.sfds.stack.service.GenerateService;
import com.dwarfeng.statistics.impl.handler.bridge.redis.bean.RedisBridgeBridgeData;
import com.dwarfeng.statistics.impl.handler.bridge.redis.bean.RedisBridgeBridgeDataKey;
import com.dwarfeng.statistics.impl.handler.bridge.redis.dao.RedisBridgeBridgeDataDao;
import com.dwarfeng.subgrade.impl.generation.ExceptionKeyGenerator;
import com.dwarfeng.subgrade.impl.service.DaoOnlyBatchCrudService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.generation.KeyGenerator;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisBridgeServiceConfiguration {

    private final GenerateService snowflakeGenerateService;

    private final ServiceExceptionMapper sem;

    private final RedisBridgeBridgeDataDao redisBridgeBridgeDataDao;

    public RedisBridgeServiceConfiguration(
            GenerateService snowflakeGenerateService,
            ServiceExceptionMapper sem,
            RedisBridgeBridgeDataDao redisBridgeBridgeDataDao
    ) {
        this.snowflakeGenerateService = snowflakeGenerateService;
        this.sem = sem;
        this.redisBridgeBridgeDataDao = redisBridgeBridgeDataDao;
    }

    @Bean("redisBridge.snowflakeLongIdKeyGenerator")
    public KeyGenerator<LongIdKey> snowflakeLongIdKeyGenerator() {
        return new SnowflakeLongIdKeyGenerator(snowflakeGenerateService);
    }

    @Bean
    public DaoOnlyBatchCrudService<RedisBridgeBridgeDataKey, RedisBridgeBridgeData>
    redisBridgeBridgeDataDaoOnlyBatchCrudService() {
        return new DaoOnlyBatchCrudService<>(
                sem,
                LogLevel.WARN,
                redisBridgeBridgeDataDao,
                new ExceptionKeyGenerator<>()
        );
    }
}
