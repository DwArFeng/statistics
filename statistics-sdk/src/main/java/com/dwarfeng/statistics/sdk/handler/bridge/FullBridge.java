package com.dwarfeng.statistics.sdk.handler.bridge;

/**
 * 完整桥接器。
 *
 * <p>
 * 完整桥接器是指同时支持保持器和持久器的桥接器。
 *
 * @author zhaofz
 * @since 1.4.0
 */
public abstract class FullBridge extends AbstractBridge {

    public FullBridge() {
    }

    public FullBridge(String bridgeType) {
        super(bridgeType);
    }

    @Override
    public String toString() {
        return "FullBridge{" +
                "bridgeType='" + bridgeType + '\'' +
                '}';
    }
}
