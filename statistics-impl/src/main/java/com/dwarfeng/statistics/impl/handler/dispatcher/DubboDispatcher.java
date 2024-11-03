package com.dwarfeng.statistics.impl.handler.dispatcher;

import com.dwarfeng.statistics.impl.handler.receiver.DubboReceiver;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Dubbo 调度器。
 *
 * <p>
 * 基于 Dubbo 实现的调度器，利用 Dubbo 的服务提供者机制实现多个调度节点的负载均衡。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class DubboDispatcher extends AbstractDispatcher {

    public static final String DISPATCHER_TYPE = "dubbo";

    private static final Logger LOGGER = LoggerFactory.getLogger(DubboDispatcher.class);

    private final RegistryConfig registry;

    @Value("${dubbo.provider.group}")
    private String group;

    private final Lock lock = new ReentrantLock();

    private ReferenceConfig<DubboReceiver.DubboReceiveService> referenceConfig = null;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public DubboDispatcher(RegistryConfig registry) {
        super(DISPATCHER_TYPE);
        this.registry = registry;
    }

    @Override
    protected void doStart() {
        lock.lock();
        try {
            // 日志记录。
            LOGGER.info("dubbo dispatcher 开启...");

            // 引用远程服务。
            ReferenceConfig<DubboReceiver.DubboReceiveService> referenceConfig = new ReferenceConfig<>();
            referenceConfig.setRegistry(registry);
            referenceConfig.setGroup(group);
            referenceConfig.setCheck(false);
            referenceConfig.setInterface(DubboReceiver.DubboReceiveService.class);
            referenceConfig.setScope("remote");

            // 缓存引用。
            this.referenceConfig = referenceConfig;
        } finally {
            lock.unlock();
        }
    }

    @Override
    protected void doStop() {
        lock.lock();
        try {
            // 日志记录。
            LOGGER.info("dubbo dispatcher 关闭...");

            // 关闭引用。
            if (Objects.nonNull(referenceConfig)) {
                referenceConfig.destroy();
                referenceConfig = null;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    protected void doDispatch(LongIdKey statisticsSettingKey) throws Exception {
        lock.lock();
        try {
            // 调用远程服务。
            referenceConfig.get().execute(statisticsSettingKey);
        } finally {
            lock.unlock();
        }
    }
}
