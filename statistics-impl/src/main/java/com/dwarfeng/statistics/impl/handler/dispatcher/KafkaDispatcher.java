package com.dwarfeng.statistics.impl.handler.dispatcher;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Kafka 调度器。
 *
 * <p>
 * 基于 Kafka 实现的调度器，利用 Kafka 的消费者机制实现多个接收节点的负载均衡。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class KafkaDispatcher extends AbstractDispatcher {

    public static final String DISPATCHER_TYPE = "kafka";

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDispatcher.class);

    private final KafkaSender kafkaSender;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public KafkaDispatcher(KafkaSender kafkaSender) {
        super(DISPATCHER_TYPE);
        this.kafkaSender = kafkaSender;
    }

    @Override
    protected void doStart() {
        LOGGER.info("kafka dispatcher 上线...");
        kafkaSender.start();
    }

    @Override
    protected void doStop() {
        LOGGER.info("kafka dispatcher 下线...");
        kafkaSender.stop();
    }

    @Override
    protected void doDispatch(LongIdKey statisticsSettingKey) {
        kafkaSender.send(JSON.toJSONString(FastJsonLongIdKey.of(statisticsSettingKey)));
    }

    @Override
    public String toString() {
        return "KafkaDispatcher{" +
                "kafkaSender=" + kafkaSender +
                '}';
    }

    @Component("kafkaDispatcher.kafkaSender")
    public static class KafkaSender {

        private static final Logger LOGGER = LoggerFactory.getLogger(KafkaSender.class);

        private static final String LOAD_BALANCE_MODE_DEFAULT = "DEFAULT";
        private static final String LOAD_BALANCE_MODE_ROUND_ROBIN = "ROUND_ROBIN";
        private static final String LOAD_BALANCE_MODE_RANDOM = "RANDOM";

        private final ApplicationContext ctx;

        private final KafkaTemplate<String, String> kafkaTemplate;

        private final ThreadPoolTaskScheduler scheduler;

        @Value("${dispatcher.kafka.load_balance_mode}")
        private String loadBalanceMode;
        @Value("${dispatcher.kafka.partition_check_interval}")
        private long partitionCheckInterval;
        @Value("${dispatcher.kafka.topic.dispatch}")
        private String dispatchTopic;

        private final Lock lock = new ReentrantLock();

        private Future<?> partitionCheckFuture = null;
        private List<PartitionInfo> partitionInfos = null;
        private int roundRobinIndex = 0;

        public KafkaSender(
                ApplicationContext ctx,
                @Qualifier("kafkaDispatcher.kafkaTemplate") KafkaTemplate<String, String> kafkaTemplate,
                ThreadPoolTaskScheduler scheduler
        ) {
            this.ctx = ctx;
            this.kafkaTemplate = kafkaTemplate;
            this.scheduler = scheduler;
        }

        @Transactional(transactionManager = "kafkaDispatcher.kafkaTransactionManager")
        public void start() {
            lock.lock();
            try {
                // 立即检查分区信息。
                partitionInfos = kafkaTemplate.partitionsFor(dispatchTopic);
                // 启动定时检查分区信息的任务。
                scheduler.scheduleAtFixedRate(ctx.getBean(PartitionCheckTask.class, this), partitionCheckInterval);
            } finally {
                lock.unlock();
            }
        }

        @Transactional(transactionManager = "kafkaDispatcher.kafkaTransactionManager")
        public void stop() {
            lock.lock();
            try {
                // 清除分区信息。
                if (Objects.nonNull(partitionInfos)) {
                    partitionInfos = null;
                }
                // 取消定时检查分区信息的任务。
                if (Objects.nonNull(partitionCheckFuture)) {
                    partitionCheckFuture.cancel(true);
                    partitionCheckFuture = null;
                }
                // 重置轮询索引。
                roundRobinIndex = 0;
            } finally {
                lock.unlock();
            }
        }

        @Transactional(transactionManager = "kafkaDispatcher.kafkaTransactionManager")
        public void send(String message) {
            lock.lock();
            try {
                switch (StringUtils.upperCase(loadBalanceMode)) {
                    case LOAD_BALANCE_MODE_DEFAULT:
                        sendDefault(message);
                        break;
                    case LOAD_BALANCE_MODE_ROUND_ROBIN:
                        sendRoundRobin(message);
                        break;
                    case LOAD_BALANCE_MODE_RANDOM:
                        sendRandom(message);
                        break;
                    default:
                        throw new IllegalStateException("未知的负载均衡模式: " + loadBalanceMode);
                }
            } finally {
                lock.unlock();
            }
        }

        private void sendDefault(String message) {
            kafkaTemplate.send(new ProducerRecord<>(dispatchTopic, message));
        }

        private void sendRoundRobin(String message) {
            int partition = partitionInfos.get(roundRobinIndex).partition();
            kafkaTemplate.send(new ProducerRecord<>(dispatchTopic, partition, null, message));
            roundRobinIndex = (roundRobinIndex + 1) % partitionInfos.size();
        }

        private void sendRandom(String message) {
            int partition = (int) (Math.random() * partitionInfos.size());
            kafkaTemplate.send(new ProducerRecord<>(dispatchTopic, partition, null, message));
        }

        @Override
        public String toString() {
            return "KafkaSender{" +
                    "kafkaTemplate=" + kafkaTemplate +
                    '}';
        }

        @Component("kafkaDispatcher.partitionCheckTask")
        @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
        public class PartitionCheckTask implements Runnable {

            @Transactional(transactionManager = "kafkaDispatcher.kafkaTransactionManager")
            @Override
            public void run() {
                lock.lock();
                try {
                    LOGGER.debug("定时检查分区信息...");
                    partitionInfos = kafkaTemplate.partitionsFor(dispatchTopic);
                } finally {
                    lock.unlock();
                }
            }

            @Override
            public String toString() {
                return "PartitionCheckTask{}";
            }
        }
    }

    @Configuration("kafkaDispatcher.kafkaConfiguration")
    public static class KafkaConfiguration {

        private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConfiguration.class);

        @Value("${dispatcher.kafka.bootstrap_servers}")
        private String producerBootstrapServers;
        @Value("${dispatcher.kafka.retries}")
        private int retries;
        @Value("${dispatcher.kafka.linger}")
        private long linger;
        @Value("${dispatcher.kafka.buffer_memory}")
        private long bufferMemory;
        @Value("${dispatcher.kafka.batch_size}")
        private int batchSize;
        @Value("${dispatcher.kafka.acks}")
        private String acks;
        @Value("${dispatcher.kafka.transaction_prefix}")
        private String transactionPrefix;

        @Bean("kafkaDispatcher.producerProperties")
        public Map<String, Object> producerProperties() {
            LOGGER.info("配置Kafka生产者属性...");
            Map<String, Object> props = new HashMap<>();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerBootstrapServers);
            props.put(ProducerConfig.RETRIES_CONFIG, retries);
            props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
            props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
            props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
            props.put(ProducerConfig.ACKS_CONFIG, acks);
            LOGGER.debug("Kafka生产者属性配置完成...");
            return props;
        }

        @Bean("kafkaDispatcher.producerFactory")
        public ProducerFactory<String, String> producerFactory() {
            LOGGER.info("配置Kafka生产者工厂...");
            Map<String, Object> properties = producerProperties();
            DefaultKafkaProducerFactory<String, String> factory = new DefaultKafkaProducerFactory<>(properties);
            factory.setTransactionIdPrefix(transactionPrefix);
            factory.setKeySerializer(new StringSerializer());
            factory.setValueSerializer(new StringSerializer());
            LOGGER.debug("Kafka生产者工厂配置完成");
            return factory;
        }

        @Bean("kafkaDispatcher.kafkaTemplate")
        public KafkaTemplate<String, String> kafkaTemplate() {
            LOGGER.info("生成KafkaTemplate...");
            ProducerFactory<String, String> producerFactory = producerFactory();
            KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory, true);
            LOGGER.debug("KafkaTemplate生成完成...");
            return kafkaTemplate;
        }

        @Bean("kafkaDispatcher.kafkaTransactionManager")
        public KafkaTransactionManager<String, String> kafkaTransactionManager() {
            LOGGER.info("生成KafkaTransactionManager...");
            ProducerFactory<String, String> producerFactory = producerFactory();
            LOGGER.debug("KafkaTransactionManager生成完成...");
            return new KafkaTransactionManager<>(producerFactory);
        }

        @Override
        public String toString() {
            return "KafkaPusherConfiguration{" +
                    "producerBootstrapServers='" + producerBootstrapServers + '\'' +
                    ", retries=" + retries +
                    ", linger=" + linger +
                    ", bufferMemory=" + bufferMemory +
                    ", batchSize=" + batchSize +
                    ", acks='" + acks + '\'' +
                    ", transactionPrefix='" + transactionPrefix + '\'' +
                    '}';
        }
    }
}
