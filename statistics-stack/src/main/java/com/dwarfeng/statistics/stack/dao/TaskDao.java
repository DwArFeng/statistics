package com.dwarfeng.statistics.stack.dao;

import com.dwarfeng.statistics.stack.bean.entity.Task;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 任务数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface TaskDao extends BatchBaseDao<LongIdKey, Task>, EntireLookupDao<Task>,
        PresetLookupDao<Task> {
}
