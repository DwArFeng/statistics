package com.dwarfeng.statistics.stack.struct;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 消费信息。
 *
 * @author DwArFeng
 * @since 1.2.0
 */
public final class ConsumeInfo {

    private final LongIdKey statisticsSettingKey;

    public ConsumeInfo(LongIdKey statisticsSettingKey) {
        this.statisticsSettingKey = statisticsSettingKey;
    }

    public LongIdKey getStatisticsSettingKey() {
        return statisticsSettingKey;
    }

    @Override
    public String toString() {
        return "ConsumeInfo{" +
                "statisticsSettingKey=" + statisticsSettingKey +
                '}';
    }
}
