package com.dwarfeng.statistics.impl.handler.bridge.influxdb;

import com.dwarfeng.statistics.impl.handler.bridge.PersisterOnlyBridge;
import org.springframework.stereotype.Component;

/**
 * Influxdb 桥接器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class InfluxdbBridge extends PersisterOnlyBridge {

    public static final String BRIDGE_TYPE = "influxdb";

    private final InfluxdbBridgePersister persister;

    public InfluxdbBridge(InfluxdbBridgePersister persister) {
        super(BRIDGE_TYPE);
        this.persister = persister;
    }

    @Override
    public Persister getPersister() {
        return persister;
    }

    @Override
    public String toString() {
        return "InfluxdbBridge{" +
                "persister=" + persister +
                '}';
    }
}
