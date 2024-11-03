package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.bean.dto.*;
import com.dwarfeng.statistics.stack.bean.key.BridgeDataKey;
import com.dwarfeng.statistics.stack.handler.ViewHandler;
import com.dwarfeng.statistics.stack.service.ViewService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ViewServiceImpl implements ViewService {

    private final ViewHandler viewHandler;

    private final ServiceExceptionMapper sem;

    public ViewServiceImpl(ViewHandler viewHandler, ServiceExceptionMapper sem) {
        this.viewHandler = viewHandler;
        this.sem = sem;
    }

    @Override
    public BridgeData latest(BridgeDataKey bridgeDataKey) throws ServiceException {
        try {
            return viewHandler.latest(bridgeDataKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询最新数据时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public List<BridgeData> latest(List<BridgeDataKey> bridgeDataKeys) throws ServiceException {
        try {
            return viewHandler.latest(bridgeDataKeys);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询最新数据时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public LookupResult lookup(LookupInfo lookupInfo) throws ServiceException {
        try {
            return viewHandler.lookup(lookupInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查看时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public List<LookupResult> lookup(List<LookupInfo> lookupInfos) throws ServiceException {
        try {
            return viewHandler.lookup(lookupInfos);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查看时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public QueryResult query(QueryInfo queryInfo) throws ServiceException {
        try {
            return viewHandler.query(queryInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public List<QueryResult> query(List<QueryInfo> queryInfos) throws ServiceException {
        try {
            return viewHandler.query(queryInfos);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public QueryResult nativeQuery(NativeQueryInfo queryInfo) throws ServiceException {
        try {
            return viewHandler.nativeQuery(queryInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("原生查询时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public List<QueryResult> nativeQuery(List<NativeQueryInfo> queryInfos) throws ServiceException {
        try {
            return viewHandler.nativeQuery(queryInfos);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("原生查询时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public CompletableFuture<BridgeData> latestAsync(BridgeDataKey bridgeDataKey) throws ServiceException {
        try {
            return viewHandler.latestAsync(bridgeDataKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询最新数据时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public CompletableFuture<List<BridgeData>> latestAsync(List<BridgeDataKey> bridgeDataKeys)
            throws ServiceException {
        try {
            return viewHandler.latestAsync(bridgeDataKeys);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询最新数据时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public CompletableFuture<LookupResult> lookupAsync(LookupInfo lookupInfo) throws ServiceException {
        try {
            return viewHandler.lookupAsync(lookupInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查看时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public CompletableFuture<List<LookupResult>> lookupAsync(List<LookupInfo> lookupInfos) throws ServiceException {
        try {
            return viewHandler.lookupAsync(lookupInfos);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查看时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public CompletableFuture<QueryResult> nativeQueryAsync(NativeQueryInfo queryInfo) throws ServiceException {
        try {
            return viewHandler.nativeQueryAsync(queryInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("原生查询时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public CompletableFuture<List<QueryResult>> nativeQueryAsync(List<NativeQueryInfo> queryInfos)
            throws ServiceException {
        try {
            return viewHandler.nativeQueryAsync(queryInfos);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("原生查询时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public CompletableFuture<QueryResult> queryAsync(QueryInfo queryInfo) throws ServiceException {
        try {
            return viewHandler.queryAsync(queryInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public CompletableFuture<List<QueryResult>> queryAsync(List<QueryInfo> queryInfos) throws ServiceException {
        try {
            return viewHandler.queryAsync(queryInfos);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public String toString() {
        return "ViewServiceImpl{" +
                "viewHandler=" + viewHandler +
                ", sem=" + sem +
                '}';
    }
}
