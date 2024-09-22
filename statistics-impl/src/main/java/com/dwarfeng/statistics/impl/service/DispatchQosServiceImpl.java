package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.handler.DispatchHandler;
import com.dwarfeng.statistics.stack.handler.Dispatcher;
import com.dwarfeng.statistics.stack.handler.DispatcherHandler;
import com.dwarfeng.statistics.stack.service.DispatchQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DispatchQosServiceImpl implements DispatchQosService {

    private final DispatchHandler dispatchHandler;
    private final DispatcherHandler dispatcherHandler;

    private final ServiceExceptionMapper sem;

    public DispatchQosServiceImpl(
            DispatchHandler dispatchHandler,
            DispatcherHandler dispatcherHandler,
            ServiceExceptionMapper sem
    ) {
        this.dispatchHandler = dispatchHandler;
        this.dispatcherHandler = dispatcherHandler;
        this.sem = sem;
    }

    @Override
    public boolean isStarted() throws ServiceException {
        try {
            return dispatchHandler.isStarted();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断调度处理器是否启动时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public Dispatcher currentDispatcher() throws ServiceException {
        try {
            return dispatcherHandler.current();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取当前正在使用的调度器时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public List<Dispatcher> allDispatchers() throws ServiceException {
        try {
            return dispatcherHandler.all();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取全部调度器时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
