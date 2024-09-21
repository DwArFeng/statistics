package com.dwarfeng.statistics.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * 统计执行简报。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class StatisticsExecutionProfile implements Entity<LongIdKey> {

    private static final long serialVersionUID = -2695792952398982070L;

    private LongIdKey key;
    private int executingCount;
    private int succeededCount;
    private int failedCount;
    private int consecutiveFailedCount;
    private Date lastExecutedDate;
    private Date lastSucceededDate;
    private Date lastFailedDate;

    public StatisticsExecutionProfile() {
    }

    public StatisticsExecutionProfile(
            LongIdKey key, int executingCount, int succeededCount, int failedCount, int consecutiveFailedCount,
            Date lastExecutedDate, Date lastSucceededDate, Date lastFailedDate
    ) {
        this.key = key;
        this.executingCount = executingCount;
        this.succeededCount = succeededCount;
        this.failedCount = failedCount;
        this.consecutiveFailedCount = consecutiveFailedCount;
        this.lastExecutedDate = lastExecutedDate;
        this.lastSucceededDate = lastSucceededDate;
        this.lastFailedDate = lastFailedDate;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public int getExecutingCount() {
        return executingCount;
    }

    public void setExecutingCount(int executingCount) {
        this.executingCount = executingCount;
    }

    public int getSucceededCount() {
        return succeededCount;
    }

    public void setSucceededCount(int succeededCount) {
        this.succeededCount = succeededCount;
    }

    public int getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
    }

    public int getConsecutiveFailedCount() {
        return consecutiveFailedCount;
    }

    public void setConsecutiveFailedCount(int consecutiveFailedCount) {
        this.consecutiveFailedCount = consecutiveFailedCount;
    }

    public Date getLastExecutedDate() {
        return lastExecutedDate;
    }

    public void setLastExecutedDate(Date lastExecutedDate) {
        this.lastExecutedDate = lastExecutedDate;
    }

    public Date getLastSucceededDate() {
        return lastSucceededDate;
    }

    public void setLastSucceededDate(Date lastSucceededDate) {
        this.lastSucceededDate = lastSucceededDate;
    }

    public Date getLastFailedDate() {
        return lastFailedDate;
    }

    public void setLastFailedDate(Date lastFailedDate) {
        this.lastFailedDate = lastFailedDate;
    }

    @Override
    public String toString() {
        return "StatisticsExecutionProfile{" +
                "key=" + key +
                ", executingCount=" + executingCount +
                ", succeededCount=" + succeededCount +
                ", failedCount=" + failedCount +
                ", consecutiveFailedCount=" + consecutiveFailedCount +
                ", lastExecutedDate=" + lastExecutedDate +
                ", lastSucceededDate=" + lastSucceededDate +
                ", lastFailedDate=" + lastFailedDate +
                '}';
    }
}
