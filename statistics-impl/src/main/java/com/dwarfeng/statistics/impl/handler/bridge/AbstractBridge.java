package com.dwarfeng.statistics.impl.handler.bridge;

import com.dwarfeng.statistics.impl.handler.Bridge;

import java.util.Objects;

/**
 * 桥接器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractBridge implements Bridge {

    protected String bridgeType;

    public AbstractBridge() {
        this(null);
    }

    public AbstractBridge(String bridgeType) {
        this.bridgeType = bridgeType;
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(bridgeType, type);
    }

    public String getBridgeType() {
        return bridgeType;
    }

    public void setBridgeType(String bridgeType) {
        this.bridgeType = bridgeType;
    }

    @Override
    public String toString() {
        return "AbstractBridge{" +
                "bridgeType='" + bridgeType + '\'' +
                '}';
    }
}
