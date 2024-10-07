package com.dwarfeng.statistics.impl.handler.mapper;

import com.dwarfeng.statistics.stack.exception.MapperException;
import com.dwarfeng.statistics.stack.exception.MapperExecutionException;
import com.dwarfeng.statistics.stack.handler.Mapper;

import java.util.List;

/**
 * 映射器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractMapper implements Mapper {

    @Override
    public List<Sequence> map(MapParam mapParam, List<Sequence> sequences) throws MapperException {
        try {
            return doMap(mapParam, sequences);
        } catch (MapperException e) {
            throw e;
        } catch (Exception e) {
            throw new MapperExecutionException(e);
        }
    }

    /**
     * 执行映射操作。
     *
     * @param mapParam  映射参数。
     * @param sequences 待映射的序列组成的列表。
     * @return 映射后的序列组成的列表。
     * @throws Exception 映射过程中出现的任何异常。
     */
    protected abstract List<Sequence> doMap(MapParam mapParam, List<Sequence> sequences) throws Exception;
}
