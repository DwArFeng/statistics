package com.dwarfeng.statistics.impl.handler.bridge.hibernate;

import com.dwarfeng.dct.handler.ValueCodingHandler;
import com.dwarfeng.statistics.impl.handler.bridge.FullPersister;
import com.dwarfeng.statistics.impl.handler.bridge.hibernate.bean.HibernateBridgeBridgeData;
import com.dwarfeng.statistics.impl.handler.bridge.hibernate.service.HibernateBridgeBridgeDataMaintainService;
import com.dwarfeng.statistics.sdk.util.ViewUtil;
import com.dwarfeng.statistics.stack.bean.dto.*;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * Hibernate 桥接器持久器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class HibernateBridgePersister extends FullPersister {

    public static final String LOOKUP_PRESET_DEFAULT = "default";

    private final HibernateBridgeBridgeDataMaintainService service;
    private final ValueCodingHandler valueCodingHandler;
    private final ThreadPoolTaskExecutor executor;

    public HibernateBridgePersister(
            HibernateBridgeBridgeDataMaintainService service,
            @Qualifier("hibernateBridge.valueCodingHandler") ValueCodingHandler valueCodingHandler,
            ThreadPoolTaskExecutor executor
    ) {
        this.service = service;
        this.valueCodingHandler = valueCodingHandler;
        this.executor = executor;
    }

    @Override
    protected void doRecord(BridgeData data) throws Exception {
        HibernateBridgeBridgeData transformed = transform(data);
        service.write(transformed);
    }

    @Override
    protected void doRecord(List<BridgeData> datas) throws Exception {
        List<HibernateBridgeBridgeData> entities = new ArrayList<>();
        for (BridgeData data : datas) {
            HibernateBridgeBridgeData transformed = transform(data);
            entities.add(transformed);
        }
        service.batchWrite(entities);
    }

    private HibernateBridgeBridgeData transform(BridgeData data) throws Exception {
        String flatValue = valueCodingHandler.encode(data.getValue());
        return new HibernateBridgeBridgeData(
                null,
                data.getStatisticsSettingKey(),
                flatValue,
                data.getHappenedDate()
        );
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

    private LookupResult lookupSingle(LookupInfo lookupInfo) throws Exception {
        // 展开查询信息。
        LongIdKey statisticsSettingKey = lookupInfo.getStatisticsSettingKey();
        Date startDate = ViewUtil.validStartDate(lookupInfo.getStartDate());
        Date endDate = ViewUtil.validEndDate(lookupInfo.getEndDate());
        int page = ViewUtil.validPage(lookupInfo.getPage());
        int rows = ViewUtil.validRows(lookupInfo.getRows());
        boolean includeStartDate = lookupInfo.isIncludeStartDate();
        boolean includeEndDate = lookupInfo.isIncludeEndDate();

        // 检查预设是否合法。
        String preset = lookupInfo.getPreset();
        if (!LOOKUP_PRESET_DEFAULT.equals(preset)) {
            throw new IllegalArgumentException("预设不合法: " + preset);
        }

        // 查询数据。
        String servicePreset = resolveServicePreset(includeStartDate, includeEndDate);
        PagedData<HibernateBridgeBridgeData> lookup = service.lookup(
                servicePreset, new Object[]{statisticsSettingKey, startDate, endDate}, new PagingInfo(page, rows)
        );

        // 处理数据。
        List<HibernateBridgeBridgeData> transformedDatas = lookup.getData();
        List<BridgeData> datas = new ArrayList<>(transformedDatas.size());
        for (HibernateBridgeBridgeData transformedData : transformedDatas) {
            BridgeData data = reverseTransform(transformedData);
            datas.add(data);
        }

        // 返回结果。
        return new LookupResult(
                statisticsSettingKey, datas, lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(),
                lookup.getCount()
        );
    }

    private String resolveServicePreset(boolean includeStartDate, boolean includeEndDate) {
        String servicePreset;
        if (includeStartDate && includeEndDate) {
            servicePreset = HibernateBridgeBridgeDataMaintainService.CHILD_FOR_STATISTICS_SETTING_BETWEEN_CLOSE_CLOSE;
        } else if (includeStartDate) {
            servicePreset = HibernateBridgeBridgeDataMaintainService.CHILD_FOR_STATISTICS_SETTING_BETWEEN_CLOSE_OPEN;
        } else if (includeEndDate) {
            servicePreset = HibernateBridgeBridgeDataMaintainService.CHILD_FOR_STATISTICS_SETTING_BETWEEN_OPEN_CLOSE;
        } else {
            servicePreset = HibernateBridgeBridgeDataMaintainService.CHILD_FOR_STATISTICS_SETTING_BETWEEN_OPEN_OPEN;
        }
        return servicePreset;
    }

    private BridgeData reverseTransform(HibernateBridgeBridgeData t) throws Exception {
        Object value = valueCodingHandler.decode(t.getValue());
        return new BridgeData(
                t.getStatisticsSettingKey(),
                value,
                t.getHappenedDate()
        );
    }


    @Override
    protected QueryResult doNativeQuery(NativeQueryInfo queryInfo) throws IllegalArgumentException {
        String preset = queryInfo.getPreset();
        throw new IllegalArgumentException("预设不合法: " + preset);
    }

    @Override
    protected List<QueryResult> doNativeQuery(List<NativeQueryInfo> queryInfos) throws IllegalArgumentException {
        if (queryInfos.isEmpty()) {
            return new ArrayList<>(0);
        }
        String preset = queryInfos.get(0).getPreset();
        throw new IllegalArgumentException("预设不合法: " + preset);
    }
}
