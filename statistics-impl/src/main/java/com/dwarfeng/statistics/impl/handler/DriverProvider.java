package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.stack.handler.Driver;

/**
 * 驱动器提供器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface DriverProvider {

    /**
     * 返回提供器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 提供器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 提供驱动器。
     *
     * @return 提供的驱动器。
     */
    Driver provide();
}
