package com.dwarfeng.statistics.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.statistics.stack.bean.entity.HistoryTask;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 历史任务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonHistoryTask implements Bean {

    private static final long serialVersionUID = -4305845389083251049L;

    public static JSFixedFastJsonHistoryTask of(HistoryTask historyTask) {
        if (Objects.isNull(historyTask)) {
            return null;
        } else {
            return new JSFixedFastJsonHistoryTask(
                    JSFixedFastJsonLongIdKey.of(historyTask.getKey()),
                    JSFixedFastJsonLongIdKey.of(historyTask.getStatisticsSettingKey()),
                    historyTask.getStatus(),
                    historyTask.getCreateDate(),
                    historyTask.getStartDate(),
                    historyTask.getEndDate(),
                    historyTask.getDuration(),
                    historyTask.getExpiredDate(),
                    historyTask.getDiedDate(),
                    historyTask.getMessage()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "statistics_setting_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey statisticsSettingKey;

    @JSONField(name = "status", ordinal = 3)
    private int status;

    @JSONField(name = "create_date", ordinal = 4)
    private Date createDate;

    @JSONField(name = "start_date", ordinal = 5)
    private Date startDate;

    @JSONField(name = "end_date", ordinal = 6)
    private Date endDate;

    @JSONField(name = "duration", ordinal = 7, serializeUsing = ToStringSerializer.class)
    private Long duration;

    @JSONField(name = "expired_date", ordinal = 8)
    private Date expiredDate;

    @JSONField(name = "died_date", ordinal = 9)
    private Date diedDate;

    @JSONField(name = "message", ordinal = 10)
    private String message;

    public JSFixedFastJsonHistoryTask() {
    }

    public JSFixedFastJsonHistoryTask(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey statisticsSettingKey, int status, Date createDate,
            Date startDate, Date endDate, Long duration, Date expiredDate, Date diedDate,
            String message
    ) {
        this.key = key;
        this.statisticsSettingKey = statisticsSettingKey;
        this.status = status;
        this.createDate = createDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.expiredDate = expiredDate;
        this.diedDate = diedDate;
        this.message = message;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getStatisticsSettingKey() {
        return statisticsSettingKey;
    }

    public void setStatisticsSettingKey(JSFixedFastJsonLongIdKey statisticsSettingKey) {
        this.statisticsSettingKey = statisticsSettingKey;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Date getDiedDate() {
        return diedDate;
    }

    public void setDiedDate(Date diedDate) {
        this.diedDate = diedDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonHistoryTask{" +
                "key=" + key +
                ", statisticsSettingKey=" + statisticsSettingKey +
                ", status=" + status +
                ", createDate=" + createDate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", duration=" + duration +
                ", expiredDate=" + expiredDate +
                ", diedDate=" + diedDate +
                ", message='" + message + '\'' +
                '}';
    }
}
