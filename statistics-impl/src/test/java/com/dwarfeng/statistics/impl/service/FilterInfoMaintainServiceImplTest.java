package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.bean.entity.FilterInfo;
import com.dwarfeng.statistics.stack.bean.entity.StatisticsSetting;
import com.dwarfeng.statistics.stack.service.FilterInfoMaintainService;
import com.dwarfeng.statistics.stack.service.StatisticsSettingMaintainService;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class FilterInfoMaintainServiceImplTest {

    @Autowired
    private StatisticsSettingMaintainService statisticsSettingMaintainService;
    @Autowired
    private FilterInfoMaintainService filterInfoMaintainService;

    private StatisticsSetting statisticsSetting;
    private List<FilterInfo> filterInfos;

    @Before
    public void setUp() {
        statisticsSetting = new StatisticsSetting(null, true, "name", "remark");
        filterInfos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            FilterInfo filterInfo = new FilterInfo(null, null, true, "type", "param", "remark", 12450);
            filterInfos.add(filterInfo);
        }
    }

    @After
    public void tearDown() {
        statisticsSetting = null;
        filterInfos.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            statisticsSetting.setKey(statisticsSettingMaintainService.insertOrUpdate(statisticsSetting));
            for (FilterInfo filterInfo : filterInfos) {
                filterInfo.setStatisticsSettingKey(statisticsSetting.getKey());
                filterInfo.setKey(filterInfoMaintainService.insertOrUpdate(filterInfo));
                FilterInfo testFilterInfo = filterInfoMaintainService.get(filterInfo.getKey());
                assertEquals(BeanUtils.describe(filterInfo), BeanUtils.describe(testFilterInfo));
            }
        } finally {
            for (FilterInfo filterInfo : filterInfos) {
                filterInfoMaintainService.deleteIfExists(filterInfo.getKey());
            }
            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());
        }
    }

    @Test
    public void testForStatisticsSettingCascade() throws Exception {
        try {
            statisticsSetting.setKey(statisticsSettingMaintainService.insertOrUpdate(statisticsSetting));
            for (FilterInfo filterInfo : filterInfos) {
                filterInfo.setStatisticsSettingKey(statisticsSetting.getKey());
                filterInfo.setKey(filterInfoMaintainService.insertOrUpdate(filterInfo));
            }

            assertEquals(
                    filterInfos.size(),
                    filterInfoMaintainService.lookupAsList(
                            FilterInfoMaintainService.CHILD_FOR_STATISTICS_SETTING,
                            new Object[]{statisticsSetting.getKey()}
                    ).size()
            );

            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());

            assertEquals(
                    0,
                    filterInfoMaintainService.lookupAsList(
                            FilterInfoMaintainService.CHILD_FOR_STATISTICS_SETTING,
                            new Object[]{statisticsSetting.getKey()}
                    ).size()
            );

            for (FilterInfo filterInfo : filterInfos) {
                assertFalse(filterInfoMaintainService.exists(filterInfo.getKey()));
            }
        } finally {
            for (FilterInfo filterInfo : filterInfos) {
                filterInfoMaintainService.deleteIfExists(filterInfo.getKey());
            }
            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());
        }
    }
}
