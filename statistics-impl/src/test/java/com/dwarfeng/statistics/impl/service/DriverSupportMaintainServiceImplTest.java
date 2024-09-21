package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.bean.entity.DriverSupport;
import com.dwarfeng.statistics.stack.service.DriverSupportMaintainService;
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
public class DriverSupportMaintainServiceImplTest {

    @Autowired
    private DriverSupportMaintainService service;

    private final List<DriverSupport> driverSupports = new ArrayList<>();

    @Before
    public void setUp() {
        for (int i = 0; i < 5; i++) {
            DriverSupport driverSupport = new DriverSupport(
                    new StringIdKey("driver-support-" + (i + 1)), "label", "description", "exampleParam"
            );
            driverSupports.add(driverSupport);
        }
    }

    @After
    public void tearDown() {
        driverSupports.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            for (DriverSupport driverSupport : driverSupports) {
                driverSupport.setKey(service.insert(driverSupport));
                service.update(driverSupport);
                DriverSupport testDriverSupport = service.get(driverSupport.getKey());
                assertEquals(BeanUtils.describe(driverSupport), BeanUtils.describe(testDriverSupport));
            }
        } finally {
            for (DriverSupport driverSupport : driverSupports) {
                service.deleteIfExists(driverSupport.getKey());
            }
        }
    }
}
