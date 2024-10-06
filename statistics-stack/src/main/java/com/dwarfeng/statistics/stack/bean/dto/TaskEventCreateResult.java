package com.dwarfeng.statistics.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 任务事件创建结果。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskEventCreateResult implements Dto {

    private static final long serialVersionUID = -1880047519441083418L;

    private LongIdKey taskEventKey;

    public TaskEventCreateResult() {
    }

    public TaskEventCreateResult(LongIdKey taskEventKey) {
        this.taskEventKey = taskEventKey;
    }

    public LongIdKey getTaskEventKey() {
        return taskEventKey;
    }

    public void setTaskEventKey(LongIdKey taskEventKey) {
        this.taskEventKey = taskEventKey;
    }

    @Override
    public String toString() {
        return "TaskEventCreateResult{" +
                "taskEventKey=" + taskEventKey +
                '}';
    }
}
