package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.bean.entity.HistoryTask;
import com.dwarfeng.statistics.stack.bean.entity.HistoryTaskEvent;
import com.dwarfeng.statistics.stack.bean.entity.StatisticsSetting;
import com.dwarfeng.statistics.stack.service.HistoryTaskEventMaintainService;
import com.dwarfeng.statistics.stack.service.HistoryTaskMaintainService;
import com.dwarfeng.statistics.stack.service.StatisticsSettingMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
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
public class HistoryTaskEventMaintainServiceImplTest {

    private static final Long STATISTICS_SETTING_ID = 12450L;
    private static final Long HISTORY_TASK_ID = 22450L;

    @Autowired
    private StatisticsSettingMaintainService statisticsSettingMaintainService;
    @Autowired
    private HistoryTaskMaintainService historyTaskMaintainService;
    @Autowired
    private HistoryTaskEventMaintainService historyTaskEventMaintainService;

    private StatisticsSetting statisticsSetting;
    private HistoryTask historyTask;
    private List<HistoryTaskEvent> historyTaskEvents;

    @Before
    public void setUp() {
        statisticsSetting = new StatisticsSetting(new LongIdKey(STATISTICS_SETTING_ID), true, "name", "remark");
        historyTask = new HistoryTask(
                new LongIdKey(HISTORY_TASK_ID), new LongIdKey(STATISTICS_SETTING_ID), 12450, new Date(), new Date(),
                new Date(), 12450L, new Date(), new Date(), 12450, "frontMessage", "remark"
        );
        historyTaskEvents = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            HistoryTaskEvent historyTaskEvent = new HistoryTaskEvent(
                    null, new LongIdKey(HISTORY_TASK_ID), new Date(), "message", "remark"
            );
            historyTaskEvents.add(historyTaskEvent);
        }
    }

    @After
    public void tearDown() {
        statisticsSetting = null;
        historyTask = null;
        historyTaskEvents.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            statisticsSettingMaintainService.insertOrUpdate(statisticsSetting);
            historyTaskMaintainService.insertOrUpdate(historyTask);
            for (HistoryTaskEvent historyTaskEvent : historyTaskEvents) {
                historyTaskEvent.setKey(historyTaskEventMaintainService.insertOrUpdate(historyTaskEvent));
                HistoryTaskEvent testHistoryTaskEvent = historyTaskEventMaintainService.get(historyTaskEvent.getKey());
                assertEquals(BeanUtils.describe(historyTaskEvent), BeanUtils.describe(testHistoryTaskEvent));
            }
        } finally {
            for (HistoryTaskEvent historyTaskEvent : historyTaskEvents) {
                historyTaskEventMaintainService.deleteIfExists(historyTaskEvent.getKey());
            }
            historyTaskMaintainService.deleteIfExists(historyTask.getKey());
            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());
        }
    }

    @Test
    public void testForHistoryTaskCascade() throws Exception {
        try {
            statisticsSettingMaintainService.insertOrUpdate(statisticsSetting);
            historyTaskMaintainService.insertOrUpdate(historyTask);
            for (HistoryTaskEvent historyTaskEvent : historyTaskEvents) {
                historyTaskEvent.setKey(historyTaskEventMaintainService.insertOrUpdate(historyTaskEvent));
            }

            assertEquals(
                    historyTaskEvents.size(),
                    historyTaskEventMaintainService.lookupAsList(
                            HistoryTaskEventMaintainService.CHILD_FOR_HISTORY_TASK, new Object[]{historyTask.getKey()}
                    ).size()
            );

            historyTaskMaintainService.deleteIfExists(historyTask.getKey());

            assertEquals(
                    0,
                    historyTaskEventMaintainService.lookupAsList(
                            HistoryTaskEventMaintainService.CHILD_FOR_HISTORY_TASK, new Object[]{historyTask.getKey()}
                    ).size()
            );

            for (HistoryTaskEvent historyTaskEvent : historyTaskEvents) {
                assertFalse(historyTaskEventMaintainService.exists(historyTaskEvent.getKey()));
            }
        } finally {
            for (HistoryTaskEvent historyTaskEvent : historyTaskEvents) {
                historyTaskEventMaintainService.deleteIfExists(historyTaskEvent.getKey());
            }
            historyTaskMaintainService.deleteIfExists(historyTask.getKey());
            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());
        }
    }

    @Test
    public void testForStatisticsSettingCascade() throws Exception {
        try {
            statisticsSettingMaintainService.insertOrUpdate(statisticsSetting);
            historyTaskMaintainService.insertOrUpdate(historyTask);
            for (HistoryTaskEvent historyTaskEvent : historyTaskEvents) {
                historyTaskEvent.setKey(historyTaskEventMaintainService.insertOrUpdate(historyTaskEvent));
            }

            assertEquals(
                    historyTaskEvents.size(),
                    historyTaskEventMaintainService.lookupAsList(
                            HistoryTaskEventMaintainService.CHILD_FOR_STATISTICS_SETTING,
                            new Object[]{statisticsSetting.getKey()}
                    ).size()
            );

            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());

            assertEquals(
                    0,
                    historyTaskEventMaintainService.lookupAsList(
                            HistoryTaskEventMaintainService.CHILD_FOR_STATISTICS_SETTING,
                            new Object[]{statisticsSetting.getKey()}
                    ).size()
            );

            for (HistoryTaskEvent historyTaskEvent : historyTaskEvents) {
                assertFalse(historyTaskEventMaintainService.exists(historyTaskEvent.getKey()));
            }
        } finally {
            for (HistoryTaskEvent historyTaskEvent : historyTaskEvents) {
                historyTaskEventMaintainService.deleteIfExists(historyTaskEvent.getKey());
            }
            historyTaskMaintainService.deleteIfExists(historyTask.getKey());
            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());
        }
    }
}
