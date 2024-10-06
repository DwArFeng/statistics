package com.dwarfeng.statistics.impl.handler.filter;

import com.dwarfeng.statistics.impl.handler.FilterMaker;
import com.dwarfeng.statistics.impl.handler.FilterSupporter;

import java.util.Objects;

/**
 * 抽象过滤器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractFilterRegistry implements FilterMaker, FilterSupporter {

    protected String filterType;

    public AbstractFilterRegistry() {
    }

    public AbstractFilterRegistry(String filterType) {
        this.filterType = filterType;
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(filterType, type);
    }

    @Override
    public String provideType() {
        return filterType;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    @Override
    public String toString() {
        return "AbstractFilterRegistry{" +
                "filterType='" + filterType + '\'' +
                '}';
    }
}
