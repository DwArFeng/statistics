package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.bean.entity.ProviderSupport;
import com.dwarfeng.statistics.stack.service.ProviderSupportMaintainService;
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
public class ProviderSupportMaintainServiceImplTest {

    @Autowired
    private ProviderSupportMaintainService service;

    private final List<ProviderSupport> providerSupports = new ArrayList<>();

    @Before
    public void setUp() {
        for (int i = 0; i < 5; i++) {
            ProviderSupport providerSupport = new ProviderSupport(
                    new StringIdKey("provider-support-" + (i + 1)), "label", "description", "exampleParam"
            );
            providerSupports.add(providerSupport);
        }
    }

    @After
    public void tearDown() {
        providerSupports.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            for (ProviderSupport providerSupport : providerSupports) {
                providerSupport.setKey(service.insert(providerSupport));
                service.update(providerSupport);
                ProviderSupport testProviderSupport = service.get(providerSupport.getKey());
                assertEquals(BeanUtils.describe(providerSupport), BeanUtils.describe(testProviderSupport));
            }
        } finally {
            for (ProviderSupport providerSupport : providerSupports) {
                service.deleteIfExists(providerSupport.getKey());
            }
        }
    }
}
