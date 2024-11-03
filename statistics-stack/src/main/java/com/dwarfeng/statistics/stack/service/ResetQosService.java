package com.dwarfeng.statistics.stack.service;

import com.dwarfeng.statistics.stack.handler.Resetter;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 重置 QOS 服务。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface ResetQosService extends Service {

    /**
     * 列出在用的全部重置器。
     *
     * @return 在用的全部重置器组成的列表。
     * @throws ServiceException 服务异常。
     */
    List<Resetter> all() throws ServiceException;

    /**
     * 重置服务是否启动。
     *
     * @return 重置服务是否启动。
     * @throws ServiceException 服务异常。
     */
    boolean isStarted() throws ServiceException;

    /**
     * 重置服务启动。
     *
     * @throws ServiceException 服务异常。
     */
    void start() throws ServiceException;

    /**
     * 重置服务停止。
     *
     * @throws ServiceException 服务异常。
     */
    void stop() throws ServiceException;

    /**
     * 重置主管功能。
     *
     * @throws ServiceException 服务异常。
     */
    void resetSupervise() throws ServiceException;

    /**
     * 重置执行功能。
     *
     * @throws ServiceException 服务异常。
     */
    void resetExecute() throws ServiceException;
}
