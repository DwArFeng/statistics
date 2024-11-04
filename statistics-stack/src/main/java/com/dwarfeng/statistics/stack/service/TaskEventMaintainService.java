package com.dwarfeng.statistics.stack.service;

import com.dwarfeng.statistics.stack.bean.entity.TaskEvent;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 任务事件维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface TaskEventMaintainService extends BatchCrudService<LongIdKey, TaskEvent>,
        EntireLookupService<TaskEvent>, PresetLookupService<TaskEvent> {

    String CHILD_FOR_STATISTICS_SETTING = "child_for_statistics_setting";
    String CHILD_FOR_TASK = "child_for_task";
}
