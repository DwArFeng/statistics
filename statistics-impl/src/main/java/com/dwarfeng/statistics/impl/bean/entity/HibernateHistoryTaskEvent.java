package com.dwarfeng.statistics.impl.bean.entity;

import com.dwarfeng.statistics.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_history_task_event")
public class HibernateHistoryTaskEvent implements Bean {

    private static final long serialVersionUID = 8625211901909598355L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "history_task_id")
    private Long historyTaskLongId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "happened_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date happenedDate;

    @Column(name = "message", length = Constraints.LENGTH_MESSAGE)
    private String message;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernateHistoryTask.class)
    @JoinColumns({ //
            @JoinColumn(name = "history_task_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateHistoryTask historyTask;

    public HibernateHistoryTaskEvent() {
    }

    // -----------------------------------------------------------映射用属性区-----------------------------------------------------------
    public HibernateLongIdKey getKey() {
        return Optional.ofNullable(longId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setKey(HibernateLongIdKey key) {
        this.longId = Optional.ofNullable(key).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    public HibernateLongIdKey getHistoryTaskKey() {
        return Optional.ofNullable(historyTaskLongId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setHistoryTaskKey(HibernateLongIdKey key) {
        this.historyTaskLongId = Optional.ofNullable(key).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    // -----------------------------------------------------------常规属性区-----------------------------------------------------------
    public Long getLongId() {
        return longId;
    }

    public void setLongId(Long longId) {
        this.longId = longId;
    }

    public Long getHistoryTaskLongId() {
        return historyTaskLongId;
    }

    public void setHistoryTaskLongId(Long historyTaskLongId) {
        this.historyTaskLongId = historyTaskLongId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HibernateHistoryTask getHistoryTask() {
        return historyTask;
    }

    public void setHistoryTask(HibernateHistoryTask historyTask) {
        this.historyTask = historyTask;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "longId = " + longId + ", " +
                "historyTaskLongId = " + historyTaskLongId + ", " +
                "happenedDate = " + happenedDate + ", " +
                "message = " + message + ", " +
                "remark = " + remark + ", " +
                "historyTask = " + historyTask + ")";
    }
}
