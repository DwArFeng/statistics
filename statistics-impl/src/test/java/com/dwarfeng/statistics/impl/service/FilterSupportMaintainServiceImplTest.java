package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.bean.entity.FilterSupport;
import com.dwarfeng.statistics.stack.service.FilterSupportMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
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
public class FilterSupportMaintainServiceImplTest {

    @Autowired
    private FilterSupportMaintainService service;

    private final List<FilterSupport> filterSupports = new ArrayList<>();

    @Before
    public void setUp() {
        for (int i = 0; i < 5; i++) {
            FilterSupport filterSupport = new FilterSupport(
                    new StringIdKey("filter-support-" + (i + 1)), "label", "description", "exampleParam"
            );
            filterSupports.add(filterSupport);
        }
    }

    @After
    public void tearDown() {
        filterSupports.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            for (FilterSupport filterSupport : filterSupports) {
                filterSupport.setKey(service.insert(filterSupport));
                service.update(filterSupport);
                FilterSupport testFilterSupport = service.get(filterSupport.getKey());
                assertEquals(BeanUtils.describe(filterSupport), BeanUtils.describe(testFilterSupport));
            }
        } finally {
            for (FilterSupport filterSupport : filterSupports) {
                service.deleteIfExists(filterSupport.getKey());
            }
        }
    }
}
