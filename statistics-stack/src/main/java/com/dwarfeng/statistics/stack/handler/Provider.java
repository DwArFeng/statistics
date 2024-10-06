package com.dwarfeng.statistics.stack.handler;

import com.dwarfeng.statistics.stack.bean.dto.ProviderData;
import com.dwarfeng.statistics.stack.bean.dto.TaskEventCreateInfo;
import com.dwarfeng.statistics.stack.bean.dto.TaskUpdateModalInfo;
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
         * @throws ProviderException 提供器异常。
         */
        void start() throws ProviderException;

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
         * @throws ProviderException 提供器异常。
         */
        void stop() throws ProviderException;

        /**
         * 提供数据。
         *
         * @return 提供的数据。
         * @throws ProviderException 提供器异常。
         */
        List<ProviderData> provide() throws ProviderException;
    }

    /**
     * 提供器上下文。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    interface Context {

        /**
         * 获取任务的键。
         *
         * @return 任务的键。
         * @throws ProviderException 提供器异常。
         */
        LongIdKey getTaskKey() throws ProviderException;

        /**
         * 更新任务的模态信息。
         *
         * @param info 任务更新模态消息信息。
         * @throws ProviderException 提供器异常。
         */
        void updateTaskModal(TaskUpdateModalInfo info) throws ProviderException;

        /**
         * 创建任务事件。
         *
         * @param info 任务事件创建信息。
         * @throws ProviderException 提供器异常。
         */
        void createTaskEvent(TaskEventCreateInfo info) throws ProviderException;
    }
}
