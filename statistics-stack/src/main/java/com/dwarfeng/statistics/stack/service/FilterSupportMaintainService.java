package com.dwarfeng.statistics.stack.service;

import com.dwarfeng.statistics.stack.bean.entity.FilterSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 过滤器支持维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface FilterSupportMaintainService extends BatchCrudService<StringIdKey, FilterSupport>,
        EntireLookupService<FilterSupport>, PresetLookupService<FilterSupport> {

    String ID_LIKE = "id_like";
    String LABEL_LIKE = "label_like";

    /**
     * 重置过滤器支持。
     *
     * @throws ServiceException 服务异常。
     */
    void reset() throws ServiceException;
}
