package com.dwarfeng.statistics.stack.service;

import com.dwarfeng.statistics.stack.bean.entity.DriverSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 驱动器支持维护服务。
 *
 * @author DwArFeng
 * @since beta-1.0.0
 */
public interface DriverSupportMaintainService extends BatchCrudService<StringIdKey, DriverSupport>,
        EntireLookupService<DriverSupport>, PresetLookupService<DriverSupport> {

    String ID_LIKE = "id_like";
    String LABEL_LIKE = "label_like";
}
