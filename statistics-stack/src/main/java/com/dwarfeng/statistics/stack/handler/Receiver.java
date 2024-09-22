package com.dwarfeng.statistics.stack.handler;

import com.dwarfeng.statistics.stack.exception.ReceiverException;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 接收器。
 *
 * <p>
 * 接收器负责接收对应调度器的调度，并调用上下文的相关方法，实际地执行统计业务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface Receiver {

    /**
     * 返回制造器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 制造器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 初始化接收器。
     *
     * <p>
     * 该方法会在接收器初始化后调用。<br>
     * 该方法会传入一个 {@link Context}，此对象为接收器的上下文，其中包含了接收器需要使用的所有方法。<br>
     * 实现该方法时，应妥善保存上下文，以便在后续的方法调用中使用。
     *
     * @param context 接收器的上下文。
     */
    void init(Context context);

    /**
     * 启动接收处理器。
     *
     * <p>
     * 连续多次调用该方法，只有第一次调用有效。
     *
     * @throws ReceiverException 接收器异常。
     */
    void start() throws ReceiverException;

    /**
     * 停止接收处理器。
     *
     * <p>
     * 连续多次调用该方法，只有第一次调用有效。
     *
     * @throws ReceiverException 接收器异常。
     */
    void stop() throws ReceiverException;

    /**
     * 接收器上下文。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    interface Context {

        /**
         * 指定的统计设置调用执行动作。
         *
         * <p>
         * 接收器需要保证在其启动后才能调用该方法，任何在其启动前尝试调用该方法的行为都应该抛出
         * {@link com.dwarfeng.statistics.stack.exception.ReceiverNotStartException}
         *
         * @param statisticsSettingKey 指定统计设置的主键。
         * @throws ReceiverException 接收器异常。
         */
        void execute(LongIdKey statisticsSettingKey) throws ReceiverException;
    }
}
