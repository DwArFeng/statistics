package com.dwarfeng.statistics.stack.dao;

import com.dwarfeng.statistics.stack.bean.entity.Variable;
import com.dwarfeng.statistics.stack.bean.key.VariableKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 变量数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface VariableDao extends BatchBaseDao<VariableKey, Variable>, EntireLookupDao<Variable>,
        PresetLookupDao<Variable> {
}
