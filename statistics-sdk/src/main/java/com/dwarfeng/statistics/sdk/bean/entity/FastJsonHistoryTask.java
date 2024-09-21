package com.dwarfeng.statistics.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.stack.bean.entity.HistoryTask;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 历史任务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonHistoryTask implements Bean {

    private static final long serialVersionUID = -3577422582550245152L;

    public static FastJsonHistoryTask of(HistoryTask historyTask) {
        if (Objects.isNull(historyTask)) {
            return null;
        } else {
            return new FastJsonHistoryTask(
                    FastJsonLongIdKey.of(historyTask.getKey()),
                    FastJsonLongIdKey.of(historyTask.getStatisticsSettingKey()),
                    historyTask.getStatus(),
                    historyTask.getCreateDate(),
                    historyTask.getStartDate(),
                    historyTask.getEndDate(),
                    historyTask.getDuration(),
                    historyTask.getExpiredDate(),
                    historyTask.getDiedDate(),
                    historyTask.getExecutionNodeId(),
                    historyTask.getFrontMessage(),
                    historyTask.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "statistics_setting_key", ordinal = 2)
    private FastJsonLongIdKey statisticsSettingKey;

    @JSONField(name = "status", ordinal = 3)
    private int status;

    @JSONField(name = "create_date", ordinal = 4)
    private Date createDate;

    @JSONField(name = "start_date", ordinal = 5)
    private Date startDate;

    @JSONField(name = "end_date", ordinal = 6)
    private Date endDate;

    @JSONField(name = "duration", ordinal = 7)
    private Long duration;

    @JSONField(name = "expired_date", ordinal = 8)
    private Date expiredDate;

    @JSONField(name = "died_date", ordinal = 9)
    private Date diedDate;

    @JSONField(name = "execution_node_id", ordinal = 10)
    private int executionNodeId;

    @JSONField(name = "front_message", ordinal = 11)
    private String frontMessage;

    @JSONField(name = "remark", ordinal = 12)
    private String remark;

    public FastJsonHistoryTask() {
    }

    public FastJsonHistoryTask(
            FastJsonLongIdKey key, FastJsonLongIdKey statisticsSettingKey, int status, Date createDate, Date startDate,
            Date endDate, Long duration, Date expiredDate, Date diedDate, int executionNodeId, String frontMessage,
            String remark
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
        this.executionNodeId = executionNodeId;
        this.frontMessage = frontMessage;
        this.remark = remark;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
        this.key = key;
    }

    public FastJsonLongIdKey getStatisticsSettingKey() {
        return statisticsSettingKey;
    }

    public void setStatisticsSettingKey(FastJsonLongIdKey statisticsSettingKey) {
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

    public int getExecutionNodeId() {
        return executionNodeId;
    }

    public void setExecutionNodeId(int executionNodeId) {
        this.executionNodeId = executionNodeId;
    }

    public String getFrontMessage() {
        return frontMessage;
    }

    public void setFrontMessage(String frontMessage) {
        this.frontMessage = frontMessage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "FastJsonHistoryTask{" +
                "key=" + key +
                ", statisticsSettingKey=" + statisticsSettingKey +
                ", status=" + status +
                ", createDate=" + createDate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", duration=" + duration +
                ", expiredDate=" + expiredDate +
                ", diedDate=" + diedDate +
                ", executionNodeId=" + executionNodeId +
                ", frontMessage='" + frontMessage + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
