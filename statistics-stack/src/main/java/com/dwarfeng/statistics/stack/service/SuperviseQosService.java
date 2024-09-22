package com.dwarfeng.statistics.stack.service;

import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 主管 QOS 服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface SuperviseQosService extends Service {

    /**
     * 主管服务是否上线。
     *
     * @return 是否上线。
     * @throws ServiceException 服务异常。
     */
    boolean isOnline() throws ServiceException;

    /**
     * 上线主管服务。
     *
     * @throws ServiceException 服务异常。
     */
    void online() throws ServiceException;

    /**
     * 下线主管服务。
     *
     * @throws ServiceException 服务异常。
     */
    void offline() throws ServiceException;

    /**
     * 主管服务是否正在持有锁。
     *
     * @return 主管服务是否正在持有锁。
     * @throws ServiceException 服务异常。
     */
    boolean isLockHolding() throws ServiceException;

    /**
     * 主管服务是否启动。
     *
     * @return 主管服务是否启动。
     * @throws ServiceException 服务异常。
     */
    boolean isStarted() throws ServiceException;

    /**
     * 主管服务启动。
     *
     * @throws ServiceException 服务异常。
     */
    void start() throws ServiceException;

    /**
     * 主管服务停止。
     *
     * @throws ServiceException 服务异常。
     */
    void stop() throws ServiceException;

    /**
     * 主管服务是否正在工作。
     *
     * @return 主管服务是否正在工作。
     * @throws ServiceException 服务异常。
     */
    boolean isWorking() throws ServiceException;
}
