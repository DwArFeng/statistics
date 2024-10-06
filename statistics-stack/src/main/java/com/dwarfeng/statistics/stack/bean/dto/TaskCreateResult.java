package com.dwarfeng.statistics.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 任务创建结果。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskCreateResult implements Dto {

    private static final long serialVersionUID = -3835887861472452807L;

    private LongIdKey taskKey;

    public TaskCreateResult() {
    }

    public TaskCreateResult(LongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    public LongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(LongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    @Override
    public String toString() {
        return "TaskCreateResult{" +
                "taskKey=" + taskKey +
                '}';
    }
}
