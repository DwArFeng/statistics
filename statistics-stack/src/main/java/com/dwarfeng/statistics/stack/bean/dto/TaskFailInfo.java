package com.dwarfeng.statistics.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 任务失败信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskFailInfo implements Dto {

    private static final long serialVersionUID = 3349326411646426536L;

    private LongIdKey taskKey;

    public TaskFailInfo() {
    }

    public TaskFailInfo(LongIdKey taskKey) {
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
        return "TaskFailInfo{" +
                "taskKey=" + taskKey +
                '}';
    }
}
