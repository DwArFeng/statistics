package com.dwarfeng.statistics.stack.service;

import com.dwarfeng.statistics.stack.bean.entity.HistoryTask;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 历史任务维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface HistoryTaskMaintainService extends BatchCrudService<LongIdKey, HistoryTask>,
        EntireLookupService<HistoryTask>, PresetLookupService<HistoryTask> {

    String CHILD_FOR_STATISTICS_SETTING = "child_for_statistics_setting";
}
