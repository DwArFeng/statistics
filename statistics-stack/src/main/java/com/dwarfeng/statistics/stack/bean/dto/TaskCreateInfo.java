package com.dwarfeng.statistics.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 任务创建信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskCreateInfo implements Dto {

    private static final long serialVersionUID = -2442665691906225871L;

    private LongIdKey statisticsSettingKey;

    public TaskCreateInfo() {
    }

    public TaskCreateInfo(LongIdKey statisticsSettingKey) {
        this.statisticsSettingKey = statisticsSettingKey;
    }

    public LongIdKey getStatisticsSettingKey() {
        return statisticsSettingKey;
    }

    public void setStatisticsSettingKey(LongIdKey statisticsSettingKey) {
        this.statisticsSettingKey = statisticsSettingKey;
    }

    @Override
    public String toString() {
        return "TaskCreateInfo{" +
                "statisticsSettingKey=" + statisticsSettingKey +
                '}';
    }
}
