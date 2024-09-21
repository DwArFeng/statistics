package com.dwarfeng.statistics.impl.bean.entity;

import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_statistics_execution_profile")
public class HibernateStatisticsExecutionProfile implements Bean {

    private static final long serialVersionUID = 980215893193232634L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "executing_count", nullable = false)
    private int executingCount;

    @Column(name = "succeeded_count", nullable = false)
    private int succeededCount;

    @Column(name = "failed_count", nullable = false)
    private int failedCount;

    @Column(name = "consecutive_failed_count", nullable = false)
    private int consecutiveFailedCount;

    @Column(name = "last_executed_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastExecutedDate;

    @Column(name = "last_succeeded_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSucceededDate;

    @Column(name = "last_failed_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastFailedDate;

    // -----------------------------------------------------------一对一-----------------------------------------------------------
    @OneToOne(targetEntity = HibernateStatisticsSetting.class)
    @JoinColumns({ //
            @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateStatisticsSetting statisticsSetting;

    public HibernateStatisticsExecutionProfile() {
    }

    // -----------------------------------------------------------映射用属性区-----------------------------------------------------------
    public HibernateLongIdKey getKey() {
        return Optional.ofNullable(longId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setKey(HibernateLongIdKey key) {
        this.longId = Optional.ofNullable(key).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    // -----------------------------------------------------------常规属性区-----------------------------------------------------------
    public Long getLongId() {
        return longId;
    }

    public void setLongId(Long longId) {
        this.longId = longId;
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

    public HibernateStatisticsSetting getStatisticsSetting() {
        return statisticsSetting;
    }

    public void setStatisticsSetting(HibernateStatisticsSetting statisticsSetting) {
        this.statisticsSetting = statisticsSetting;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "longId = " + longId + ", " +
                "executingCount = " + executingCount + ", " +
                "succeededCount = " + succeededCount + ", " +
                "failedCount = " + failedCount + ", " +
                "consecutiveFailedCount = " + consecutiveFailedCount + ", " +
                "lastExecutedDate = " + lastExecutedDate + ", " +
                "lastSucceededDate = " + lastSucceededDate + ", " +
                "lastFailedDate = " + lastFailedDate + ", " +
                "statisticsSetting = " + statisticsSetting + ")";
    }
}
