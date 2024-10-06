package com.dwarfeng.statistics.impl.handler.bridge;

import com.dwarfeng.statistics.stack.exception.PersisterNotSupportedException;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 仅支持保持器的桥接器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class KeeperOnlyBridge extends AbstractBridge {

    public KeeperOnlyBridge() {
    }

    public KeeperOnlyBridge(String bridgeType) {
        super(bridgeType);
    }

    @Override
    public Persister getPersister() throws HandlerException {
        throw new PersisterNotSupportedException();
    }

    @Override
    public String toString() {
        return "KeeperOnlyBridge{" +
                "bridgeType='" + bridgeType + '\'' +
                '}';
    }
}
