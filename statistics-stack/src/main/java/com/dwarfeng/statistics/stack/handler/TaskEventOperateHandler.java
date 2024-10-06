package com.dwarfeng.statistics.stack.handler;

import com.dwarfeng.statistics.stack.bean.dto.TaskEventCreateInfo;
import com.dwarfeng.statistics.stack.bean.dto.TaskEventCreateResult;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 任务事件操作处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface TaskEventOperateHandler extends Handler {

    /**
     * 创建任务事件。
     *
     * @param info 任务事件的创建信息。
     * @return 生成的任务事件的主键。
     * @throws HandlerException 处理器异常。
     */
    TaskEventCreateResult create(TaskEventCreateInfo info) throws HandlerException;
}
