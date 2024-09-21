package com.dwarfeng.statistics.stack.service;

import com.dwarfeng.statistics.stack.bean.entity.HistoryTaskEvent;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 历史任务事件维护服务。
 *
 * @author DwArFeng
 * @since beta-1.0.0
 */
public interface HistoryTaskEventMaintainService extends BatchCrudService<LongIdKey, HistoryTaskEvent>,
        EntireLookupService<HistoryTaskEvent>, PresetLookupService<HistoryTaskEvent> {

    String CHILD_FOR_STATISTICS_SETTING = "child_for_statistics_setting";
    String CHILD_FOR_HISTORY_TASK = "child_for_history_task";
}
