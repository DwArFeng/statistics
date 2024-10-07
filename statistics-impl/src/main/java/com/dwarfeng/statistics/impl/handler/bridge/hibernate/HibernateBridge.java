package com.dwarfeng.statistics.impl.handler.bridge.hibernate;

import com.dwarfeng.statistics.impl.handler.bridge.PersisterOnlyBridge;
import org.springframework.stereotype.Component;

/**
 * Hibernate 桥接器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class HibernateBridge extends PersisterOnlyBridge {

    public static final String BRIDGE_TYPE = "hibernate";

    private final HibernateBridgePersister persister;

    public HibernateBridge(HibernateBridgePersister persister) {
        super(BRIDGE_TYPE);
        this.persister = persister;
    }

    @Override
    public Persister getPersister() {
        return persister;
    }

    @Override
    public String toString() {
        return "HibernateBridge{" +
                "persister=" + persister +
                ", bridgeType='" + bridgeType + '\'' +
                '}';
    }
}
