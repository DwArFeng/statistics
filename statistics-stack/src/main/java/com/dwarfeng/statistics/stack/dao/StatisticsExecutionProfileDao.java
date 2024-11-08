package com.dwarfeng.statistics.stack.dao;

import com.dwarfeng.statistics.stack.bean.entity.StatisticsExecutionProfile;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 统计执行简报数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface StatisticsExecutionProfileDao extends BatchBaseDao<LongIdKey, StatisticsExecutionProfile>, EntireLookupDao<StatisticsExecutionProfile>,
        PresetLookupDao<StatisticsExecutionProfile> {
}
