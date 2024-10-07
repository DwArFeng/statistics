package com.dwarfeng.statistics.impl.handler.bridge.hibernate.dao.preset;

import com.dwarfeng.statistics.impl.handler.bridge.hibernate.service.HibernateBridgeBridgeDataMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Component
public class HibernateBridgeBridgeDataPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria detachedCriteria, String s, Object[] objects) {
        switch (s) {
            case HibernateBridgeBridgeDataMaintainService.CHILD_FOR_STATISTICS_SETTING_BETWEEN_CLOSE_CLOSE:
                childForStatisticsSettingBetweenCloseClose(detachedCriteria, objects);
                break;
            case HibernateBridgeBridgeDataMaintainService.CHILD_FOR_STATISTICS_SETTING_BETWEEN_CLOSE_OPEN:
                childForStatisticsSettingBetweenCloseOpen(detachedCriteria, objects);
                break;
            case HibernateBridgeBridgeDataMaintainService.CHILD_FOR_STATISTICS_SETTING_BETWEEN_OPEN_CLOSE:
                childForStatisticsSettingBetweenOpenClose(detachedCriteria, objects);
                break;
            case HibernateBridgeBridgeDataMaintainService.CHILD_FOR_STATISTICS_SETTING_BETWEEN_OPEN_OPEN:
                childForStatisticsSettingBetweenOpenOpen(detachedCriteria, objects);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + s);
        }
    }

    private void childForStatisticsSettingBetweenCloseClose(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            Long statisticsSettingLongId = ((LongIdKey) objects[0]).getLongId();
            Date startDate = (Date) objects[1];
            Date endDate = (Date) objects[2];

            detachedCriteria.add(Restrictions.eqOrIsNull("statisticsSettingLongId", statisticsSettingLongId));
            detachedCriteria.add(Restrictions.ge("happenedDate", startDate));
            detachedCriteria.add(Restrictions.le("happenedDate", endDate));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void childForStatisticsSettingBetweenCloseOpen(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            Long statisticsSettingLongId = ((LongIdKey) objects[0]).getLongId();
            Date startDate = (Date) objects[1];
            Date endDate = (Date) objects[2];

            detachedCriteria.add(Restrictions.eqOrIsNull("statisticsSettingLongId", statisticsSettingLongId));
            detachedCriteria.add(Restrictions.ge("happenedDate", startDate));
            detachedCriteria.add(Restrictions.lt("happenedDate", endDate));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void childForStatisticsSettingBetweenOpenClose(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            Long statisticsSettingLongId = ((LongIdKey) objects[0]).getLongId();
            Date startDate = (Date) objects[1];
            Date endDate = (Date) objects[2];

            detachedCriteria.add(Restrictions.eqOrIsNull("statisticsSettingLongId", statisticsSettingLongId));
            detachedCriteria.add(Restrictions.gt("happenedDate", startDate));
            detachedCriteria.add(Restrictions.le("happenedDate", endDate));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void childForStatisticsSettingBetweenOpenOpen(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            Long statisticsSettingLongId = ((LongIdKey) objects[0]).getLongId();
            Date startDate = (Date) objects[1];
            Date endDate = (Date) objects[2];

            detachedCriteria.add(Restrictions.eqOrIsNull("statisticsSettingLongId", statisticsSettingLongId));
            detachedCriteria.add(Restrictions.gt("happenedDate", startDate));
            detachedCriteria.add(Restrictions.lt("happenedDate", endDate));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }
}
