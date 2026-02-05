package com.dwarfeng.statistics.sdk.handler.bridge;

import com.dwarfeng.statistics.stack.exception.KeeperNotSupportedException;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 仅支持持久器的桥接器。
 *
 * @author zhaofz
 * @since 1.4.0
 */
public abstract class PersisterOnlyBridge extends AbstractBridge {

    public PersisterOnlyBridge() {
    }

    public PersisterOnlyBridge(String bridgeType) {
        super(bridgeType);
    }

    @Override
    public Keeper getKeeper() throws HandlerException {
        throw new KeeperNotSupportedException();
    }

    @Override
    public String toString() {
        return "PersisterOnlyBridge{" +
                "bridgeType='" + bridgeType + '\'' +
                '}';
    }
}
