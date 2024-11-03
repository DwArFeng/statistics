package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.stack.handler.DriveLocalCacheHandler;
import com.dwarfeng.statistics.stack.handler.ExecuteLocalCacheHandler;
import com.dwarfeng.statistics.stack.handler.ReceiveHandler;
import com.dwarfeng.statistics.stack.handler.SuperviseHandler;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重置处理器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class ResetProcessor {

    private final SuperviseHandler superviseHandler;
    private final DriveLocalCacheHandler driveLocalCacheHandler;

    private final ReceiveHandler receiveHandler;
    private final ExecuteLocalCacheHandler executeLocalCacheHandler;

    private final Lock lock = new ReentrantLock();

    public ResetProcessor(
            SuperviseHandler superviseHandler,
            DriveLocalCacheHandler driveLocalCacheHandler,
            ReceiveHandler receiveHandler,
            ExecuteLocalCacheHandler executeLocalCacheHandler
    ) {
        this.superviseHandler = superviseHandler;
        this.driveLocalCacheHandler = driveLocalCacheHandler;
        this.receiveHandler = receiveHandler;
        this.executeLocalCacheHandler = executeLocalCacheHandler;
    }

    public void resetSupervise() throws HandlerException {
        lock.lock();
        try {
            doResetSupervise();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        } finally {
            lock.unlock();
        }
    }

    private void doResetSupervise() throws Exception {
        // 获取当前的主管处理器的状态。
        boolean superviseHandlerStarted = superviseHandler.isStarted();

        // 主管处理器停止，且清空本地缓存。
        superviseHandler.stop();
        driveLocalCacheHandler.clear();

        // 如果主管处理器之前是启动的，则重新启动。
        if (superviseHandlerStarted) {
            superviseHandler.start();
        }
    }

    public void resetExecute() throws HandlerException {
        lock.lock();
        try {
            doResetExecute();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        } finally {
            lock.unlock();
        }
    }

    private void doResetExecute() throws Exception {
        // 获取当前的接收处理器的状态。
        boolean receiveHandlerStarted = receiveHandler.isStarted();

        // 接收处理器停止，且清空本地缓存。
        receiveHandler.stop();
        executeLocalCacheHandler.clear();

        // 如果接收处理器之前是启动的，则重新启动。
        if (receiveHandlerStarted) {
            receiveHandler.start();
        }
    }
}
