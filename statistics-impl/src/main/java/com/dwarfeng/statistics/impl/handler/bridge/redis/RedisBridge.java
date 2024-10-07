package com.dwarfeng.statistics.impl.handler.bridge.redis;

import com.dwarfeng.statistics.impl.handler.bridge.KeeperOnlyBridge;
import org.springframework.stereotype.Component;

/**
 * Redis 桥接器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class RedisBridge extends KeeperOnlyBridge {

    public static final String BRIDGE_TYPE = "redis";

    private final RedisBridgeKeeper keeper;

    public RedisBridge(RedisBridgeKeeper keeper) {
        super(BRIDGE_TYPE);
        this.keeper = keeper;
    }

    @Override
    public Keeper getKeeper() {
        return keeper;
    }

    @Override
    public String toString() {
        return "RedisBridge{" +
                "keeper=" + keeper +
                ", bridgeType='" + bridgeType + '\'' +
                '}';
    }
}
