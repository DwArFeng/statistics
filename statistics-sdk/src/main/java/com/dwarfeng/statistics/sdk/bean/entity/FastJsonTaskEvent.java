package com.dwarfeng.statistics.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.stack.bean.entity.TaskEvent;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 任务事件。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonTaskEvent implements Bean {

    private static final long serialVersionUID = 9048400508115034924L;

    public static FastJsonTaskEvent of(TaskEvent taskEvent) {
        if (Objects.isNull(taskEvent)) {
            return null;
        } else {
            return new FastJsonTaskEvent(
                    FastJsonLongIdKey.of(taskEvent.getKey()),
                    FastJsonLongIdKey.of(taskEvent.getTaskKey()),
                    taskEvent.getHappenedDate(),
                    taskEvent.getMessage(),
                    taskEvent.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "task_key", ordinal = 2)
    private FastJsonLongIdKey taskKey;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    @JSONField(name = "message", ordinal = 4)
    private String message;

    @JSONField(name = "remark", ordinal = 5)
    private String remark;

    public FastJsonTaskEvent() {
    }

    public FastJsonTaskEvent(
            FastJsonLongIdKey key, FastJsonLongIdKey taskKey, Date happenedDate, String message, String remark
    ) {
        this.key = key;
        this.taskKey = taskKey;
        this.happenedDate = happenedDate;
        this.message = message;
        this.remark = remark;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
        this.key = key;
    }

    public FastJsonLongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(FastJsonLongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "FastJsonTaskEvent{" +
                "key=" + key +
                ", taskKey=" + taskKey +
                ", happenedDate=" + happenedDate +
                ", message='" + message + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
