package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.stack.bean.dto.*;
import com.dwarfeng.statistics.stack.bean.key.BridgeDataKey;
import com.dwarfeng.statistics.stack.handler.KeepHandler;
import com.dwarfeng.statistics.stack.handler.PersistHandler;
import com.dwarfeng.statistics.stack.handler.QueryHandler;
import com.dwarfeng.statistics.stack.handler.ViewHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Component
public class ViewHandlerImpl implements ViewHandler {

    private final KeepHandler keepHandler;
    private final PersistHandler persistHandler;
    private final QueryHandler queryHandler;

    private final ThreadPoolTaskExecutor executor;

    public ViewHandlerImpl(
            KeepHandler keepHandler,
            PersistHandler persistHandler,
            QueryHandler queryHandler,
            ThreadPoolTaskExecutor executor
    ) {
        this.keepHandler = keepHandler;
        this.persistHandler = persistHandler;
        this.queryHandler = queryHandler;
        this.executor = executor;
    }

    @Override
    public BridgeData latest(BridgeDataKey bridgeDataKey) throws HandlerException {
        return keepHandler.latest(bridgeDataKey);
    }

    @Override
    public List<BridgeData> latest(List<BridgeDataKey> bridgeDataKeys) throws HandlerException {
        return keepHandler.latest(bridgeDataKeys);
    }

    @Override
    public LookupResult lookup(LookupInfo lookupInfo) throws HandlerException {
        return persistHandler.lookup(lookupInfo);
    }

    @Override
    public List<LookupResult> lookup(List<LookupInfo> lookupInfos) throws HandlerException {
        return persistHandler.lookup(lookupInfos);
    }

    @Override
    public QueryResult nativeQuery(NativeQueryInfo queryInfo) throws HandlerException {
        return persistHandler.nativeQuery(queryInfo);
    }

    @Override
    public List<QueryResult> nativeQuery(List<NativeQueryInfo> queryInfos) throws HandlerException {
        return persistHandler.nativeQuery(queryInfos);
    }

    @Override
    public QueryResult query(QueryInfo queryInfo) throws HandlerException {
        return queryHandler.query(queryInfo);
    }

    @Override
    public List<QueryResult> query(List<QueryInfo> queryInfos) throws HandlerException {
        return queryHandler.query(queryInfos);
    }

    @Override
    public CompletableFuture<BridgeData> latestAsync(BridgeDataKey bridgeDataKey) {
        return CompletableFuture.supplyAsync(() -> wrappedLatest(bridgeDataKey), executor);
    }

    private BridgeData wrappedLatest(BridgeDataKey bridgeDataKey) throws CompletionException {
        try {
            return keepHandler.latest(bridgeDataKey);
        } catch (HandlerException e) {
            throw new CompletionException(e);
        }
    }

    @Override
    public CompletableFuture<List<BridgeData>> latestAsync(List<BridgeDataKey> bridgeDataKeys) {
        return CompletableFuture.supplyAsync(() -> wrappedLatest(bridgeDataKeys), executor);
    }

    private List<BridgeData> wrappedLatest(List<BridgeDataKey> bridgeDataKeys) throws CompletionException {
        try {
            return keepHandler.latest(bridgeDataKeys);
        } catch (HandlerException e) {
            throw new CompletionException(e);
        }
    }

    @Override
    public CompletableFuture<LookupResult> lookupAsync(LookupInfo lookupInfo) {
        return CompletableFuture.supplyAsync(() -> wrappedLookup(lookupInfo), executor);
    }

    private LookupResult wrappedLookup(LookupInfo lookupInfo) throws CompletionException {
        try {
            return persistHandler.lookup(lookupInfo);
        } catch (HandlerException e) {
            throw new CompletionException(e);
        }
    }

    @Override
    public CompletableFuture<List<LookupResult>> lookupAsync(List<LookupInfo> lookupInfos) {
        return CompletableFuture.supplyAsync(() -> wrappedLookup(lookupInfos), executor);
    }

    private List<LookupResult> wrappedLookup(List<LookupInfo> lookupInfos) throws CompletionException {
        try {
            return persistHandler.lookup(lookupInfos);
        } catch (HandlerException e) {
            throw new CompletionException(e);
        }
    }

    @Override
    public CompletableFuture<QueryResult> nativeQueryAsync(NativeQueryInfo queryInfo) {
        return CompletableFuture.supplyAsync(() -> wrappedNativeQuery(queryInfo), executor);
    }

    private QueryResult wrappedNativeQuery(NativeQueryInfo queryInfo) throws CompletionException {
        try {
            return persistHandler.nativeQuery(queryInfo);
        } catch (HandlerException e) {
            throw new CompletionException(e);
        }
    }

    @Override
    public CompletableFuture<List<QueryResult>> nativeQueryAsync(List<NativeQueryInfo> queryInfos) {
        return CompletableFuture.supplyAsync(() -> wrappedNativeQuery(queryInfos), executor);
    }

    private List<QueryResult> wrappedNativeQuery(List<NativeQueryInfo> queryInfos) throws CompletionException {
        try {
            return persistHandler.nativeQuery(queryInfos);
        } catch (HandlerException e) {
            throw new CompletionException(e);
        }
    }

    @Override
    public CompletableFuture<QueryResult> queryAsync(QueryInfo queryInfo) {
        return CompletableFuture.supplyAsync(() -> wrappedQuery(queryInfo), executor);
    }

    private QueryResult wrappedQuery(QueryInfo queryInfo) throws CompletionException {
        try {
            return queryHandler.query(queryInfo);
        } catch (HandlerException e) {
            throw new CompletionException(e);
        }
    }

    @Override
    public CompletableFuture<List<QueryResult>> queryAsync(List<QueryInfo> queryInfos) {
        return CompletableFuture.supplyAsync(() -> wrappedQuery(queryInfos), executor);
    }

    private List<QueryResult> wrappedQuery(List<QueryInfo> queryInfos) throws CompletionException {
        try {
            return queryHandler.query(queryInfos);
        } catch (HandlerException e) {
            throw new CompletionException(e);
        }
    }

    @Override
    public String toString() {
        return "ViewHandlerImpl{" +
                "keepHandler=" + keepHandler +
                ", persistHandler=" + persistHandler +
                ", queryHandler=" + queryHandler +
                ", executor=" + executor +
                '}';
    }
}
