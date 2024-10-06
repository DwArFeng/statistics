package com.dwarfeng.statistics.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 任务心跳信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskBeatInfo implements Dto {

    private static final long serialVersionUID = 5897024126298981038L;

    private LongIdKey taskKey;

    public TaskBeatInfo() {
    }

    public TaskBeatInfo(LongIdKey taskKey) {
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
        return "TaskBeatInfo{" +
                "taskKey=" + taskKey +
                '}';
    }
}
