package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.bean.entity.Variable;
import com.dwarfeng.statistics.stack.bean.entity.StatisticsSetting;
import com.dwarfeng.statistics.stack.bean.key.VariableKey;
import com.dwarfeng.statistics.stack.service.VariableMaintainService;
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
public class VariableMaintainServiceImplTest {

    private static final Long STATISTICS_SETTING_ID = 12450L;

    @Autowired
    private StatisticsSettingMaintainService statisticsSettingMaintainService;
    @Autowired
    private VariableMaintainService variableMaintainService;

    private StatisticsSetting statisticsSetting;
    private List<Variable> variables;

    @Before
    public void setUp() {
        statisticsSetting = new StatisticsSetting(new LongIdKey(STATISTICS_SETTING_ID), true, "name", "remark");
        variables = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Variable variable = new Variable(
                    new VariableKey(STATISTICS_SETTING_ID, "variableStringId." + i), 12450, "stringValue", 12450L,
                    12.450, true, new Date()
            );
            variables.add(variable);
        }
    }

    @After
    public void tearDown() {
        statisticsSetting = null;
        variables.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            statisticsSettingMaintainService.insertOrUpdate(statisticsSetting);
            for (Variable variable : variables) {
                variableMaintainService.insertOrUpdate(variable);
                Variable testVariable = variableMaintainService.get(variable.getKey());
                assertEquals(BeanUtils.describe(variable), BeanUtils.describe(testVariable));
            }
        } finally {
            for (Variable variable : variables) {
                variableMaintainService.deleteIfExists(variable.getKey());
            }
            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());
        }
    }

    @Test
    public void testForStatisticsSettingCascade() throws Exception {
        try {
            statisticsSettingMaintainService.insertOrUpdate(statisticsSetting);
            for (Variable variable : variables) {
                variableMaintainService.insertOrUpdate(variable);
            }

            assertEquals(
                    variables.size(),
                    variableMaintainService.lookupAsList(
                            VariableMaintainService.CHILD_FOR_STATISTICS_SETTING,
                            new Object[]{statisticsSetting.getKey()}
                    ).size()
            );

            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());

            assertEquals(
                    0,
                    variableMaintainService.lookupAsList(
                            VariableMaintainService.CHILD_FOR_STATISTICS_SETTING,
                            new Object[]{statisticsSetting.getKey()}
                    ).size()
            );

            for (Variable variable : variables) {
                assertFalse(variableMaintainService.exists(variable.getKey()));
            }
        } finally {
            for (Variable variable : variables) {
                variableMaintainService.deleteIfExists(variable.getKey());
            }
            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());
        }
    }
}
