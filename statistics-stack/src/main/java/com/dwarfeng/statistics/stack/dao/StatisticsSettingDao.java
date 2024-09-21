package com.dwarfeng.statistics.stack.dao;

import com.dwarfeng.statistics.stack.bean.entity.StatisticsSetting;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 统计设置数据访问层。
 *
 * @author DwArFeng
 * @since beta-1.0.0
 */
public interface StatisticsSettingDao extends BatchBaseDao<LongIdKey, StatisticsSetting>, EntireLookupDao<StatisticsSetting>,
        PresetLookupDao<StatisticsSetting> {
}
