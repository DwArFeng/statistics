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
     * 查看功能的默认预设。
     *
     * <p>
     * 该预设包含如下约束：
     * <ul>
     *     <li>statisticsSettingLongId 为指定值。</li>
     *     <li>tag 为指定值。</li>
     *     <li>起始时间闭合。</li>
     *     <li>结束时间闭合。</li>
     * </ul>
     */
    String LOOKUP_DEFAULT_CLOSE_CLOSE = "lookup_default_close_close";

    /**
     * 查看功能的默认预设。
     *
     * <p>
     * 该预设包含如下约束：
     * <ul>
     *     <li>statisticsSettingLongId 为指定值。</li>
     *     <li>tag 为指定值。</li>
     *     <li>起始时间闭合。</li>
     *     <li>结束时间开放。</li>
     * </ul>
     */
    String LOOKUP_DEFAULT_CLOSE_OPEN = "lookup_default_close_open";

    /**
     * 查看功能的默认预设。
     *
     * <p>
     * 该预设包含如下约束：
     * <ul>
     *     <li>statisticsSettingLongId 为指定值。</li>
     *     <li>tag 为指定值。</li>
     *     <li>起始时间开放。</li>
     *     <li>结束时间闭合。</li>
     * </ul>
     */
    String LOOKUP_DEFAULT_OPEN_CLOSE = "lookup_default_open_close";

    /**
     * 查看功能的默认预设。
     *
     * <p>
     * 该预设包含如下约束：
     * <ul>
     *     <li>statisticsSettingLongId 为指定值。</li>
     *     <li>tag 为指定值。</li>
     *     <li>起始时间开放。</li>
     *     <li>结束时间开放。</li>
     * </ul>
     */
    String LOOKUP_DEFAULT_OPEN_OPEN = "lookup_default_open_open";
}
