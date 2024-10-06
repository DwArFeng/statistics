package com.dwarfeng.statistics.impl.handler.bridge;

import com.dwarfeng.statistics.impl.handler.Bridge.Persister;

/**
 * 持久器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractPersister implements Persister {

    @Override
    public String toString() {
        return "AbstractPersister{}";
    }
}
