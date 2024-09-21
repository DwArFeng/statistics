package com.dwarfeng.statistics.stack.service;

import com.dwarfeng.statistics.stack.bean.entity.ProviderSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 提供器支持维护服务。
 *
 * @author DwArFeng
 * @since beta-1.0.0
 */
public interface ProviderSupportMaintainService extends BatchCrudService<StringIdKey, ProviderSupport>,
        EntireLookupService<ProviderSupport>, PresetLookupService<ProviderSupport> {

    String ID_LIKE = "id_like";
    String LABEL_LIKE = "label_like";
}
