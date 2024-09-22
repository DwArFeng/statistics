package com.dwarfeng.statistics.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.List;

/**
 * 调度器处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface DispatcherHandler extends Handler {

    /**
     * 返回当前正在使用的调度器。
     *
     * @return 当前正在使用的调度器。
     * @throws HandlerException 处理器异常。
     */
    Dispatcher current() throws HandlerException;

    /**
     * 列出在用的全部调度器。
     *
     * @return 在用的全部调度器组成的列表。
     * @throws HandlerException 处理器异常。
     */
    List<Dispatcher> all() throws HandlerException;
}
