package com.dwarfeng.statistics.impl.handler.bridge.drain;

import com.dwarfeng.statistics.impl.handler.bridge.WriteOnlyKeeper;
import com.dwarfeng.statistics.stack.bean.dto.BridgeData;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Drain 桥接器保持器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class DrainBridgeKeeper extends WriteOnlyKeeper {

    @Override
    protected void doUpdate(BridgeData bridgeData) {
    }

    @Override
    protected void doUpdate(List<BridgeData> bridgeDatas) {
    }
}
