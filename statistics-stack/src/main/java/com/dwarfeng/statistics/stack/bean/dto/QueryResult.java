package com.dwarfeng.statistics.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;
import java.util.List;

/**
 * 查看结果。
 *
 * <p>
 * 该实体表示查看结果，包含了查看的序列。
 *
 * <p>
 * 有关查看的详细信息，请参阅术语。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class QueryResult implements Dto {

    private static final long serialVersionUID = 9098345980770463104L;

    private List<Sequence> sequences;

    public QueryResult() {
    }

    public QueryResult(List<Sequence> sequences) {
        this.sequences = sequences;
    }

    public List<Sequence> getSequences() {
        return sequences;
    }

    public void setSequences(List<Sequence> sequences) {
        this.sequences = sequences;
    }

    @Override
    public String toString() {
        return "QueryResult{" +
                "sequences=" + sequences +
                '}';
    }

    /**
     * 序列。
     *
     * <p>
     * 该实体表示序列，包含了序列的桥接器数据、序列的开始时间和序列的结束时间。
     *
     * <p>
     * 序列是查看结果的组成部分。
     *
     * <p>
     * 有关查看的详细信息，请参阅术语。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    public static class Sequence implements Dto {

        private static final long serialVersionUID = -4131757749463045936L;

        private LongIdKey statisticsSettingKey;
        private List<BridgeData> bridgeDatas;
        private Date startDate;
        private Date endDate;

        public Sequence() {
        }

        public Sequence(LongIdKey statisticsSettingKey, List<BridgeData> bridgeDatas, Date startDate, Date endDate) {
            this.statisticsSettingKey = statisticsSettingKey;
            this.bridgeDatas = bridgeDatas;
            this.startDate = startDate;
            this.endDate = endDate;
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

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        @Override
        public String toString() {
            return "Sequence{" +
                    "statisticsSettingKey=" + statisticsSettingKey +
                    ", bridgeDatas=" + bridgeDatas +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    '}';
        }
    }
}
