package com.dwarfeng.statistics.impl.handler.dispatcher;

import com.dwarfeng.statistics.impl.handler.receiver.InjvmReceiver;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Injvm 调度器。
 *
 * <p>
 * 该调度器适用于单节点服务，直接调用虚拟机内部的调度器调用器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class InjvmDispatcher extends AbstractDispatcher {

    public static final String DISPATCHER_TYPE = "injvm";

    private static final Logger LOGGER = LoggerFactory.getLogger(InjvmDispatcher.class);

    private final InjvmReceiver injvmReceiver;

    public InjvmDispatcher(InjvmReceiver injvmReceiver) {
        super(DISPATCHER_TYPE);
        this.injvmReceiver = injvmReceiver;
    }

    @Override
    protected void doStart() {
        LOGGER.info("injvm dispatcher 上线...");
    }

    @Override
    protected void doStop() {
        LOGGER.info("injvm dispatcher 下线...");
    }

    @Override
    protected void doDispatch(LongIdKey statisticsSettingKey) {
        InjvmReceiver.InjvmDispatcherCaller caller = injvmReceiver.getDispatcherCaller();
        if (!caller.isStarted()) {
            throw new IllegalStateException("Injvm 接收器未启动");
        }
        caller.execute(statisticsSettingKey);
    }

    @Override
    public String toString() {
        return "InjvmDispatcher{" +
                "injvmReceiver=" + injvmReceiver +
                ", dispatcherType='" + dispatcherType + '\'' +
                '}';
    }
}
