package com.dwarfeng.statistics.impl.handler.mapper;

/**
 * 聚合映射器。
 *
 * <p>
 * 聚合映射器是一个抽象的映射器，它执行映射操作时，会遍历数据表中的所有序列，
 * 并将每个序列中的所有数据条目聚合为一个数据条目。<br>
 * 聚合后的数据条目的主键为序列的主键，数据条目的值由具体的聚合映射器决定，
 * 数据条目的发生时间为序列的开始时间和结束时间的中间值。
 *
 * <p>
 * 聚合映射器要求数据表中的所有序列的统计设置主键不能为 null，否则会抛出异常。
 *
 * @author DwArFeng
 * @see com.dwarfeng.statistics.sdk.handler.mapper.AggregateMapper
 * @since 1.0.0
 * @deprecated 该对象已经被废弃，请使用 sdk 模块下的对应对象代替。
 */
@Deprecated
public abstract class AggregateMapper extends com.dwarfeng.statistics.sdk.handler.mapper.AggregateMapper {

    public AggregateMapper() {
    }
}
