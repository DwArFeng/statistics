package com.dwarfeng.statistics.impl.handler.filter.identity;

import com.dwarfeng.statistics.impl.handler.filter.AbstractFilter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 模拟提供器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component("identityFilterRegistry.identityFilter")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IdentityFilter extends AbstractFilter {

    private final ApplicationContext ctx;

    public IdentityFilter(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    protected Executor doNewExecutor() {
        return ctx.getBean(IdentityExecutor.class);
    }

    @Override
    public String toString() {
        return "IdentityFilter{" +
                "ctx=" + ctx +
                '}';
    }
}
