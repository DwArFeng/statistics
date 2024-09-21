package com.dwarfeng.statistics.stack.service;

import com.dwarfeng.statistics.stack.bean.entity.DriverInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 驱动器信息维护服务。
 *
 * @author DwArFeng
 * @since beta-1.0.0
 */
public interface DriverInfoMaintainService extends BatchCrudService<LongIdKey, DriverInfo>,
        EntireLookupService<DriverInfo>, PresetLookupService<DriverInfo> {

    String CHILD_FOR_STATISTICS_SETTING = "child_for_statistics_setting";
    String CHILD_FOR_STATISTICS_SETTING_ENABLED = "child_for_statistics_setting_enabled";
}
