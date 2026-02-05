package com.dwarfeng.statistics.impl.handler.bridge;

/**
 * 仅支持保持器的桥接器。
 *
 * @author DwArFeng
 * @see com.dwarfeng.statistics.sdk.handler.bridge.KeeperOnlyBridge
 * @since 1.0.0
 * @deprecated 该对象已经被废弃，请使用 sdk 模块下的对应对象代替。
 */
@Deprecated
public abstract class KeeperOnlyBridge extends com.dwarfeng.statistics.sdk.handler.bridge.KeeperOnlyBridge {

    public KeeperOnlyBridge() {
    }

    public KeeperOnlyBridge(String bridgeType) {
        super(bridgeType);
    }
}
