package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.stack.handler.DispatchHandler;
import com.dwarfeng.statistics.stack.handler.Dispatcher;
import com.dwarfeng.statistics.stack.handler.DispatcherHandler;
import com.dwarfeng.subgrade.impl.handler.GeneralStartableHandler;
import com.dwarfeng.subgrade.impl.handler.Worker;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DispatchHandlerImpl implements DispatchHandler {

    private final GeneralStartableHandler handler;

    public DispatchHandlerImpl(DispatchWorker dispatchWorker) {
        handler = new GeneralStartableHandler(dispatchWorker);
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
    public static class DispatchWorker implements Worker {

        private static final Logger LOGGER = LoggerFactory.getLogger(DispatchWorker.class);

        private final DispatcherHandler dispatcherHandler;

        public DispatchWorker(DispatcherHandler dispatcherHandler) {
            this.dispatcherHandler = dispatcherHandler;
        }

        @Override
        public void work() throws Exception {
            // 记录日志。
            LOGGER.info("调度器开始工作...");

            Dispatcher dispatcher = dispatcherHandler.current();
            dispatcher.start();
        }

        @Override
        public void rest() throws Exception {
            // 记录日志。
            LOGGER.info("调度器停止工作...");

            Dispatcher dispatcher = dispatcherHandler.current();
            dispatcher.stop();
        }
    }
}
