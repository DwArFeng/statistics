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

    private static final long serialVersionUID = -5555700695528814012L;

    @JSONField(name = "statistics_setting_long_id")
    private Long statisticsSettingLongId;

    @JSONField(name = "tag")
    private String tag;

    @JSONField(name = "statistics_setting_type")
    private String statisticsSettingType;

    public MockBridgeDataConfigItem() {
    }

    public MockBridgeDataConfigItem(Long statisticsSettingLongId, String tag, String statisticsSettingType) {
        this.statisticsSettingLongId = statisticsSettingLongId;
        this.tag = tag;
        this.statisticsSettingType = statisticsSettingType;
    }

    public Long getStatisticsSettingLongId() {
        return statisticsSettingLongId;
    }

    public void setStatisticsSettingLongId(Long statisticsSettingLongId) {
        this.statisticsSettingLongId = statisticsSettingLongId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getStatisticsSettingType() {
        return statisticsSettingType;
    }

    public void setStatisticsSettingType(String statisticsSettingType) {
        this.statisticsSettingType = statisticsSettingType;
    }

    @Override
    public String toString() {
        return "MockBridgeDataConfigItem{" +
                "statisticsSettingLongId=" + statisticsSettingLongId +
                ", tag='" + tag + '\'' +
                ", statisticsSettingType='" + statisticsSettingType + '\'' +
                '}';
    }
}
