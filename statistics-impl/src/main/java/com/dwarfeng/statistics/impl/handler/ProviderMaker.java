package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.stack.exception.ProviderException;
import com.dwarfeng.statistics.stack.handler.Provider;

/**
 * 提供器构造器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ProviderMaker {

    /**
     * 返回构造器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 构造器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 根据指定的提供器信息生成一个提供器对象。
     *
     * <p>
     * 可以保证传入的提供器信息中的类型是支持的。
     *
     * @param type  提供器类型。
     * @param param 提供器参数。
     * @return 构造的提供器。
     * @throws ProviderException 提供器异常。
     */
    Provider makeProvider(String type, String param) throws ProviderException;
}
