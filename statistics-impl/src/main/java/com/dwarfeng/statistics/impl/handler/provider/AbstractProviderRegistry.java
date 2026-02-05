package com.dwarfeng.statistics.impl.handler.provider;

/**
 * 抽象提供器注册。
 *
 * @author DwArFeng
 * @see com.dwarfeng.statistics.sdk.handler.provider.AbstractProviderRegistry
 * @since 1.0.0
 * @deprecated 该对象已经被废弃，请使用 sdk 模块下的对应对象代替。
 */
@Deprecated
public abstract class AbstractProviderRegistry extends com.dwarfeng.statistics.sdk.handler.provider.AbstractProviderRegistry {

    public AbstractProviderRegistry() {
    }

    public AbstractProviderRegistry(String providerType) {
        super(providerType);
    }
}
