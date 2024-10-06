package com.dwarfeng.statistics.impl.handler.bridge;

import com.dwarfeng.statistics.stack.bean.dto.*;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.List;

/**
 * 完整持久器。
 *
 * <p>
 * 完整持久器是指同时支持写入和查询的持久器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class FullPersister extends AbstractPersister {

    @Override
    public void record(BridgeData bridgeData) throws HandlerException {
        try {
            doRecord(bridgeData);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    protected abstract void doRecord(BridgeData bridgeData) throws Exception;

    @Override
    public void record(List<BridgeData> bridgeDatas) throws HandlerException {
        try {
            doRecord(bridgeDatas);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    protected abstract void doRecord(List<BridgeData> bridgeDatas) throws Exception;

    @Override
    public LookupResult lookup(LookupInfo lookupInfo) throws HandlerException {
        try {
            return doLookup(lookupInfo);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    protected abstract LookupResult doLookup(LookupInfo lookupInfo) throws Exception;

    @Override
    public List<LookupResult> lookup(List<LookupInfo> lookupInfos) throws HandlerException {
        try {
            return doLookup(lookupInfos);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    protected abstract List<LookupResult> doLookup(List<LookupInfo> lookupInfos) throws Exception;

    @Override
    public QueryResult nativeQuery(NativeQueryInfo queryInfo) throws HandlerException {
        try {
            return doNativeQuery(queryInfo);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    protected abstract QueryResult doNativeQuery(NativeQueryInfo queryInfo) throws Exception;

    @Override
    public List<QueryResult> nativeQuery(List<NativeQueryInfo> queryInfos) throws HandlerException {
        try {
            return doNativeQuery(queryInfos);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    protected abstract List<QueryResult> doNativeQuery(List<NativeQueryInfo> queryInfos) throws Exception;

    @Override
    public String toString() {
        return "FullPersister{}";
    }
}
