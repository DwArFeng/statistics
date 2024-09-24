package com.dwarfeng.statistics.impl.handler.receiver;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Kafka 接收器。
 *
 * <p>
 * 基于 Kafka 实现的接收器，利用 Kafka 的消费者机制实现多个接收节点的负载均衡。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class KafkaReceiver extends AbstractReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaReceiver.class);

    public static final String RECEIVER_TYPE = "kafka";

    private final KafkaListenerEndpointRegistry registry;

    @Value("${receiver.kafka.listener_id}")
    private String listenerId;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public KafkaReceiver(KafkaListenerEndpointRegistry registry) {
        super(RECEIVER_TYPE);
        this.registry = registry;
    }

    @Override
    protected void doStart() {
        LOGGER.info("kafka receiver 上线...");
        MessageListenerContainer listenerContainer = registry.getListenerContainer(listenerId);
        if (Objects.isNull(listenerContainer)) {
            throw new IllegalStateException("找不到 kafka listener container " + listenerId);
        }
        //判断监听容器是否启动，未启动则将其启动
        if (!listenerContainer.isRunning()) {
            listenerContainer.start();
        }
        listenerContainer.resume();
    }

    @Override
    protected void doStop() {
        LOGGER.info("kafka receiver 下线...");
        MessageListenerContainer listenerContainer = registry.getListenerContainer(listenerId);
        if (Objects.isNull(listenerContainer)) {
            throw new IllegalStateException("找不到 kafka listener container " + listenerId);
        }
        listenerContainer.stop();
    }

    @KafkaListener(
            id = "${receiver.kafka.listener_id}",
            containerFactory = "kafkaReceiver.kafkaListenerContainerFactory",
            topics = "${receiver.kafka.listener_topic}"
    )
    public void handleConsumerRecordsPolled(
            List<ConsumerRecord<String, String>> consumerRecords, Consumer<String, String> consumer, Acknowledgment ack
    ) {
        for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
            String message = consumerRecord.value();
            try {
                LongIdKey statisticsSettingKey = FastJsonLongIdKey.toStackBean(
                        JSON.parseObject(message, FastJsonLongIdKey.class)
                );
                // 代码执行到此处，说明接收器一定处于启动状态，因此不需要额外判断接收器是否启动。
                // 直接调用上下文的执行方法。
                execute(statisticsSettingKey);
            } catch (Exception e) {
                String message1 = "接收器无法处理 kafka 消息, 将忽略统计任务执行 1 次, " +
                        "消息内容为 " + message + ", 异常信息如下";
                LOGGER.warn(message1, e);
            }
        }
        ack.acknowledge();
    }

    private void execute(LongIdKey statisticsSettingKey) {
        // 调用上下文的执行方法。
        try {
            context.execute(statisticsSettingKey);
        } catch (Exception e) {
            String message = "接收器调用执行动作时发生异常, 将忽略统计任务执行 1 次, " +
                    "相关 statisticsSettingKey 为 " + statisticsSettingKey + ", 异常信息如下: ";
            LOGGER.warn(message, e);
        }
    }

    @Override
    public String toString() {
        return "KafkaReceiver{" +
                "registry=" + registry +
                ", listenerId='" + listenerId + '\'' +
                ", receiverType='" + receiverType + '\'' +
                ", context=" + context +
                '}';
    }

    @Configuration("kafkaReceiver.kafkaConfiguration")
    public static class KafkaConfiguration {

        private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConfiguration.class);

        @Value("${receiver.kafka.bootstrap_servers}")
        private String consumerBootstrapServers;
        @Value("${receiver.kafka.session_timeout_ms}")
        private int sessionTimeoutMs;
        @Value("${receiver.kafka.auto_offset_reset}")
        private String autoOffsetReset;
        @Value("${receiver.kafka.concurrency}")
        private int concurrency;
        @Value("${receiver.kafka.poll_timeout}")
        private int pollTimeout;
        @Value("${receiver.kafka.max_poll_records}")
        private int maxPollRecords;
        @Value("${receiver.kafka.max_poll_interval_ms}")
        private int maxPollIntervalMs;

        @SuppressWarnings("DuplicatedCode")
        @Bean("kafkaReceiver.consumerProperties")
        public Map<String, Object> consumerProperties() {
            LOGGER.debug("配置 Kafka 消费者属性...");
            Map<String, Object> props = new HashMap<>();

            // 配置值。
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerBootstrapServers);
            props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMs);
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
            props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
            props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollIntervalMs);

            // 默认值。
            // 本实例使用ack手动提交，因此禁止自动提交的功能。
            props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

            LOGGER.debug("Kafka 消费者属性配置完成...");
            return props;
        }

        @SuppressWarnings("DuplicatedCode")
        @Bean("kafkaReceiver.consumerFactory")
        public ConsumerFactory<String, String> consumerFactory() {
            LOGGER.debug("配置 Kafka 消费者工厂...");
            Map<String, Object> properties = consumerProperties();
            DefaultKafkaConsumerFactory<String, String> factory = new DefaultKafkaConsumerFactory<>(properties);
            factory.setKeyDeserializer(new StringDeserializer());
            factory.setValueDeserializer(new StringDeserializer());
            LOGGER.debug("Kafka 消费者工厂配置完成");
            return factory;
        }

        @SuppressWarnings("DuplicatedCode")
        @Bean("kafkaReceiver.kafkaListenerContainerFactory")
        public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
        kafkaListenerContainerFactory() {
            LOGGER.debug("配置 Kafka 侦听容器工厂...");
            ConsumerFactory<String, String> consumerFactory = consumerFactory();
            ConcurrentKafkaListenerContainerFactory<String, String> factory
                    = new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerFactory);
            factory.setConcurrency(concurrency);
            factory.getContainerProperties().setPollTimeout(pollTimeout);
            // Kafka侦听容器通过框架对开启和关闭进行托管，因此在启动时不自动开启。
            factory.setAutoStartup(false);
            // 监听器启用批量监听模式。
            factory.setBatchListener(true);
            // 配置ACK模式为手动立即提交。
            factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
            LOGGER.info("配置 Kafka 侦听容器工厂...");
            return factory;
        }

        @Override
        public String toString() {
            return "KafkaConfiguration{" +
                    "consumerBootstrapServers='" + consumerBootstrapServers + '\'' +
                    ", sessionTimeoutMs=" + sessionTimeoutMs +
                    ", autoOffsetReset='" + autoOffsetReset + '\'' +
                    ", concurrency=" + concurrency +
                    ", pollTimeout=" + pollTimeout +
                    ", maxPollRecords=" + maxPollRecords +
                    ", maxPollIntervalMs=" + maxPollIntervalMs +
                    '}';
        }
    }
}
