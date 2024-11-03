package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.bean.dto.*;
import com.dwarfeng.statistics.stack.bean.key.BridgeDataKey;
import com.dwarfeng.statistics.stack.handler.ViewHandler;
import com.dwarfeng.statistics.stack.service.ViewQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewQosServiceImpl implements ViewQosService {

    private final ViewHandler viewHandler;

    private final ServiceExceptionMapper sem;

    public ViewQosServiceImpl(ViewHandler viewHandler, ServiceExceptionMapper sem) {
        this.viewHandler = viewHandler;
        this.sem = sem;
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
    public QueryResult lookup(QueryInfo queryInfo) throws ServiceException {
        try {
            return viewHandler.query(queryInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查看时发生异常", LogLevel.WARN, e, sem);
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
    public LookupResult query(LookupInfo lookupInfo) throws ServiceException {
        try {
            return viewHandler.lookup(lookupInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public String toString() {
        return "ViewQosServiceImpl{" +
                "viewHandler=" + viewHandler +
                ", sem=" + sem +
                '}';
    }
}
