package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.stack.exception.FilterException;
import com.dwarfeng.statistics.stack.handler.Filter;

/**
 * 过滤器构造器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface FilterMaker {

    /**
     * 返回构造器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 构造器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 根据指定的过滤器信息生成一个过滤器对象。
     *
     * <p>
     * 可以保证传入的过滤器信息中的类型是支持的。
     *
     * @param type  过滤器类型。
     * @param param 过滤器参数。
     * @return 构造的过滤器。
     * @throws FilterException 过滤器异常。
     */
    Filter makeFilter(String type, String param) throws FilterException;
}
