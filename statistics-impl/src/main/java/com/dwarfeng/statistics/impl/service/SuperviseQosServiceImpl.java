package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.handler.SuperviseHandler;
import com.dwarfeng.statistics.stack.service.SuperviseQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class SuperviseQosServiceImpl implements SuperviseQosService {

    private final SuperviseHandler superviseHandler;

    private final ServiceExceptionMapper sem;

    public SuperviseQosServiceImpl(
            SuperviseHandler superviseHandler,
            ServiceExceptionMapper sem
    ) {
        this.superviseHandler = superviseHandler;
        this.sem = sem;
    }

    @Override
    public boolean isOnline() throws ServiceException {
        try {
            return superviseHandler.isOnline();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断主管处理器是否上线时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void online() throws ServiceException {
        try {
            superviseHandler.online();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("上线主管处理器时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void offline() throws ServiceException {
        try {
            superviseHandler.offline();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("下线主管处理器时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public boolean isLockHolding() throws ServiceException {
        try {
            return superviseHandler.isLockHolding();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断主管处理器是否正在持有锁时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public boolean isStarted() throws ServiceException {
        try {
            return superviseHandler.isStarted();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断主管处理器是否启动时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void start() throws ServiceException {
        try {
            superviseHandler.start();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("主管处理器启动时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void stop() throws ServiceException {
        try {
            superviseHandler.stop();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("主管处理器停止时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public boolean isWorking() throws ServiceException {
        try {
            return superviseHandler.isWorking();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断主管处理器是否正在工作时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
