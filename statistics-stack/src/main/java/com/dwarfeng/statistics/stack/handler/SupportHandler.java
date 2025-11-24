package com.dwarfeng.statistics.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 支持处理器。
 *
 * @author zhaofz
 * @since 1.3.0
 */
public interface SupportHandler extends Handler {

    /**
     * 重置驱动器支持。
     *
     * @throws HandlerException 服务异常。
     */
    void resetDriver() throws HandlerException;

    /**
     * 重置过滤器支持。
     *
     * @throws HandlerException 服务异常。
     */
    void resetFilter() throws HandlerException;

    /**
     * 重置映射器支持。
     *
     * @throws HandlerException 服务异常。
     */

    void resetMapper() throws HandlerException;

    /**
     * 重置提供器支持。
     *
     * @throws HandlerException 服务异常。
     */
    void resetProvider() throws HandlerException;

}
