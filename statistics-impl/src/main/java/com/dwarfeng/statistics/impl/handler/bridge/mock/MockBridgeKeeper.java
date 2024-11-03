package com.dwarfeng.statistics.impl.handler.bridge.mock;

import com.dwarfeng.statistics.impl.handler.bridge.FullKeeper;
import com.dwarfeng.statistics.stack.bean.dto.BridgeData;
import com.dwarfeng.statistics.stack.bean.key.BridgeDataKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 模拟保持器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class MockBridgeKeeper extends FullKeeper {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockBridgeKeeper.class);

    protected final MockBridgeConfig config;
    protected final MockBridgeDataValueGenerator dataValueGenerator;

    public MockBridgeKeeper(
            MockBridgeConfig config,
            MockBridgeDataValueGenerator dataValueGenerator
    ) {
        this.config = config;
        this.dataValueGenerator = dataValueGenerator;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void doUpdate(BridgeData data) {
        long startTimestamp = System.currentTimeMillis();
        long anchorTimestamp = System.currentTimeMillis();

        long updateBeforeDelay = config.getUpdateBeforeDelay();
        long updateDelay = config.getUpdateDelay();
        long updateAfterDelay = config.getUpdateAfterDelay();

        if (updateBeforeDelay > 0) {
            anchorTimestamp += updateBeforeDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        if (updateDelay > 0) {
            anchorTimestamp += updateDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        if (updateAfterDelay > 0) {
            anchorTimestamp += updateAfterDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        long endTimestamp = System.currentTimeMillis();
        LOGGER.info("模拟更新数据, 耗时 {} 毫秒", endTimestamp - startTimestamp);
        LOGGER.debug("数据内容: {}", data);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void doUpdate(List<BridgeData> datas) {
        long startTimestamp = System.currentTimeMillis();
        long anchorTimestamp = System.currentTimeMillis();

        long updateBeforeDelay = config.getUpdateBeforeDelay();
        long updateDelay = config.getUpdateDelay();
        long updateAfterDelay = config.getUpdateAfterDelay();

        if (updateBeforeDelay > 0) {
            anchorTimestamp += updateBeforeDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        if (updateDelay > 0) {
            anchorTimestamp += updateDelay * datas.size();
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        if (updateAfterDelay > 0) {
            anchorTimestamp += updateAfterDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        long endTimestamp = System.currentTimeMillis();
        LOGGER.info("模拟更新数据, 耗时 {} 毫秒", endTimestamp - startTimestamp);
        LOGGER.debug("数据内容: {}", datas);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected BridgeData doLatest(BridgeDataKey bridgeDataKey) throws Exception {
        long startTimestamp = System.currentTimeMillis();
        long anchorTimestamp = System.currentTimeMillis();

        long latestBeforeDelay = config.getLatestBeforeDelay();
        long latestDelay = config.getLatestDelay();
        long latestAfterDelay = config.getLatestAfterDelay();

        if (latestBeforeDelay > 0) {
            anchorTimestamp += latestBeforeDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        Object value = dataValueGenerator.nextValue(bridgeDataKey);
        BridgeData result = new BridgeData(bridgeDataKey, value, new Date());

        if (latestDelay > 0) {
            anchorTimestamp += latestDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        if (latestAfterDelay > 0) {
            anchorTimestamp += latestAfterDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        long endTimestamp = System.currentTimeMillis();
        LOGGER.info("模拟检查数据, 耗时 {} 毫秒", endTimestamp - startTimestamp);
        LOGGER.debug("数据内容: {}", result);

        return result;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<BridgeData> doLatest(List<BridgeDataKey> bridgeDataKeys) throws Exception {
        long startTimestamp = System.currentTimeMillis();
        long anchorTimestamp = System.currentTimeMillis();

        long latestBeforeDelay = config.getLatestBeforeDelay();
        long latestDelay = config.getLatestDelay();
        long latestAfterDelay = config.getLatestAfterDelay();

        if (latestBeforeDelay > 0) {
            anchorTimestamp += latestBeforeDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        List<BridgeData> result = new ArrayList<>();
        for (BridgeDataKey bridgeDataKey : bridgeDataKeys) {
            Object value = dataValueGenerator.nextValue(bridgeDataKey);
            result.add(new BridgeData(bridgeDataKey, value, new Date()));
        }
        if (latestDelay > 0) {
            anchorTimestamp += latestDelay * bridgeDataKeys.size();
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        if (latestAfterDelay > 0) {
            anchorTimestamp += latestAfterDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        long endTimestamp = System.currentTimeMillis();
        LOGGER.info("模拟检查数据, 耗时 {} 毫秒", endTimestamp - startTimestamp);
        LOGGER.debug("数据内容: {}", result);

        return result;
    }

    @Override
    public String toString() {
        return "MockBridgeKeeper{" +
                "config=" + config +
                ", dataValueGenerator=" + dataValueGenerator +
                '}';
    }
}
