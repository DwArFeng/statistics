package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.bean.entity.DriverInfo;
import com.dwarfeng.statistics.stack.bean.entity.StatisticsSetting;
import com.dwarfeng.statistics.stack.service.DriverInfoMaintainService;
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
public class DriverInfoMaintainServiceImplTest {

    @Autowired
    private StatisticsSettingMaintainService statisticsSettingMaintainService;
    @Autowired
    private DriverInfoMaintainService driverInfoMaintainService;

    private StatisticsSetting statisticsSetting;
    private List<DriverInfo> driverInfos;

    @Before
    public void setUp() {
        statisticsSetting = new StatisticsSetting(null, true, "name", "remark");
        driverInfos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            DriverInfo driverInfo = new DriverInfo(null, null, true, "type", "param", "remark");
            driverInfos.add(driverInfo);
        }
    }

    @After
    public void tearDown() {
        statisticsSetting = null;
        driverInfos.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            statisticsSetting.setKey(statisticsSettingMaintainService.insertOrUpdate(statisticsSetting));
            for (DriverInfo driverInfo : driverInfos) {
                driverInfo.setStatisticsSettingKey(statisticsSetting.getKey());
                driverInfo.setKey(driverInfoMaintainService.insertOrUpdate(driverInfo));
                DriverInfo testDriverInfo = driverInfoMaintainService.get(driverInfo.getKey());
                assertEquals(BeanUtils.describe(driverInfo), BeanUtils.describe(testDriverInfo));
            }
        } finally {
            for (DriverInfo driverInfo : driverInfos) {
                driverInfoMaintainService.deleteIfExists(driverInfo.getKey());
            }
            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());
        }
    }

    @Test
    public void testForStatisticsSettingCascade() throws Exception {
        try {
            statisticsSetting.setKey(statisticsSettingMaintainService.insertOrUpdate(statisticsSetting));
            for (DriverInfo driverInfo : driverInfos) {
                driverInfo.setStatisticsSettingKey(statisticsSetting.getKey());
                driverInfo.setKey(driverInfoMaintainService.insertOrUpdate(driverInfo));
            }

            assertEquals(
                    driverInfos.size(),
                    driverInfoMaintainService.lookupAsList(
                            DriverInfoMaintainService.CHILD_FOR_STATISTICS_SETTING,
                            new Object[]{statisticsSetting.getKey()}
                    ).size()
            );

            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());

            assertEquals(
                    0,
                    driverInfoMaintainService.lookupAsList(
                            DriverInfoMaintainService.CHILD_FOR_STATISTICS_SETTING,
                            new Object[]{statisticsSetting.getKey()}
                    ).size()
            );

            for (DriverInfo driverInfo : driverInfos) {
                assertFalse(driverInfoMaintainService.exists(driverInfo.getKey()));
            }
        } finally {
            for (DriverInfo driverInfo : driverInfos) {
                driverInfoMaintainService.deleteIfExists(driverInfo.getKey());
            }
            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());
        }
    }
}
