package com.dwarfeng.statistics.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.stack.bean.entity.StatisticsExecutionProfile;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 统计执行简报。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonStatisticsExecutionProfile implements Bean {

    private static final long serialVersionUID = 1372282043373596673L;

    public static FastJsonStatisticsExecutionProfile of(StatisticsExecutionProfile statisticsExecutionProfile) {
        if (Objects.isNull(statisticsExecutionProfile)) {
            return null;
        } else {
            return new FastJsonStatisticsExecutionProfile(
                    FastJsonLongIdKey.of(statisticsExecutionProfile.getKey()),
                    statisticsExecutionProfile.getExecutingCount(),
                    statisticsExecutionProfile.getSucceededCount(),
                    statisticsExecutionProfile.getFailedCount(),
                    statisticsExecutionProfile.getConsecutiveFailedCount(),
                    statisticsExecutionProfile.getLastExecutedDate(),
                    statisticsExecutionProfile.getLastSucceededDate(),
                    statisticsExecutionProfile.getLastFailedDate()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "executing_count", ordinal = 2)
    private int executingCount;

    @JSONField(name = "succeeded_count", ordinal = 3)
    private int succeededCount;

    @JSONField(name = "failed_count", ordinal = 4)
    private int failedCount;

    @JSONField(name = "consecutive_failed_count", ordinal = 5)
    private int consecutiveFailedCount;

    @JSONField(name = "last_executed_date", ordinal = 6)
    private Date lastExecutedDate;

    @JSONField(name = "last_succeeded_date", ordinal = 7)
    private Date lastSucceededDate;

    @JSONField(name = "last_failed_date", ordinal = 8)
    private Date lastFailedDate;

    public FastJsonStatisticsExecutionProfile() {
    }

    public FastJsonStatisticsExecutionProfile(
            FastJsonLongIdKey key, int executingCount, int succeededCount, int failedCount, int consecutiveFailedCount,
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

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
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
        return "FastJsonStatisticsExecutionProfile{" +
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
