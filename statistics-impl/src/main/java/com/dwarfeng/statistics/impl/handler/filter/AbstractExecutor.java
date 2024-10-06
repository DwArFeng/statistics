package com.dwarfeng.statistics.impl.handler.filter;

import com.dwarfeng.statistics.stack.handler.Filter.Context;
import com.dwarfeng.statistics.stack.handler.Filter.Executor;

/**
 * 过滤器执行器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractExecutor implements Executor {

    protected Context context;

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "AbstractExecutor{" +
                "context=" + context +
                '}';
    }
}
