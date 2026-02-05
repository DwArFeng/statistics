package com.dwarfeng.statistics.impl.handler.mapper;

/**
 * 一对多映射器。
 *
 * <p>
 * 一对多映射器是一个抽象的映射器，它执行映射操作时，会遍历数据表中的所有序列，并将一个序列映射为多个序列。
 *
 * @author DwArFeng
 * @see com.dwarfeng.statistics.sdk.handler.mapper.OneToManyMapper
 * @since 1.0.0
 * @deprecated 该对象已经被废弃，请使用 sdk 模块下的对应对象代替。
 */
@Deprecated
public abstract class OneToManyMapper extends com.dwarfeng.statistics.sdk.handler.mapper.OneToManyMapper {

    public OneToManyMapper() {
    }
}
