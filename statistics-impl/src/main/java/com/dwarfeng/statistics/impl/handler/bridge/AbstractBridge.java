package com.dwarfeng.statistics.impl.handler.bridge;

/**
 * 桥接器的抽象实现。
 *
 * @author DwArFeng
 * @see com.dwarfeng.statistics.sdk.handler.bridge.AbstractBridge
 * @since 1.0.0
 * @deprecated 该对象已经被废弃，请使用 sdk 模块下的对应对象代替。
 */
@Deprecated
public abstract class AbstractBridge extends com.dwarfeng.statistics.sdk.handler.bridge.AbstractBridge {

    public AbstractBridge() {
    }

    public AbstractBridge(String bridgeType) {
        super(bridgeType);
    }
}
