package com.dwarfeng.statistics.stack.handler;

import com.dwarfeng.statistics.stack.bean.entity.DriverInfo;
import com.dwarfeng.statistics.stack.exception.DriverException;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 驱动器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface Driver {

    /**
     * 初始化驱动器。
     *
     * <p>
     * 该方法会在驱动器初始化后调用。<br>
     * 该方法会传入一个 {@link Context}，此对象为驱动器的上下文，其中包含了驱动器需要使用的所有方法。<br>
     * 实现该方法时，应妥善保存上下文，以便在后续的方法调用中使用。
     *
     * @param context 驱动器的上下文。
     */
    void init(Context context);

    /**
     * 注册指定的驱动器信息。
     *
     * @param driverInfo 指定的驱动器信息。
     * @throws DriverException 驱动器异常。
     */
    void register(DriverInfo driverInfo) throws DriverException;

    /**
     * 解除注册所有的驱动器信息。
     *
     * @throws DriverException 驱动器异常。
     */
    void unregisterAll() throws DriverException;

    /**
     * 驱动器上下文。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    interface Context {

        /**
         * 指定的统计设置调用执行动作。
         *
         * @param statisticsSettingKey 指定统计设置的主键。
         * @throws DriverException 驱动器异常。
         */
        void execute(LongIdKey statisticsSettingKey) throws DriverException;
    }
}
