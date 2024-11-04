package com.dwarfeng.statistics.impl.handler.provider.groovy;

import com.dwarfeng.statistics.stack.bean.dto.ProviderData;
import com.dwarfeng.statistics.stack.handler.Provider;

import java.util.List;

/**
 * Groovy 处理器。
 *
 * @author DwArFeng
 * @since 1.1.1
 */
public interface Processor {

    /**
     * 启动提供器执行器。
     *
     * <p>
     * 该方法会在执行器使用开始前被调用。
     *
     * <p>
     * 可以在该方法中开启所需的资源/连接/服务等。
     *
     * @param context 执行器的提供器上下文。
     * @throws Exception 方法执行过程中发生的任何异常。
     */
    void start(Provider.Context context) throws Exception;

    /**
     * 停止提供器执行器。
     *
     * <p>
     * 该方法会在执行器使用完毕后被调用。
     *
     * <p>
     * 可以在该方法中释放 {@link #start(Provider.Context)} 中开启的资源/连接/服务等。
     *
     * <p>
     * 无论 {@link #start(Provider.Context)} 是否成功执行，该方法都会被调用，
     * 因此，如果 {@link #start(Provider.Context)} 执行失败，请注意不要在该方法中释放未初始化的资源。
     *
     * @param context 执行器的提供器上下文。
     * @throws Exception 方法执行过程中发生的任何异常。
     */
    void stop(Provider.Context context) throws Exception;

    /**
     * 提供数据。
     *
     * @param context 执行器的提供器上下文。
     * @return 提供的数据。
     * @throws Exception 方法执行过程中发生的任何异常。
     */
    List<ProviderData> provide(Provider.Context context) throws Exception;
}
