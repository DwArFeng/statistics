package com.dwarfeng.statistics.impl.handler.bridge;

import com.dwarfeng.statistics.stack.bean.dto.BridgeData;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.List;

/**
 * 完整保持器。
 *
 * <p>
 * 完整保持器是指同时支持写入和查询的保持器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class FullKeeper extends AbstractKeeper {

    @Override
    public void update(BridgeData bridgeData) throws HandlerException {
        try {
            doUpdate(bridgeData);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    protected abstract void doUpdate(BridgeData bridgeData) throws Exception;

    @Override
    public void update(List<BridgeData> bridgeDatas) throws HandlerException {
        try {
            doUpdate(bridgeDatas);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    protected abstract void doUpdate(List<BridgeData> bridgeDatas) throws Exception;

    @Override
    public BridgeData latest(LongIdKey statisticsSettingKey) throws HandlerException {
        try {
            return doLatest(statisticsSettingKey);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    protected abstract BridgeData doLatest(LongIdKey statisticsSettingKey) throws Exception;

    @Override
    public List<BridgeData> latest(List<LongIdKey> statisticsSettingKeys) throws HandlerException {
        try {
            return doLatest(statisticsSettingKeys);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    protected abstract List<BridgeData> doLatest(List<LongIdKey> statisticsSettingKeys) throws Exception;

    @Override
    public String toString() {
        return "FullKeeper{}";
    }
}