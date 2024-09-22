package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.stack.handler.DispatchHandler;
import com.dwarfeng.statistics.stack.handler.DriveHandler;
import com.dwarfeng.statistics.stack.handler.SuperviseHandler;
import com.dwarfeng.subgrade.impl.handler.CuratorDistributedLockHandler;
import com.dwarfeng.subgrade.impl.handler.Worker;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SuperviseHandlerImpl implements SuperviseHandler {

    private final CuratorDistributedLockHandler handler;

    public SuperviseHandlerImpl(
            CuratorFramework curatorFramework,
            @Value("${curator.latch_path.supervise.leader_latch}") String leaserLatchPath,
            SuperviseWorker superviseWorker
    ) {
        handler = new CuratorDistributedLockHandler(curatorFramework, leaserLatchPath, superviseWorker);
    }

    @BehaviorAnalyse
    @Override
    public boolean isOnline() {
        return handler.isOnline();
    }

    @BehaviorAnalyse
    @Override
    public void online() throws HandlerException {
        handler.online();
    }

    @BehaviorAnalyse
    @Override
    public void offline() throws HandlerException {
        handler.offline();
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

    @BehaviorAnalyse
    @Override
    public boolean isLockHolding() {
        return handler.isLockHolding();
    }

    @BehaviorAnalyse
    @Override
    public boolean isWorking() {
        return handler.isWorking();
    }

    @Component
    public static class SuperviseWorker implements Worker {

        private static final Logger LOGGER = LoggerFactory.getLogger(SuperviseWorker.class);

        private final DriveHandler driveHandler;
        private final DispatchHandler dispatchHandler;

        public SuperviseWorker(DriveHandler driveHandler, DispatchHandler dispatchHandler) {
            this.driveHandler = driveHandler;
            this.dispatchHandler = dispatchHandler;
        }

        @Override
        public void work() throws Exception {
            LOGGER.info("主管处理器开始工作...");
            dispatchHandler.start();
            driveHandler.start();
        }

        @Override
        public void rest() throws Exception {
            LOGGER.info("驱动器停止工作...");
            driveHandler.stop();
            dispatchHandler.stop();
        }
    }
}
