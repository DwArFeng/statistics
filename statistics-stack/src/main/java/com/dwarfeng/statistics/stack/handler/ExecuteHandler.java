package com.dwarfeng.statistics.stack.handler;

import com.dwarfeng.statistics.stack.bean.dto.TaskCreateInfo;
import com.dwarfeng.statistics.stack.bean.dto.TaskCreateResult;
import com.dwarfeng.statistics.stack.bean.dto.TaskExecuteInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.concurrent.CompletableFuture;

/**
 * 执行处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExecuteHandler extends Handler {

    /**
     * 创建任务。
     *
     * @param info 任务创建信息。
     * @return 任务创建结果。
     * @throws HandlerException 处理器异常。
     * @deprecated 该方法由于命名不规范，已经被 {@link #createTask(TaskCreateInfo)} 取代。
     */
    @Deprecated
    TaskCreateResult create(TaskCreateInfo info) throws HandlerException;

    /**
     * 执行任务。
     *
     * <p>
     * 该方法调用后，会等待任务执行完成，然后返回。
     *
     * @param info 任务执行信息。
     * @throws HandlerException 处理器异常。
     * @deprecated 该方法由于命名不规范，已经被 {@link #executeTask(TaskExecuteInfo)} 取代。
     */
    @Deprecated
    void execute(TaskExecuteInfo info) throws HandlerException;

    /**
     * 异步执行任务。
     *
     * <p>
     * 该方法调用后，会立即返回一个 <code>CompletableFuture</code>。
     * 返回的 <code>CompletableFuture</code> 会在任务执行完成后完成。
     *
     * @param info 任务执行信息
     * @return 返回的 <code>CompletableFuture</code>。
     * @throws HandlerException 处理器异常。
     * @deprecated 该方法由于命名不规范，已经被 {@link #executeTaskAsync(TaskExecuteInfo)} 取代。
     */
    @Deprecated
    CompletableFuture<Void> executeAsync(TaskExecuteInfo info) throws HandlerException;

    /**
     * 创建任务。
     *
     * @param info 任务创建信息。
     * @return 任务创建结果。
     * @throws HandlerException 处理器异常。
     * @since 1.1.2
     */
    TaskCreateResult createTask(TaskCreateInfo info) throws HandlerException;

    /**
     * 执行任务。
     *
     * <p>
     * 该方法调用后，会等待任务执行完成，然后返回。
     *
     * @param info 任务执行信息。
     * @throws HandlerException 处理器异常。
     * @since 1.1.2
     */
    void executeTask(TaskExecuteInfo info) throws HandlerException;

    /**
     * 异步执行任务。
     *
     * <p>
     * 该方法调用后，会立即返回一个 <code>CompletableFuture</code>。
     * 返回的 <code>CompletableFuture</code> 会在任务执行完成后完成。
     *
     * @param info 任务执行信息
     * @return 返回的 <code>CompletableFuture</code>。
     * @throws HandlerException 处理器异常。
     * @since 1.1.2
     */
    CompletableFuture<Void> executeTaskAsync(TaskExecuteInfo info) throws HandlerException;
}
