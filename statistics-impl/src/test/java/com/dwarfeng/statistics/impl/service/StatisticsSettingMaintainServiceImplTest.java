package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.bean.entity.StatisticsSetting;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class StatisticsSettingMaintainServiceImplTest {

    @Autowired
    private StatisticsSettingMaintainService statisticsSettingMaintainService;

    private List<StatisticsSetting> statisticsSettings;

    @Before
    public void setUp() {
        statisticsSettings = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            StatisticsSetting statisticsSetting = new StatisticsSetting(null, true, "name", "remark");
            statisticsSettings.add(statisticsSetting);
        }
    }

    @After
    public void tearDown() {
        statisticsSettings.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            for (StatisticsSetting statisticsSetting : statisticsSettings) {
                statisticsSetting.setKey(statisticsSettingMaintainService.insertOrUpdate(statisticsSetting));
                StatisticsSetting testStatisticsSetting =
                        statisticsSettingMaintainService.get(statisticsSetting.getKey());
                assertEquals(BeanUtils.describe(statisticsSetting), BeanUtils.describe(testStatisticsSetting));
            }
        } finally {
            for (StatisticsSetting statisticsSetting : statisticsSettings) {
                statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());
            }
        }
    }
}
