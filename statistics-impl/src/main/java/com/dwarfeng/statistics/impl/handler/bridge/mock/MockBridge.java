package com.dwarfeng.statistics.impl.handler.bridge.mock;

import com.dwarfeng.statistics.impl.handler.bridge.FullBridge;
import org.springframework.stereotype.Component;

/**
 * 模拟桥接器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class MockBridge extends FullBridge {

    public static final String BRIDGE_TYPE = "mock";

    private final MockBridgeKeeper mockBridgeKeeper;
    private final MockBridgePersister mockBridgePersister;

    public MockBridge(
            MockBridgeKeeper mockBridgeKeeper,
            MockBridgePersister mockBridgePersister
    ) {
        super(BRIDGE_TYPE);
        this.mockBridgeKeeper = mockBridgeKeeper;
        this.mockBridgePersister = mockBridgePersister;
    }

    @Override
    public Keeper getKeeper() {
        return mockBridgeKeeper;
    }

    @Override
    public Persister getPersister() {
        return mockBridgePersister;
    }

    @Override
    public String toString() {
        return "MockBridge{" +
                "mockBridgeKeeper=" + mockBridgeKeeper +
                ", mockBridgePersister=" + mockBridgePersister +
                ", bridgeType='" + bridgeType + '\'' +
                '}';
    }
}
