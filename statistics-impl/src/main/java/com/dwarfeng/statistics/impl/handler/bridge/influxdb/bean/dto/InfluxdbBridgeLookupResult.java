package com.dwarfeng.statistics.impl.handler.bridge.influxdb.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Influxdb 桥接器查询结果。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class InfluxdbBridgeLookupResult implements Dto {

    private static final long serialVersionUID = -5703434419075168238L;

    private String measurement;

    private List<Item> items;

    private long count;

    public InfluxdbBridgeLookupResult() {
    }

    public InfluxdbBridgeLookupResult(String measurement, List<Item> items, long count) {
        this.measurement = measurement;
        this.items = items;
        this.count = count;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
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

    @Override
    public String toString() {
        return "InfluxdbBridgeLookupResult{" +
                "measurement='" + measurement + '\'' +
                ", items=" + items +
                ", count=" + count +
                '}';
    }

    public static class Item {

        private String measurement;
        private Map<String, Object> valueMap;
        private Instant happenedInstant;

        public Item() {
        }

        public Item(String measurement, Map<String, Object> valueMap, Instant happenedInstant) {
            this.measurement = measurement;
            this.valueMap = valueMap;
            this.happenedInstant = happenedInstant;
        }

        public String getMeasurement() {
            return measurement;
        }

        public void setMeasurement(String measurement) {
            this.measurement = measurement;
        }

        public Map<String, Object> getValueMap() {
            return valueMap;
        }

        public void setValueMap(Map<String, Object> valueMap) {
            this.valueMap = valueMap;
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
                    "measurement='" + measurement + '\'' +
                    ", valueMap=" + valueMap +
                    ", happenedInstant=" + happenedInstant +
                    '}';
        }
    }
}
