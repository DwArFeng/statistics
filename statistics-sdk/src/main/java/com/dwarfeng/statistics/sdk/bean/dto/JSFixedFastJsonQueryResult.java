package com.dwarfeng.statistics.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.stack.bean.dto.QueryResult;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
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
public class JSFixedFastJsonQueryResult implements Dto {

    private static final long serialVersionUID = -6098540113668033740L;

    public static JSFixedFastJsonQueryResult of(QueryResult queryResult) {
        if (Objects.isNull(queryResult)) {
            return null;
        } else {
            return new JSFixedFastJsonQueryResult(
                    Optional.ofNullable(queryResult.getSequences()).map(
                            f -> f.stream().map(JSFixedFastJsonSequence::of).collect(Collectors.toList())
                    ).orElse(null)
            );
        }
    }

    @JSONField(name = "sequences", ordinal = 1)
    private List<JSFixedFastJsonSequence> sequences;

    public JSFixedFastJsonQueryResult() {
    }

    public JSFixedFastJsonQueryResult(List<JSFixedFastJsonSequence> sequences) {
        this.sequences = sequences;
    }

    public List<JSFixedFastJsonSequence> getSequences() {
        return sequences;
    }

    public void setSequences(List<JSFixedFastJsonSequence> sequences) {
        this.sequences = sequences;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonQueryResult{" +
                "sequences=" + sequences +
                '}';
    }

    /**
     * JSFixed FastJson序列。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    public static class JSFixedFastJsonSequence implements Dto {

        private static final long serialVersionUID = -5964680980663723267L;

        public static JSFixedFastJsonSequence of(QueryResult.Sequence sequence) {
            if (Objects.isNull(sequence)) {
                return null;
            } else {
                return new JSFixedFastJsonSequence(
                        JSFixedFastJsonLongIdKey.of(sequence.getStatisticsSettingKey()),
                        Optional.ofNullable(sequence.getBridgeDatas()).map(
                                f -> f.stream().map(JSFixedFastJsonBridgeData::of).collect(Collectors.toList())
                        ).orElse(null),
                        sequence.getStartDate(),
                        sequence.getEndDate()
                );
            }
        }

        @JSONField(name = "statistics_setting_key", ordinal = 1)
        private JSFixedFastJsonLongIdKey statisticsSettingKey;

        @JSONField(name = "bridge_datas", ordinal = 2)
        private List<JSFixedFastJsonBridgeData> bridgeDatas;

        @JSONField(name = "start_date", ordinal = 3)
        private Date startDate;

        @JSONField(name = "end_date", ordinal = 4)
        private Date endDate;

        public JSFixedFastJsonSequence() {
        }

        public JSFixedFastJsonSequence(
                JSFixedFastJsonLongIdKey statisticsSettingKey, List<JSFixedFastJsonBridgeData> bridgeDatas,
                Date startDate, Date endDate
        ) {
            this.statisticsSettingKey = statisticsSettingKey;
            this.bridgeDatas = bridgeDatas;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public JSFixedFastJsonLongIdKey getStatisticsSettingKey() {
            return statisticsSettingKey;
        }

        public void setStatisticsSettingKey(JSFixedFastJsonLongIdKey statisticsSettingKey) {
            this.statisticsSettingKey = statisticsSettingKey;
        }

        public List<JSFixedFastJsonBridgeData> getBridgeDatas() {
            return bridgeDatas;
        }

        public void setBridgeDatas(List<JSFixedFastJsonBridgeData> bridgeDatas) {
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
            return "JSFixedFastJsonSequence{" +
                    "statisticsSettingKey=" + statisticsSettingKey +
                    ", bridgeDatas=" + bridgeDatas +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    '}';
        }
    }
}
