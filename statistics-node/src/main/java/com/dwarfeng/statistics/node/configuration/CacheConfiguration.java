package com.dwarfeng.statistics.node.configuration;

import com.dwarfeng.statistics.sdk.bean.FastJsonMapper;
import com.dwarfeng.statistics.sdk.bean.entity.*;
import com.dwarfeng.statistics.sdk.bean.key.formatter.VariableStringKeyFormatter;
import com.dwarfeng.statistics.stack.bean.entity.*;
import com.dwarfeng.statistics.stack.bean.key.VariableKey;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.impl.cache.RedisBatchBaseCache;
import com.dwarfeng.subgrade.impl.cache.RedisKeyListCache;
import com.dwarfeng.subgrade.sdk.redis.formatter.LongIdStringKeyFormatter;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringIdStringKeyFormatter;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class CacheConfiguration {

    private final RedisTemplate<String, ?> template;

    @Value("${cache.prefix.entity.statistics_setting}")
    private String statisticsSettingPrefix;
    @Value("${cache.prefix.entity.statistics_execution_profile}")
    private String statisticsExecutionProfilePrefix;
    @Value("${cache.prefix.entity.variable}")
    private String variablePrefix;
    @Value("${cache.prefix.entity.driver_info}")
    private String driverInfoPrefix;
    @Value("${cache.prefix.entity.filter_info}")
    private String filterInfoPrefix;
    @Value("${cache.prefix.entity.provider_info}")
    private String providerInfoPrefix;
    @Value("${cache.prefix.entity.driver_support}")
    private String driverSupportPrefix;
    @Value("${cache.prefix.entity.filter_support}")
    private String filterSupportPrefix;
    @Value("${cache.prefix.entity.provider_support}")
    private String providerSupportPrefix;
    @Value("${cache.prefix.entity.mapper_support}")
    private String mapperSupportPrefix;
    @Value("${cache.prefix.entity.task}")
    private String taskPrefix;
    @Value("${cache.prefix.entity.task_event}")
    private String taskEventPrefix;
    @Value("${cache.prefix.entity.history_task}")
    private String historyTaskPrefix;
    @Value("${cache.prefix.entity.history_task_event}")
    private String historyTaskEventPrefix;

    @Value("${cache.prefix.list.enabled_driver_info}")
    private String enabledDriverInfoPrefix;
    @Value("${cache.prefix.list.enabled_provider_info}")
    private String enabledProviderInfoPrefix;
    @Value("${cache.prefix.list.enabled_filter_info}")
    private String enabledFilterInfoPrefix;

    public CacheConfiguration(
            @Qualifier("redisTemplate") RedisTemplate<String, ?> template
    ) {
        this.template = template;
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, StatisticsSetting, FastJsonStatisticsSetting>
    statisticsSettingRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonStatisticsSetting>) template,
                new LongIdStringKeyFormatter(statisticsSettingPrefix),
                new MapStructBeanTransformer<>(
                        StatisticsSetting.class, FastJsonStatisticsSetting.class, FastJsonMapper.class
                )
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, StatisticsExecutionProfile, FastJsonStatisticsExecutionProfile>
    statisticsExecutionProfileRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonStatisticsExecutionProfile>) template,
                new LongIdStringKeyFormatter(statisticsExecutionProfilePrefix),
                new MapStructBeanTransformer<>(
                        StatisticsExecutionProfile.class,
                        FastJsonStatisticsExecutionProfile.class,
                        FastJsonMapper.class
                )
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<VariableKey, Variable, FastJsonVariable> variableRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonVariable>) template,
                new VariableStringKeyFormatter(variablePrefix),
                new MapStructBeanTransformer<>(Variable.class, FastJsonVariable.class, FastJsonMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, DriverInfo, FastJsonDriverInfo> driverInfoRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonDriverInfo>) template,
                new LongIdStringKeyFormatter(driverInfoPrefix),
                new MapStructBeanTransformer<>(DriverInfo.class, FastJsonDriverInfo.class, FastJsonMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, FilterInfo, FastJsonFilterInfo> filterInfoRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonFilterInfo>) template,
                new LongIdStringKeyFormatter(filterInfoPrefix),
                new MapStructBeanTransformer<>(FilterInfo.class, FastJsonFilterInfo.class, FastJsonMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, ProviderInfo, FastJsonProviderInfo> providerInfoRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonProviderInfo>) template,
                new LongIdStringKeyFormatter(providerInfoPrefix),
                new MapStructBeanTransformer<>(ProviderInfo.class, FastJsonProviderInfo.class, FastJsonMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, DriverSupport, FastJsonDriverSupport> driverSupportRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonDriverSupport>) template,
                new StringIdStringKeyFormatter(driverSupportPrefix),
                new MapStructBeanTransformer<>(DriverSupport.class, FastJsonDriverSupport.class, FastJsonMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, FilterSupport, FastJsonFilterSupport> filterSupportRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonFilterSupport>) template,
                new StringIdStringKeyFormatter(filterSupportPrefix),
                new MapStructBeanTransformer<>(FilterSupport.class, FastJsonFilterSupport.class, FastJsonMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, ProviderSupport, FastJsonProviderSupport>
    providerSupportRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonProviderSupport>) template,
                new StringIdStringKeyFormatter(providerSupportPrefix),
                new MapStructBeanTransformer<>(
                        ProviderSupport.class, FastJsonProviderSupport.class, FastJsonMapper.class
                )
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, MapperSupport, FastJsonMapperSupport> mapperSupportRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonMapperSupport>) template,
                new StringIdStringKeyFormatter(mapperSupportPrefix),
                new MapStructBeanTransformer<>(MapperSupport.class, FastJsonMapperSupport.class, FastJsonMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, Task, FastJsonTask> taskRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonTask>) template,
                new LongIdStringKeyFormatter(taskPrefix),
                new MapStructBeanTransformer<>(Task.class, FastJsonTask.class, FastJsonMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, TaskEvent, FastJsonTaskEvent> taskEventRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonTaskEvent>) template,
                new LongIdStringKeyFormatter(taskEventPrefix),
                new MapStructBeanTransformer<>(TaskEvent.class, FastJsonTaskEvent.class, FastJsonMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, HistoryTask, FastJsonHistoryTask> historyTaskRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonHistoryTask>) template,
                new LongIdStringKeyFormatter(historyTaskPrefix),
                new MapStructBeanTransformer<>(HistoryTask.class, FastJsonHistoryTask.class, FastJsonMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, HistoryTaskEvent, FastJsonHistoryTaskEvent>
    historyTaskEventRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonHistoryTaskEvent>) template,
                new LongIdStringKeyFormatter(historyTaskEventPrefix),
                new MapStructBeanTransformer<>(
                        HistoryTaskEvent.class, FastJsonHistoryTaskEvent.class, FastJsonMapper.class
                )
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisKeyListCache<LongIdKey, DriverInfo, FastJsonDriverInfo> driverInfoEnabledRedisKeyListCache() {
        return new RedisKeyListCache<>(
                (RedisTemplate<String, FastJsonDriverInfo>) template,
                new LongIdStringKeyFormatter(enabledDriverInfoPrefix),
                new MapStructBeanTransformer<>(DriverInfo.class, FastJsonDriverInfo.class, FastJsonMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisKeyListCache<LongIdKey, ProviderInfo, FastJsonProviderInfo> providerInfoEnabledRedisKeyListCache() {
        return new RedisKeyListCache<>(
                (RedisTemplate<String, FastJsonProviderInfo>) template,
                new LongIdStringKeyFormatter(enabledProviderInfoPrefix),
                new MapStructBeanTransformer<>(ProviderInfo.class, FastJsonProviderInfo.class, FastJsonMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisKeyListCache<LongIdKey, FilterInfo, FastJsonFilterInfo> filterInfoEnabledRedisKeyListCache() {
        return new RedisKeyListCache<>(
                (RedisTemplate<String, FastJsonFilterInfo>) template,
                new LongIdStringKeyFormatter(enabledFilterInfoPrefix),
                new MapStructBeanTransformer<>(FilterInfo.class, FastJsonFilterInfo.class, FastJsonMapper.class)
        );
    }
}
