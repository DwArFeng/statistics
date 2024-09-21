package com.dwarfeng.statistics.stack.service;

import com.dwarfeng.statistics.stack.bean.entity.StatisticsSetting;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 统计设置维护服务。
 *
 * @author DwArFeng
 * @since beta-1.0.0
 */
public interface StatisticsSettingMaintainService extends BatchCrudService<LongIdKey, StatisticsSetting>,
        EntireLookupService<StatisticsSetting>, PresetLookupService<StatisticsSetting> {

    String ENABLED = "enabled";
}
