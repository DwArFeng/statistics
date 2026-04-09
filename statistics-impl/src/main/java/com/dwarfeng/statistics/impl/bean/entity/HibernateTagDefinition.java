package com.dwarfeng.statistics.impl.bean.entity;

import com.dwarfeng.datamark.bean.jpa.DatamarkEntityListener;
import com.dwarfeng.datamark.bean.jpa.DatamarkField;
import com.dwarfeng.statistics.impl.bean.key.HibernateTagDefinitionKey;
import com.dwarfeng.statistics.sdk.util.Constraints;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(HibernateTagDefinitionKey.class)
@Table(name = "tbl_tag_definition")
@EntityListeners(DatamarkEntityListener.class)
public class HibernateTagDefinition implements Bean {

    private static final long serialVersionUID = -3734426331400550357L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "tag_definition_id", length = Constraints.LENGTH_STRING_ID, nullable = false)
    private String tag;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Id
    @Column(name = "statistics_setting_id", nullable = false)
    private Long statisticsSettingLongId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "definition", length = Constraints.LENGTH_REMARK)
    private int definition;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernateStatisticsSetting.class)
    @JoinColumns({ //
            @JoinColumn(name = "statistics_setting_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateStatisticsSetting statisticsSetting;

    // -----------------------------------------------------------审计-----------------------------------------------------------
    @DatamarkField(handlerName = "statisticsSettingDatamarkHandler")
    @Column(
            name = "created_datamark",
            length = com.dwarfeng.datamark.util.Constraints.LENGTH_DATAMARK_VALUE,
            updatable = false
    )
    private String createdDatamark;

    @DatamarkField(handlerName = "statisticsSettingDatamarkHandler")
    @Column(
            name = "modified_datamark",
            length = com.dwarfeng.datamark.util.Constraints.LENGTH_DATAMARK_VALUE
    )
    private String modifiedDatamark;

    public HibernateTagDefinition() {
    }

    // -----------------------------------------------------------映射用属性区-----------------------------------------------------------
    public HibernateTagDefinitionKey getKey() {
        return new HibernateTagDefinitionKey(statisticsSettingLongId, tag);
    }

    public void setKey(HibernateTagDefinitionKey key) {
        if (Objects.isNull(key)) {
            this.statisticsSettingLongId = null;
            this.tag = null;
        } else {
            this.statisticsSettingLongId = key.getStatisticsSettingLongId();
            this.tag = key.getTag();
        }
    }

    // -----------------------------------------------------------常规属性区-----------------------------------------------------------
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getStatisticsSettingLongId() {
        return statisticsSettingLongId;
    }

    public void setStatisticsSettingLongId(Long statisticsSettingLongId) {
        this.statisticsSettingLongId = statisticsSettingLongId;
    }

    public int getDefinition() {
        return definition;
    }

    public void setDefinition(int definition) {
        this.definition = definition;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HibernateStatisticsSetting getStatisticsSetting() {
        return statisticsSetting;
    }

    public void setStatisticsSetting(HibernateStatisticsSetting statisticsSetting) {
        this.statisticsSetting = statisticsSetting;
    }

    public String getCreatedDatamark() {
        return createdDatamark;
    }

    public void setCreatedDatamark(String createdDatamark) {
        this.createdDatamark = createdDatamark;
    }

    public String getModifiedDatamark() {
        return modifiedDatamark;
    }

    public void setModifiedDatamark(String modifiedDatamark) {
        this.modifiedDatamark = modifiedDatamark;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "tag = " + tag + ", " +
                "statisticsSettingLongId = " + statisticsSettingLongId + ", " +
                "definition = " + definition + ", " +
                "remark = " + remark + ", " +
                "statisticsSetting = " + statisticsSetting + ", " +
                "createdDatamark = " + createdDatamark + ", " +
                "modifiedDatamark = " + modifiedDatamark + ")";
    }
}
