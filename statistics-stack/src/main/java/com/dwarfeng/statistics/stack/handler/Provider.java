package com.dwarfeng.statistics.stack.handler;

import com.dwarfeng.statistics.stack.bean.dto.*;
import com.dwarfeng.statistics.stack.exception.ProviderException;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.List;

/**
 * 提供器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface Provider {

    /**
     * 生成一个新的提供器执行器。
     *
     * @return 新的提供器执行器。
     * @throws ProviderException 提供器异常。
     */
    Executor newExecutor() throws ProviderException;

    /**
     * 提供器执行器。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    interface Executor {

        /**
         * 初始化提供器执行器。
         *
         * @param context 指定的提供器上下文。
         */
        void init(Context context);

        /**
         * 启动提供器执行器。
         *
         * <p>
         * 该方法会在执行器使用开始前被调用。
         *
         * <p>
         * 可以在该方法中开启所需的资源/连接/服务等。
         *
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        void start() throws Exception;

        /**
         * 停止提供器执行器。
         *
         * <p>
         * 该方法会在执行器使用完毕后被调用。
         *
         * <p>
         * 可以在该方法中释放 {@link #start()} 中开启的资源/连接/服务等。
         *
         * <p>
         * 无论 {@link #start()} 是否成功执行，该方法都会被调用，因此，如果 {@link #start()} 执行失败，
         * 请注意不要在该方法中释放未初始化的资源。
         *
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        void stop() throws Exception;

        /**
         * 提供数据。
         *
         * @return 提供的数据。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        List<ProviderData> provide() throws Exception;
    }

    /**
     * 提供器上下文。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    interface Context {

        /**
         * 获取统计设置键。
         *
         * @return 统计设置键。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        LongIdKey getStatisticsSettingKey() throws Exception;

        /**
         * 获取任务键。
         *
         * @return 任务键。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        LongIdKey getTaskKey() throws Exception;

        /**
         * 更新任务模态信息。
         *
         * @param info 更新任务模态信息信息。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        void updateTaskModal(TaskUpdateModalInfo info) throws Exception;

        /**
         * 创建任务事件。
         *
         * @param info 任务事件创建信息。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        void createTaskEvent(TaskEventCreateInfo info) throws Exception;

        /**
         * 查看变量。
         *
         * @param info 变量查看信息。
         * @return 变量的查看结果。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        VariableInspectResult inspectVariable(VariableInspectInfo info) throws Exception;

        /**
         * 插入/更新变量。
         *
         * @param info 变量插入/更新信息。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        void upsertVariable(VariableUpsertInfo info) throws Exception;

        /**
         * 删除变量。
         *
         * @param info 变量删除信息。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        void removeVariable(VariableRemoveInfo info) throws Exception;
    }
}
