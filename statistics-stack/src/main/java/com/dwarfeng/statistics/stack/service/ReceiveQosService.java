package com.dwarfeng.statistics.stack.service;

import com.dwarfeng.statistics.stack.handler.Receiver;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 接收 QOS 服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ReceiveQosService extends Service {

    /**
     * 接收服务是否启动。
     *
     * @return 接收服务是否启动。
     * @throws ServiceException 服务异常。
     */
    boolean isStarted() throws ServiceException;

    /**
     * 接收服务启动。
     *
     * @throws ServiceException 服务异常。
     */
    void start() throws ServiceException;

    /**
     * 接收服务停止。
     *
     * @throws ServiceException 服务异常。
     */
    void stop() throws ServiceException;

    /**
     * 返回当前正在使用的接收器。
     *
     * @return 当前正在使用的接收器。
     * @throws ServiceException 服务异常。
     */
    Receiver currentReceiver() throws ServiceException;

    /**
     * 获取所有的接收器。
     *
     * @return 所有的接收器。
     * @throws ServiceException 服务异常。
     */
    List<Receiver> allReceivers() throws ServiceException;
}