package com.dwarfeng.statistics.stack.service;

import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 支持 QoS 服务。
 *
 * @author zhaofz
 * @since 1.3.0
 */

public interface SupportQosService extends Service {

    /**
     * 重置驱动器支持。
     *
     * @throws ServiceException 服务异常。
     */
    void resetDriver() throws ServiceException;

    /**
     * 重置过滤器支持。
     *
     * @throws ServiceException 服务异常。
     */
    void resetFilter() throws ServiceException;

    /**
     * 重置映射器支持。
     *
     * @throws ServiceException 服务异常。
     */

    void resetMapper() throws ServiceException;

    /**
     * 重置提供器支持。
     *
     * @throws ServiceException 服务异常。
     */
    void resetProvider() throws ServiceException;
}
