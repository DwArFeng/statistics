package com.dwarfeng.statistics.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.stack.bean.entity.Task;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 任务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonTask implements Bean {

    private static final long serialVersionUID = 5104834249889881987L;

    public static JSFixedFastJsonTask of(Task task) {
        if (Objects.isNull(task)) {
            return null;
        } else {
            return new JSFixedFastJsonTask(
                    JSFixedFastJsonLongIdKey.of(task.getKey()),
                    JSFixedFastJsonLongIdKey.of(task.getStatisticsSettingKey()),
                    task.getStatus(),
                    task.getCreateDate(),
                    task.getStartDate(),
                    task.getShouldExpireDate(),
                    task.getShouldDieDate(),
                    task.getExecutionNodeId(),
                    task.getFrontMessage(),
                    task.getRemark()
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

    @JSONField(name = "should_expire_date", ordinal = 6)
    private Date shouldExpireDate;

    @JSONField(name = "should_die_date", ordinal = 7)
    private Date shouldDieDate;

    @JSONField(name = "execution_node_id", ordinal = 8)
    private int executionNodeId;

    @JSONField(name = "front_message", ordinal = 9)
    private String frontMessage;

    @JSONField(name = "remark", ordinal = 10)
    private String remark;

    public JSFixedFastJsonTask() {
    }

    public JSFixedFastJsonTask(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey statisticsSettingKey, int status, Date createDate,
            Date startDate, Date shouldExpireDate, Date shouldDieDate, int executionNodeId, String frontMessage,
            String remark
    ) {
        this.key = key;
        this.statisticsSettingKey = statisticsSettingKey;
        this.status = status;
        this.createDate = createDate;
        this.startDate = startDate;
        this.shouldExpireDate = shouldExpireDate;
        this.shouldDieDate = shouldDieDate;
        this.executionNodeId = executionNodeId;
        this.frontMessage = frontMessage;
        this.remark = remark;
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

    public Date getShouldExpireDate() {
        return shouldExpireDate;
    }

    public void setShouldExpireDate(Date shouldExpireDate) {
        this.shouldExpireDate = shouldExpireDate;
    }

    public Date getShouldDieDate() {
        return shouldDieDate;
    }

    public void setShouldDieDate(Date shouldDieDate) {
        this.shouldDieDate = shouldDieDate;
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
        return "JSFixedFastJsonTask{" +
                "key=" + key +
                ", statisticsSettingKey=" + statisticsSettingKey +
                ", status=" + status +
                ", createDate=" + createDate +
                ", startDate=" + startDate +
                ", shouldExpireDate=" + shouldExpireDate +
                ", shouldDieDate=" + shouldDieDate +
                ", executionNodeId=" + executionNodeId +
                ", frontMessage='" + frontMessage + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
