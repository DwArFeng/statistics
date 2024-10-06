package com.dwarfeng.statistics.impl.handler.provider;

import com.dwarfeng.statistics.stack.exception.ProviderException;
import com.dwarfeng.statistics.stack.handler.Provider;

/**
 * 提供器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractProvider implements Provider {

    @Override
    public Executor newExecutor() throws ProviderException {
        try {
            return doNewExecutor();
        } catch (ProviderException e) {
            throw e;
        } catch (Exception e) {
            throw new ProviderException(e);
        }
    }

    protected abstract Executor doNewExecutor() throws Exception;

    @Override
    public String toString() {
        return "AbstractProvider{}";
    }
}
