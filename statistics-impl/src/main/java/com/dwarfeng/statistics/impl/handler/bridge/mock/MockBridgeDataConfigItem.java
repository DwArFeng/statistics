package com.dwarfeng.statistics.impl.handler.bridge.mock;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.stack.bean.Bean;

/**
 * 模拟桥接数据配置项。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class MockBridgeDataConfigItem implements Bean {

    private static final long serialVersionUID = 8510224495947819357L;

    @JSONField(name = "statistics_setting_id")
    private Long statisticsSettingId;

    @JSONField(name = "statistics_setting_type")
    private String statisticsSettingType;

    public MockBridgeDataConfigItem() {
    }

    public MockBridgeDataConfigItem(Long statisticsSettingId, String statisticsSettingType) {
        this.statisticsSettingId = statisticsSettingId;
        this.statisticsSettingType = statisticsSettingType;
    }

    public Long getStatisticsSettingId() {
        return statisticsSettingId;
    }

    public void setStatisticsSettingId(Long statisticsSettingId) {
        this.statisticsSettingId = statisticsSettingId;
    }

    public String getStatisticsSettingType() {
        return statisticsSettingType;
    }

    public void setStatisticsSettingType(String statisticsSettingType) {
        this.statisticsSettingType = statisticsSettingType;
    }

    @Override
    public String toString() {
        return "RealtimeMockSourceDataConfigItem{" +
                "statisticsSettingId=" + statisticsSettingId +
                ", statisticsSettingType='" + statisticsSettingType + '\'' +
                '}';
    }
}
