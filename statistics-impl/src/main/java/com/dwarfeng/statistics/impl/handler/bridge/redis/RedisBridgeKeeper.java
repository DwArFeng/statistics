package com.dwarfeng.statistics.impl.handler.bridge.redis;

import com.dwarfeng.dct.handler.ValueCodingHandler;
import com.dwarfeng.statistics.impl.handler.bridge.FullKeeper;
import com.dwarfeng.statistics.impl.handler.bridge.redis.bean.RedisBridgeBridgeData;
import com.dwarfeng.statistics.impl.handler.bridge.redis.service.RedisBridgeBridgeDataMaintainService;
import com.dwarfeng.statistics.stack.bean.dto.BridgeData;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Redis 桥接器保持器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class RedisBridgeKeeper extends FullKeeper {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisBridgeKeeper.class);

    private final RedisBridgeBridgeDataMaintainService service;

    private final ValueCodingHandler valueCodingHandler;

    @Value("${bridge.redis.earlier_override}")
    private boolean allowEarlierDataOverride;

    public RedisBridgeKeeper(
            RedisBridgeBridgeDataMaintainService service,
            @Qualifier("redisBridge.valueCodingHandler") ValueCodingHandler valueCodingHandler
    ) {
        this.service = service;
        this.valueCodingHandler = valueCodingHandler;
    }

    @Override
    protected void doUpdate(BridgeData data) throws Exception {
        if (!allowEarlierDataOverride) {
            RedisBridgeBridgeData oldEntity = service.getIfExists(data.getStatisticsSettingKey());
            if (Objects.nonNull(oldEntity) && oldEntity.getHappenedDate().after(data.getHappenedDate())) {
                logEarlierDataWillNotUpdate(data, oldEntity);
                return;
            }
        }
        RedisBridgeBridgeData entity = transformData(data);
        service.insertOrUpdate(entity);
    }

    @Override
    protected void doUpdate(List<BridgeData> datas) throws Exception {
        List<LongIdKey> entityKeys = datas.stream().map(BridgeData::getStatisticsSettingKey)
                .collect(Collectors.toList());
        Map<LongIdKey, RedisBridgeBridgeData> oldEntityMap = service.batchGetIfExists(entityKeys).stream()
                .collect(Collectors.toMap(Entity::getKey, Function.identity()));
        List<RedisBridgeBridgeData> ds = new ArrayList<>();
        for (BridgeData data : datas) {
            if (!allowEarlierDataOverride) {
                RedisBridgeBridgeData oldEntity = oldEntityMap.get(data.getStatisticsSettingKey());
                if (Objects.nonNull(oldEntity) && oldEntity.getHappenedDate().after(data.getHappenedDate())) {
                    logEarlierDataWillNotUpdate(data, oldEntity);
                    continue;
                }
            }
            RedisBridgeBridgeData entity = transformData(data);
            ds.add(entity);
        }
        service.batchInsertOrUpdate(ds);
    }

    private void logEarlierDataWillNotUpdate(BridgeData data, RedisBridgeBridgeData oldEntity) {
        String message = "数据点 " + data.getStatisticsSettingKey() + " 的发生时间 " + data.getHappenedDate() +
                " 早于保持器中的数据的发生时间 " + oldEntity.getHappenedDate() + ", 不更新";
        LOGGER.debug(message);
    }

    @Override
    protected BridgeData doLatest(LongIdKey statisticsSettingKey) throws Exception {
        RedisBridgeBridgeData entity = service.getIfExists(statisticsSettingKey);
        return reverseTransform(entity);
    }

    @Override
    protected List<BridgeData> doLatest(List<LongIdKey> statisticsSettingKeys) throws Exception {
        List<RedisBridgeBridgeData> entities = service.batchGetIfExists(statisticsSettingKeys);
        List<BridgeData> datas = new ArrayList<>(entities.size());
        for (RedisBridgeBridgeData entity : entities) {
            datas.add(reverseTransform(entity));
        }
        return datas;
    }

    private RedisBridgeBridgeData transformData(@Nullable BridgeData data) throws Exception {
        if (Objects.isNull(data)) {
            return null;
        }
        String flatValue = valueCodingHandler.encode(data.getValue());
        return new RedisBridgeBridgeData(
                data.getStatisticsSettingKey(),
                flatValue,
                data.getHappenedDate()
        );
    }

    private BridgeData reverseTransform(@Nullable RedisBridgeBridgeData data) throws Exception {
        if (Objects.isNull(data)) {
            return null;
        }
        Object value = valueCodingHandler.decode(data.getValue());
        return new BridgeData(
                data.getKey(),
                value,
                data.getHappenedDate()
        );
    }

    @Override
    public String toString() {
        return "RedisBridgeKeeper{" +
                "service=" + service +
                ", valueCodingHandler=" + valueCodingHandler +
                ", allowEarlierDataOverride=" + allowEarlierDataOverride +
                '}';
    }
}
