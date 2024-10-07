package com.dwarfeng.statistics.impl.handler.bridge.redis.configuration;

import com.alibaba.fastjson.parser.ParserConfig;
import com.dwarfeng.statistics.impl.handler.bridge.redis.bean.RedisBridgeFastJsonBridgeData;
import com.dwarfeng.statistics.impl.handler.bridge.redis.serialize.RedisBridgeRedisSerializer;
import com.dwarfeng.subgrade.sdk.redis.serialize.FastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

@Configuration
public class RedisBridgeBaseConfiguration {

    @Value("${bridge.redis.use_project_config}")
    private boolean useProjectConfig;

    @Value("${redis.hostName}")
    private String projectHostName;
    @Value("${redis.port}")
    private int projectPort;
    @Value("${redis.password}")
    private String projectPassword;
    @Value("${redis.timeout}")
    private int projectTimeout;
    @Value("${redis.maxIdle}")
    private int projectMaxIdle;
    @Value("${redis.maxTotal}")
    private int projectMaxTotal;
    @Value("${redis.maxWaitMillis}")
    private int projectMaxWaitMillis;
    @Value("${redis.minEvictableIdleTimeMillis}")
    private int projectMinEvictableIdleTimeMillis;
    @Value("${redis.numTestsPerEvictionRun}")
    private int projectNumTestsPerEvictionRun;
    @Value("${redis.timeBetweenEvictionRunsMillis}")
    private int projectTimeBetweenEvictionRunsMillis;
    @Value("${redis.testOnBorrow}")
    private boolean projectTestOnBorrow;
    @Value("${redis.testWhileIdle}")
    private boolean projectTestWhileIdle;

    @Value("${bridge.redis.host_name}")
    private String bridgeHostName;
    @Value("${bridge.redis.port}")
    private int bridgePort;
    @Value("${bridge.redis.password}")
    private String bridgePassword;
    @Value("${bridge.redis.timeout}")
    private int bridgeTimeout;
    @Value("${bridge.redis.max_idle}")
    private int bridgeMaxIdle;
    @Value("${bridge.redis.max_total}")
    private int bridgeMaxTotal;
    @Value("${bridge.redis.max_wait_millis}")
    private int bridgeMaxWaitMillis;
    @Value("${bridge.redis.min_evictable_idle_time_millis}")
    private int bridgeMinEvictableIdleTimeMillis;
    @Value("${bridge.redis.num_tests_per_eviction_run}")
    private int bridgeNumTestsPerEvictionRun;
    @Value("${bridge.redis.time_between_eviction_runs_millis}")
    private int bridgeTimeBetweenEvictionRunsMillis;
    @Value("${bridge.redis.test_on_borrow}")
    private boolean bridgeTestOnBorrow;
    @Value("${bridge.redis.test_while_idle}")
    private boolean bridgeTestWhileIdle;

    @Bean("redisBridge.fastJsonRedisSerializer")
    public FastJsonRedisSerializer<Object> fastJsonRedisSerializer() {
        return new FastJsonRedisSerializer<>(Object.class);
    }

    @Bean("redisBridge.parserConfig")
    public ParserConfig parserConfig() {
        ParserConfig parserConfig = new ParserConfig(false);
        parserConfig.addAccept(RedisBridgeFastJsonBridgeData.class.getCanonicalName());
        return parserConfig;
    }

    @Bean("redisBridge.jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle());
        jedisPoolConfig.setMaxTotal(maxTotal());
        jedisPoolConfig.setMaxWait(Duration.ofMillis(maxWaitMillis()));
        jedisPoolConfig.setMinEvictableIdleTime(Duration.ofMillis(minEvictableIdleTimeMillis()));
        jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun());
        jedisPoolConfig.setTimeBetweenEvictionRuns(Duration.ofMillis(timeBetweenEvictionRunsMillis()));
        jedisPoolConfig.setTestOnBorrow(testOnBorrow());
        jedisPoolConfig.setTestWhileIdle(testWhileIdle());
        return jedisPoolConfig;
    }

    @SuppressWarnings("deprecation")
    @Bean("redisBridge.jedisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory(
            @Qualifier("redisBridge.jedisPoolConfig") JedisPoolConfig jedisPoolConfig
    ) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(hostName());
        jedisConnectionFactory.setPort(port());
        jedisConnectionFactory.setPassword(password());
        jedisConnectionFactory.setTimeout(timeout());
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
        return jedisConnectionFactory;
    }

    @Bean("redisBridge.redisTemplate")
    public RedisTemplate<String, ?> redisTemplate(
            @Qualifier("redisBridge.jedisConnectionFactory") JedisConnectionFactory jedisConnectionFactory,
            RedisBridgeRedisSerializer redisBridgeRedisSerializer
    ) {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        RedisTemplate<String, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(redisBridgeRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(redisBridgeRedisSerializer);
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }

    private String hostName() {
        return useProjectConfig ? projectHostName : bridgeHostName;
    }

    private int port() {
        return useProjectConfig ? projectPort : bridgePort;
    }

    private String password() {
        return useProjectConfig ? projectPassword : bridgePassword;
    }

    private int timeout() {
        return useProjectConfig ? projectTimeout : bridgeTimeout;
    }

    private int maxIdle() {
        return useProjectConfig ? projectMaxIdle : bridgeMaxIdle;
    }

    private int maxTotal() {
        return useProjectConfig ? projectMaxTotal : bridgeMaxTotal;
    }

    private int maxWaitMillis() {
        return useProjectConfig ? projectMaxWaitMillis : bridgeMaxWaitMillis;
    }

    private int minEvictableIdleTimeMillis() {
        return useProjectConfig ? projectMinEvictableIdleTimeMillis : bridgeMinEvictableIdleTimeMillis;
    }

    private int numTestsPerEvictionRun() {
        return useProjectConfig ? projectNumTestsPerEvictionRun : bridgeNumTestsPerEvictionRun;
    }

    private int timeBetweenEvictionRunsMillis() {
        return useProjectConfig ? projectTimeBetweenEvictionRunsMillis : bridgeTimeBetweenEvictionRunsMillis;
    }

    private boolean testOnBorrow() {
        return useProjectConfig ? projectTestOnBorrow : bridgeTestOnBorrow;
    }

    private boolean testWhileIdle() {
        return useProjectConfig ? projectTestWhileIdle : bridgeTestWhileIdle;
    }
}
