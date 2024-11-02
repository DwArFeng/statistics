package com.dwarfeng.statistics.impl.handler.bridge.influxdb;

import com.dwarfeng.statistics.impl.handler.bridge.FullPersister;
import com.dwarfeng.statistics.impl.handler.bridge.influxdb.bean.dto.*;
import com.dwarfeng.statistics.impl.handler.bridge.influxdb.handler.InfluxdbBridgeDataHandler;
import com.dwarfeng.statistics.impl.handler.bridge.influxdb.util.Constants;
import com.dwarfeng.statistics.impl.handler.bridge.influxdb.util.DateUtil;
import com.dwarfeng.statistics.sdk.util.ViewUtil;
import com.dwarfeng.statistics.stack.bean.dto.*;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

/**
 * Influxdb 桥接器数据持久器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class InfluxdbBridgePersister extends FullPersister {

    public static final String LOOKUP_PRESET_DEFAULT = "default";
    public static final String NATIVE_QUERY_PRESET_DEFAULT = "default";
    public static final String NATIVE_QUERY_PRESET_AGGREGATE_WINDOW = "aggregate_window";
    public static final String NATIVE_QUERY_PRESET_CUSTOM = "custom";

    protected final InfluxdbBridgeDataHandler handler;

    protected final ThreadPoolTaskExecutor executor;

    protected InfluxdbBridgePersister(
            InfluxdbBridgeDataHandler handler,
            ThreadPoolTaskExecutor executor
    ) {
        this.handler = handler;
        this.executor = executor;
    }

    @Override
    protected void doRecord(BridgeData data) throws Exception {
        Point point = dataToPoint(data);
        handler.write(point);
    }

    @Override
    protected void doRecord(List<BridgeData> datas) throws Exception {
        List<Point> points = datas.stream().map(this::dataToPoint).collect(Collectors.toList());
        handler.write(points);
    }

    private Point dataToPoint(BridgeData data) {
        Point point = new Point(Long.toString(data.getStatisticsSettingKey().getLongId()));
        Map<String, Object> fieldMap = new HashMap<>();
        if (Objects.nonNull(data.getValue())) {
            fieldMap.put(Constants.FIELD_NAME_VALUE, data.getValue());
        }
        point.addFields(fieldMap);
        point.time(DateUtil.date2Instant(data.getHappenedDate()), WritePrecision.MS);
        return point;
    }

    @Override
    protected LookupResult doLookup(LookupInfo lookupInfo) throws Exception {
        return lookupSingle(lookupInfo);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<LookupResult> doLookup(List<LookupInfo> lookupInfos) throws Exception {
        // 构造查看结果，并初始化。
        List<LookupResult> lookupResults = new ArrayList<>(lookupInfos.size());
        for (int i = 0; i < lookupInfos.size(); i++) {
            lookupResults.add(null);
        }

        // 遍历 lookupInfos，异步查看。
        List<CompletableFuture<?>> futures = new ArrayList<>(lookupInfos.size());
        for (int i = 0; i < lookupInfos.size(); i++) {
            final int index = i;
            final LookupInfo lookupInfo = lookupInfos.get(index);
            CompletableFuture<Void> future = CompletableFuture.runAsync(
                    () -> {
                        LookupResult lookupResult = wrappedLookupSingle(lookupInfo);
                        lookupResults.set(index, lookupResult);
                    },
                    executor
            );
            futures.add(future);
        }
        try {
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        } catch (CompletionException e) {
            throw (Exception) e.getCause();
        }

        // 返回查看结果。
        return lookupResults;
    }

    private LookupResult wrappedLookupSingle(LookupInfo lookupInfo) throws CompletionException {
        try {
            return lookupSingle(lookupInfo);
        } catch (Exception e) {
            throw new CompletionException(e);
        }
    }

    private LookupResult lookupSingle(LookupInfo lookupInfo) throws HandlerException {
        // 展开参数。
        String preset = lookupInfo.getPreset();
        String[] params = lookupInfo.getParams();
        LongIdKey statisticsSettingKey = lookupInfo.getStatisticsSettingKey();
        Date startDate = ViewUtil.validStartDate(lookupInfo.getStartDate());
        Date endDate = ViewUtil.validEndDate(lookupInfo.getEndDate());
        boolean includeStartDate = lookupInfo.isIncludeStartDate();
        boolean includeEndDate = lookupInfo.isIncludeEndDate();
        int page = ViewUtil.validPage(lookupInfo.getPage());
        int rows = ViewUtil.validRows(lookupInfo.getRows());

        // 构造查看信息。
        String measurement = Long.toString(statisticsSettingKey.getLongId());
        long startOffset = includeStartDate ? 0 : 1;
        long stopOffset = includeEndDate ? 1 : 0;
        Date rangeStart = DateUtil.offsetDate(startDate, startOffset);
        Date rangeStop = DateUtil.offsetDate(endDate, stopOffset);
        InfluxdbBridgeLookupInfo queryInfo = new InfluxdbBridgeLookupInfo(
                measurement, rangeStart, rangeStop, page, rows, params
        );

        // 根据 preset 分类处理。
        InfluxdbBridgeLookupResult queryResult;
        if (preset.equals(LOOKUP_PRESET_DEFAULT)) {
            queryResult = handler.lookup(queryInfo);
        } else {
            throw new IllegalArgumentException("非法的预设: " + preset);
        }

        // 展开参数。
        List<InfluxdbBridgeLookupResult.Item> items = queryResult.getItems();
        long count = queryResult.getCount();

        // 计算总页数。
        int totalPages = (int) ((count + rows - 1) / rows);

        // 构造查看结果，并返回。
        List<BridgeData> datas = new ArrayList<>();
        for (int i = 0; i < rows && i < items.size(); i++) {
            datas.add(itemToData(items.get(i)));
        }
        return new LookupResult(statisticsSettingKey, datas, page, totalPages, rows, count);
    }

    private BridgeData itemToData(InfluxdbBridgeLookupResult.Item item) {
        LongIdKey statisticsSettingKey = new LongIdKey(Long.parseLong(item.getMeasurement()));
        Object value = null;
        Map<String, Object> valueMap = item.getValueMap();
        Object filedValue;
        if (Objects.nonNull((filedValue = valueMap.get(Constants.FIELD_NAME_VALUE)))) {
            value = filedValue;
        }
        Date happenedDate = DateUtil.instant2Date(item.getHappenedInstant());
        return new BridgeData(statisticsSettingKey, value, happenedDate);
    }

    @Override
    protected QueryResult doNativeQuery(NativeQueryInfo queryInfo) throws Exception {
        return nativeQuerySingle(queryInfo);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<QueryResult> doNativeQuery(List<NativeQueryInfo> queryInfos) throws Exception {
        // 构造查看结果，并初始化。
        List<QueryResult> queryResults = new ArrayList<>(queryInfos.size());
        for (int i = 0; i < queryInfos.size(); i++) {
            queryResults.add(null);
        }

        // 遍历 queryInfos，异步查看。
        List<CompletableFuture<?>> futures = new ArrayList<>(queryInfos.size());
        for (int i = 0; i < queryInfos.size(); i++) {
            final int index = i;
            final NativeQueryInfo queryInfo = queryInfos.get(index);
            CompletableFuture<Void> future = CompletableFuture.runAsync(
                    () -> {
                        QueryResult queryResult = wrappedNativeQuerySingle(queryInfo);
                        queryResults.set(index, queryResult);
                    },
                    executor
            );
            futures.add(future);
        }
        try {
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        } catch (CompletionException e) {
            throw (Exception) e.getCause();
        }

        // 返回查看结果。
        return queryResults;
    }

    private QueryResult wrappedNativeQuerySingle(NativeQueryInfo queryInfo) throws CompletionException {
        try {
            return nativeQuerySingle(queryInfo);
        } catch (Exception e) {
            throw new CompletionException(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private QueryResult nativeQuerySingle(NativeQueryInfo queryInfo) throws HandlerException {
        // 展开参数。
        String preset = queryInfo.getPreset();
        String[] params = queryInfo.getParams();
        List<LongIdKey> statisticsSettingKeys = queryInfo.getStatisticsSettingKeys();
        Date startDate = ViewUtil.validStartDate(queryInfo.getStartDate());
        Date endDate = ViewUtil.validEndDate(queryInfo.getEndDate());
        boolean includeStartDate = queryInfo.isIncludeStartDate();
        boolean includeEndDate = queryInfo.isIncludeEndDate();

        // 构造查看信息。
        List<String> measurements = new ArrayList<>(statisticsSettingKeys.size());
        for (LongIdKey statisticsSettingKey : statisticsSettingKeys) {
            measurements.add(Long.toString(statisticsSettingKey.getLongId()));
        }
        long startOffset = includeStartDate ? 0 : 1;
        long stopOffset = includeEndDate ? 1 : 0;
        Date rangeStart = DateUtil.offsetDate(startDate, startOffset);
        Date rangeStop = DateUtil.offsetDate(endDate, stopOffset);

        // 根据 preset 分类处理。
        InfluxdbBridgeQueryResult queryResult;
        switch (preset) {
            case NATIVE_QUERY_PRESET_DEFAULT:
            case NATIVE_QUERY_PRESET_AGGREGATE_WINDOW:
                queryResult = handler.defaultQuery(
                        resolveDefaultQueryInfo(measurements, rangeStart, rangeStop, params)
                );
                break;
            case NATIVE_QUERY_PRESET_CUSTOM:
                queryResult = handler.customQuery(
                        resolveCustomQueryInfo(measurements, rangeStart, rangeStop, params)
                );
                break;
            default:
                throw new IllegalArgumentException("非法的预设: " + preset);
        }

        // 构造查看结果，并返回。
        List<InfluxdbBridgeQueryResult.InfluxdbBridgeSequence> influxdbBridgeSequences = queryResult.getSequences();
        List<QueryResult.Sequence> sequences = new ArrayList<>(influxdbBridgeSequences.size());
        for (InfluxdbBridgeQueryResult.InfluxdbBridgeSequence influxdbBridgeSequence : influxdbBridgeSequences) {
            LongIdKey statisticsSettingKey = new LongIdKey(Long.parseLong(influxdbBridgeSequence.getMeasurement()));
            startDate = DateUtil.instant2Date(influxdbBridgeSequence.getStartInstant());
            endDate = DateUtil.instant2Date(influxdbBridgeSequence.getEndInstant());

            List<InfluxdbBridgeQueryResult.InfluxdbBridgeItem> influxdbBridgeItems
                    = influxdbBridgeSequence.getItems();
            List<BridgeData> items = new ArrayList<>(influxdbBridgeItems.size());
            for (InfluxdbBridgeQueryResult.InfluxdbBridgeItem influxdbBridgeItem : influxdbBridgeItems) {
                Date happenedDate = DateUtil.instant2Date(influxdbBridgeItem.getHappenedInstant());
                items.add(new BridgeData(statisticsSettingKey, influxdbBridgeItem.getValue(), happenedDate));
            }

            sequences.add(new QueryResult.Sequence(statisticsSettingKey, items, startDate, endDate));
        }
        return new QueryResult(sequences);
    }

    private InfluxdbBridgeDefaultQueryInfo resolveDefaultQueryInfo(
            List<String> measurements, Date rangeStart, Date rangeStop, String[] params
    ) {
        // params 的第 0 个元素是 aggregateWindowEvery。
        long aggregateWindowEvery = Long.parseLong(params[0]);
        // params 的第 1 个元素是 aggregateWindowOffset。
        long aggregateWindowOffset = Long.parseLong(params[1]);
        // params 的第 2 个元素是 aggregateWindowFn。
        String aggregateWindowFn = params[2];

        // 返回结果。
        return new InfluxdbBridgeDefaultQueryInfo(
                measurements, rangeStart, rangeStop, aggregateWindowEvery, aggregateWindowOffset, aggregateWindowFn
        );
    }

    private InfluxdbBridgeCustomQueryInfo resolveCustomQueryInfo(
            List<String> measurements, Date rangeStart, Date rangeStop, String[] params
    ) {
        // params 的第 0 个元素是 fluxFragment。
        String fluxFragment = params[0];

        // 返回结果。
        return new InfluxdbBridgeCustomQueryInfo(measurements, rangeStart, rangeStop, fluxFragment);
    }

    @Override
    public String toString() {
        return "InfluxdbBridgePersister{" +
                "handler=" + handler +
                ", executor=" + executor +
                '}';
    }
}
