package com.dwarfeng.statistics.impl.handler.bridge.influxdb.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.time.Instant;
import java.util.List;

/**
 * Influxdb 桥接器查询信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class InfluxdbBridgeQueryResult implements Dto {

    private static final long serialVersionUID = 6146889878011558075L;

    private List<InfluxdbBridgeSequence> sequences;

    public InfluxdbBridgeQueryResult() {
    }

    public InfluxdbBridgeQueryResult(List<InfluxdbBridgeSequence> sequences) {
        this.sequences = sequences;
    }

    public List<InfluxdbBridgeSequence> getSequences() {
        return sequences;
    }

    public void setSequences(List<InfluxdbBridgeSequence> sequences) {
        this.sequences = sequences;
    }

    @Override
    public String toString() {
        return "InfluxdbBridgeQueryResult{" +
                "sequences=" + sequences +
                '}';
    }

    public static class InfluxdbBridgeSequence implements Dto {

        private static final long serialVersionUID = 5891289048475631914L;

        private InfluxdbBridgeDataGroup dataGroup;
        private List<InfluxdbBridgeItem> items;
        private Instant startInstant;
        private Instant endInstant;

        public InfluxdbBridgeSequence() {
        }

        public InfluxdbBridgeSequence(
                InfluxdbBridgeDataGroup dataGroup, List<InfluxdbBridgeItem> items,
                Instant startInstant, Instant endInstant
        ) {
            this.dataGroup = dataGroup;
            this.items = items;
            this.startInstant = startInstant;
            this.endInstant = endInstant;
        }

        public InfluxdbBridgeDataGroup getDataGroup() {
            return dataGroup;
        }

        public void setDataGroup(InfluxdbBridgeDataGroup dataGroup) {
            this.dataGroup = dataGroup;
        }

        public List<InfluxdbBridgeItem> getItems() {
            return items;
        }

        public void setItems(List<InfluxdbBridgeItem> items) {
            this.items = items;
        }

        public Instant getStartInstant() {
            return startInstant;
        }

        public void setStartInstant(Instant startInstant) {
            this.startInstant = startInstant;
        }

        public Instant getEndInstant() {
            return endInstant;
        }

        public void setEndInstant(Instant endInstant) {
            this.endInstant = endInstant;
        }

        @Override
        public String toString() {
            return "InfluxdbBridgeSequence{" +
                    "dataGroup=" + dataGroup +
                    ", items=" + items +
                    ", startInstant=" + startInstant +
                    ", endInstant=" + endInstant +
                    '}';
        }
    }

    public static class InfluxdbBridgeItem implements Dto {

        private static final long serialVersionUID = 2971345554027553544L;

        private InfluxdbBridgeDataGroup dataGroup;
        private Object value;
        private Instant happenedInstant;

        public InfluxdbBridgeItem() {
        }

        public InfluxdbBridgeItem(InfluxdbBridgeDataGroup dataGroup, Object value, Instant happenedInstant) {
            this.dataGroup = dataGroup;
            this.value = value;
            this.happenedInstant = happenedInstant;
        }

        public InfluxdbBridgeDataGroup getDataGroup() {
            return dataGroup;
        }

        public void setDataGroup(InfluxdbBridgeDataGroup dataGroup) {
            this.dataGroup = dataGroup;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Instant getHappenedInstant() {
            return happenedInstant;
        }

        public void setHappenedInstant(Instant happenedInstant) {
            this.happenedInstant = happenedInstant;
        }

        @Override
        public String toString() {
            return "InfluxdbBridgeItem{" +
                    "dataGroup=" + dataGroup +
                    ", value=" + value +
                    ", happenedInstant=" + happenedInstant +
                    '}';
        }
    }
}
