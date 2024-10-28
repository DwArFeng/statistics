package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.sdk.util.ViewUtil;
import com.dwarfeng.statistics.stack.bean.dto.*;
import com.dwarfeng.statistics.stack.exception.QueryException;
import com.dwarfeng.statistics.stack.exception.UnsupportedMapperTypeException;
import com.dwarfeng.statistics.stack.handler.MapLocalCacheHandler;
import com.dwarfeng.statistics.stack.handler.Mapper;
import com.dwarfeng.statistics.stack.handler.QueryHandler;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Component
public class QueryHandlerImpl implements QueryHandler {

    private final MapLocalCacheHandler mapLocalCacheHandler;

    private final ThreadPoolTaskExecutor executor;

    private final List<Bridge> bridges;

    private Bridge.Persister persister;

    @Value("${bridge.persister.type}")
    private String persisterType;

    @Value("${query.max_period_span}")
    private long maxPeriodSpan;
    @Value("${query.max_page_size}")
    private int maxPageSize;

    public QueryHandlerImpl(
            MapLocalCacheHandler mapLocalCacheHandler,
            ThreadPoolTaskExecutor executor,
            List<Bridge> bridges
    ) {
        this.mapLocalCacheHandler = mapLocalCacheHandler;
        this.executor = executor;
        this.bridges = bridges;
    }

    @PostConstruct
    public void init() throws Exception {
        Bridge bridge = bridges.stream().filter(b -> b.supportType(persisterType)).findAny()
                .orElseThrow(() -> new HandlerException("未知的 persister 对应的 bridge 类型: " + persisterType));
        this.persister = bridge.getPersister();
    }

