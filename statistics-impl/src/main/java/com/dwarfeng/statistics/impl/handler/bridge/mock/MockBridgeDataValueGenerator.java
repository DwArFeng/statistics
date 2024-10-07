package com.dwarfeng.statistics.impl.handler.bridge.mock;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 模拟桥接数据值生成器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class MockBridgeDataValueGenerator {

    private final MockBridgeRandomGenerator randomGenerator;

    @Value("${bridge.mock.data_config}")
    private String dataConfig;

    private Map<LongIdKey, MockBridgeDataConfigItem> dataConfigItemMap = null;
    private Map<String, Method> methodMap = null;

    public MockBridgeDataValueGenerator(MockBridgeRandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    @PostConstruct
    public void init() {
        // 将 dataConfig 转换为 RealtimeMockSourceDataConfigItem 的列表。
        List<MockBridgeDataConfigItem> dataConfigItems = JSON.parseArray(dataConfig, MockBridgeDataConfigItem.class);
        dataConfigItemMap = dataConfigItems.stream().collect(
                Collectors.toMap((item) -> new LongIdKey(item.getStatisticsSettingId()), Function.identity())
        );
        // 构造 methodMap。
        methodMap = new HashMap<>();
        // 扫描 MockBridgeRandomGenerator 的所有带有 RequiredStatisticsSettingType 注解的方法，将其放入 methodMap。
        for (Method method : MockBridgeRandomGenerator.class.getMethods()) {
            if (!method.isAnnotationPresent(RequiredBridgeDataValueType.class)) {
                continue;
            }
            RequiredBridgeDataValueType requiredBridgeDataValueType = method.getAnnotation(
                    RequiredBridgeDataValueType.class
            );
            methodMap.put(requiredBridgeDataValueType.value(), method);
        }
    }

    public Object nextValue(LongIdKey statisticsSettingKey) throws Exception {
        if (!dataConfigItemMap.containsKey(statisticsSettingKey)) {
            throw new HandlerException("未知的统计设置键: " + statisticsSettingKey);
        }
        MockBridgeDataConfigItem dataConfigItem = dataConfigItemMap.get(statisticsSettingKey);
        Method method = methodMap.get(dataConfigItem.getStatisticsSettingType());
        if (method == null) {
            throw new HandlerException("未知的统计设置键类型: " + dataConfigItem.getStatisticsSettingType());
        }
        return method.invoke(randomGenerator);
    }
}
