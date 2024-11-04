package com.dwarfeng.statistics.impl.handler.provider.groovy;

import com.dwarfeng.statistics.impl.handler.provider.AbstractProvider;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Groovy 提供器。
 *
 * @author DwArFeng
 * @since 1.1.1
 */
@Component("groovyProviderRegistry.groovyProvider")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GroovyProvider extends AbstractProvider {

    private final ApplicationContext ctx;

    private final Processor processor;

    public GroovyProvider(ApplicationContext ctx, Processor processor) {
        this.ctx = ctx;
        this.processor = processor;
    }

    @Override
    protected Executor doNewExecutor() {
        return ctx.getBean(GroovyExecutor.class, processor);
    }

    @Override
    public String toString() {
        return "GroovyProvider{" +
                "ctx=" + ctx +
                ", processor=" + processor +
                '}';
    }
}
