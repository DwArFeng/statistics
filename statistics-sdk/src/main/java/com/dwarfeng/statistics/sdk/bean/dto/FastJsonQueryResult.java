package com.dwarfeng.statistics.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.sdk.bean.key.FastJsonBridgeDataKey;
import com.dwarfeng.statistics.stack.bean.dto.QueryResult;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * FastJson 查看结果。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonQueryResult implements Dto {

    private static final long serialVersionUID = -5188205237701897225L;

    public static FastJsonQueryResult of(QueryResult queryResult) {
        if (Objects.isNull(queryResult)) {
            return null;
        } else {
            return new FastJsonQueryResult(
                    Optional.ofNullable(queryResult.getSequences()).map(
                            f -> f.stream().map(FastJsonSequence::of).collect(Collectors.toList())
                    ).orElse(null)
            );
        }
    }

    @JSONField(name = "sequences", ordinal = 1)
    private List<FastJsonSequence> sequences;

    public FastJsonQueryResult() {
    }

    public FastJsonQueryResult(List<FastJsonSequence> sequences) {
        this.sequences = sequences;
    }

    public List<FastJsonSequence> getSequences() {
        return sequences;
    }

    public void setSequences(List<FastJsonSequence> sequences) {
        this.sequences = sequences;
    }

    @Override
    public String toString() {
        return "FastJsonQueryResult{" +
                "sequences=" + sequences +
                '}';
    }

    /**
     * FastJson 序列。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    public static class FastJsonSequence implements Dto {

        private static final long serialVersionUID = -5265384164653308314L;

        public static FastJsonSequence of(QueryResult.Sequence sequence) {
            if (Objects.isNull(sequence)) {
                return null;
            } else {
                return new FastJsonSequence(
                        FastJsonBridgeDataKey.of(sequence.getBridgeDataKey()),
                        Optional.ofNullable(sequence.getBridgeDatas()).map(
                                f -> f.stream().map(FastJsonBridgeData::of).collect(Collectors.toList())
                        ).orElse(null),
                        sequence.getStartDate(),
                        sequence.getEndDate()
                );
            }
        }

        @JSONField(name = "bridge_data_key", ordinal = 1)
        private FastJsonBridgeDataKey bridgeDataKey;

        @JSONField(name = "bridge_datas", ordinal = 2)
        private List<FastJsonBridgeData> bridgeDatas;

        @JSONField(name = "start_date", ordinal = 3)
        private Date startDate;

        @JSONField(name = "end_date", ordinal = 4)
        private Date endDate;

        public FastJsonSequence() {
        }

        public FastJsonSequence(
                FastJsonBridgeDataKey bridgeDataKey, List<FastJsonBridgeData> bridgeDatas, Date startDate, Date endDate
        ) {
            this.bridgeDataKey = bridgeDataKey;
            this.bridgeDatas = bridgeDatas;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public FastJsonBridgeDataKey getBridgeDataKey() {
            return bridgeDataKey;
        }

        public void setBridgeDataKey(FastJsonBridgeDataKey bridgeDataKey) {
            this.bridgeDataKey = bridgeDataKey;
        }

        public List<FastJsonBridgeData> getBridgeDatas() {
            return bridgeDatas;
        }

        public void setBridgeDatas(List<FastJsonBridgeData> bridgeDatas) {
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
            return "FastJsonSequence{" +
                    "bridgeDataKey=" + bridgeDataKey +
                    ", bridgeDatas=" + bridgeDatas +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    '}';
        }
    }
}
