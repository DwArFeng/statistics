package com.dwarfeng.statistics.impl.handler.bridge.drain;

import com.dwarfeng.statistics.impl.handler.bridge.WriteOnlyPersister;
import com.dwarfeng.statistics.stack.bean.dto.BridgeData;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Drain 桥接器持久器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class DrainBridgePersister extends WriteOnlyPersister {

    @Override
    protected void doRecord(BridgeData bridgeData) {
    }

    @Override
    protected void doRecord(List<BridgeData> bridgeDatas) {
    }
}
