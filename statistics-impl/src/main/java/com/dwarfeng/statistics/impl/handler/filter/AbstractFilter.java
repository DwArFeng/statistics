package com.dwarfeng.statistics.impl.handler.filter;

import com.dwarfeng.statistics.stack.exception.FilterException;
import com.dwarfeng.statistics.stack.handler.Filter;

/**
 * 过滤器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractFilter implements Filter {

    @Override
    public Executor newExecutor() throws FilterException {
        try {
            return doNewExecutor();
        } catch (FilterException e) {
            throw e;
        } catch (Exception e) {
            throw new FilterException(e);
        }
    }

    protected abstract Executor doNewExecutor() throws Exception;

    @Override
    public String toString() {
        return "AbstractFilter{}";
    }
}
