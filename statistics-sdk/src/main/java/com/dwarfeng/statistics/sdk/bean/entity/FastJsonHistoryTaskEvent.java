package com.dwarfeng.statistics.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.stack.bean.entity.HistoryTaskEvent;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 历史任务事件。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonHistoryTaskEvent implements Bean {

    private static final long serialVersionUID = -2458561598168609116L;

    public static FastJsonHistoryTaskEvent of(HistoryTaskEvent historyTaskEvent) {
        if (Objects.isNull(historyTaskEvent)) {
            return null;
        } else {
            return new FastJsonHistoryTaskEvent(
                    FastJsonLongIdKey.of(historyTaskEvent.getKey()),
                    FastJsonLongIdKey.of(historyTaskEvent.getHistoryTaskKey()),
                    historyTaskEvent.getHappenedDate(),
                    historyTaskEvent.getMessage(),
                    historyTaskEvent.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "history_task_key", ordinal = 2)
    private FastJsonLongIdKey historyTaskKey;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    @JSONField(name = "message", ordinal = 4)
    private String message;

    @JSONField(name = "remark", ordinal = 5)
    private String remark;

    public FastJsonHistoryTaskEvent() {
    }

    public FastJsonHistoryTaskEvent(
            FastJsonLongIdKey key, FastJsonLongIdKey historyTaskKey, Date happenedDate, String message, String remark
    ) {
        this.key = key;
        this.historyTaskKey = historyTaskKey;
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

    public FastJsonLongIdKey getHistoryTaskKey() {
        return historyTaskKey;
    }

    public void setHistoryTaskKey(FastJsonLongIdKey historyTaskKey) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "FastJsonHistoryTaskEvent{" +
                "key=" + key +
                ", historyTaskKey=" + historyTaskKey +
                ", happenedDate=" + happenedDate +
                ", message='" + message + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
