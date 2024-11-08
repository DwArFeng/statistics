package com.dwarfeng.statistics.impl.handler.driver;

import com.dwarfeng.dcti.sdk.util.DataInfoUtil;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.statistics.impl.handler.DriverProvider;
import com.dwarfeng.statistics.stack.bean.entity.DriverInfo;
import com.dwarfeng.statistics.stack.exception.DriverException;
import com.dwarfeng.statistics.stack.handler.Driver;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
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
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Dcti 标准数据采集接口 Kafka 驱动提供器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class DctiKafkaDriverProvider implements DriverProvider {

    public static final String SUPPORT_TYPE = "dcti_kafka_driver";

    private final DctiKafkaDriver dctiKafkaDriver;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public DctiKafkaDriverProvider(DctiKafkaDriver dctiKafkaDriver) {
        this.dctiKafkaDriver = dctiKafkaDriver;
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(SUPPORT_TYPE, type);
    }

    @Override
    public Driver provide() {
        return dctiKafkaDriver;
    }

    @Component
    public static class DctiKafkaDriver extends AbstractDriver {

        private static final Logger LOGGER = LoggerFactory.getLogger(DctiKafkaDriver.class);

        private final KafkaListenerEndpointRegistry registry;

        @Value("${driver.kafka.dcti.listener_id}")
        private String listenerId;

        private final Map<Long, LongIdKey> registerMap = new HashMap<>();
        private final Lock lock = new ReentrantLock();

        private boolean kafkaListenerContainerStartFlag = false;

        public DctiKafkaDriver(KafkaListenerEndpointRegistry registry) {
            this.registry = registry;
        }

        @Override
        public void register(DriverInfo driverInfo) throws DriverException {
            lock.lock();
            try {
                // 将驱动信息注册到驱动器中。
                LongIdKey statisticsSettingKey = driverInfo.getStatisticsSettingKey();
                Long dataInfoKey = Long.parseLong(driverInfo.getParam());
                registerMap.put(dataInfoKey, statisticsSettingKey);
                // 按条件启动 Kafka 侦听容器。
                mayStartKafkaListenerContainer();
            } catch (Exception e) {
                throw new DriverException(e);
            } finally {
                lock.unlock();
            }
        }

        private void mayStartKafkaListenerContainer() {
            // 如果已经启动，则直接返回。
            if (kafkaListenerContainerStartFlag) {
                return;
            }
            // Kafka 侦听容器启动。
            LOGGER.info("Kafka 侦听容器启动...");
            MessageListenerContainer listenerContainer = registry.getListenerContainer(listenerId);
            if (Objects.isNull(listenerContainer)) {
                throw new IllegalStateException("找不到 kafka listener container " + listenerId);
            }
            //判断监听容器是否启动，未启动则将其启动。
            if (!listenerContainer.isRunning()) {
                listenerContainer.start();
            }
            listenerContainer.resume();
            // 置位启动标志。
            kafkaListenerContainerStartFlag = true;
        }

        @Override
        public void unregisterAll() {
            lock.lock();
            try {
                // 清除注册信息。
                registerMap.clear();
                // 按条件停止 Kafka 侦听容器。
                mayStopKafkaListenerContainer();
            } finally {
                lock.unlock();
            }
        }

        private void mayStopKafkaListenerContainer() {
            // 如果没有启动，则直接返回。
            if (!kafkaListenerContainerStartFlag) {
                return;
            }
            // Kafka 侦听容器停止。
            LOGGER.info("Kafka 侦听容器停止...");
            MessageListenerContainer listenerContainer = registry.getListenerContainer(listenerId);
            if (Objects.isNull(listenerContainer)) {
                throw new IllegalStateException("找不到 kafka listener container " + listenerId);
            }
            listenerContainer.pause();
            // 复位启动标志。
            kafkaListenerContainerStartFlag = false;
        }

        @KafkaListener(
                id = "${driver.kafka.dcti.listener_id}",
                containerFactory = "dctiKafkaDriver.kafkaListenerContainerFactory",
                topics = "${driver.kafka.dcti.listener_topic}"
        )
        public void handleDataInfo(String message, Acknowledgment ack) {
            lock.lock();
            try {
                // 无论之后的驱动判断是否正常，都只判断一次，因此首先提交 ack。
                ack.acknowledge();
                // 驱动判断。
                DataInfo dataInfo = DataInfoUtil.fromMessage(message);
                Long dataInfoKey = dataInfo.getPointLongId();
                LongIdKey statisticsSettingKey = registerMap.getOrDefault(dataInfoKey, null);
                if (Objects.nonNull(statisticsSettingKey)) {
                    context.execute(statisticsSettingKey);
                }
            } catch (Exception e) {
                LOGGER.warn("处理 dcti dataInfo 时出现异常，将忽略驱动动作 1 次，异常信息如下", e);
            } finally {
                lock.unlock();
            }
        }

        @Override
        public String toString() {
            return "DctiKafkaDriver{" +
                    "context=" + context +
                    '}';
        }
    }

    @Configuration
    @EnableKafka
    public static class KafkaDriverConfiguration {

        private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDriverConfiguration.class);

        @Value("${driver.kafka.dcti.bootstrap_servers}")
        private String consumerBootstrapServers;
        @Value("${driver.kafka.dcti.session_timeout_ms}")
        private int sessionTimeoutMs;
        @Value("${driver.kafka.dcti.auto_offset_reset}")
        private String autoOffsetReset;
        @Value("${driver.kafka.dcti.concurrency}")
        private int concurrency;
        @Value("${driver.kafka.dcti.poll_timeout}")
        private int pollTimeout;
        @Value("${driver.kafka.dcti.max_poll_records}")
        private int maxPollRecords;
        @Value("${driver.kafka.dcti.max_poll_interval_ms}")
        private int maxPollIntervalMs;

        @SuppressWarnings("DuplicatedCode")
        @Bean("dctiKafkaDriver.consumerProperties")
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
        @Bean("dctiKafkaDriver.consumerFactory")
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
        @Bean("dctiKafkaDriver.kafkaListenerContainerFactory")
        public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
        kafkaListenerContainerFactory() {
            LOGGER.debug("配置 Kafka 侦听容器工厂...");
            ConsumerFactory<String, String> consumerFactory = consumerFactory();
            ConcurrentKafkaListenerContainerFactory<String, String> factory =
                    new ConcurrentKafkaListenerContainerFactory<>();
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
    }
}
