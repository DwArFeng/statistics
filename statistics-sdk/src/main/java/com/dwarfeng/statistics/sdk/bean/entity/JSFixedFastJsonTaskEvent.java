package com.dwarfeng.statistics.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.stack.bean.entity.TaskEvent;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 任务事件。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonTaskEvent implements Bean {

    private static final long serialVersionUID = 398677106457587294L;

    public static JSFixedFastJsonTaskEvent of(TaskEvent taskEvent) {
        if (Objects.isNull(taskEvent)) {
            return null;
        } else {
            return new JSFixedFastJsonTaskEvent(
                    JSFixedFastJsonLongIdKey.of(taskEvent.getKey()),
                    JSFixedFastJsonLongIdKey.of(taskEvent.getTaskKey()),
                    taskEvent.getHappenedDate(),
                    taskEvent.getMessage(),
                    taskEvent.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "task_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey taskKey;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    @JSONField(name = "message", ordinal = 4)
    private String message;

    @JSONField(name = "remark", ordinal = 5)
    private String remark;

    public JSFixedFastJsonTaskEvent() {
    }

    public JSFixedFastJsonTaskEvent(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey taskKey, Date happenedDate, String message,
            String remark
    ) {
        this.key = key;
        this.taskKey = taskKey;
        this.happenedDate = happenedDate;
        this.message = message;
        this.remark = remark;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(JSFixedFastJsonLongIdKey taskKey) {
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
        return "JSFixedFastJsonTaskEvent{" +
                "key=" + key +
                ", taskKey=" + taskKey +
                ", happenedDate=" + happenedDate +
                ", message='" + message + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
