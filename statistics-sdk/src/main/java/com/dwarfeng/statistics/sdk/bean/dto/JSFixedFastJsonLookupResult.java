package com.dwarfeng.statistics.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.statistics.sdk.bean.key.JSFixedFastJsonBridgeDataKey;
import com.dwarfeng.statistics.stack.bean.dto.LookupResult;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * JSFixed FastJson查看结果。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonLookupResult implements Dto {

    private static final long serialVersionUID = 74715469130561524L;

    public static JSFixedFastJsonLookupResult of(LookupResult lookupResult) {
        if (Objects.isNull(lookupResult)) {
            return null;
        } else {
            return new JSFixedFastJsonLookupResult(
                    JSFixedFastJsonBridgeDataKey.of(lookupResult.getBridgeDataKey()),
                    Optional.ofNullable(lookupResult.getBridgeDatas()).map(
                            f -> f.stream().map(JSFixedFastJsonBridgeData::of).collect(Collectors.toList())
                    ).orElse(null),
                    lookupResult.getCurrentPage(),
                    lookupResult.getTotalPages(),
                    lookupResult.getRows(),
                    lookupResult.getCount()
            );
        }
    }

    @JSONField(name = "bridge_data_key", ordinal = 1)
    private JSFixedFastJsonBridgeDataKey bridgeDataKey;

    @JSONField(name = "bridge_datas", ordinal = 2)
    private List<JSFixedFastJsonBridgeData> bridgeDatas;

    @JSONField(name = "current_page", ordinal = 3)
    private int currentPage;

    @JSONField(name = "total_pages", ordinal = 4)
    private int totalPages;

    @JSONField(name = "rows", ordinal = 5)
    private int rows;

    @JSONField(name = "count", ordinal = 6, serializeUsing = ToStringSerializer.class)
    private long count;

    public JSFixedFastJsonLookupResult() {
    }

    public JSFixedFastJsonLookupResult(
            JSFixedFastJsonBridgeDataKey bridgeDataKey, List<JSFixedFastJsonBridgeData> bridgeDatas,
            int currentPage, int totalPages, int rows, long count
    ) {
        this.bridgeDataKey = bridgeDataKey;
        this.bridgeDatas = bridgeDatas;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.rows = rows;
        this.count = count;
    }

    public JSFixedFastJsonBridgeDataKey getBridgeDataKey() {
        return bridgeDataKey;
    }

    public void setBridgeDataKey(JSFixedFastJsonBridgeDataKey bridgeDataKey) {
        this.bridgeDataKey = bridgeDataKey;
    }

    public List<JSFixedFastJsonBridgeData> getBridgeDatas() {
        return bridgeDatas;
    }

    public void setBridgeDatas(List<JSFixedFastJsonBridgeData> bridgeDatas) {
        this.bridgeDatas = bridgeDatas;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonLookupResult{" +
                "bridgeDataKey=" + bridgeDataKey +
                ", bridgeDatas=" + bridgeDatas +
                ", currentPage=" + currentPage +
                ", totalPages=" + totalPages +
                ", rows=" + rows +
                ", count=" + count +
                '}';
    }
}
