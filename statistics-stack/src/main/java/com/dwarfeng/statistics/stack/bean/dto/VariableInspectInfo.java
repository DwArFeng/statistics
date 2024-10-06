package com.dwarfeng.statistics.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 变量查看信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class VariableInspectInfo implements Dto {

    private static final long serialVersionUID = -3948332845270257835L;

    private LongIdKey statisticsSettingKey;
    private String variableId;

    public VariableInspectInfo() {
    }

    public VariableInspectInfo(LongIdKey statisticsSettingKey, String variableId) {
        this.statisticsSettingKey = statisticsSettingKey;
        this.variableId = variableId;
    }

    public LongIdKey getStatisticsSettingKey() {
        return statisticsSettingKey;
    }

    public void setStatisticsSettingKey(LongIdKey statisticsSettingKey) {
        this.statisticsSettingKey = statisticsSettingKey;
    }

    public String getVariableId() {
        return variableId;
    }

    public void setVariableId(String variableId) {
        this.variableId = variableId;
    }

    @Override
    public String toString() {
        return "VariableInspectInfo{" +
                "statisticsSettingKey=" + statisticsSettingKey +
                ", variableId='" + variableId + '\'' +
                '}';
    }
}
