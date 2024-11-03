package com.dwarfeng.statistics.impl.handler.bridge.mock;

import com.dwarfeng.statistics.impl.handler.bridge.FullPersister;
import com.dwarfeng.statistics.sdk.util.ViewUtil;
import com.dwarfeng.statistics.stack.bean.dto.*;
import com.dwarfeng.statistics.stack.bean.key.BridgeDataKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 模拟的持久器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class MockBridgePersister extends FullPersister {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockBridgePersister.class);

    public static final String LOOKUP_PRESET_DEFAULT = "default";
    public static final String NATIVE_QUERY_PRESET_DEFAULT = "default";

    private final MockBridgeConfig config;
    private final MockBridgeDataValueGenerator dataValueGenerator;

    public MockBridgePersister(MockBridgeConfig config, MockBridgeDataValueGenerator dataValueGenerator) {
        this.config = config;
        this.dataValueGenerator = dataValueGenerator;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void doRecord(BridgeData data) {
        long startTimestamp = System.currentTimeMillis();
        long anchorTimestamp = System.currentTimeMillis();

        long recordBeforeDelay = config.getRecordBeforeDelay();
        long recordDelay = config.getRecordDelay();
        long recordAfterDelay = config.getRecordAfterDelay();

        if (recordBeforeDelay > 0) {
            anchorTimestamp += recordBeforeDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        if (recordDelay > 0) {
            anchorTimestamp += recordDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        if (recordAfterDelay > 0) {
            anchorTimestamp += recordAfterDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        long endTimestamp = System.currentTimeMillis();
        LOGGER.info("模拟记录数据, 耗时 {} 毫秒", endTimestamp - startTimestamp);
        LOGGER.debug("数据内容: {}", data);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void doRecord(List<BridgeData> datas) {
        long startTimestamp = System.currentTimeMillis();
        long anchorTimestamp = System.currentTimeMillis();

        long recordBeforeDelay = config.getRecordBeforeDelay();
        long recordDelay = config.getRecordDelay();
        long recordAfterDelay = config.getRecordAfterDelay();

        if (recordBeforeDelay > 0) {
            anchorTimestamp += recordBeforeDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        if (recordDelay > 0) {
            anchorTimestamp += recordDelay * datas.size();
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        if (recordAfterDelay > 0) {
            anchorTimestamp += recordAfterDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        long endTimestamp = System.currentTimeMillis();
        LOGGER.info("模拟记录数据, 耗时 {} 毫秒", endTimestamp - startTimestamp);
        LOGGER.debug("数据内容: {}", datas);
    }

    @Override
    protected LookupResult doLookup(LookupInfo lookupInfo) throws Exception {
        return doSingleLookup(lookupInfo);
    }

    @Override
    protected List<LookupResult> doLookup(List<LookupInfo> lookupInfos) throws Exception {
        List<LookupResult> result = new ArrayList<>();
        for (LookupInfo lookupInfo : lookupInfos) {
            result.add(doSingleLookup(lookupInfo));
        }
        return result;
    }

    private LookupResult doSingleLookup(LookupInfo lookupInfo) throws Exception {
        // 展开查询信息。
        long lookupStartTimestamp = ViewUtil.validStartDate(lookupInfo.getStartDate()).getTime();
        long lookupEndTimestamp = ViewUtil.validEndDate(lookupInfo.getEndDate()).getTime();
        int page = ViewUtil.validPage(lookupInfo.getPage());
        int rows = ViewUtil.validRows(lookupInfo.getRows());

        // 检查预设是否合法。
        String preset = lookupInfo.getPreset();
        if (!LOOKUP_PRESET_DEFAULT.equals(preset)) {
            throw new IllegalArgumentException("预设不合法");
        }

        return mockLookup(
                lookupInfo.getBridgeDataKey(),
                lookupStartTimestamp, lookupEndTimestamp,
                lookupInfo.isIncludeStartDate(), lookupInfo.isIncludeEndDate(),
                page, rows
        );
    }

    private LookupResult mockLookup(
            BridgeDataKey bridgeDataKey, long lookupStartTimestamp, long lookupEndTimestamp,
            boolean includeStartDate, boolean includeEndDate, int page, int rows
    ) throws Exception {
        long startTimestamp = System.currentTimeMillis();
        long anchorTimestamp = System.currentTimeMillis();

        long lookupBeforeDelay = config.getLookupBeforeDelay();
        long lookupOffsetDelay = config.getLookupOffsetDelay();
        long lookupDelay = config.getLookupDelay();
        long lookupAfterDelay = config.getLookupAfterDelay();

        if (lookupBeforeDelay > 0) {
            anchorTimestamp += lookupBeforeDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        long lookupDataInterval = config.getLookupDataInterval();
        // 计算数据的起始时间，对齐到 lookupDataInterval 的整数倍。
        long dataStartTimestamp = lookupStartTimestamp - lookupStartTimestamp % lookupDataInterval;
        if (dataStartTimestamp == lookupStartTimestamp && !includeStartDate) {
            dataStartTimestamp += lookupDataInterval;
        }
        // 计算数据的数量。
        int dataCount = (int) ((lookupEndTimestamp - dataStartTimestamp) / lookupDataInterval);
        if (dataStartTimestamp + dataCount * lookupDataInterval == lookupEndTimestamp && !includeEndDate) {
            dataCount--;
        }
        // 计算实际偏移量。
        int actualOffset = Math.min(page * rows, dataCount);
        // 计算返回数据量。
        int actualLimit = Math.min(rows, dataCount - actualOffset);
        // 计算总页数。
        int totalPage = (dataCount + rows - 1) / rows;
        // 根据发生日期的顺序生成返回的数据。
        List<BridgeData> datas = new ArrayList<>(actualLimit);
        for (int i = 0; i < actualLimit; i++) {
            Object value = dataValueGenerator.nextValue(bridgeDataKey);
            datas.add(new BridgeData(
                    bridgeDataKey,
                    value,
                    new Date(dataStartTimestamp + (actualOffset + i) * lookupDataInterval)
            ));
        }
        LookupResult lookupResult = new LookupResult(bridgeDataKey, datas, page, totalPage, rows, dataCount);

        if (lookupOffsetDelay > 0) {
            anchorTimestamp += lookupOffsetDelay * actualOffset;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }
        if (lookupDelay > 0) {
            anchorTimestamp += lookupDelay * actualLimit;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        if (lookupAfterDelay > 0) {
            anchorTimestamp += lookupAfterDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        long endTimestamp = System.currentTimeMillis();
        LOGGER.info("模拟查询数据, 耗时 {} 毫秒", endTimestamp - startTimestamp);
        LOGGER.debug("查询结果: {}", lookupResult);

        return lookupResult;
    }

    @Override
    protected QueryResult doNativeQuery(NativeQueryInfo queryInfo) throws Exception {
        return doSingleNativeQuery(queryInfo);
    }

    @Override
    protected List<QueryResult> doNativeQuery(List<NativeQueryInfo> queryInfos) throws Exception {
        List<QueryResult> result = new ArrayList<>();
        for (NativeQueryInfo queryInfo : queryInfos) {
            result.add(doSingleNativeQuery(queryInfo));
        }
        return result;
    }

    private QueryResult doSingleNativeQuery(NativeQueryInfo queryInfo) throws Exception {
        // 展开查询信息。
        long queryStartTimestamp = ViewUtil.validStartDate(queryInfo.getStartDate()).getTime();
        long queryEndTimestamp = ViewUtil.validEndDate(queryInfo.getEndDate()).getTime();

        // 检查预设是否合法。
        String preset = queryInfo.getPreset();
        if (!NATIVE_QUERY_PRESET_DEFAULT.equals(preset)) {
            throw new IllegalArgumentException("预设不合法: " + preset);
        }

        // 展开参数。
        String[] params = queryInfo.getParams();
        long period = Long.parseLong(params[0]);
        long offset = Long.parseLong(params[1]);

        return mockNativeQuery(
                queryInfo.getBridgeDataKeys(),
                queryStartTimestamp, queryEndTimestamp,
                queryInfo.isIncludeStartDate(), queryInfo.isIncludeEndDate(),
                period, offset
        );
    }

    private QueryResult mockNativeQuery(
            List<BridgeDataKey> bridgeDataKeys, long queryStartTimestamp, long queryEndTimestamp,
            boolean includeStartDate, boolean includeEndDate, long period, long offset
    ) throws Exception {
        // 获取当前时间戳，用于模拟延迟。
        long anchorTimestamp = System.currentTimeMillis();

        // 获取配置。
        long nativeQueryBeforeDelay = config.getNativeQueryBeforeDelay();
        long nativeQueryDelayPerSecond = config.getNativeQueryDelayPerSecond();
        long nativeQueryAfterDelay = config.getNativeQueryAfterDelay();

        // 根据查询区间的开闭情况调整查询区间。
        long actualQueryStartTimestamp = includeStartDate ? queryStartTimestamp : queryStartTimestamp + 1;
        long actualQueryEndTimestamp = includeEndDate ? queryEndTimestamp : queryEndTimestamp - 1;

        if (nativeQueryBeforeDelay > 0) {
            anchorTimestamp += nativeQueryBeforeDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        // 计算第一个点的数据起始时间。
        // 数据的第一个起始时间应该大于等于 actualQueryStartTimestamp，且减去 offset 后应该是 period 的整数倍。
        long cursor = actualQueryStartTimestamp + (actualQueryStartTimestamp - offset) % period;
        // 在 cursor 小于等于 actualQueryEndTimestamp 之前，生成数据，随后 cursor 自增 period。
        List<List<BridgeData>> datasList = new ArrayList<>(bridgeDataKeys.size());
        for (int i = 0; i < bridgeDataKeys.size(); i++) {
            datasList.add(new ArrayList<>());
        }
        while (cursor <= actualQueryEndTimestamp) {
            for (int i = 0; i < bridgeDataKeys.size(); i++) {
                BridgeDataKey bridgeDataKey = bridgeDataKeys.get(i);
                Object value = dataValueGenerator.nextValue(bridgeDataKey);
                datasList.get(i).add(new BridgeData(bridgeDataKey, value, new Date(cursor)));
            }
            cursor += period;
        }
        List<QueryResult.Sequence> sequences = new ArrayList<>(bridgeDataKeys.size());
        for (int i = 0; i < bridgeDataKeys.size(); i++) {
            BridgeDataKey bridgeDataKey = bridgeDataKeys.get(i);
            List<BridgeData> items = datasList.get(i);
            Date startDate = new Date(queryStartTimestamp);
            Date endDate = new Date(queryEndTimestamp);
            sequences.add(new QueryResult.Sequence(bridgeDataKey, items, startDate, endDate));
        }

        // 模拟延迟，延迟时间为(查询的时间范围 / 1000 + 1) * nativeQueryDelayPerSecond。
        // 如果时间区间小于等于0，则不延迟。
        if (nativeQueryDelayPerSecond > 0) {
            long timeRange = actualQueryEndTimestamp - actualQueryStartTimestamp;
            long delay = timeRange <= 0 ? 0 : (timeRange / 1000 + 1) * nativeQueryDelayPerSecond;
            anchorTimestamp += delay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        if (nativeQueryAfterDelay > 0) {
            anchorTimestamp += nativeQueryAfterDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        // 返回结果。
        return new QueryResult(sequences);
    }

    @Override
    public String toString() {
        return "MockBridgePersister{" +
                "config=" + config +
                ", dataValueGenerator=" + dataValueGenerator +
                '}';
    }
}
