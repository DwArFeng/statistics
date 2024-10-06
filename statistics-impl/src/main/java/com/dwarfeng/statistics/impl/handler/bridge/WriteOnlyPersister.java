package com.dwarfeng.statistics.impl.handler.bridge;

import com.dwarfeng.statistics.stack.bean.dto.*;
import com.dwarfeng.statistics.stack.exception.LookupNotSupportedException;
import com.dwarfeng.statistics.stack.exception.NativeQueryNotSupportedException;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.List;

/**
 * 只写持久器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class WriteOnlyPersister extends AbstractPersister {

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
        throw new LookupNotSupportedException();
    }

    @Override
    public List<LookupResult> lookup(List<LookupInfo> lookupInfos) throws HandlerException {
        throw new LookupNotSupportedException();
    }

    @Override
    public QueryResult nativeQuery(NativeQueryInfo queryInfo) throws HandlerException {
        throw new NativeQueryNotSupportedException();
    }

    @Override
    public List<QueryResult> nativeQuery(List<NativeQueryInfo> queryInfos) throws HandlerException {
        throw new NativeQueryNotSupportedException();
    }

    @Override
    public String toString() {
        return "WriteOnlyPersister{}";
    }
}
