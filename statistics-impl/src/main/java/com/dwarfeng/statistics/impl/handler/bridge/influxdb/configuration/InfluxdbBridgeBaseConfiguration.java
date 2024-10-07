package com.dwarfeng.statistics.impl.handler.bridge.influxdb.configuration;

import com.influxdb.client.*;
import io.reactivex.rxjava3.internal.schedulers.ExecutorScheduler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class InfluxdbBridgeBaseConfiguration {

    private final ThreadPoolTaskExecutor executor;

    @Value("${bridge.influxdb.url}")
    private String url;
    @Value("${bridge.influxdb.token}")
    private String token;

    public InfluxdbBridgeBaseConfiguration(ThreadPoolTaskExecutor executor) {
        this.executor = executor;
    }

    @Bean(name = "influxdbBridge.influxDBClient", destroyMethod = "close")
    public InfluxDBClient influxDBClient() {
        return InfluxDBClientFactory.create(url, token.toCharArray());
    }

    @Bean(name = "influxdbBridge.writeApi", destroyMethod = "close")
    public WriteApi writeApi(
            @Qualifier("influxdbBridge.influxDBClient") InfluxDBClient influxDBClient

    ) {
        WriteOptions writeOptions = WriteOptions.builder()
                .writeScheduler(new ExecutorScheduler(executor, true, true))
                .build();
        return influxDBClient.makeWriteApi(writeOptions);
    }

    @Bean(name = "influxdbBridge.queryApi")
    public QueryApi queryApi(
            @Qualifier("influxdbBridge.influxDBClient") InfluxDBClient influxDBClient

    ) {
        return influxDBClient.getQueryApi();
    }
}
