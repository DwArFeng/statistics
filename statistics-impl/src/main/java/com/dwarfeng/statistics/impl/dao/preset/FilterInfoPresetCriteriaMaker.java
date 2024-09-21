package com.dwarfeng.statistics.impl.dao.preset;

import com.dwarfeng.statistics.stack.service.FilterInfoMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class FilterInfoPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria criteria, String preset, Object[] objs) {
        switch (preset) {
            case FilterInfoMaintainService.CHILD_FOR_STATISTICS_SETTING:
                childForStatisticsSetting(criteria, objs);
                break;
            case FilterInfoMaintainService.CHILD_FOR_STATISTICS_SETTING_INDEX_ASC:
                childForStatisticsSettingIndexAsc(criteria, objs);
                break;
            case FilterInfoMaintainService.CHILD_FOR_STATISTICS_SETTING_ENABLED:
                childForStatisticsSettingEnabled(criteria, objs);
                break;
            case FilterInfoMaintainService.CHILD_FOR_STATISTICS_SETTING_ENABLED_INDEX_ASC:
                childForStatisticsSettingEnabledIndexAsc(criteria, objs);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForStatisticsSetting(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("statisticsSettingLongId"));
            } else {
                LongIdKey longIdKey = (LongIdKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull("statisticsSettingLongId", longIdKey.getLongId()));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void childForStatisticsSettingIndexAsc(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("statisticsSettingLongId"));
            } else {
                LongIdKey longIdKey = (LongIdKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull("statisticsSettingLongId", longIdKey.getLongId()));
            }
            criteria.addOrder(Order.asc("index"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForStatisticsSettingEnabled(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("statisticsSettingLongId"));
            } else {
                LongIdKey longIdKey = (LongIdKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull("statisticsSettingLongId", longIdKey.getLongId()));
            }
            criteria.add(Restrictions.eq("enabled", true));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void childForStatisticsSettingEnabledIndexAsc(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("statisticsSettingLongId"));
            } else {
                LongIdKey longIdKey = (LongIdKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull("statisticsSettingLongId", longIdKey.getLongId()));
            }
            criteria.add(Restrictions.eq("enabled", true));
            criteria.addOrder(Order.asc("index"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }
}
