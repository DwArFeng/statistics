package com.dwarfeng.statistics.sdk.handler.bridge;

import com.dwarfeng.statistics.sdk.handler.Bridge.Persister;

/**
 * 持久器的抽象实现。
 *
 * @author zhaofz
 * @since 1.4.0
 */
public abstract class AbstractPersister implements Persister {

    @Override
    public String toString() {
        return "AbstractPersister{}";
    }
}
