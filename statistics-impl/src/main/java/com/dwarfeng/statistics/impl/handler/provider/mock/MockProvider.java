package com.dwarfeng.statistics.impl.handler.provider.mock;

import com.dwarfeng.statistics.impl.handler.provider.AbstractProvider;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 模拟提供器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("mockProviderRegistry.mockProvider")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MockProvider extends AbstractProvider {

    private final ApplicationContext ctx;

    private final Config config;

    public MockProvider(ApplicationContext ctx, Config config) {
        this.ctx = ctx;
        this.config = config;
    }

    @Override
    protected Executor doNewExecutor() {
        return ctx.getBean(MockExecutor.class, config);
    }

    @Override
    public String toString() {
        return "MockProvider{" +
                "ctx=" + ctx +
                ", config=" + config +
                '}';
    }
}
