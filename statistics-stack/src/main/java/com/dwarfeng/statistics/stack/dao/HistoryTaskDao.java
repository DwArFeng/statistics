package com.dwarfeng.statistics.stack.dao;

import com.dwarfeng.statistics.stack.bean.entity.HistoryTask;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 历史任务数据访问层。
 *
 * @author DwArFeng
 * @since beta-1.0.0
 */
public interface HistoryTaskDao extends BatchBaseDao<LongIdKey, HistoryTask>, EntireLookupDao<HistoryTask>,
        PresetLookupDao<HistoryTask> {
}
