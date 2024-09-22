package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.handler.ReceiveHandler;
import com.dwarfeng.statistics.stack.handler.Receiver;
import com.dwarfeng.statistics.stack.handler.ReceiverHandler;
import com.dwarfeng.statistics.stack.service.ReceiveQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiverQosServiceImpl implements ReceiveQosService {

    private final ReceiveHandler receiveHandler;
    private final ReceiverHandler receiverHandler;

    private final ServiceExceptionMapper sem;

    public ReceiverQosServiceImpl(
            ReceiveHandler receiveHandler,
            ReceiverHandler receiverHandler,
            ServiceExceptionMapper sem
    ) {
        this.receiveHandler = receiveHandler;
        this.receiverHandler = receiverHandler;
        this.sem = sem;
    }

    @Override
    public boolean isStarted() throws ServiceException {
        try {
            return receiveHandler.isStarted();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断接收处理器是否启动时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void start() throws ServiceException {
        try {
            receiveHandler.start();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("启动接收处理器时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void stop() throws ServiceException {
        try {
            receiveHandler.stop();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("停止接收处理器时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public Receiver currentReceiver() throws ServiceException {
        try {
            return receiverHandler.current();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取当前正在使用的接收器时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public List<Receiver> allReceivers() throws ServiceException {
        try {
            return receiverHandler.all();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取全部接收器时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
