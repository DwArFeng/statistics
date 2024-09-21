package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.bean.entity.ProviderInfo;
import com.dwarfeng.statistics.stack.bean.entity.StatisticsSetting;
import com.dwarfeng.statistics.stack.service.ProviderInfoMaintainService;
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
public class ProviderInfoMaintainServiceImplTest {

    @Autowired
    private StatisticsSettingMaintainService statisticsSettingMaintainService;
    @Autowired
    private ProviderInfoMaintainService providerInfoMaintainService;

    private StatisticsSetting statisticsSetting;
    private List<ProviderInfo> providerInfos;

    @Before
    public void setUp() {
        statisticsSetting = new StatisticsSetting(null, true, "name", "remark");
        providerInfos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ProviderInfo providerInfo = new ProviderInfo(null, null, true, "type", "param", "remark");
            providerInfos.add(providerInfo);
        }
    }

    @After
    public void tearDown() {
        statisticsSetting = null;
        providerInfos.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            statisticsSetting.setKey(statisticsSettingMaintainService.insertOrUpdate(statisticsSetting));
            for (ProviderInfo providerInfo : providerInfos) {
                providerInfo.setStatisticsSettingKey(statisticsSetting.getKey());
                providerInfo.setKey(providerInfoMaintainService.insertOrUpdate(providerInfo));
                ProviderInfo testProviderInfo = providerInfoMaintainService.get(providerInfo.getKey());
                assertEquals(BeanUtils.describe(providerInfo), BeanUtils.describe(testProviderInfo));
            }
        } finally {
            for (ProviderInfo providerInfo : providerInfos) {
                providerInfoMaintainService.deleteIfExists(providerInfo.getKey());
            }
            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());
        }
    }

    @Test
    public void testForStatisticsSettingCascade() throws Exception {
        try {
            statisticsSetting.setKey(statisticsSettingMaintainService.insertOrUpdate(statisticsSetting));
            for (ProviderInfo providerInfo : providerInfos) {
                providerInfo.setStatisticsSettingKey(statisticsSetting.getKey());
                providerInfo.setKey(providerInfoMaintainService.insertOrUpdate(providerInfo));
            }

            assertEquals(
                    providerInfos.size(),
                    providerInfoMaintainService.lookupAsList(
                            ProviderInfoMaintainService.CHILD_FOR_STATISTICS_SETTING,
                            new Object[]{statisticsSetting.getKey()}
                    ).size()
            );

            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());

            assertEquals(
                    0,
                    providerInfoMaintainService.lookupAsList(
                            ProviderInfoMaintainService.CHILD_FOR_STATISTICS_SETTING,
                            new Object[]{statisticsSetting.getKey()}
                    ).size()
            );

            for (ProviderInfo providerInfo : providerInfos) {
                assertFalse(providerInfoMaintainService.exists(providerInfo.getKey()));
            }
        } finally {
            for (ProviderInfo providerInfo : providerInfos) {
                providerInfoMaintainService.deleteIfExists(providerInfo.getKey());
            }
            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());
        }
    }
}
