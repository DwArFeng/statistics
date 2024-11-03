package com.dwarfeng.statistics.impl.handler.bridge.hibernate.dao.preset;

import com.dwarfeng.statistics.impl.handler.bridge.hibernate.service.HibernateBridgeBridgeDataMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
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
            case HibernateBridgeBridgeDataMaintainService.LOOKUP_DEFAULT_CLOSE_CLOSE:
                lookupDefaultCloseClose(detachedCriteria, objects);
                break;
            case HibernateBridgeBridgeDataMaintainService.LOOKUP_DEFAULT_CLOSE_OPEN:
                lookupDefaultCloseOpen(detachedCriteria, objects);
                break;
            case HibernateBridgeBridgeDataMaintainService.LOOKUP_DEFAULT_OPEN_CLOSE:
                lookupDefaultOpenClose(detachedCriteria, objects);
                break;
            case HibernateBridgeBridgeDataMaintainService.LOOKUP_DEFAULT_OPEN_OPEN:
                lookupDefaultOpenOpen(detachedCriteria, objects);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + s);
        }
    }

    private void lookupDefaultCloseClose(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            long statisticsSettingLongId = (long) objects[0];
            String tag = (String) objects[1];
            Date startDate = (Date) objects[2];
            Date endDate = (Date) objects[3];

            detachedCriteria.add(Restrictions.eq("statisticsSettingLongId", statisticsSettingLongId));
            detachedCriteria.add(Restrictions.eq("tag", tag));
            detachedCriteria.add(Restrictions.ge("happenedDate", startDate));
            detachedCriteria.add(Restrictions.le("happenedDate", endDate));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void lookupDefaultCloseOpen(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            long statisticsSettingLongId = (long) objects[0];
            String tag = (String) objects[1];
            Date startDate = (Date) objects[2];
            Date endDate = (Date) objects[3];

            detachedCriteria.add(Restrictions.eq("statisticsSettingLongId", statisticsSettingLongId));
            detachedCriteria.add(Restrictions.eq("tag", tag));
            detachedCriteria.add(Restrictions.ge("happenedDate", startDate));
            detachedCriteria.add(Restrictions.lt("happenedDate", endDate));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void lookupDefaultOpenClose(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            long statisticsSettingLongId = (long) objects[0];
            String tag = (String) objects[1];
            Date startDate = (Date) objects[2];
            Date endDate = (Date) objects[3];

            detachedCriteria.add(Restrictions.eq("statisticsSettingLongId", statisticsSettingLongId));
            detachedCriteria.add(Restrictions.eq("tag", tag));
            detachedCriteria.add(Restrictions.gt("happenedDate", startDate));
            detachedCriteria.add(Restrictions.le("happenedDate", endDate));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void lookupDefaultOpenOpen(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            long statisticsSettingLongId = (long) objects[0];
            String tag = (String) objects[1];
            Date startDate = (Date) objects[2];
            Date endDate = (Date) objects[3];

            detachedCriteria.add(Restrictions.eq("statisticsSettingLongId", statisticsSettingLongId));
            detachedCriteria.add(Restrictions.eq("tag", tag));
            detachedCriteria.add(Restrictions.gt("happenedDate", startDate));
            detachedCriteria.add(Restrictions.lt("happenedDate", endDate));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }
}
