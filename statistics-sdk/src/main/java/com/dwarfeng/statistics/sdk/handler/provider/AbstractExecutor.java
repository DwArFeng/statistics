package com.dwarfeng.statistics.sdk.handler.provider;

import com.dwarfeng.statistics.stack.handler.Provider.Context;
import com.dwarfeng.statistics.stack.handler.Provider.Executor;

/**
 * 提供器执行器的抽象实现。
 *
 * @author zhaofz
 * @since 1.4.0
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
