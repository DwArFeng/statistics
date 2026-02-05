package com.dwarfeng.statistics.impl.handler.filter;

/**
 * 抽象过滤器注册。
 *
 * @author DwArFeng
 * @see com.dwarfeng.statistics.sdk.handler.filter.AbstractFilterRegistry
 * @since 1.0.0
 * @deprecated 该对象已经被废弃，请使用 sdk 模块下的对应对象代替。
 */
@Deprecated
public abstract class AbstractFilterRegistry extends com.dwarfeng.statistics.sdk.handler.filter.AbstractFilterRegistry {

    public AbstractFilterRegistry() {
    }

    public AbstractFilterRegistry(String filterType) {
        super(filterType);
    }
}
