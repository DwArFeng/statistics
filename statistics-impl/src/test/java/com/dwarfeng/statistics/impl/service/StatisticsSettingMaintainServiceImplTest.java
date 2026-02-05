package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.bean.entity.StatisticsSetting;
import com.dwarfeng.statistics.stack.service.StatisticsSettingMaintainService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
            StatisticsSetting statisticsSetting = new StatisticsSetting(null, true, "name","description", "remark");
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
                assertNotNull(testStatisticsSetting);
                assertNotNull(testStatisticsSetting.getKey());
                assertEquals(statisticsSetting.getKey().getLongId(), testStatisticsSetting.getKey().getLongId());
                assertEquals(statisticsSetting.isEnabled(), testStatisticsSetting.isEnabled());
                assertEquals(statisticsSetting.getName(), testStatisticsSetting.getName());
                assertEquals(statisticsSetting.getDescription(), testStatisticsSetting.getDescription());
                assertEquals(statisticsSetting.getRemark(), testStatisticsSetting.getRemark());
            }
        } finally {
            for (StatisticsSetting statisticsSetting : statisticsSettings) {
                if (Objects.isNull(statisticsSetting.getKey())) {
                    continue;
                }
                statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());
            }
        }
    }
}
