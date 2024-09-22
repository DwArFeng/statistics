package com.dwarfeng.statistics.stack.handler;

import com.dwarfeng.statistics.stack.exception.DispatcherException;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 调度器。
 *
 * <p>
 * 调度器负责将统计执行任务分发给集群中的接收器，并保证负载均衡。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface Dispatcher {

    /**
     * 返回制造器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 制造器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 启动调度处理器。
     *
     * <p>
     * 连续多次调用该方法，只有第一次调用有效。
     *
     * @throws DispatcherException 调度器异常。
     */
    void start() throws DispatcherException;

    /**
     * 停止调度处理器。
     *
     * <p>
     * 连续多次调用该方法，只有第一次调用有效。
     *
     * @throws DispatcherException 调度器异常。
     */
    void stop() throws DispatcherException;

    /**
     * 调度统计执行任务。
     *
     * <p>
     * 该方法会将统计执行任务分发给集群中的接收器，并保证负载均衡。
     *
     * <p>
     * 该方法执行时，需要判断调度器的启动状态，如果调度器未启动，则抛出异常。
     *
     * @param statisticsSettingKey 统计设置的主键。
     * @throws DispatcherException 调度器异常。
     */
    void dispatch(LongIdKey statisticsSettingKey) throws DispatcherException;
}
