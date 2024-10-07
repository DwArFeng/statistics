package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.stack.exception.MapperException;
import com.dwarfeng.statistics.stack.exception.UnsupportedMapperTypeException;
import com.dwarfeng.statistics.stack.handler.Mapper;
import com.dwarfeng.statistics.stack.handler.MapperHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class MapperHandlerImpl implements MapperHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapperHandlerImpl.class);

    private final List<MapperMaker> mapperMakers;

    public MapperHandlerImpl(List<MapperMaker> mapperMakers) {
        this.mapperMakers = Optional.ofNullable(mapperMakers).orElse(Collections.emptyList());
    }

    @Override
    public boolean supportType(String type) throws HandlerException {
        try {
            return mapperMakers.stream().anyMatch(maker -> maker.supportType(type));
        } catch (Exception e) {
            throw new MapperException(e);
        }
    }

    @Override
    public Mapper make(String type) throws HandlerException {
        try {
            // 生成过滤器。
            LOGGER.debug("通过过滤器信息构建新的的映射器...");
            MapperMaker mapperMaker = mapperMakers.stream().filter(maker -> maker.supportType(type))
                    .findFirst().orElseThrow(() -> new UnsupportedMapperTypeException(type));
            Mapper mapper = mapperMaker.makeMapper();
            LOGGER.debug("映射器构建成功!");
            LOGGER.debug("映射器: {}", mapper);
            return mapper;
        } catch (MapperException e) {
            throw e;
        } catch (Exception e) {
            throw new MapperException(e);
        }
    }
}
