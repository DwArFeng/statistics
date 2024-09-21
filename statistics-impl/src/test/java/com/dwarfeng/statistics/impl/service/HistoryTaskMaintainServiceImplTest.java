package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.bean.entity.HistoryTask;
import com.dwarfeng.statistics.stack.bean.entity.StatisticsSetting;
import com.dwarfeng.statistics.stack.service.HistoryTaskMaintainService;
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
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class HistoryTaskMaintainServiceImplTest {

    @Autowired
    private StatisticsSettingMaintainService statisticsSettingMaintainService;
    @Autowired
    private HistoryTaskMaintainService historyTaskMaintainService;

    private StatisticsSetting statisticsSetting;
    private List<HistoryTask> historyTasks;

    @Before
    public void setUp() {
        statisticsSetting = new StatisticsSetting(null, true, "name", "remark");
        historyTasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            HistoryTask historyTask = new HistoryTask(
                    null, null, 12450, new Date(), new Date(), new Date(), 12450L, new Date(), new Date(), 12450,
                    "frontMessage", "remark"
            );
            historyTasks.add(historyTask);
        }
    }

    @After
    public void tearDown() {
        statisticsSetting = null;
        historyTasks.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            statisticsSetting.setKey(statisticsSettingMaintainService.insertOrUpdate(statisticsSetting));
            for (HistoryTask historyTask : historyTasks) {
                historyTask.setStatisticsSettingKey(statisticsSetting.getKey());
                historyTask.setKey(historyTaskMaintainService.insertOrUpdate(historyTask));
                HistoryTask testHistoryTask = historyTaskMaintainService.get(historyTask.getKey());
                assertEquals(BeanUtils.describe(historyTask), BeanUtils.describe(testHistoryTask));
            }
        } finally {
            for (HistoryTask historyTask : historyTasks) {
                historyTaskMaintainService.deleteIfExists(historyTask.getKey());
            }
            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());
        }
    }

    @Test
    public void testForStatisticsSettingCascade() throws Exception {
        try {
            statisticsSetting.setKey(statisticsSettingMaintainService.insertOrUpdate(statisticsSetting));
            for (HistoryTask historyTask : historyTasks) {
                historyTask.setStatisticsSettingKey(statisticsSetting.getKey());
                historyTask.setKey(historyTaskMaintainService.insertOrUpdate(historyTask));
            }

            assertEquals(
                    historyTasks.size(),
                    historyTaskMaintainService.lookupAsList(
                            HistoryTaskMaintainService.CHILD_FOR_STATISTICS_SETTING,
                            new Object[]{statisticsSetting.getKey()}
                    ).size()
            );

            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());

            assertEquals(
                    0,
                    historyTaskMaintainService.lookupAsList(
                            HistoryTaskMaintainService.CHILD_FOR_STATISTICS_SETTING,
                            new Object[]{statisticsSetting.getKey()}
                    ).size()
            );

            for (HistoryTask historyTask : historyTasks) {
                assertFalse(historyTaskMaintainService.exists(historyTask.getKey()));
            }
        } finally {
            for (HistoryTask historyTask : historyTasks) {
                historyTaskMaintainService.deleteIfExists(historyTask.getKey());
            }
            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());
        }
    }
}
