package com.dwarfeng.statistics.stack.dao;

import com.dwarfeng.statistics.stack.bean.entity.HistoryTaskEvent;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 历史任务事件数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface HistoryTaskEventDao extends BatchBaseDao<LongIdKey, HistoryTaskEvent>, EntireLookupDao<HistoryTaskEvent>,
        PresetLookupDao<HistoryTaskEvent> {
}
