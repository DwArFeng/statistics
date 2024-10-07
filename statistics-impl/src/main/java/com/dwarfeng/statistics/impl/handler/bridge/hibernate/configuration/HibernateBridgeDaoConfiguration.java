package com.dwarfeng.statistics.impl.handler.bridge.hibernate.configuration;

import com.dwarfeng.statistics.impl.handler.bridge.hibernate.bean.HibernateBridgeBridgeData;
import com.dwarfeng.statistics.impl.handler.bridge.hibernate.bean.HibernateBridgeHibernateBridgeData;
import com.dwarfeng.statistics.impl.handler.bridge.hibernate.bean.HibernateMapper;
import com.dwarfeng.statistics.impl.handler.bridge.hibernate.dao.preset.HibernateBridgeBridgeDataPresetCriteriaMaker;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchBaseDao;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchWriteDao;
import com.dwarfeng.subgrade.impl.dao.HibernateDaoFactory;
import com.dwarfeng.subgrade.impl.dao.HibernateEntireLookupDao;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.hibernate.modification.DefaultDeletionMod;
import com.dwarfeng.subgrade.sdk.hibernate.nativelookup.DialectNativeLookup;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Configuration
public class HibernateBridgeDaoConfiguration {

    private static <T> List<T> validList(List<T> list) {
        return Optional.ofNullable(list).orElse(Collections.emptyList());
    }

    private final HibernateTemplate hibernateTemplate;

    private final HibernateBridgeBridgeDataPresetCriteriaMaker hibernateBridgeBridgeDataPresetCriteriaMaker;
    private final List<DialectNativeLookup<HibernateBridgeBridgeData>> hibernateBridgeBridgeDataDialectNativeLookups;

    @Value("${bridge.hibernate.use_project_config}")
    private boolean useProjectConfig;

    @Value("${hibernate.dialect}")
    private String projectHibernateDialect;
    @Value("${hibernate.jdbc.batch_size}")
    private int projectHibernateJdbcBatchSize;

    @Value("${bridge.hibernate.hibernate.dialect}")
    private String bridgeHibernateDialect;
    @Value("${bridge.hibernate.hibernate.jdbc.batch_size}")
    private int bridgeHibernateJdbcBatchSize;

    @Value("${bridge.hibernate.hibernate.accelerate_enabled}")
    private boolean hibernateAccelerateEnabled;

    public HibernateBridgeDaoConfiguration(
            @Qualifier("hibernateBridge.hibernateTemplate") HibernateTemplate hibernateTemplate,
            HibernateBridgeBridgeDataPresetCriteriaMaker hibernateBridgeBridgeDataPresetCriteriaMaker,
            List<DialectNativeLookup<HibernateBridgeBridgeData>> hibernateBridgeBridgeDataDialectNativeLookups
    ) {
        this.hibernateTemplate = hibernateTemplate;
        this.hibernateBridgeBridgeDataPresetCriteriaMaker = hibernateBridgeBridgeDataPresetCriteriaMaker;
        this.hibernateBridgeBridgeDataDialectNativeLookups = validList(
                hibernateBridgeBridgeDataDialectNativeLookups
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, HibernateBridgeBridgeData,
            HibernateBridgeHibernateBridgeData> hibernateBridgeBridgeDataHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(
                        HibernateBridgeBridgeData.class,
                        HibernateBridgeHibernateBridgeData.class,
                        HibernateMapper.class
                ),
                HibernateBridgeHibernateBridgeData.class,
                new DefaultDeletionMod<>(),
                hibernateJdbcBatchSize()
        );
    }

    @Bean
    public HibernateEntireLookupDao<HibernateBridgeBridgeData, HibernateBridgeHibernateBridgeData>
    hibernateBridgeBridgeDataHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        HibernateBridgeBridgeData.class,
                        HibernateBridgeHibernateBridgeData.class,
                        HibernateMapper.class
                ),
                HibernateBridgeHibernateBridgeData.class
        );
    }

    @Bean
    public PresetLookupDao<HibernateBridgeBridgeData> hibernateBridgeBridgeDataPresetLookupDao() {
        return HibernateDaoFactory.newPresetLookupDaoWithChosenDialect(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        HibernateBridgeBridgeData.class,
                        HibernateBridgeHibernateBridgeData.class,
                        HibernateMapper.class
                ),
                HibernateBridgeHibernateBridgeData.class,
                hibernateBridgeBridgeDataPresetCriteriaMaker,
                hibernateBridgeBridgeDataDialectNativeLookups,
                hibernateDialect(),
                hibernateAccelerateEnabled
        );
    }

    @Bean
    public HibernateBatchWriteDao<HibernateBridgeBridgeData, HibernateBridgeHibernateBridgeData>
    hibernateBridgeBridgeDataHibernateBatchWriteDao() {
        return new HibernateBatchWriteDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        HibernateBridgeBridgeData.class,
                        HibernateBridgeHibernateBridgeData.class,
                        HibernateMapper.class
                ),
                hibernateJdbcBatchSize()
        );
    }

    private String hibernateDialect() {
        return useProjectConfig ? projectHibernateDialect : bridgeHibernateDialect;
    }

    private int hibernateJdbcBatchSize() {
        return useProjectConfig ? projectHibernateJdbcBatchSize : bridgeHibernateJdbcBatchSize;
    }
}
