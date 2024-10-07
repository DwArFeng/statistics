package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.stack.handler.MapLocalCacheHandler;
import com.dwarfeng.statistics.stack.handler.Mapper;
import com.dwarfeng.statistics.stack.handler.MapperHandler;
import com.dwarfeng.subgrade.impl.handler.Fetcher;
import com.dwarfeng.subgrade.impl.handler.GeneralLocalCacheHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

@Component
public class MapLocalCacheHandlerImpl implements MapLocalCacheHandler {

    private final GeneralLocalCacheHandler<String, Mapper> handler;

    public MapLocalCacheHandlerImpl(MapperFetcher mapperFetcher) {
        handler = new GeneralLocalCacheHandler<>(mapperFetcher);
    }

    @BehaviorAnalyse
    @Override
    public boolean exists(String key) throws HandlerException {
        return handler.exists(key);
    }

    @BehaviorAnalyse
    @Override
    public Mapper get(String key) throws HandlerException {
        return handler.get(key);
    }

    @BehaviorAnalyse
    @Override
    public boolean remove(String key) {
        return handler.remove(key);
    }

    @BehaviorAnalyse
    @Override
    public void clear() {
        handler.clear();
    }

    @Component
    public static class MapperFetcher implements Fetcher<String, Mapper> {

        private final MapperHandler mapperHandler;

        public MapperFetcher(MapperHandler mapperHandler) {
            this.mapperHandler = mapperHandler;
        }

        @Override
        public boolean exists(String key) throws Exception {
            return mapperHandler.supportType(key);
        }

        @Override
        public Mapper fetch(String key) throws Exception {
            return mapperHandler.make(key);
        }
    }
}
