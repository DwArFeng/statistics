package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.handler.MapLocalCacheHandler;
import com.dwarfeng.statistics.stack.handler.Mapper;
import com.dwarfeng.statistics.stack.service.MapQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class MapQosServiceImpl implements MapQosService {

    final
    MapLocalCacheHandler mapLocalCacheHandler;

    private final ServiceExceptionMapper sem;

    private final Lock lock = new ReentrantLock();

    public MapQosServiceImpl(
            MapLocalCacheHandler mapLocalCacheHandler,
            ServiceExceptionMapper sem
    ) {
        this.mapLocalCacheHandler = mapLocalCacheHandler;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    public Mapper getMapper(String mapperType) throws ServiceException {
        lock.lock();
        try {
            return mapLocalCacheHandler.get(mapperType);
        } catch (HandlerException e) {
            throw ServiceExceptionHelper.logParse("从本地缓存中获取映射器时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public void clearLocalCache() throws ServiceException {
        lock.lock();
        try {
            mapLocalCacheHandler.clear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("清除本地缓存时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }
}
