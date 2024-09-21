package com.dwarfeng.statistics.impl.bean.entity;

import com.dwarfeng.statistics.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_statistics_setting")
public class HibernateStatisticsSetting implements Bean {

    private static final long serialVersionUID = 3535711318921951817L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "name", length = Constraints.LENGTH_NAME, nullable = false)
    private String name;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    // -----------------------------------------------------------一对一-----------------------------------------------------------
    @OneToOne(cascade = CascadeType.MERGE, targetEntity = HibernateStatisticsExecutionProfile.class, mappedBy = "statisticsSetting")
    private HibernateStatisticsExecutionProfile statisticsExecutionProfile;

    // -----------------------------------------------------------一对多-----------------------------------------------------------
    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateDriverInfo.class, mappedBy = "statisticsSetting")
    private Set<HibernateDriverInfo> driverInfos = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateProviderInfo.class, mappedBy = "statisticsSetting")
    private Set<HibernateProviderInfo> providerInfos = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateFilterInfo.class, mappedBy = "statisticsSetting")
    private Set<HibernateFilterInfo> filterInfos = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateTask.class, mappedBy = "statisticsSetting")
    private Set<HibernateTask> tasks = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateHistoryTask.class, mappedBy = "statisticsSetting")
    private Set<HibernateHistoryTask> historyTasks = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateVariable.class, mappedBy = "statisticsSetting")
    private Set<HibernateVariable> variables = new HashSet<>();

    public HibernateStatisticsSetting() {
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HibernateStatisticsExecutionProfile getStatisticsExecutionProfile() {
        return statisticsExecutionProfile;
    }

    public void setStatisticsExecutionProfile(HibernateStatisticsExecutionProfile statisticsExecutionProfile) {
        this.statisticsExecutionProfile = statisticsExecutionProfile;
    }

    public Set<HibernateDriverInfo> getDriverInfos() {
        return driverInfos;
    }

    public void setDriverInfos(Set<HibernateDriverInfo> driverInfos) {
        this.driverInfos = driverInfos;
    }

    public Set<HibernateProviderInfo> getProviderInfos() {
        return providerInfos;
    }

    public void setProviderInfos(Set<HibernateProviderInfo> providerInfos) {
        this.providerInfos = providerInfos;
    }

    public Set<HibernateFilterInfo> getFilterInfos() {
        return filterInfos;
    }

    public void setFilterInfos(Set<HibernateFilterInfo> filterInfos) {
        this.filterInfos = filterInfos;
    }

    public Set<HibernateTask> getTasks() {
        return tasks;
    }

    public void setTasks(Set<HibernateTask> tasks) {
        this.tasks = tasks;
    }

    public Set<HibernateHistoryTask> getHistoryTasks() {
        return historyTasks;
    }

    public void setHistoryTasks(Set<HibernateHistoryTask> historyTasks) {
        this.historyTasks = historyTasks;
    }

    public Set<HibernateVariable> getVariables() {
        return variables;
    }

    public void setVariables(Set<HibernateVariable> variables) {
        this.variables = variables;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "longId = " + longId + ", " +
                "enabled = " + enabled + ", " +
                "name = " + name + ", " +
                "remark = " + remark + ", " +
                "statisticsExecutionProfile = " + statisticsExecutionProfile + ")";
    }
}
