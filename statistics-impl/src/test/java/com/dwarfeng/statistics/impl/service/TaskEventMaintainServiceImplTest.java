package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.bean.entity.StatisticsSetting;
import com.dwarfeng.statistics.stack.bean.entity.Task;
import com.dwarfeng.statistics.stack.bean.entity.TaskEvent;
import com.dwarfeng.statistics.stack.service.StatisticsSettingMaintainService;
import com.dwarfeng.statistics.stack.service.TaskEventMaintainService;
import com.dwarfeng.statistics.stack.service.TaskMaintainService;
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
public class TaskEventMaintainServiceImplTest {

    private static final Long STATISTICS_SETTING_ID = 12450L;
    private static final Long TASK_ID = 22450L;

    @Autowired
    private StatisticsSettingMaintainService statisticsSettingMaintainService;
    @Autowired
    private TaskMaintainService taskMaintainService;
    @Autowired
    private TaskEventMaintainService taskEventMaintainService;

    private StatisticsSetting statisticsSetting;
    private Task task;
    private List<TaskEvent> taskEvents;

    @Before
    public void setUp() {
        statisticsSetting = new StatisticsSetting(new LongIdKey(STATISTICS_SETTING_ID), true, "name", "remark");
        task = new Task(
                new LongIdKey(TASK_ID), new LongIdKey(STATISTICS_SETTING_ID), 12450, new Date(), new Date(), new Date(),
                new Date(), 12450, "frontMessage", "remark"
        );
        taskEvents = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TaskEvent taskEvent = new TaskEvent(
                    null, new LongIdKey(TASK_ID), new Date(), "message", "remark"
            );
            taskEvents.add(taskEvent);
        }
    }

    @After
    public void tearDown() {
        statisticsSetting = null;
        task = null;
        taskEvents.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            statisticsSettingMaintainService.insertOrUpdate(statisticsSetting);
            taskMaintainService.insertOrUpdate(task);
            for (TaskEvent taskEvent : taskEvents) {
                taskEvent.setKey(taskEventMaintainService.insertOrUpdate(taskEvent));
                TaskEvent testTaskEvent = taskEventMaintainService.get(taskEvent.getKey());
                assertEquals(BeanUtils.describe(taskEvent), BeanUtils.describe(testTaskEvent));
            }
        } finally {
            for (TaskEvent taskEvent : taskEvents) {
                taskEventMaintainService.deleteIfExists(taskEvent.getKey());
            }
            taskMaintainService.deleteIfExists(task.getKey());
            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());
        }
    }

    @Test
    public void testForTaskCascade() throws Exception {
        try {
            statisticsSettingMaintainService.insertOrUpdate(statisticsSetting);
            taskMaintainService.insertOrUpdate(task);
            for (TaskEvent taskEvent : taskEvents) {
                taskEvent.setKey(taskEventMaintainService.insertOrUpdate(taskEvent));
            }

            assertEquals(
                    taskEvents.size(),
                    taskEventMaintainService.lookupAsList(
                            TaskEventMaintainService.CHILD_FOR_TASK, new Object[]{task.getKey()}
                    ).size()
            );

            taskMaintainService.deleteIfExists(task.getKey());

            assertEquals(
                    0,
                    taskEventMaintainService.lookupAsList(
                            TaskEventMaintainService.CHILD_FOR_TASK, new Object[]{task.getKey()}
                    ).size()
            );

            for (TaskEvent taskEvent : taskEvents) {
                assertFalse(taskEventMaintainService.exists(taskEvent.getKey()));
            }
        } finally {
            for (TaskEvent taskEvent : taskEvents) {
                taskEventMaintainService.deleteIfExists(taskEvent.getKey());
            }
            taskMaintainService.deleteIfExists(task.getKey());
            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());
        }
    }

    @Test
    public void testForStatisticsSettingCascade() throws Exception {
        try {
            statisticsSettingMaintainService.insertOrUpdate(statisticsSetting);
            taskMaintainService.insertOrUpdate(task);
            for (TaskEvent taskEvent : taskEvents) {
                taskEvent.setKey(taskEventMaintainService.insertOrUpdate(taskEvent));
            }

            assertEquals(
                    taskEvents.size(),
                    taskEventMaintainService.lookupAsList(
                            TaskEventMaintainService.CHILD_FOR_STATISTICS_SETTING,
                            new Object[]{statisticsSetting.getKey()}
                    ).size()
            );

            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());

            assertEquals(
                    0,
                    taskEventMaintainService.lookupAsList(
                            TaskEventMaintainService.CHILD_FOR_STATISTICS_SETTING,
                            new Object[]{statisticsSetting.getKey()}
                    ).size()
            );

            for (TaskEvent taskEvent : taskEvents) {
                assertFalse(taskEventMaintainService.exists(taskEvent.getKey()));
            }
        } finally {
            for (TaskEvent taskEvent : taskEvents) {
                taskEventMaintainService.deleteIfExists(taskEvent.getKey());
            }
            taskMaintainService.deleteIfExists(task.getKey());
            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());
        }
    }
}
