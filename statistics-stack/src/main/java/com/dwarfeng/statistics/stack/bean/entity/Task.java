package com.dwarfeng.statistics.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * 任务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class Task implements Entity<LongIdKey> {

    private static final long serialVersionUID = -6608818783384186394L;

    private LongIdKey key;
    private LongIdKey statisticsSettingKey;

    /**
     * 任务状态。
     *
     * <p>
     * int 枚举，可能的状态为：
     * <ol>
     *     <li>任务创建</li>
     *     <li>任务进行</li>
     * </ol>
     * 详细值参考 sdk 模块的常量工具类。
     */
    private int status;

    private Date createDate;
    private Date startDate;
    private Date shouldExpireDate;
    private Date shouldDieDate;

    /**
     * 执行节点的 ID。
     */
    private int executionNodeId;

    /**
     * 任务执行是在最前方显示的信息。
     */
    private String frontMessage;

    private String remark;

    public Task() {
    }

    public Task(
            LongIdKey key, LongIdKey statisticsSettingKey, int status, Date createDate, Date startDate,
            Date shouldExpireDate, Date shouldDieDate, int executionNodeId, String frontMessage, String remark
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

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getStatisticsSettingKey() {
        return statisticsSettingKey;
    }

    public void setStatisticsSettingKey(LongIdKey statisticsSettingKey) {
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
        return "Task{" +
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
