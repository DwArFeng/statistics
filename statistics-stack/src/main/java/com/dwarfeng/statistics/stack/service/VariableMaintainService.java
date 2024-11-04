package com.dwarfeng.statistics.stack.service;

import com.dwarfeng.statistics.stack.bean.entity.Variable;
import com.dwarfeng.statistics.stack.bean.key.VariableKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 变量维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface VariableMaintainService extends BatchCrudService<VariableKey, Variable>,
        EntireLookupService<Variable>, PresetLookupService<Variable> {

    String CHILD_FOR_STATISTICS_SETTING = "child_for_statistics_setting";
}
