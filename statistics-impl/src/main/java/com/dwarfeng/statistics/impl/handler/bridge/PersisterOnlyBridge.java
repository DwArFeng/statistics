package com.dwarfeng.statistics.impl.handler.bridge;

/**
 * 仅支持持久器的桥接器。
 *
 * @author DwArFeng
 * @see com.dwarfeng.statistics.sdk.handler.bridge.PersisterOnlyBridge
 * @since 1.0.0
 * @deprecated 该对象已经被废弃，请使用 sdk 模块下的对应对象代替。
 */
@Deprecated
public abstract class PersisterOnlyBridge extends com.dwarfeng.statistics.sdk.handler.bridge.PersisterOnlyBridge {

    public PersisterOnlyBridge() {
    }

    public PersisterOnlyBridge(String bridgeType) {
        super(bridgeType);
    }
}
