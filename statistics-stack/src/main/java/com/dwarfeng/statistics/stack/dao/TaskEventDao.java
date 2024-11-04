package com.dwarfeng.statistics.stack.dao;

import com.dwarfeng.statistics.stack.bean.entity.TaskEvent;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 任务事件数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface TaskEventDao extends BatchBaseDao<LongIdKey, TaskEvent>, EntireLookupDao<TaskEvent>,
        PresetLookupDao<TaskEvent> {
}
