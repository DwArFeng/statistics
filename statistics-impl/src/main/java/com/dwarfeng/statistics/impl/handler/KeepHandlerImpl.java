package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.stack.bean.dto.BridgeData;
import com.dwarfeng.statistics.stack.exception.LatestException;
import com.dwarfeng.statistics.stack.exception.UpdateException;
import com.dwarfeng.statistics.stack.handler.KeepHandler;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class KeepHandlerImpl implements KeepHandler {

    private final List<Bridge> bridges;

    @Value("${bridge.keeper.type}")
    private String keeperType;

    private Bridge.Keeper keeper;

    protected KeepHandlerImpl(List<Bridge> bridges) {
        this.bridges = Optional.ofNullable(bridges).orElse(Collections.emptyList());
    }

    @PostConstruct
    public void init() throws Exception {
        // 从保持器列表中找到对应类型的保持器。
        Bridge bridge = bridges.stream().filter(b -> b.supportType(keeperType)).findAny()
                .orElseThrow(() -> new HandlerException("未知的 bridge 类型: " + keeperType));
        // 如果桥接器支持保持器，则获取保持器。
        keeper = bridge.getKeeper();
    }

    @Override
    public void update(BridgeData bridgeData) throws HandlerException {
        try {
            keeper.update(bridgeData);
        } catch (UpdateException e) {
            throw e;
        } catch (Exception e) {
            throw new UpdateException(e, Collections.singletonList(bridgeData));
        }
    }

    @Override
    public void update(List<BridgeData> bridgeDatas) throws HandlerException {
        try {
            keeper.update(bridgeDatas);
        } catch (UpdateException e) {
            throw e;
        } catch (Exception e) {
            throw new UpdateException(e, bridgeDatas);
        }
    }

    @Override
    public BridgeData latest(LongIdKey statisticsSettingKey) throws HandlerException {
        try {
            return keeper.latest(statisticsSettingKey);
        } catch (LatestException e) {
            throw e;
        } catch (Exception e) {
            throw new LatestException(e, Collections.singletonList(statisticsSettingKey));
        }
    }

    @Override
    public List<BridgeData> latest(List<LongIdKey> statisticsSettingKeys) throws HandlerException {
        try {
            return keeper.latest(statisticsSettingKeys);
        } catch (LatestException e) {
            throw e;
        } catch (Exception e) {
            throw new LatestException(e, statisticsSettingKeys);
        }
    }

    @Override
    public String toString() {
        return "KeepHandlerImpl{" +
                "bridges=" + bridges +
                ", keeper=" + keeper +
                '}';
    }
}
