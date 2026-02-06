package com.dwarfeng.statistics.stack.service;

import com.dwarfeng.statistics.stack.bean.entity.TagDefinition;
import com.dwarfeng.statistics.stack.bean.key.TagDefinitionKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 标签定义。
 *
 * @author zhaofz
 * @since 1.4.0
 */
public interface TagDefinitionMaintainService extends BatchCrudService<TagDefinitionKey, TagDefinition>,
        EntireLookupService<TagDefinition>, PresetLookupService<TagDefinition> {

    String CHILD_FOR_STATISTICS_SETTING = "child_for_statistics_setting";
}
