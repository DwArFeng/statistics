package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.stack.exception.FilterException;
import com.dwarfeng.statistics.stack.exception.UnsupportedFilterTypeException;
import com.dwarfeng.statistics.stack.handler.Filter;
import com.dwarfeng.statistics.stack.handler.FilterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class FilterHandlerImpl implements FilterHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterHandlerImpl.class);

    private final List<FilterMaker> filterMakers;

    public FilterHandlerImpl(List<FilterMaker> filterMakers) {
        this.filterMakers = Optional.ofNullable(filterMakers).orElse(Collections.emptyList());
    }

    @Override
    public Filter make(String type, String param) throws FilterException {
        try {
            // 生成过滤器。
            LOGGER.debug("通过过滤器信息构建新的的过滤器...");
            FilterMaker filterMaker = filterMakers.stream().filter(maker -> maker.supportType(type))
                    .findFirst().orElseThrow(() -> new UnsupportedFilterTypeException(type));
            Filter filter = filterMaker.makeFilter(type, param);
            LOGGER.debug("过滤器构建成功!");
            LOGGER.debug("过滤器: {}", filter);
            return filter;
        } catch (FilterException e) {
            throw e;
        } catch (Exception e) {
            throw new FilterException(e);
        }
    }
}
