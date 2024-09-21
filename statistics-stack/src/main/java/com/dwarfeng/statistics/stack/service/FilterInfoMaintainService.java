package com.dwarfeng.statistics.stack.service;

import com.dwarfeng.statistics.stack.bean.entity.FilterInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 过滤器信息维护服务。
 *
 * @author DwArFeng
 * @since beta-1.0.0
 */
public interface FilterInfoMaintainService extends BatchCrudService<LongIdKey, FilterInfo>,
        EntireLookupService<FilterInfo>, PresetLookupService<FilterInfo> {

    String CHILD_FOR_STATISTICS_SETTING = "child_for_statistics_setting";
    String CHILD_FOR_STATISTICS_SETTING_INDEX_ASC = "child_for_statistics_setting_index_asc";
    String CHILD_FOR_STATISTICS_SETTING_ENABLED = "child_for_statistics_setting_enabled";
    String CHILD_FOR_STATISTICS_SETTING_ENABLED_INDEX_ASC = "child_for_statistics_setting_enabled_index_asc";
}
