package com.dwarfeng.statistics.impl.handler.dispatcher;

/**
 * 调度器的抽象实现。
 *
 * @author DwArFeng
 *  @see com.dwarfeng.statistics.sdk.handler.dispatcher.AbstractDispatcher
 * @since 1.0.0
 * @deprecated 该对象已经被废弃，请使用 sdk 模块下的对应对象代替。
 */
@Deprecated
public abstract class AbstractDispatcher extends com.dwarfeng.statistics.sdk.handler.dispatcher.AbstractDispatcher {

    public AbstractDispatcher() {
    }

    public AbstractDispatcher(String dispatcherType) {
        super(dispatcherType);
    }
}
