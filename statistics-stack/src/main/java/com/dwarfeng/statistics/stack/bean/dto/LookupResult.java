package com.dwarfeng.statistics.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.List;

/**
 * 查看结果。
 *
 * <p>
 * 该实体表示查询结果，包含了查询的桥接器数据记录和是否还有更多的桥接器数据记录。
 *
 * <p>
 * 有关查询的详细信息，请参阅术语。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class LookupResult implements Dto {

    private static final long serialVersionUID = -5363946465028798306L;

    private LongIdKey statisticsSettingKey;
    private List<BridgeData> bridgeDatas;

    /**
     * 当前的页数，从 0 开始计数。
     */
    private int currentPage;

    /**
     * 总共的页数。
     *
     * <p>
     * 在一般情况下，该值是一个非负数，代表总共的页数。<br>
     * 特殊情况下，该值是负数，代表总页数无法计算，这通常在分页查询时，将每页返回的行数设置为 0 时出现。
     */
    private int totalPages;

    /**
     * 每页返回的行数。
     */
    private int rows;

    /**
     * 总共数量。
     */
    private long count;

    public LookupResult() {
    }

    public LookupResult(
            LongIdKey statisticsSettingKey, List<BridgeData> bridgeDatas, int currentPage, int totalPages, int rows,
            long count
    ) {
        this.statisticsSettingKey = statisticsSettingKey;
        this.bridgeDatas = bridgeDatas;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.rows = rows;
        this.count = count;
    }

    public LongIdKey getStatisticsSettingKey() {
        return statisticsSettingKey;
    }

    public void setStatisticsSettingKey(LongIdKey statisticsSettingKey) {
        this.statisticsSettingKey = statisticsSettingKey;
    }

    public List<BridgeData> getBridgeDatas() {
        return bridgeDatas;
    }

    public void setBridgeDatas(List<BridgeData> bridgeDatas) {
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
        return "LookupResult{" +
                "statisticsSettingKey=" + statisticsSettingKey +
                ", bridgeDatas=" + bridgeDatas +
                ", currentPage=" + currentPage +
                ", totalPages=" + totalPages +
                ", rows=" + rows +
                ", count=" + count +
                '}';
    }
}
