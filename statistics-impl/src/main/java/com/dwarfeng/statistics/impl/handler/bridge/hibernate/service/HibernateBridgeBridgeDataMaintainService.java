package com.dwarfeng.statistics.impl.handler.bridge.hibernate.service;

import com.dwarfeng.statistics.impl.handler.bridge.hibernate.bean.HibernateBridgeBridgeData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.BatchWriteService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * Hibernate 桥接器数据维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface HibernateBridgeBridgeDataMaintainService extends
        BatchCrudService<LongIdKey, HibernateBridgeBridgeData>,
        EntireLookupService<HibernateBridgeBridgeData>, PresetLookupService<HibernateBridgeBridgeData>,
        BatchWriteService<HibernateBridgeBridgeData> {

    /**
     * 统计设置键为指定值，且发生时间在起始时间和结束时间之间，起始时间闭合，结束时间闭合。
     */
    String CHILD_FOR_STATISTICS_SETTING_BETWEEN_CLOSE_CLOSE = "child_for_statistics_setting_between_close_close";

    /**
     * 统计设置键为指定值，且发生时间在起始时间和结束时间之间，起始时间闭合，结束时间开放。
     */
    String CHILD_FOR_STATISTICS_SETTING_BETWEEN_CLOSE_OPEN = "child_for_statistics_setting_between_close_open";

    /**
     * 统计设置键为指定值，且发生时间在起始时间和结束时间之间，起始时间开放，结束时间闭合。
     */
    String CHILD_FOR_STATISTICS_SETTING_BETWEEN_OPEN_CLOSE = "child_for_statistics_setting_between_open_close";

    /**
     * 统计设置键为指定值，且发生时间在起始时间和结束时间之间，起始时间开放，结束时间开放。
     */
    String CHILD_FOR_STATISTICS_SETTING_BETWEEN_OPEN_OPEN = "child_for_statistics_setting_between_open_open";
}
