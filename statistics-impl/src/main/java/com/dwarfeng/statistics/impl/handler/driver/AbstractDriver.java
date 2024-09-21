package com.dwarfeng.statistics.impl.handler.driver;

import com.dwarfeng.statistics.stack.handler.Driver;

/**
 * 驱动器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractDriver implements Driver {

    protected Context context;

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "AbstractDriver{" +
                "context=" + context +
                '}';
    }
}
