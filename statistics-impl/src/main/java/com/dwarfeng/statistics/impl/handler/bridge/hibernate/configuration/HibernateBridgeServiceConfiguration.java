package com.dwarfeng.statistics.impl.handler.bridge.hibernate.configuration;

import com.dwarfeng.sfds.api.integration.subgrade.SnowflakeLongIdKeyGenerator;
import com.dwarfeng.sfds.stack.service.GenerateService;
import com.dwarfeng.statistics.impl.handler.bridge.hibernate.bean.HibernateBridgeBridgeData;
import com.dwarfeng.statistics.impl.handler.bridge.hibernate.dao.HibernateBridgeBridgeDataDao;
import com.dwarfeng.subgrade.impl.service.DaoOnlyBatchCrudService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyBatchWriteService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyEntireLookupService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyPresetLookupService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.generation.KeyGenerator;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateBridgeServiceConfiguration {

    private final GenerateService snowflakeGenerateService;

    private final ServiceExceptionMapper sem;

    private final HibernateBridgeBridgeDataDao hibernateBridgeBridgeDataDao;

    public HibernateBridgeServiceConfiguration(
            GenerateService snowflakeGenerateService,
            ServiceExceptionMapper sem,
            HibernateBridgeBridgeDataDao hibernateBridgeBridgeDataDao
    ) {
        this.snowflakeGenerateService = snowflakeGenerateService;
        this.sem = sem;
        this.hibernateBridgeBridgeDataDao = hibernateBridgeBridgeDataDao;
    }

    @Bean("hibernateBridge.snowflakeLongIdKeyGenerator")
    public KeyGenerator<LongIdKey> snowflakeLongIdKeyGenerator() {
        return new SnowflakeLongIdKeyGenerator(snowflakeGenerateService);
    }

    @Bean
    public DaoOnlyBatchCrudService<LongIdKey, HibernateBridgeBridgeData>
    hibernateBridgeBridgeDataDaoOnlyBatchCrudService() {
        return new DaoOnlyBatchCrudService<>(
                sem,
                LogLevel.WARN,
                hibernateBridgeBridgeDataDao,
                snowflakeLongIdKeyGenerator()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<HibernateBridgeBridgeData>
    hibernateBridgeBridgeDataDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                sem,
                LogLevel.WARN,
                hibernateBridgeBridgeDataDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<HibernateBridgeBridgeData>
    hibernateBridgeBridgeDataDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                sem,
                LogLevel.WARN,
                hibernateBridgeBridgeDataDao
        );
    }

    @Bean
    public DaoOnlyBatchWriteService<LongIdKey, HibernateBridgeBridgeData>
    hibernateBridgeBridgeDataDaoOnlyBatchWriteService() {
        return new DaoOnlyBatchWriteService<>(
                sem,
                LogLevel.WARN,
                hibernateBridgeBridgeDataDao,
                snowflakeLongIdKeyGenerator()
        );
    }
}
