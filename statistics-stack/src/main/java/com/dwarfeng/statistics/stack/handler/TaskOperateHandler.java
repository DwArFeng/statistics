package com.dwarfeng.statistics.stack.handler;

import com.dwarfeng.statistics.stack.bean.dto.*;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 任务操作处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface TaskOperateHandler extends Handler {

    /**
     * 创建任务。
     *
     * @param createInfo 任务创建信息。
     * @return 任务创建结果。
     * @throws HandlerException 处理器异常。
     */
    TaskCreateResult create(TaskCreateInfo createInfo) throws HandlerException;

    /**
     * 启动任务。
     *
     * @param startInfo 任务启动信息。
     * @throws HandlerException 处理器异常。
     */
    void start(TaskStartInfo startInfo) throws HandlerException;

    /**
     * 完成任务。
     *
     * @param finishInfo 任务完成信息。
     * @throws HandlerException 处理器异常。
     */
    void finish(TaskFinishInfo finishInfo) throws HandlerException;

    /**
     * 失败任务。
     *
     * @param failInfo 任务失败信息。
     * @throws HandlerException 处理器异常。
     */
    void fail(TaskFailInfo failInfo) throws HandlerException;

    /**
     * 过期任务。
     *
     * @param expireInfo 任务过期信息。
     * @throws HandlerException 处理器异常。
     */
    void expire(TaskExpireInfo expireInfo) throws HandlerException;

    /**
     * 死亡任务。
     *
     * @param dieInfo 任务死亡信息。
     * @throws HandlerException 处理器异常。
     */
    void die(TaskDieInfo dieInfo) throws HandlerException;

    /**
     * 更新任务模态。
     *
     * @param updateModalInfo 任务更新模态信息。
     * @throws HandlerException 处理器异常。
     */
    void updateModal(TaskUpdateModalInfo updateModalInfo) throws HandlerException;

    /**
     * 心跳任务。
     *
     * @param beatInfo 任务心跳信息。
     * @throws HandlerException 处理器异常。
     */
    void beat(TaskBeatInfo beatInfo) throws HandlerException;
}
