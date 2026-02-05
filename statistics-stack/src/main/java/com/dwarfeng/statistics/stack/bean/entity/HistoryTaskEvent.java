package com.dwarfeng.statistics.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * 历史任务事件。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class HistoryTaskEvent implements Entity<LongIdKey> {

    private static final long serialVersionUID = 2994959499556509562L;

    private LongIdKey key;
    private LongIdKey historyTaskKey;
    private Date happenedDate;
    private String message;

    public HistoryTaskEvent() {
    }

    public HistoryTaskEvent(LongIdKey key, LongIdKey historyTaskKey, Date happenedDate, String message) {
        this.key = key;
        this.historyTaskKey = historyTaskKey;
        this.happenedDate = happenedDate;
        this.message = message;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getHistoryTaskKey() {
        return historyTaskKey;
    }

    public void setHistoryTaskKey(LongIdKey historyTaskKey) {
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
        return "HistoryTaskEvent{" +
                "key=" + key +
                ", historyTaskKey=" + historyTaskKey +
                ", happenedDate=" + happenedDate +
                ", message='" + message + '\'' +
                '}';
    }
}
