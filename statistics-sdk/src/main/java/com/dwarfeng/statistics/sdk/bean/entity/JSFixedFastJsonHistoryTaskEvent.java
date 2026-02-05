package com.dwarfeng.statistics.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.stack.bean.entity.HistoryTaskEvent;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 历史任务事件。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonHistoryTaskEvent implements Bean {

    private static final long serialVersionUID = -3381064028803925216L;

    public static JSFixedFastJsonHistoryTaskEvent of(HistoryTaskEvent historyTaskEvent) {
        if (Objects.isNull(historyTaskEvent)) {
            return null;
        } else {
            return new JSFixedFastJsonHistoryTaskEvent(
                    JSFixedFastJsonLongIdKey.of(historyTaskEvent.getKey()),
                    JSFixedFastJsonLongIdKey.of(historyTaskEvent.getHistoryTaskKey()),
                    historyTaskEvent.getHappenedDate(),
                    historyTaskEvent.getMessage()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "history_task_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey historyTaskKey;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    @JSONField(name = "message", ordinal = 4)
    private String message;

    public JSFixedFastJsonHistoryTaskEvent() {
    }

    public JSFixedFastJsonHistoryTaskEvent(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey historyTaskKey, Date happenedDate, String message

    ) {
        this.key = key;
        this.historyTaskKey = historyTaskKey;
        this.happenedDate = happenedDate;
        this.message = message;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getHistoryTaskKey() {
        return historyTaskKey;
    }

    public void setHistoryTaskKey(JSFixedFastJsonLongIdKey historyTaskKey) {
        this.historyTaskKey = historyTaskKey;
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

    @Override
    public String toString() {
        return "JSFixedFastJsonHistoryTaskEvent{" +
                "key=" + key +
                ", historyTaskKey=" + historyTaskKey +
                ", happenedDate=" + happenedDate +
                ", message='" + message + '\'' +
                '}';
    }
}
