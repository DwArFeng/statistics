package com.dwarfeng.statistics.impl.handler.bridge.influxdb.handler;

import com.dwarfeng.statistics.impl.handler.bridge.influxdb.bean.dto.*;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;
import com.influxdb.client.write.Point;

import java.util.List;

/**
 * Influxdb 桥接器数据处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface InfluxdbBridgeDataHandler extends Handler {

    /**
     * 写入。
     *
     * @param point 指定的数据点。
     * @throws HandlerException 处理器异常。
     */
    void write(Point point) throws HandlerException;

    /**
     * 写入。
     *
     * @param point 指定的数据点组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void write(List<Point> point) throws HandlerException;

    /**
     * 默认查看。
     *
     * @param lookupInfo 指定的查看信息。
     * @return 查看结果。
     * @throws HandlerException 处理器异常。
     */
    InfluxdbBridgeLookupResult lookup(InfluxdbBridgeLookupInfo lookupInfo) throws HandlerException;

    /**
     * 默认查询。
     *
     * <p>
     * 默认查询使用 InfluxDB 的 aggregateWindow 函数进行开窗聚合。
     *
     * @param queryInfo 指定的查询信息。
     * @return 查询结果。
     * @throws HandlerException 处理器异常。
     */
    InfluxdbBridgeQueryResult defaultQuery(InfluxdbBridgeDefaultQueryInfo queryInfo) throws HandlerException;

    /**
     * 自定义查询。
     *
     * <p>
     * 自定义查询使用默认的 Flux 语句过滤 range 和 measurement，然后拼接用户指定的 Flux 语句。<br>
     * 默认的 Flux 语句为：
     *
     * <pre>{@code
     * from(bucket: "xxx")
     *   |> range(start: v.timeRangeStart, stop: v.timeRangeStop)
     *   |> filter(fn: (r) => r["_measurement"] == "1")
     *   |> filter(fn: (r) => r["_field"] == "value")
     * }</pre>
     * <p>
     * 用户指定的 Flux 语句拼接在默认的 Flux 语句之后，可以为任意合法的 Flux 语句，但是应该保证 Flux 语句应保证返回的
     * {@link com.influxdb.query.FluxTable} 中的 {@link com.influxdb.query.FluxRecord} 中包含 _time 和 _value 字段。
     *
     * @param queryInfo 指定的查询信息。
     * @return 查询结果。
     * @throws HandlerException 处理器异常。
     */
    InfluxdbBridgeQueryResult customQuery(InfluxdbBridgeCustomQueryInfo queryInfo) throws HandlerException;
}
