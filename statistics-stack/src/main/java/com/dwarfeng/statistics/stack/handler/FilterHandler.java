package com.dwarfeng.statistics.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 过滤器处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface FilterHandler extends Handler {

    /**
     * 根据指定的过滤器信息构造一个过滤器。
     *
     * @param type    过滤器类型。
     * @param content 过滤器内容。
     * @return 构造的过滤器。
     * @throws HandlerException 处理器异常。
     */
    Filter make(String type, String content) throws HandlerException;
}
