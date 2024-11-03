package com.dwarfeng.statistics.stack.service;

import com.dwarfeng.statistics.stack.bean.entity.Task;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 任务维护服务。
 *
 * @author DwArFeng
 * @since beta-1.0.0
 */
public interface TaskMaintainService extends BatchCrudService<LongIdKey, Task>,
        EntireLookupService<Task>, PresetLookupService<Task> {

    String CHILD_FOR_STATISTICS_SETTING = "child_for_statistics_setting";

    /**
     * 获取应该过期的任务。
     *
     * @since 1.1.0
     */
    String SHOULD_EXPIRE = "should_expire";

    /**
     * 获取应该死亡的任务。
     *
     * @since 1.1.0
     */
    String SHOULD_DIE = "should_die";

}
