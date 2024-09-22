package com.dwarfeng.statistics.impl.handler.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 什么也不做的接收器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class DoNothingReceiver extends AbstractReceiver {

    public static final String RECEIVER_TYPE = "do_nothing";

    private static final Logger LOGGER = LoggerFactory.getLogger(DoNothingReceiver.class);

    public DoNothingReceiver() {
        super(RECEIVER_TYPE);
    }

    @Override
    protected void doStart() {
        LOGGER.info("Do nothing 接收器启动, 该接收器仅用于测试和调试, 不应该在生产环境中使用");
    }

    @Override
    protected void doStop() {
        LOGGER.info("Do nothing 接收器停止, 该接收器仅用于测试和调试, 不应该在生产环境中使用");
    }

    @Override
    public String toString() {
        return "DoNothingReceiver{" +
                "receiverType='" + receiverType + '\'' +
                ", context=" + context +
                '}';
    }
}