    @Override
    public QueryResult query(QueryInfo queryInfo) throws HandlerException {
        try {
            return querySingle(queryInfo);
        } catch (QueryException e) {
            throw e;
        } catch (Exception e) {
            throw new QueryException(e, Collections.singletonList(queryInfo));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public List<QueryResult> query(List<QueryInfo> queryInfos) throws HandlerException {
        try {
            // 构造查询结果，并初始化。
            List<QueryResult> queryResults = new ArrayList<>(queryInfos.size());
            for (int i = 0; i < queryInfos.size(); i++) {
                queryResults.add(null);
            }

            // 遍历 queryInfos，异步查询。
            List<CompletableFuture<?>> futures = new ArrayList<>(queryInfos.size());
            for (int i = 0; i < queryInfos.size(); i++) {
                final int index = i;
                final QueryInfo queryInfo = queryInfos.get(index);
                CompletableFuture<Void> future = CompletableFuture.runAsync(
                        () -> {
                            QueryResult queryResult = wrappedQuerySingle(queryInfo);
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

            // 返回结果。
            return queryResults;
        } catch (QueryException e) {
            throw e;
        } catch (Exception e) {
            throw new QueryException(e, queryInfos);
        }
    }

    private QueryResult wrappedQuerySingle(QueryInfo queryInfo) throws CompletionException {
        try {
            return querySingle(queryInfo);
        } catch (Exception e) {
            throw new CompletionException(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private QueryResult querySingle(QueryInfo queryInfo) throws Exception {
        // 展开查询信息。
        String preset = queryInfo.getPreset();
        String[] params = queryInfo.getParams();
        List<LongIdKey> statisticsSettingKeys = queryInfo.getStatisticsSettingKeys();
        Date startDate = ViewUtil.validStartDate(queryInfo.getStartDate());
        Date endDate = ViewUtil.validEndDate(queryInfo.getEndDate());
        boolean includeStartDate = queryInfo.isIncludeStartDate();
        boolean includeEndDate = queryInfo.isIncludeEndDate();
        List<QueryInfo.MapInfo> mapInfos = queryInfo.getMapInfos();

        // 进行查询，构造 List<Mapper.Sequence>。
        List<Mapper.Sequence> sequences = lookupSequences(
                maxPeriodSpan, maxPageSize, preset, params, statisticsSettingKeys, startDate, endDate, includeStartDate,
                includeEndDate
        );

        // 进行映射。
        for (QueryInfo.MapInfo mapInfo : mapInfos) {
            sequences = mapSingleSequence(mapInfo, sequences);
        }

        // 根据 List<Mapper.Sequence> 构造 QueryResult。
        return buildLookupResult(sequences);
    }

    private List<Mapper.Sequence> lookupSequences(
            long maxPeriodSpan, int maxPageSize, String preset, String[] params, List<LongIdKey> statisticsSettingKeys,
            Date startDate, Date endDate, boolean includeStartDate, boolean includeEndDate
    ) throws Exception {
        // 构造查询结果，并初始化。
        List<Mapper.Sequence> sequences = new ArrayList<>(statisticsSettingKeys.size());
        for (int i = 0; i < statisticsSettingKeys.size(); i++) {
            sequences.add(null);
        }

        // 遍历 statisticsSettingKeys，异步查询。
        List<CompletableFuture<?>> futures = new ArrayList<>(statisticsSettingKeys.size());
        for (int i = 0; i < statisticsSettingKeys.size(); i++) {
            final int index = i;
            final LongIdKey statisticsSettingKey = statisticsSettingKeys.get(index);
            CompletableFuture<Void> future = CompletableFuture.runAsync(
                    () -> {
                        Mapper.Sequence sequence = wrappedLookupSingleSequence(
                                maxPeriodSpan, maxPageSize, preset, params, statisticsSettingKey, startDate, endDate,
                                includeStartDate, includeEndDate
                        );
                        sequences.set(index, sequence);
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

        // 返回结果。
        return sequences;
    }

    private Mapper.Sequence wrappedLookupSingleSequence(
            long maxPeriodSpan, int maxPageSize, String preset, String[] params, LongIdKey statisticsSettingKey,
            Date startDate, Date endDate, boolean includeStartDate, boolean includeEndDate
    ) throws CompletionException {
        try {
            return lookupSingleSequence(
                    maxPeriodSpan, maxPageSize, preset, params, statisticsSettingKey, startDate, endDate, includeStartDate,
                    includeEndDate
            );
        } catch (Exception e) {
            throw new CompletionException(e);
        }
    }

    private Mapper.Sequence lookupSingleSequence(
            long maxPeriodSpan, int maxPageSize, String preset, String[] params, LongIdKey statisticsSettingKey,
            Date startDate, Date endDate, boolean includeStartDate, boolean includeEndDate
    ) throws Exception {
        // 定义中间变量。
        List<BridgeData> bridgeDatas = new ArrayList<>();

        // 循环查询数据，每次查询的时间跨度最大为 maxPeriodSpan。
        Date anchorStartDate = startDate;
        boolean notLastPeriodFlag = anchorStartDate.getTime() + maxPeriodSpan < endDate.getTime();
        Date anchorEndDate = notLastPeriodFlag ? new Date(anchorStartDate.getTime() + maxPeriodSpan) : endDate;
        boolean anchorIncludeStartDate = includeStartDate;
        // 为了代码的可读性，此处不简化三目运算符。
        @SuppressWarnings("SimplifiableConditionalExpression")
        boolean anchorIncludeEndDate = notLastPeriodFlag ? false : includeEndDate;
        do {
            // 构造查询参数，查询第一页的内容。
            LookupInfo lookupInfo = new LookupInfo(
                    preset, params, statisticsSettingKey, anchorStartDate, anchorEndDate, anchorIncludeStartDate,
                    anchorIncludeEndDate, 0, maxPageSize
            );
            LookupResult lookupResult = persister.lookup(lookupInfo);
            // 将查询结果转换为 BridgeData，添加到返回值中。
            for (BridgeData bridgeData : lookupResult.getBridgeDatas()) {
                bridgeDatas.add(new BridgeData(
                        bridgeData.getStatisticsSettingKey(), bridgeData.getValue(), bridgeData.getHappenedDate()
                ));
            }

            // 如果总页数大于 1，则继续查询。
            if (lookupResult.getTotalPages() > 1) {
                for (int i = 1; i < lookupResult.getTotalPages(); i++) {
                    lookupInfo = new LookupInfo(
                            preset, params, statisticsSettingKey, anchorStartDate, anchorEndDate,
                            anchorIncludeStartDate, anchorIncludeEndDate, i, maxPageSize
                    );
                    lookupResult = persister.lookup(lookupInfo);
                    for (BridgeData bridgeData : lookupResult.getBridgeDatas()) {
                        bridgeDatas.add(new BridgeData(
                                bridgeData.getStatisticsSettingKey(),
                                bridgeData.getValue(),
                                bridgeData.getHappenedDate()
                        ));
                    }
                }
            }

            // 如果 notLastPeriodFlag 为 true，则更新查询数据。
            if (notLastPeriodFlag) {
                anchorStartDate = anchorEndDate;
                notLastPeriodFlag = anchorStartDate.getTime() + maxPeriodSpan < endDate.getTime();
                anchorEndDate = notLastPeriodFlag ? new Date(anchorStartDate.getTime() + maxPeriodSpan) : endDate;
                anchorIncludeStartDate = false;
                // 为了代码的可读性，此处不简化三目运算符。
                @SuppressWarnings("SimplifiableConditionalExpression")
                boolean anchorIncludeEndDateDejaVu = notLastPeriodFlag ? false : includeEndDate;
                anchorIncludeEndDate = anchorIncludeEndDateDejaVu;
            }
        } while (notLastPeriodFlag);

        // 将查询结果添加到返回值中。
        return new Mapper.Sequence(statisticsSettingKey, bridgeDatas, startDate, endDate);
    }

    private List<Mapper.Sequence> mapSingleSequence(QueryInfo.MapInfo mapInfo, List<Mapper.Sequence> sequences)
            throws Exception {
        // 展开映射信息。
        String type = mapInfo.getType();
        String param = mapInfo.getParam();

        // 根据 mapInfo 找到对应的 Mapper。
        Mapper mapper = mapLocalCacheHandler.get(type);
        // 如果找不到对应的 Mapper，则抛出异常。
        if (mapper == null) {
            throw new UnsupportedMapperTypeException(type);
        }

        // 调用 mapper 进行映射，返回结果。
        return mapper.map(new Mapper.MapParam(param), sequences);
    }

    private QueryResult buildLookupResult(List<Mapper.Sequence> sequences) {
        List<QueryResult.Sequence> resultSequences = new ArrayList<>();
        for (Mapper.Sequence sequence : sequences) {
            List<BridgeData> bridgeDatas = new ArrayList<>(sequence.getDatas());
            resultSequences.add(new QueryResult.Sequence(
                    sequence.getStatisticsSettingKey(), bridgeDatas, sequence.getStartDate(), sequence.getEndDate()
            ));
        }
        return new QueryResult(resultSequences);
    }

    @Override
    public String toString() {
        return "QueryHandlerImpl{" +
                "mapLocalCacheHandler=" + mapLocalCacheHandler +
                ", executor=" + executor +
                ", bridges=" + bridges +
                ", persister=" + persister +
                ", persisterType='" + persisterType + '\'' +
                ", maxPeriodSpan=" + maxPeriodSpan +
                ", maxPageSize=" + maxPageSize +
                '}';
    }
}
