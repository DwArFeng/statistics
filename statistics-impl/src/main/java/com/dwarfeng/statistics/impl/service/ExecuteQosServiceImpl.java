package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.handler.ExecuteLocalCacheHandler;
import com.dwarfeng.statistics.stack.service.ExecuteQosService;
import com.dwarfeng.statistics.stack.struct.ExecuteInfo;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class ExecuteQosServiceImpl implements ExecuteQosService {

    private final ExecuteLocalCacheHandler executeLocalCacheHandler;

    private final ServiceExceptionMapper sem;

    public ExecuteQosServiceImpl(ExecuteLocalCacheHandler executeLocalCacheHandler, ServiceExceptionMapper sem) {
        this.executeLocalCacheHandler = executeLocalCacheHandler;
        this.sem = sem;
    }

    @Override
    public ExecuteInfo getExecuteInfo(LongIdKey recordSettingKey) throws ServiceException {
        try {
            return executeLocalCacheHandler.get(recordSettingKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取指定记录设置的执行信息时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void clearLocalCache() throws ServiceException {
        try {
            executeLocalCacheHandler.clear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("清除本地缓存时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
