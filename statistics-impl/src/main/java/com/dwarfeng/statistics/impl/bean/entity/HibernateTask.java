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
@Table(name = "tbl_task")
public class HibernateTask implements Bean {

    private static final long serialVersionUID = -7007461118178556000L;

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

    @Column(name = "should_expire_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date shouldExpireDate;

    @Column(name = "should_die_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date shouldDieDate;

    @Column(name = "message", length = Constraints.LENGTH_MESSAGE)
    private String message;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernateStatisticsSetting.class)
    @JoinColumns({ //
            @JoinColumn(name = "statistics_setting_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateStatisticsSetting statisticsSetting;

    // -----------------------------------------------------------一对多-----------------------------------------------------------
    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateTaskEvent.class, mappedBy = "task")
    private Set<HibernateTaskEvent> taskEvents = new HashSet<>();

    public HibernateTask() {
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

    public Set<HibernateTaskEvent> getTaskEvents() {
        return taskEvents;
    }

    public void setTaskEvents(Set<HibernateTaskEvent> taskEvents) {
        this.taskEvents = taskEvents;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "longId = " + longId + ", " +
                "statisticsSettingLongId = " + statisticsSettingLongId + ", " +
                "status = " + status + ", " +
                "createDate = " + createDate + ", " +
                "startDate = " + startDate + ", " +
                "shouldExpireDate = " + shouldExpireDate + ", " +
                "shouldDieDate = " + shouldDieDate + ", " +
                "message = " + message + ", " +
                "statisticsSetting = " + statisticsSetting + ")";
    }
}
