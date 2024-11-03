package com.dwarfeng.statistics.impl.handler.bridge.influxdb.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.time.Instant;
import java.util.List;

/**
 * Influxdb 桥接器查询结果。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class InfluxdbBridgeLookupResult implements Dto {

    private static final long serialVersionUID = 862018297816424402L;

    private InfluxdbBridgeDataGroup dataGroup;
    private List<Item> items;
    private long count;

    public InfluxdbBridgeLookupResult() {
    }

    public InfluxdbBridgeLookupResult(InfluxdbBridgeDataGroup dataGroup, List<Item> items, long count) {
        this.dataGroup = dataGroup;
        this.items = items;
        this.count = count;
    }

    public InfluxdbBridgeDataGroup getDataGroup() {
        return dataGroup;
    }

    public void setDataGroup(InfluxdbBridgeDataGroup dataGroup) {
        this.dataGroup = dataGroup;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public static class Item {

        private InfluxdbBridgeDataGroup dataGroup;
        private Object value;
        private Instant happenedInstant;

        public Item() {
        }

        public Item(InfluxdbBridgeDataGroup dataGroup, Object value, Instant happenedInstant) {
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
            return "Item{" +
                    "dataGroup=" + dataGroup +
                    ", value=" + value +
                    ", happenedInstant=" + happenedInstant +
                    '}';
        }
    }
}
