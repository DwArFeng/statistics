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

    private static final long serialVersionUID = -7220736823907856551L;

    private LongIdKey taskKey;
    private boolean messageUpdateFlag;
    private String message;

    public TaskUpdateModalInfo() {
    }

    public TaskUpdateModalInfo(LongIdKey taskKey, boolean messageUpdateFlag, String message) {
        this.taskKey = taskKey;
        this.messageUpdateFlag = messageUpdateFlag;
        this.message = message;
    }

    public LongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(LongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    public boolean isMessageUpdateFlag() {
        return messageUpdateFlag;
    }

    public void setMessageUpdateFlag(boolean messageUpdateFlag) {
        this.messageUpdateFlag = messageUpdateFlag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TaskUpdateModalInfo{" +
                "taskKey=" + taskKey +
                ", messageUpdateFlag=" + messageUpdateFlag +
                ", message='" + message + '\'' +
                '}';
    }
}
