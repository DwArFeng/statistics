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
     */
    TaskCreateResult create(TaskCreateInfo info) throws HandlerException;

    /**
     * 执行任务。
     *
     * <p>
     * 该方法调用后，会等待任务执行完成，然后返回。
     *
     * @param info 任务执行信息。
     * @throws HandlerException 处理器异常。
     */
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
     */
    CompletableFuture<Void> executeAsync(TaskExecuteInfo info) throws HandlerException;
}
