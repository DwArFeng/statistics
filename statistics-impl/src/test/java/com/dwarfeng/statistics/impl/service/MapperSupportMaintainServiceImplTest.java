package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.bean.entity.MapperSupport;
import com.dwarfeng.statistics.stack.service.MapperSupportMaintainService;
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
public class MapperSupportMaintainServiceImplTest {

    @Autowired
    private MapperSupportMaintainService service;

    private final List<MapperSupport> mapperSupports = new ArrayList<>();

    @Before
    public void setUp() {
        for (int i = 0; i < 5; i++) {
            MapperSupport mapperSupport = new MapperSupport(
                    new StringIdKey("mapper-support-" + (i + 1)), "label", "description", "exampleParam"
            );
            mapperSupports.add(mapperSupport);
        }
    }

    @After
    public void tearDown() {
        mapperSupports.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            for (MapperSupport mapperSupport : mapperSupports) {
                mapperSupport.setKey(service.insert(mapperSupport));
                service.update(mapperSupport);
                MapperSupport testMapperSupport = service.get(mapperSupport.getKey());
                assertEquals(BeanUtils.describe(mapperSupport), BeanUtils.describe(testMapperSupport));
            }
        } finally {
            for (MapperSupport mapperSupport : mapperSupports) {
                service.deleteIfExists(mapperSupport.getKey());
            }
        }
    }
}
