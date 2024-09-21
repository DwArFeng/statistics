package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.bean.entity.StatisticsExecutionProfile;
import com.dwarfeng.statistics.stack.bean.entity.StatisticsSetting;
import com.dwarfeng.statistics.stack.service.StatisticsExecutionProfileMaintainService;
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

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class StatisticsExecutionProfileMaintainServiceImplTest {

    private static final Long STATISTICS_SETTING_ID = 12450L;

    @Autowired
    private StatisticsSettingMaintainService statisticsSettingMaintainService;
    @Autowired
    private StatisticsExecutionProfileMaintainService statisticsExecutionProfileMaintainService;

    private StatisticsSetting statisticsSetting;
    private StatisticsExecutionProfile statisticsExecutionProfile;

    @Before
    public void setUp() {
        statisticsSetting = new StatisticsSetting(new LongIdKey(STATISTICS_SETTING_ID), true, "name", "remark");
        statisticsExecutionProfile = new StatisticsExecutionProfile(
                new LongIdKey(STATISTICS_SETTING_ID), 12450, 12450, 12450, 12450, new Date(), new Date(), new Date()
        );
    }

    @After
    public void tearDown() {
        statisticsSetting = null;
        statisticsExecutionProfile = null;
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            statisticsSettingMaintainService.insertOrUpdate(statisticsSetting);
            statisticsExecutionProfileMaintainService.insertOrUpdate(statisticsExecutionProfile);
            StatisticsExecutionProfile testStatisticsExecutionProfile =
                    statisticsExecutionProfileMaintainService.get(statisticsExecutionProfile.getKey());
            assertEquals(
                    BeanUtils.describe(statisticsExecutionProfile), BeanUtils.describe(testStatisticsExecutionProfile)
            );
        } finally {
            statisticsExecutionProfileMaintainService.deleteIfExists(statisticsExecutionProfile.getKey());
            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());
        }
    }

    @Test
    public void testForXxxCascade() throws Exception {
        try {
            statisticsSettingMaintainService.insertOrUpdate(statisticsSetting);
            statisticsExecutionProfileMaintainService.insertOrUpdate(statisticsExecutionProfile);

            assertTrue(statisticsExecutionProfileMaintainService.exists(statisticsExecutionProfile.getKey()));

            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());

            assertFalse(statisticsExecutionProfileMaintainService.exists(statisticsExecutionProfile.getKey()));
        } finally {
            statisticsExecutionProfileMaintainService.deleteIfExists(statisticsExecutionProfile.getKey());
            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());
        }
    }
}
