package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.stack.handler.ReceiveHandler;
import com.dwarfeng.statistics.stack.handler.Receiver;
import com.dwarfeng.statistics.stack.handler.ReceiverHandler;
import com.dwarfeng.subgrade.impl.handler.GeneralStartableHandler;
import com.dwarfeng.subgrade.impl.handler.Worker;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ReceiveHandlerImpl implements ReceiveHandler {

    private final GeneralStartableHandler handler;

    public ReceiveHandlerImpl(ReceiveWorker receiveWorker) {
        handler = new GeneralStartableHandler(receiveWorker);
    }

    @BehaviorAnalyse
    @Override
    public boolean isStarted() {
        return handler.isStarted();
    }

    @BehaviorAnalyse
    @Override
    public void start() throws HandlerException {
        handler.start();
    }

    @BehaviorAnalyse
    @Override
    public void stop() throws HandlerException {
        handler.stop();
    }

    @Component
    public static class ReceiveWorker implements Worker {

        private static final Logger LOGGER = LoggerFactory.getLogger(ReceiveWorker.class);

        private final ReceiverHandler receiverHandler;

        public ReceiveWorker(ReceiverHandler receiverHandler) {
            this.receiverHandler = receiverHandler;
        }

        @Override
        public void work() throws Exception {
            // 记录日志。
            LOGGER.info("接收器开始工作...");

            Receiver receiver = receiverHandler.current();
            receiver.start();
        }

        @Override
        public void rest() throws Exception {
            // 记录日志。
            LOGGER.info("接收器停止工作...");

            Receiver receiver = receiverHandler.current();
            receiver.stop();
        }
    }
}
