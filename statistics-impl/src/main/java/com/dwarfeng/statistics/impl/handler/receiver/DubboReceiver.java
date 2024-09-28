package com.dwarfeng.statistics.impl.handler.receiver;

import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import com.dwarfeng.subgrade.stack.service.Service;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Dubbo 接收器。
 *
 * <p>
 * 基于 Dubbo 实现的接收器，利用 Dubbo 的服务提供者机制实现多个接收节点的负载均衡。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class DubboReceiver extends AbstractReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(DubboReceiver.class);

    public static final String RECEIVER_TYPE = "dubbo";

    private final ApplicationContext ctx;

    private final RegistryConfig registry;
    private final ProtocolConfig protocol;

    @Value("${dubbo.provider.group}")
    private String group;

    private final Lock lock = new ReentrantLock();

    private ServiceConfig<DubboReceiveService> serviceConfig = null;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public DubboReceiver(
            ApplicationContext ctx,
            RegistryConfig registry,
            @Qualifier("dubbo") ProtocolConfig protocol
    ) {
        super(RECEIVER_TYPE);
        this.ctx = ctx;
        this.registry = registry;
        this.protocol = protocol;
    }

    @Override
    protected void doStart() {
        lock.lock();
        try {
            // 日志记录。
            LOGGER.info("dubbo receiver 上线...");

            // 获取接口的实现。
            DubboReceiveService dubboReceiveService = ctx.getBean(DubboReceiveService.class);

            // 将接口注册为服务。
            ServiceConfig<DubboReceiveService> serviceConfig = new ServiceConfig<>();
            serviceConfig.setRegistry(registry);
            serviceConfig.setProtocol(protocol);
            serviceConfig.setGroup(group);
            serviceConfig.setInterface(DubboReceiveService.class);
            serviceConfig.setRef(dubboReceiveService);

            // 暴露服务。
            serviceConfig.export();

            // 缓存服务。
            this.serviceConfig = serviceConfig;
        } finally {
            lock.unlock();
        }
    }

    @Override
    protected void doStop() {
        lock.lock();
        try {
            // 日志记录。
            LOGGER.info("dubbo receiver 下线...");

            // 取消暴露服务。
            if (Objects.nonNull(serviceConfig)) {
                serviceConfig.unexport();
                serviceConfig = null;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "DubboReceiver{" +
                "ctx=" + ctx +
                ", registry=" + registry +
                ", protocol=" + protocol +
                ", group='" + group + '\'' +
                ", lock=" + lock +
                ", serviceConfig=" + serviceConfig +
                ", receiverType='" + receiverType + '\'' +
                ", context=" + context +
                '}';
    }

    public interface DubboReceiveService extends Service {

        /**
         * 指定的统计设置调用执行动作。
         *
         * <p>
         * 因为 Dubbo 广播响应机制无法处理 void 返回类型，所以方法需要返回一个结果。
         *
         * @param statisticsSettingKey 指定统计设置的主键。
         * @return 恒为 true。
         * @throws ServiceException 服务异常。
         */
        boolean execute(LongIdKey statisticsSettingKey) throws ServiceException;
    }

    @org.springframework.stereotype.Service("dubboReceiver.dubboReceiveServiceImpl")
    public class DubboReceiveServiceImpl implements DubboReceiveService {

        private final ServiceExceptionMapper sem;

        public DubboReceiveServiceImpl(ServiceExceptionMapper sem) {
            this.sem = sem;
        }

        @Override
        public boolean execute(LongIdKey statisticsSettingKey) throws ServiceException {
            try {
                // 日志记录。
                LOGGER.debug("接收到统计设置键 {}, 准备执行...", statisticsSettingKey);
                // 调用上下文的执行方法。
                try {
                    context.execute(statisticsSettingKey);
                } catch (Exception e) {
                    String message = "接收器调用执行动作时发生异常, 将忽略统计任务执行 1 次, " +
                            "相关 statisticsSettingKey 为 " + statisticsSettingKey + ", 异常信息如下: ";
                    LOGGER.warn(message, e);
                }
                // 返回 true。
                return true;
            } catch (Exception e) {
                throw ServiceExceptionHelper.logParse("发生异常", LogLevel.WARN, e, sem);
            }
        }

        @Override
        public String toString() {
            return "DubboReceiveServiceImpl{" +
                    "sem=" + sem +
                    '}';
        }
    }
}
