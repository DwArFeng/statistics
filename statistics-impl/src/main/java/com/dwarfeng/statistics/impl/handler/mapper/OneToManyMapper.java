package com.dwarfeng.statistics.impl.handler.mapper;

import java.util.List;

/**
 * 一对多映射器。
 *
 * <p>
 * 一对多映射器是一个抽象的映射器，它执行映射操作时，会遍历数据表中的所有序列，并将一个序列映射为多个序列。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class OneToManyMapper extends AbstractMapper {

    @Override
    protected List<Sequence> doMap(MapParam mapParam, List<Sequence> sequences) throws Exception {
        List<Sequence> result = new java.util.ArrayList<>();
        for (Sequence sequence : sequences) {
            result.addAll(doOneToManyMap(mapParam, sequence));
        }
        return result;
    }

    /**
     * 执行一对多映射操作。
     *
     * @param mapParam 映射参数。
     * @param sequence 指定的序列。
     * @return 映射后的序列组成的列表。
     * @throws Exception 映射操作中发生的异常。
     */
    protected abstract List<Sequence> doOneToManyMap(MapParam mapParam, Sequence sequence) throws Exception;
}
