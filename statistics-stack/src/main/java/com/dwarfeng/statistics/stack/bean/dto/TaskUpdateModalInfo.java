package com.dwarfeng.statistics.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 任务更新模态信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskUpdateModalInfo implements Dto {

    private static final long serialVersionUID = -7185221852258251752L;

    private LongIdKey taskKey;
    private String frontMessage;

    public TaskUpdateModalInfo() {
    }

    public TaskUpdateModalInfo(LongIdKey taskKey, String frontMessage) {
        this.taskKey = taskKey;
        this.frontMessage = frontMessage;
    }

    public LongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(LongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    public String getFrontMessage() {
        return frontMessage;
    }

    public void setFrontMessage(String frontMessage) {
        this.frontMessage = frontMessage;
    }

    @Override
    public String toString() {
        return "TaskUpdateModalInfo{" +
                "taskKey=" + taskKey +
                ", frontMessage='" + frontMessage + '\'' +
                '}';
    }
}
