package com.dwarfeng.statistics.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 提供器处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ProviderHandler extends Handler {

    /**
     * 根据指定的提供器信息构造一个提供器。
     *
     * @param type    提供器类型。
     * @param content 提供器内容。
     * @return 构造的提供器。
     * @throws HandlerException 处理器异常。
     */
    Provider make(String type, String content) throws HandlerException;
}
