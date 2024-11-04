package com.dwarfeng.statistics.impl.handler.filter.groovy;

import com.dwarfeng.statistics.impl.handler.filter.AbstractFilter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Groovy 过滤器。
 *
 * @author DwArFeng
 * @since 1.1.1
 */
@Component("groovyFilterRegistry.groovyFilter")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GroovyFilter extends AbstractFilter {

    private final ApplicationContext ctx;

    private final Processor processor;

    public GroovyFilter(ApplicationContext ctx, Processor processor) {
        this.ctx = ctx;
        this.processor = processor;
    }

    @Override
    protected Executor doNewExecutor() {
        return ctx.getBean(GroovyExecutor.class, processor);
    }

    @Override
    public String toString() {
        return "GroovyFilter{" +
                "ctx=" + ctx +
                ", processor=" + processor +
                '}';
    }
}
