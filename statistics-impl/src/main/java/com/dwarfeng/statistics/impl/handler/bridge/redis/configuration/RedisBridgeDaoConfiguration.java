package com.dwarfeng.statistics.impl.handler.bridge.redis.configuration;

import com.dwarfeng.statistics.impl.handler.bridge.redis.bean.*;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.impl.dao.RedisBatchBaseDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisBridgeDaoConfiguration {

    private final RedisTemplate<String, ?> redisTemplate;

    @Value("${bridge.redis.dbkey}")
    private String dbKey;

    public RedisBridgeDaoConfiguration(
            @Qualifier("redisBridge.redisTemplate") RedisTemplate<String, ?> redisTemplate
    ) {
        this.redisTemplate = redisTemplate;
    }

    @SuppressWarnings("unchecked")
    @Bean
    public RedisBatchBaseDao<RedisBridgeBridgeDataKey, RedisBridgeBridgeData, RedisBridgeFastJsonBridgeData>
    redisBridgeBridgeDataRedisBatchBaseDao() {
        return new RedisBatchBaseDao<>(
                (RedisTemplate<String, RedisBridgeFastJsonBridgeData>) redisTemplate,
                new RedisBridgeBridgeDataStringKeyFormatter("key."),
                new MapStructBeanTransformer<>(
                        RedisBridgeBridgeData.class,
                        RedisBridgeFastJsonBridgeData.class,
                        FastJsonMapper.class
                ),
                dbKey
        );
    }
}
