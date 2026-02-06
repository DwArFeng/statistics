package com.dwarfeng.statistics.stack.dao;

import com.dwarfeng.statistics.stack.bean.entity.TagDefinition;
import com.dwarfeng.statistics.stack.bean.key.TagDefinitionKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 标签定义。
 *
 * @author zhaogz
 * @since 1.4.0
 */
public interface TagDefinitionDao extends BatchBaseDao<TagDefinitionKey, TagDefinition>, EntireLookupDao<TagDefinition>,
        PresetLookupDao<TagDefinition> {
}
