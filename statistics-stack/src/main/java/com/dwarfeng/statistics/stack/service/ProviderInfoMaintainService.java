package com.dwarfeng.statistics.stack.service;

import com.dwarfeng.statistics.stack.bean.entity.ProviderInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 提供器信息维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ProviderInfoMaintainService extends BatchCrudService<LongIdKey, ProviderInfo>,
        EntireLookupService<ProviderInfo>, PresetLookupService<ProviderInfo> {

    String CHILD_FOR_STATISTICS_SETTING = "child_for_statistics_setting";
    String CHILD_FOR_STATISTICS_SETTING_ENABLED = "child_for_statistics_setting_enabled";
}
