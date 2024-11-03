package com.dwarfeng.statistics.impl.handler.provider.mock;

import com.dwarfeng.statistics.impl.handler.provider.AbstractExecutor;
import com.dwarfeng.statistics.sdk.util.Constants;
import com.dwarfeng.statistics.stack.bean.dto.*;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 模拟执行器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("mockProviderRegistry.mockExecutor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MockExecutor extends AbstractExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockExecutor.class);

    private final Config config;

    private MockProviderRandomGenerator randomGenerator;

    private Map<String, Method> methodMap;

    public MockExecutor(Config config) {
        this.config = config;
    }

    @Override
    public void start() {
        // 展开参数。
        Long randomSeed = config.getRandomSeed();
        // 初始化 randomGenerator。
        Random random;
        if (Objects.isNull(randomSeed)) {
            random = new Random();
        } else {
            random = new Random(randomSeed);
        }
        randomGenerator = new MockProviderRandomGenerator(random);
        // 初始化 methodMap。
        methodMap = new HashMap<>();
        // 扫描 MockBridgeRandomGenerator 的所有带有 RequiredStatisticsSettingType 注解的方法，将其放入 methodMap。
        for (Method method : MockProviderRandomGenerator.class.getMethods()) {
            if (!method.isAnnotationPresent(RequiredBridgeDataValueType.class)) {
                continue;
            }
            RequiredBridgeDataValueType requiredBridgeDataValueType = method.getAnnotation(
                    RequiredBridgeDataValueType.class
            );
            methodMap.put(requiredBridgeDataValueType.value(), method);
        }
    }

    @Override
    public void stop() throws Exception {
        // 展开参数。
        String lastProvidedDateVariableId = config.getLastProvidedDateVariableId();
        // 更新最后提供数据的日期变量。
        LongIdKey statisticsSettingKey = context.getStatisticsSettingKey();
        Date currentDate = new Date();
        VariableUpsertInfo variableUpsertInfo = new VariableUpsertInfo(
                statisticsSettingKey, lastProvidedDateVariableId, Constants.VARIABLE_VALUE_TYPE_DATE, currentDate
        );
        context.upsertVariable(variableUpsertInfo);
        // 销毁 randomGenerator。
        randomGenerator = null;
    }

    @Override
    public List<ProviderData> provide() throws Exception {
        // 展开参数。
        String lastProvidedDateVariableId = config.getLastProvidedDateVariableId();
        int dataSize = config.getDataSize();
        String dataType = config.getDataType();
        String tag = config.getTag();
        // 查看最后提供数据的日期变量。
        LongIdKey statisticsSettingKey = context.getStatisticsSettingKey();
        VariableInspectInfo variableInspectInfo = new VariableInspectInfo(
                statisticsSettingKey, lastProvidedDateVariableId
        );
        VariableInspectResult variableInspectResult = context.inspectVariable(variableInspectInfo);
        // 如果最后提供数据的日期变量不存在，本次调用不提供任何数据。
        if (Objects.isNull(variableInspectResult)) {
            // 记录日志。
            LOGGER.info("最后提供数据的日期变量不存在, 本次调用不提供任何数据。");
            // 记录任务事件。
            LongIdKey taskKey = context.getTaskKey();
            TaskEventCreateInfo taskEventCreateInfo = new TaskEventCreateInfo(
                    taskKey, "最后提供数据的日期变量不存在, 本次调用不提供任何数据。"
            );
            context.createTaskEvent(taskEventCreateInfo);
            // 返回空列表。
            return Collections.emptyList();
        }
        // 获取当前系统时间戳。
        long currentTimestamp = System.currentTimeMillis();
        // 获取最后提供数据的日期对应的时间戳。
        long lastProvidedTimestamp = ((Date) variableInspectResult.getValue()).getTime();
        // 计算每个数据点的时间间隔（双精度浮点）。
        double interval = (double) (currentTimestamp - lastProvidedTimestamp) / (dataSize + 1);
        // 生成指令数量的数据。
        List<ProviderData> datas = new ArrayList<>(dataSize);
        for (int i = 0; i < dataSize; i++) {
            // 计算当前数据点的时间戳。
            long timestamp = (long) (lastProvidedTimestamp + interval * (i + 1));
            // 生成数据。
            Object value = nextValue(dataType);
            // 构造数据。
            ProviderData data = new ProviderData(tag, value, new Date(timestamp));
            // 添加数据。
            datas.add(data);
        }
        // 返回数据。
        return datas;
    }

    public Object nextValue(String dataType) throws Exception {
        Method method = methodMap.get(dataType);
        if (method == null) {
            throw new HandlerException("未知的数据类型: " + dataType);
        }
        return method.invoke(randomGenerator);
    }
}
