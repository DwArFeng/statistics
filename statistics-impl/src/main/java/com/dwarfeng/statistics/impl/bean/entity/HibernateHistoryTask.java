package com.dwarfeng.statistics.impl.bean.entity;

import com.dwarfeng.statistics.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_history_task")
public class HibernateHistoryTask implements Bean {

    private static final long serialVersionUID = 2986849067529768422L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "statistics_setting_id")
    private Long statisticsSettingLongId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "expired_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredDate;

    @Column(name = "died_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date diedDate;

    @Column(name = "message", length = Constraints.LENGTH_MESSAGE)
    private String message;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernateStatisticsSetting.class)
    @JoinColumns({ //
            @JoinColumn(name = "statistics_setting_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateStatisticsSetting statisticsSetting;

    // -----------------------------------------------------------一对多-----------------------------------------------------------
    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateHistoryTaskEvent.class, mappedBy = "historyTask")
    private Set<HibernateHistoryTaskEvent> taskEventHistories = new HashSet<>();

    public HibernateHistoryTask() {
    }

    // -----------------------------------------------------------映射用属性区-----------------------------------------------------------
    public HibernateLongIdKey getKey() {
        return Optional.ofNullable(longId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setKey(HibernateLongIdKey key) {
        this.longId = Optional.ofNullable(key).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    public HibernateLongIdKey getStatisticsSettingKey() {
        return Optional.ofNullable(statisticsSettingLongId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setStatisticsSettingKey(HibernateLongIdKey key) {
        this.statisticsSettingLongId = Optional.ofNullable(key).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    // -----------------------------------------------------------常规属性区-----------------------------------------------------------
    public Long getLongId() {
        return longId;
    }

    public void setLongId(Long longId) {
        this.longId = longId;
    }

    public Long getStatisticsSettingLongId() {
        return statisticsSettingLongId;
    }

    public void setStatisticsSettingLongId(Long statisticsSettingLongId) {
        this.statisticsSettingLongId = statisticsSettingLongId;
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

    public HibernateStatisticsSetting getStatisticsSetting() {
        return statisticsSetting;
    }

    public void setStatisticsSetting(HibernateStatisticsSetting statisticsSetting) {
        this.statisticsSetting = statisticsSetting;
    }

    public Set<HibernateHistoryTaskEvent> getTaskEventHistories() {
        return taskEventHistories;
    }

    public void setTaskEventHistories(Set<HibernateHistoryTaskEvent> taskEventHistories) {
        this.taskEventHistories = taskEventHistories;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "longId = " + longId + ", " +
                "statisticsSettingLongId = " + statisticsSettingLongId + ", " +
                "status = " + status + ", " +
                "createDate = " + createDate + ", " +
                "startDate = " + startDate + ", " +
                "endDate = " + endDate + ", " +
                "duration = " + duration + ", " +
                "expiredDate = " + expiredDate + ", " +
                "diedDate = " + diedDate + ", " +
                "message = " + message + ", " +
                "statisticsSetting = " + statisticsSetting + ")";
    }
}
