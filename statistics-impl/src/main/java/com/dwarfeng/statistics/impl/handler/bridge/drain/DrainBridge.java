package com.dwarfeng.statistics.impl.handler.bridge.drain;

import com.dwarfeng.statistics.impl.handler.bridge.FullBridge;
import org.springframework.stereotype.Component;

/**
 * Drain 桥接器。
 *
 * <p>
 * 简单地丢弃掉所有的数据的桥接器，一般用于占位。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class DrainBridge extends FullBridge {

    public static final String BRIDGE_TYPE = "drain";

    private final DrainBridgeKeeper keeper;
    private final DrainBridgePersister persister;

    public DrainBridge(
            DrainBridgeKeeper keeper,
            DrainBridgePersister persister
    ) {
        super(BRIDGE_TYPE);
        this.keeper = keeper;
        this.persister = persister;
    }

    @Override
    public Keeper getKeeper() {
        return keeper;
    }

    @Override
    public Persister getPersister() {
        return persister;
    }

    @Override
    public String toString() {
        return "DrainBridge{" +
                "keeper=" + keeper +
                ", persister=" + persister +
                ", bridgeType='" + bridgeType + '\'' +
                '}';
    }
}
