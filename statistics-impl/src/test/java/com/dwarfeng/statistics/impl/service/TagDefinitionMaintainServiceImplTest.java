package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.bean.entity.StatisticsSetting;
import com.dwarfeng.statistics.stack.bean.entity.TagDefinition;
import com.dwarfeng.statistics.stack.bean.key.TagDefinitionKey;
import com.dwarfeng.statistics.stack.service.StatisticsSettingMaintainService;
import com.dwarfeng.statistics.stack.service.TagDefinitionMaintainService;
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
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class TagDefinitionMaintainServiceImplTest {

    private static final Long STATISTICS_SETTING_ID = 12450L;

    @Autowired
    private StatisticsSettingMaintainService statisticsSettingMaintainService;
    @Autowired
    private TagDefinitionMaintainService tagDefinitionMaintainService;

    private StatisticsSetting statisticsSetting;
    private List<TagDefinition> tagDefinitions;

    @Before
    public void setUp() {
        statisticsSetting = new StatisticsSetting(
                new LongIdKey(STATISTICS_SETTING_ID), true, "name", "description", "remark"
        );
        tagDefinitions = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TagDefinition tagDefinition = new TagDefinition(
                    new TagDefinitionKey(STATISTICS_SETTING_ID, "tagDefinitionStringId." + i),
                    "stringValue", "stringValue"
            );
            tagDefinitions.add(tagDefinition);
        }
    }

    @After
    public void tearDown() {
        statisticsSetting = null;
        tagDefinitions.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            statisticsSettingMaintainService.insertOrUpdate(statisticsSetting);
            for (TagDefinition tagDefinition : tagDefinitions) {
                tagDefinitionMaintainService.insertOrUpdate(tagDefinition);
                TagDefinition testTagDefinition = tagDefinitionMaintainService.get(tagDefinition.getKey());
                assertEquals(BeanUtils.describe(tagDefinition), BeanUtils.describe(testTagDefinition));
            }
        } finally {
            for (TagDefinition tagDefinition : tagDefinitions) {
                if (Objects.isNull(tagDefinition.getKey())) {
                    continue;
                }
                tagDefinitionMaintainService.deleteIfExists(tagDefinition.getKey());
            }
            if (Objects.nonNull(statisticsSetting.getKey())) {
                statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());
            }
        }
    }

    @Test
    public void testForStatisticsSettingCascade() throws Exception {
        try {
            statisticsSettingMaintainService.insertOrUpdate(statisticsSetting);
            for (TagDefinition tagDefinition : tagDefinitions) {
                tagDefinitionMaintainService.insertOrUpdate(tagDefinition);
            }

            assertEquals(
                    tagDefinitions.size(),
                    tagDefinitionMaintainService.lookupAsList(
                            TagDefinitionMaintainService.CHILD_FOR_STATISTICS_SETTING,
                            new Object[]{statisticsSetting.getKey()}
                    ).size()
            );

            statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());

            assertEquals(
                    0,
                    tagDefinitionMaintainService.lookupAsList(
                            TagDefinitionMaintainService.CHILD_FOR_STATISTICS_SETTING,
                            new Object[]{statisticsSetting.getKey()}
                    ).size()
            );

            for (TagDefinition tagDefinition : tagDefinitions) {
                assertFalse(tagDefinitionMaintainService.exists(tagDefinition.getKey()));
            }
        } finally {
            for (TagDefinition tagDefinition : tagDefinitions) {
                if (Objects.isNull(tagDefinition.getKey())) {
                    continue;
                }
                tagDefinitionMaintainService.deleteIfExists(tagDefinition.getKey());
            }
            if (Objects.nonNull(statisticsSetting.getKey())) {
                statisticsSettingMaintainService.deleteIfExists(statisticsSetting.getKey());
            }
        }
    }
}
