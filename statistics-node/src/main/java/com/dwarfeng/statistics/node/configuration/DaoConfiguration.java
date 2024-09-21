package com.dwarfeng.statistics.node.configuration;

import com.dwarfeng.statistics.impl.bean.HibernateMapper;
import com.dwarfeng.statistics.impl.bean.entity.*;
import com.dwarfeng.statistics.impl.bean.key.HibernateVariableKey;
import com.dwarfeng.statistics.impl.dao.preset.*;
import com.dwarfeng.statistics.stack.bean.entity.*;
import com.dwarfeng.statistics.stack.bean.key.VariableKey;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchBaseDao;
import com.dwarfeng.subgrade.impl.dao.HibernateEntireLookupDao;
import com.dwarfeng.subgrade.impl.dao.HibernatePresetLookupDao;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.sdk.hibernate.modification.DefaultDeletionMod;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTemplate;

@Configuration
public class DaoConfiguration {

    private final HibernateTemplate hibernateTemplate;

    private final StatisticsSettingPresetCriteriaMaker statisticsSettingPresetCriteriaMaker;
    private final StatisticsExecutionProfilePresetCriteriaMaker statisticsExecutionProfilePresetCriteriaMaker;
    private final VariablePresetCriteriaMaker variablePresetCriteriaMaker;
    private final DriverInfoPresetCriteriaMaker driverInfoPresetCriteriaMaker;
    private final FilterInfoPresetCriteriaMaker filterInfoPresetCriteriaMaker;
    private final ProviderInfoPresetCriteriaMaker providerInfoPresetCriteriaMaker;
    private final DriverSupportPresetCriteriaMaker driverSupportPresetCriteriaMaker;
    private final FilterSupportPresetCriteriaMaker filterSupportPresetCriteriaMaker;
    private final ProviderSupportPresetCriteriaMaker providerSupportPresetCriteriaMaker;
    private final MapperSupportPresetCriteriaMaker mapperSupportPresetCriteriaMaker;
    private final TaskPresetCriteriaMaker taskPresetCriteriaMaker;
    private final TaskEventPresetCriteriaMaker taskEventPresetCriteriaMaker;
    private final HistoryTaskPresetCriteriaMaker historyTaskPresetCriteriaMaker;
    private final HistoryTaskEventPresetCriteriaMaker historyTaskEventPresetCriteriaMaker;


    @Value("${hibernate.jdbc.batch_size}")
    private int batchSize;

