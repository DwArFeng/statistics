package com.dwarfeng.statistics.impl.handler.filter.identity;

import com.dwarfeng.statistics.impl.handler.filter.AbstractFilterRegistry;
import com.dwarfeng.statistics.stack.exception.FilterException;
import com.dwarfeng.statistics.stack.exception.FilterMakeException;
import com.dwarfeng.statistics.stack.handler.Filter;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 本征过滤器注册。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class IdentityFilterRegistry extends AbstractFilterRegistry {

    public static final String FILTER_TYPE = "identity_filter";

    private final ApplicationContext ctx;

    public IdentityFilterRegistry(ApplicationContext ctx) {
        super(FILTER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "本征过滤器";
    }

    @Override
    public String provideDescription() {
        return "不对数据进行任何处理，直接返回原始数据。";
    }

    @Override
    public String provideExampleParam() {
        return "";
    }

    @Override
    public Filter makeFilter(String type, String param) throws FilterException {
        try {
            return ctx.getBean(IdentityFilter.class);
        } catch (Exception e) {
            throw new FilterMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "IdentityFilterRegistry{" +
                "ctx=" + ctx +
                ", filterType='" + filterType + '\'' +
                '}';
    }
}
