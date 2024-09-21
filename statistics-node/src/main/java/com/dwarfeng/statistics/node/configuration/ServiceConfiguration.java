package com.dwarfeng.statistics.node.configuration;

import com.dwarfeng.statistics.impl.service.operation.HistoryTaskCrudOperation;
import com.dwarfeng.statistics.impl.service.operation.StatisticsSettingCrudOperation;
import com.dwarfeng.statistics.impl.service.operation.TaskCrudOperation;
import com.dwarfeng.statistics.stack.bean.entity.*;
import com.dwarfeng.statistics.stack.bean.key.VariableKey;
import com.dwarfeng.statistics.stack.cache.*;
import com.dwarfeng.statistics.stack.dao.*;
import com.dwarfeng.subgrade.impl.generation.ExceptionKeyGenerator;
import com.dwarfeng.subgrade.impl.service.CustomBatchCrudService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyEntireLookupService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyPresetLookupService;
import com.dwarfeng.subgrade.impl.service.GeneralBatchCrudService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    private final ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration;
    private final GenerateConfiguration generateConfiguration;

    private final StatisticsSettingCrudOperation statisticsSettingCrudOperation;
    private final StatisticsSettingDao statisticsSettingDao;
    private final StatisticsExecutionProfileDao statisticsExecutionProfileDao;
    private final StatisticsExecutionProfileCache statisticsExecutionProfileCache;
    private final VariableDao variableDao;
    private final VariableCache variableCache;
    private final DriverInfoDao driverInfoDao;
    private final DriverInfoCache driverInfoCache;
    private final FilterInfoDao filterInfoDao;
    private final FilterInfoCache filterInfoCache;
    private final ProviderInfoDao providerInfoDao;
    private final ProviderInfoCache providerInfoCache;
    private final DriverSupportDao driverSupportDao;
    private final DriverSupportCache driverSupportCache;
    private final FilterSupportDao filterSupportDao;
    private final FilterSupportCache filterSupportCache;
    private final ProviderSupportDao providerSupportDao;
    private final ProviderSupportCache providerSupportCache;
    private final MapperSupportDao mapperSupportDao;
    private final MapperSupportCache mapperSupportCache;
    private final TaskCrudOperation taskCrudOperation;
    private final TaskDao taskDao;
    private final TaskEventDao taskEventDao;
    private final TaskEventCache taskEventCache;
    private final HistoryTaskCrudOperation historyTaskCrudOperation;
    private final HistoryTaskDao historyTaskDao;
    private final HistoryTaskEventDao historyTaskEventDao;
    private final HistoryTaskEventCache historyTaskEventCache;

    @Value("${cache.timeout.entity.statistics_execution_profile}")
    private long statisticsExecutionProfileTimeout;
    @Value("${cache.timeout.entity.variable}")
    private long variableTimeout;
    @Value("${cache.timeout.entity.driver_info}")
    private long driverInfoTimeout;
    @Value("${cache.timeout.entity.filter_info}")
    private long filterInfoTimeout;
    @Value("${cache.timeout.entity.provider_info}")
    private long providerInfoTimeout;
    @Value("${cache.timeout.entity.driver_support}")
    private long driverSupportTimeout;
    @Value("${cache.timeout.entity.filter_support}")
    private long filterSupportTimeout;
    @Value("${cache.timeout.entity.provider_support}")
    private long providerSupportTimeout;
    @Value("${cache.timeout.entity.mapper_support}")
    private long mapperSupportTimeout;
    @Value("${cache.timeout.entity.task_event}")
    private long taskEventTimeout;
    @Value("${cache.timeout.entity.history_task_event}")
    private long historyTaskEventTimeout;

    public ServiceConfiguration(
            ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration,
            GenerateConfiguration generateConfiguration,
            StatisticsSettingCrudOperation statisticsSettingCrudOperation,
            StatisticsSettingDao statisticsSettingDao,
            StatisticsExecutionProfileDao statisticsExecutionProfileDao,
            StatisticsExecutionProfileCache statisticsExecutionProfileCache,
            VariableDao variableDao,
            VariableCache variableCache,
            DriverInfoDao driverInfoDao,
            DriverInfoCache driverInfoCache,
            FilterInfoDao filterInfoDao,
            FilterInfoCache filterInfoCache,
            ProviderInfoDao providerInfoDao,
            ProviderInfoCache providerInfoCache,
            DriverSupportDao driverSupportDao,
            DriverSupportCache driverSupportCache,
            FilterSupportDao filterSupportDao,
            FilterSupportCache filterSupportCache,
            ProviderSupportDao providerSupportDao,
            ProviderSupportCache providerSupportCache,
            MapperSupportDao mapperSupportDao,
            MapperSupportCache mapperSupportCache,
            TaskCrudOperation taskCrudOperation,
            TaskDao taskDao,
            TaskEventDao taskEventDao,
            TaskEventCache taskEventCache,
            HistoryTaskCrudOperation historyTaskCrudOperation,
            HistoryTaskDao historyTaskDao,
            HistoryTaskEventDao historyTaskEventDao,
            HistoryTaskEventCache historyTaskEventCache
    ) {
        this.serviceExceptionMapperConfiguration = serviceExceptionMapperConfiguration;
        this.generateConfiguration = generateConfiguration;
        this.statisticsSettingCrudOperation = statisticsSettingCrudOperation;
        this.statisticsSettingDao = statisticsSettingDao;
        this.statisticsExecutionProfileDao = statisticsExecutionProfileDao;
        this.statisticsExecutionProfileCache = statisticsExecutionProfileCache;
        this.variableDao = variableDao;
        this.variableCache = variableCache;
        this.driverInfoDao = driverInfoDao;
        this.driverInfoCache = driverInfoCache;
        this.filterInfoDao = filterInfoDao;
        this.filterInfoCache = filterInfoCache;
        this.providerInfoDao = providerInfoDao;
        this.providerInfoCache = providerInfoCache;
        this.driverSupportDao = driverSupportDao;
        this.driverSupportCache = driverSupportCache;
        this.filterSupportDao = filterSupportDao;
        this.filterSupportCache = filterSupportCache;
        this.providerSupportDao = providerSupportDao;
        this.providerSupportCache = providerSupportCache;
        this.mapperSupportDao = mapperSupportDao;
        this.mapperSupportCache = mapperSupportCache;
        this.taskCrudOperation = taskCrudOperation;
        this.taskDao = taskDao;
        this.taskEventDao = taskEventDao;
        this.taskEventCache = taskEventCache;
        this.historyTaskCrudOperation = historyTaskCrudOperation;
        this.historyTaskDao = historyTaskDao;
        this.historyTaskEventDao = historyTaskEventDao;
        this.historyTaskEventCache = historyTaskEventCache;
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, StatisticsSetting> statisticsSettingCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                statisticsSettingCrudOperation,
                generateConfiguration.snowflakeLongIdKeyGenerator()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<StatisticsSetting> statisticsSettingDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                statisticsSettingDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<StatisticsSetting> statisticsSettingDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                statisticsSettingDao
        );
    }

    @Bean
    public GeneralBatchCrudService<LongIdKey, StatisticsExecutionProfile>
    statisticsExecutionProfileGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                statisticsExecutionProfileDao,
                statisticsExecutionProfileCache,
                generateConfiguration.snowflakeLongIdKeyGenerator(),
                statisticsExecutionProfileTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<StatisticsExecutionProfile>
    statisticsExecutionProfileDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                statisticsExecutionProfileDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<StatisticsExecutionProfile>
    statisticsExecutionProfileDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                statisticsExecutionProfileDao
        );
    }

    @Bean
    public GeneralBatchCrudService<VariableKey, Variable> variableGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                variableDao,
                variableCache,
                new ExceptionKeyGenerator<>(),
                variableTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<Variable> variableDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                variableDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<Variable> variableDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                variableDao
        );
    }

    @Bean
    public GeneralBatchCrudService<LongIdKey, DriverInfo> driverInfoGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                driverInfoDao,
                driverInfoCache,
                generateConfiguration.snowflakeLongIdKeyGenerator(),
                driverInfoTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<DriverInfo> driverInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                driverInfoDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<DriverInfo> driverInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                driverInfoDao
        );
    }

    @Bean
    public GeneralBatchCrudService<LongIdKey, FilterInfo> filterInfoGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                filterInfoDao,
                filterInfoCache,
                generateConfiguration.snowflakeLongIdKeyGenerator(),
                filterInfoTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<FilterInfo> filterInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                filterInfoDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<FilterInfo> filterInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                filterInfoDao
        );
    }

    @Bean
    public GeneralBatchCrudService<LongIdKey, ProviderInfo> providerInfoGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                providerInfoDao,
                providerInfoCache,
                generateConfiguration.snowflakeLongIdKeyGenerator(),
                providerInfoTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ProviderInfo> providerInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                providerInfoDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ProviderInfo> providerInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                providerInfoDao
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, DriverSupport> driverSupportGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                driverSupportDao,
                driverSupportCache,
                new ExceptionKeyGenerator<>(),
                driverSupportTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<DriverSupport> driverSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                driverSupportDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<DriverSupport> driverSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                driverSupportDao
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, FilterSupport> filterSupportGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                filterSupportDao,
                filterSupportCache,
                new ExceptionKeyGenerator<>(),
                filterSupportTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<FilterSupport> filterSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                filterSupportDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<FilterSupport> filterSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                filterSupportDao
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, ProviderSupport> providerSupportGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                providerSupportDao,
                providerSupportCache,
                new ExceptionKeyGenerator<>(),
                providerSupportTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ProviderSupport> providerSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                providerSupportDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ProviderSupport> providerSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                providerSupportDao
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, MapperSupport> mapperSupportGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                mapperSupportDao,
                mapperSupportCache,
                new ExceptionKeyGenerator<>(),
                mapperSupportTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<MapperSupport> mapperSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                mapperSupportDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<MapperSupport> mapperSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                mapperSupportDao
        );
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, Task> taskCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                taskCrudOperation,
                generateConfiguration.snowflakeLongIdKeyGenerator()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<Task> taskDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                taskDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<Task> taskDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                taskDao
        );
    }

    @Bean
    public GeneralBatchCrudService<LongIdKey, TaskEvent> taskEventGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                taskEventDao,
                taskEventCache,
                generateConfiguration.snowflakeLongIdKeyGenerator(),
                taskEventTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<TaskEvent> taskEventDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                taskEventDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<TaskEvent> taskEventDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                taskEventDao
        );
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, HistoryTask> historyTaskCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                historyTaskCrudOperation,
                generateConfiguration.snowflakeLongIdKeyGenerator()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<HistoryTask> historyTaskDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                historyTaskDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<HistoryTask> historyTaskDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                historyTaskDao
        );
    }

    @Bean
    public GeneralBatchCrudService<LongIdKey, HistoryTaskEvent> historyTaskEventGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                historyTaskEventDao,
                historyTaskEventCache,
                generateConfiguration.snowflakeLongIdKeyGenerator(),
                historyTaskEventTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<HistoryTaskEvent> historyTaskEventDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                historyTaskEventDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<HistoryTaskEvent> historyTaskEventDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                historyTaskEventDao
        );
    }
}
