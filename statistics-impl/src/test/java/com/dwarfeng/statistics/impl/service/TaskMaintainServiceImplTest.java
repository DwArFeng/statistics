package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.bean.entity.StatisticsSetting;
import com.dwarfeng.statistics.stack.bean.entity.Task;
import com.dwarfeng.statistics.stack.service.StatisticsSettingMaintainService;
import com.dwarfeng.statistics.stack.service.TaskMaintainService;
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
public class TaskMaintainServiceImplTest {

    @Autowired
    private StatisticsSettingMaintainService statisticsSettingMaintainService;
    @Autowired
    private TaskMaintainService taskMaintainService;

    private StatisticsSetting statisticsSetting;
    private List<Task> tasks;

    @Before
    public void setUp() {
        statisticsSetting = new StatisticsSetting(null, true, "name", "remark");
        tasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Task task = new Task(
                    null, null, 12450, new Date(), new Date(), new Date(), new Date(), 12450, "frontMessage", "remark"
            );
            tasks.add(task);
        }
    }

    @After
    public void tearDown() {
        statisticsSetting = null;
        tasks.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            statisticsSetting.setKey(statisticsSettingMaintainService.insertOrUpdate(statisticsSetting));
            for (Task task : tasks) {
                task.setStatisticsSettingKey(statisticsSetting.getKey());
                task.setKey(taskMaintainService.insertOrUpdate(task));
                Task testTask = taskMaintainService.get(task.getKey());
                assertEquals(BeanUtils.describe(task), BeanUtils.describe(testTask));
            }
        } finally {
            for (Task task : tasks) {
                taskMaintainService.deleteIfExists(task.getKey());
            }
            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());
        }
    }

    @Test
    public void testForStatisticsSettingCascade() throws Exception {
        try {
            statisticsSetting.setKey(statisticsSettingMaintainService.insertOrUpdate(statisticsSetting));
            for (Task task : tasks) {
                task.setStatisticsSettingKey(statisticsSetting.getKey());
                task.setKey(taskMaintainService.insertOrUpdate(task));
            }

            assertEquals(
                    tasks.size(),
                    taskMaintainService.lookupAsList(
                            TaskMaintainService.CHILD_FOR_STATISTICS_SETTING,
                            new Object[]{statisticsSetting.getKey()}
                    ).size()
            );

            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());

            assertEquals(
                    0,
                    taskMaintainService.lookupAsList(
                            TaskMaintainService.CHILD_FOR_STATISTICS_SETTING,
                            new Object[]{statisticsSetting.getKey()}
                    ).size()
            );

            for (Task task : tasks) {
                assertFalse(taskMaintainService.exists(task.getKey()));
            }
        } finally {
            for (Task task : tasks) {
                taskMaintainService.deleteIfExists(task.getKey());
            }
            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());
        }
    }
}
