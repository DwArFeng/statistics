package com.dwarfeng.statistics.impl.bean.entity;

import com.dwarfeng.statistics.impl.bean.key.HibernateVariableKey;
import com.dwarfeng.statistics.sdk.util.Constraints;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@IdClass(HibernateVariableKey.class)
@Table(name = "tbl_variable")
public class HibernateVariable implements Bean {

    private static final long serialVersionUID = -3496996314855050409L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "statistics_setting_id", nullable = false)
    private Long statisticsSettingLongId;

    @Id
    @Column(name = "variable_id", length = Constraints.LENGTH_STRING_ID, nullable = false)
    private String variableStringId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "value_type", nullable = false)
    private int valueType;

    @Column(name = "string_value", columnDefinition = "TEXT")
    private String stringValue;

    @Column(name = "long_value")
    private Long longValue;

    @Column(name = "double_value")
    private Double doubleValue;

    @Column(name = "boolean_value")
    private Boolean booleanValue;

    @Column(name = "date_value")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateValue;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernateStatisticsSetting.class)
    @JoinColumns({ //
            @JoinColumn(name = "statistics_setting_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateStatisticsSetting statisticsSetting;

    public HibernateVariable() {
    }

    // -----------------------------------------------------------映射用属性区-----------------------------------------------------------
    public HibernateVariableKey getKey() {
        if (Objects.isNull(statisticsSettingLongId) || Objects.isNull(variableStringId)) {
            return null;
        }
        return new HibernateVariableKey(statisticsSettingLongId, variableStringId);
    }

    public void setKey(HibernateVariableKey key) {
        if (Objects.isNull(key)) {
            this.statisticsSettingLongId = null;
            this.variableStringId = null;
        } else {
            this.statisticsSettingLongId = key.getStatisticsSettingLongId();
            this.variableStringId = key.getVariableStringId();
        }
    }

    // -----------------------------------------------------------常规属性区-----------------------------------------------------------
    public Long getStatisticsSettingLongId() {
        return statisticsSettingLongId;
    }

    public void setStatisticsSettingLongId(Long statisticsSettingLongId) {
        this.statisticsSettingLongId = statisticsSettingLongId;
    }

    public String getVariableStringId() {
        return variableStringId;
    }

    public void setVariableStringId(String variableStringId) {
        this.variableStringId = variableStringId;
    }

    public int getValueType() {
        return valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Long getLongValue() {
        return longValue;
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
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
                "statisticsSettingLongId = " + statisticsSettingLongId + ", " +
                "variableStringId = " + variableStringId + ", " +
                "valueType = " + valueType + ", " +
                "stringValue = " + stringValue + ", " +
                "longValue = " + longValue + ", " +
                "doubleValue = " + doubleValue + ", " +
                "booleanValue = " + booleanValue + ", " +
                "dateValue = " + dateValue + ", " +
                "statisticsSetting = " + statisticsSetting + ")";
    }
}
