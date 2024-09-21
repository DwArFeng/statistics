package com.dwarfeng.statistics.stack.dao;

import com.dwarfeng.statistics.stack.bean.entity.FilterSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 过滤器支持数据访问层。
 *
 * @author DwArFeng
 * @since beta-1.0.0
 */
public interface FilterSupportDao extends BatchBaseDao<StringIdKey, FilterSupport>, EntireLookupDao<FilterSupport>,
        PresetLookupDao<FilterSupport> {
}
