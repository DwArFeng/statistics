package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.handler.TaskCheckHandler;
import com.dwarfeng.statistics.stack.service.TaskCheckQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class TaskCheckQosServiceImpl implements TaskCheckQosService {

    private final TaskCheckHandler taskCheckHandler;

    private final ServiceExceptionMapper sem;

    public TaskCheckQosServiceImpl(
            TaskCheckHandler taskCheckHandler,
            ServiceExceptionMapper sem
    ) {
        this.taskCheckHandler = taskCheckHandler;
        this.sem = sem;
    }

    @Override
    public boolean isOnline() throws ServiceException {
        try {
            return taskCheckHandler.isOnline();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断任务检查处理器是否上线时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void online() throws ServiceException {
        try {
            taskCheckHandler.online();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("上线任务检查处理器时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void offline() throws ServiceException {
        try {
            taskCheckHandler.offline();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("下线任务检查处理器时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public boolean isLockHolding() throws ServiceException {
        try {
            return taskCheckHandler.isLockHolding();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断任务检查处理器是否正在持有锁时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public boolean isStarted() throws ServiceException {
        try {
            return taskCheckHandler.isStarted();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断任务检查处理器是否启动时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void start() throws ServiceException {
        try {
            taskCheckHandler.start();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("任务检查处理器启动时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void stop() throws ServiceException {
        try {
            taskCheckHandler.stop();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("任务检查处理器停止时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public boolean isWorking() throws ServiceException {
        try {
            return taskCheckHandler.isWorking();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断任务检查处理器是否正在工作时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
