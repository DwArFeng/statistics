package com.dwarfeng.statistics.stack.service;

import com.dwarfeng.statistics.stack.handler.Dispatcher;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 调度 QOS 服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface DispatchQosService extends Service {

    /**
     * 调度服务是否启动。
     *
     * @return 调度服务是否启动。
     * @throws ServiceException 服务异常。
     */
    boolean isStarted() throws ServiceException;

    /**
     * 返回当前正在使用的调度器。
     *
     * @return 当前正在使用的调度器。
     * @throws ServiceException 服务异常。
     */
    Dispatcher currentDispatcher() throws ServiceException;

    /**
     * 列出在用的全部调度器。
     *
     * @return 在用的全部调度器组成的列表。
     * @throws ServiceException 服务异常。
     */
    List<Dispatcher> allDispatchers() throws ServiceException;
}