    public DaoConfiguration(
            HibernateTemplate hibernateTemplate,
            StatisticsSettingPresetCriteriaMaker statisticsSettingPresetCriteriaMaker,
            StatisticsExecutionProfilePresetCriteriaMaker statisticsExecutionProfilePresetCriteriaMaker,
            VariablePresetCriteriaMaker variablePresetCriteriaMaker,
            DriverInfoPresetCriteriaMaker driverInfoPresetCriteriaMaker,
            FilterInfoPresetCriteriaMaker filterInfoPresetCriteriaMaker,
            ProviderInfoPresetCriteriaMaker providerInfoPresetCriteriaMaker,
            DriverSupportPresetCriteriaMaker driverSupportPresetCriteriaMaker,
            FilterSupportPresetCriteriaMaker filterSupportPresetCriteriaMaker,
            ProviderSupportPresetCriteriaMaker providerSupportPresetCriteriaMaker,
            MapperSupportPresetCriteriaMaker mapperSupportPresetCriteriaMaker,
            TaskPresetCriteriaMaker taskPresetCriteriaMaker,
            TaskEventPresetCriteriaMaker taskEventPresetCriteriaMaker,
            HistoryTaskPresetCriteriaMaker historyTaskPresetCriteriaMaker,
            HistoryTaskEventPresetCriteriaMaker historyTaskEventPresetCriteriaMaker
    ) {
        this.hibernateTemplate = hibernateTemplate;
        this.statisticsSettingPresetCriteriaMaker = statisticsSettingPresetCriteriaMaker;
        this.statisticsExecutionProfilePresetCriteriaMaker = statisticsExecutionProfilePresetCriteriaMaker;
        this.variablePresetCriteriaMaker = variablePresetCriteriaMaker;
        this.driverInfoPresetCriteriaMaker = driverInfoPresetCriteriaMaker;
        this.filterInfoPresetCriteriaMaker = filterInfoPresetCriteriaMaker;
        this.providerInfoPresetCriteriaMaker = providerInfoPresetCriteriaMaker;
        this.driverSupportPresetCriteriaMaker = driverSupportPresetCriteriaMaker;
        this.filterSupportPresetCriteriaMaker = filterSupportPresetCriteriaMaker;
        this.providerSupportPresetCriteriaMaker = providerSupportPresetCriteriaMaker;
        this.mapperSupportPresetCriteriaMaker = mapperSupportPresetCriteriaMaker;
        this.taskPresetCriteriaMaker = taskPresetCriteriaMaker;
        this.taskEventPresetCriteriaMaker = taskEventPresetCriteriaMaker;
        this.historyTaskPresetCriteriaMaker = historyTaskPresetCriteriaMaker;
        this.historyTaskEventPresetCriteriaMaker = historyTaskEventPresetCriteriaMaker;
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, StatisticsSetting, HibernateStatisticsSetting>
    statisticsSettingHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(
                        StatisticsSetting.class, HibernateStatisticsSetting.class, HibernateMapper.class
                ),
                HibernateStatisticsSetting.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<StatisticsSetting, HibernateStatisticsSetting>
    statisticsSettingHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        StatisticsSetting.class, HibernateStatisticsSetting.class, HibernateMapper.class
                ),
                HibernateStatisticsSetting.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<StatisticsSetting, HibernateStatisticsSetting>
    statisticsSettingHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        StatisticsSetting.class, HibernateStatisticsSetting.class, HibernateMapper.class
                ),
                HibernateStatisticsSetting.class,
                statisticsSettingPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, StatisticsExecutionProfile,
            HibernateStatisticsExecutionProfile> statisticsExecutionProfileHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(
                        StatisticsExecutionProfile.class,
                        HibernateStatisticsExecutionProfile.class,
                        HibernateMapper.class
                ),
                HibernateStatisticsExecutionProfile.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<StatisticsExecutionProfile, HibernateStatisticsExecutionProfile>
    statisticsExecutionProfileHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        StatisticsExecutionProfile.class,
                        HibernateStatisticsExecutionProfile.class,
                        HibernateMapper.class
                ),
                HibernateStatisticsExecutionProfile.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<StatisticsExecutionProfile, HibernateStatisticsExecutionProfile>
    statisticsExecutionProfileHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        StatisticsExecutionProfile.class,
                        HibernateStatisticsExecutionProfile.class,
                        HibernateMapper.class
                ),
                HibernateStatisticsExecutionProfile.class,
                statisticsExecutionProfilePresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<VariableKey, HibernateVariableKey, Variable, HibernateVariable>
    variableHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(VariableKey.class, HibernateVariableKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(Variable.class, HibernateVariable.class, HibernateMapper.class),
                HibernateVariable.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<Variable, HibernateVariable> variableHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(Variable.class, HibernateVariable.class, HibernateMapper.class),
                HibernateVariable.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<Variable, HibernateVariable> variableHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(Variable.class, HibernateVariable.class, HibernateMapper.class),
                HibernateVariable.class,
                variablePresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, DriverInfo, HibernateDriverInfo>
    driverInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(DriverInfo.class, HibernateDriverInfo.class, HibernateMapper.class),
                HibernateDriverInfo.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<DriverInfo, HibernateDriverInfo> driverInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(DriverInfo.class, HibernateDriverInfo.class, HibernateMapper.class),
                HibernateDriverInfo.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<DriverInfo, HibernateDriverInfo> driverInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(DriverInfo.class, HibernateDriverInfo.class, HibernateMapper.class),
                HibernateDriverInfo.class,
                driverInfoPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, FilterInfo, HibernateFilterInfo>
    filterInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(FilterInfo.class, HibernateFilterInfo.class, HibernateMapper.class),
                HibernateFilterInfo.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<FilterInfo, HibernateFilterInfo> filterInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(FilterInfo.class, HibernateFilterInfo.class, HibernateMapper.class),
                HibernateFilterInfo.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<FilterInfo, HibernateFilterInfo> filterInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(FilterInfo.class, HibernateFilterInfo.class, HibernateMapper.class),
                HibernateFilterInfo.class,
                filterInfoPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, ProviderInfo, HibernateProviderInfo>
    providerInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(ProviderInfo.class, HibernateProviderInfo.class, HibernateMapper.class),
                HibernateProviderInfo.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<ProviderInfo, HibernateProviderInfo> providerInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(ProviderInfo.class, HibernateProviderInfo.class, HibernateMapper.class),
                HibernateProviderInfo.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<ProviderInfo, HibernateProviderInfo> providerInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(ProviderInfo.class, HibernateProviderInfo.class, HibernateMapper.class),
                HibernateProviderInfo.class,
                providerInfoPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, DriverSupport, HibernateDriverSupport>
    driverSupportHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(
                        DriverSupport.class, HibernateDriverSupport.class, HibernateMapper.class
                ),
                HibernateDriverSupport.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<DriverSupport, HibernateDriverSupport> driverSupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        DriverSupport.class, HibernateDriverSupport.class, HibernateMapper.class
                ),
                HibernateDriverSupport.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<DriverSupport, HibernateDriverSupport> driverSupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        DriverSupport.class, HibernateDriverSupport.class, HibernateMapper.class
                ),
                HibernateDriverSupport.class,
                driverSupportPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, FilterSupport, HibernateFilterSupport>
    filterSupportHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(
                        FilterSupport.class, HibernateFilterSupport.class, HibernateMapper.class
                ),
                HibernateFilterSupport.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<FilterSupport, HibernateFilterSupport> filterSupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        FilterSupport.class, HibernateFilterSupport.class, HibernateMapper.class
                ),
                HibernateFilterSupport.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<FilterSupport, HibernateFilterSupport> filterSupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        FilterSupport.class, HibernateFilterSupport.class, HibernateMapper.class
                ),
                HibernateFilterSupport.class,
                filterSupportPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, ProviderSupport, HibernateProviderSupport>
    providerSupportHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(
                        ProviderSupport.class, HibernateProviderSupport.class, HibernateMapper.class
                ),
                HibernateProviderSupport.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<ProviderSupport, HibernateProviderSupport> providerSupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ProviderSupport.class, HibernateProviderSupport.class, HibernateMapper.class
                ),
                HibernateProviderSupport.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<ProviderSupport, HibernateProviderSupport>
    providerSupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ProviderSupport.class, HibernateProviderSupport.class, HibernateMapper.class
                ),
                HibernateProviderSupport.class,
                providerSupportPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, MapperSupport, HibernateMapperSupport>
    mapperSupportHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(
                        MapperSupport.class, HibernateMapperSupport.class, HibernateMapper.class
                ),
                HibernateMapperSupport.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<MapperSupport, HibernateMapperSupport> mapperSupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        MapperSupport.class, HibernateMapperSupport.class, HibernateMapper.class
                ),
                HibernateMapperSupport.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<MapperSupport, HibernateMapperSupport> mapperSupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        MapperSupport.class, HibernateMapperSupport.class, HibernateMapper.class
                ),
                HibernateMapperSupport.class,
                mapperSupportPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, Task, HibernateTask>
    taskHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(Task.class, HibernateTask.class, HibernateMapper.class),
                HibernateTask.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<Task, HibernateTask> taskHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(Task.class, HibernateTask.class, HibernateMapper.class),
                HibernateTask.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<Task, HibernateTask> taskHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(Task.class, HibernateTask.class, HibernateMapper.class),
                HibernateTask.class,
                taskPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, TaskEvent, HibernateTaskEvent>
    taskEventHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(TaskEvent.class, HibernateTaskEvent.class, HibernateMapper.class),
                HibernateTaskEvent.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<TaskEvent, HibernateTaskEvent> taskEventHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(TaskEvent.class, HibernateTaskEvent.class, HibernateMapper.class),
                HibernateTaskEvent.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<TaskEvent, HibernateTaskEvent> taskEventHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(TaskEvent.class, HibernateTaskEvent.class, HibernateMapper.class),
                HibernateTaskEvent.class,
                taskEventPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, HistoryTask, HibernateHistoryTask>
    historyTaskHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(HistoryTask.class, HibernateHistoryTask.class, HibernateMapper.class),
                HibernateHistoryTask.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<HistoryTask, HibernateHistoryTask> historyTaskHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(HistoryTask.class, HibernateHistoryTask.class, HibernateMapper.class),
                HibernateHistoryTask.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<HistoryTask, HibernateHistoryTask> historyTaskHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(HistoryTask.class, HibernateHistoryTask.class, HibernateMapper.class),
                HibernateHistoryTask.class,
                historyTaskPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, HistoryTaskEvent, HibernateHistoryTaskEvent>
    historyTaskEventHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(
                        HistoryTaskEvent.class, HibernateHistoryTaskEvent.class, HibernateMapper.class
                ),
                HibernateHistoryTaskEvent.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<HistoryTaskEvent, HibernateHistoryTaskEvent>
    historyTaskEventHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        HistoryTaskEvent.class, HibernateHistoryTaskEvent.class, HibernateMapper.class
                ),
                HibernateHistoryTaskEvent.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<HistoryTaskEvent, HibernateHistoryTaskEvent>
    historyTaskEventHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        HistoryTaskEvent.class, HibernateHistoryTaskEvent.class, HibernateMapper.class
                ),
                HibernateHistoryTaskEvent.class,
                historyTaskEventPresetCriteriaMaker
        );
    }
}
