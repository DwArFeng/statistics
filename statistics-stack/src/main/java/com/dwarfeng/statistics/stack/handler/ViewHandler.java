package com.dwarfeng.statistics.stack.handler;

import com.dwarfeng.statistics.stack.bean.dto.*;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 观察处理器。
 *
 * <p>
 * 该处理器包含了数据的检索、查询和查看方法，同时提供了这些方法的异步版本。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ViewHandler extends Handler {

    /**
     * 查询统计设置的最新数据。
     *
     * <p>
     * 如果统计设置主键对应的数据不存在，则返回的查询结果为 null。
     *
     * @param statisticsSettingKey 指定的统计设置对应的主键。
     * @return 指定的统计设置的最新数据。
     * @throws HandlerException 处理器异常。
     */
    BridgeData latest(LongIdKey statisticsSettingKey) throws HandlerException;

    /**
     * 查询统计设置的最新数据。
     *
     * <p>
     * 如果统计设置主键组成的列表中的某个索引处的统计设置主键对应的数据不存在，
     * 则返回的查询结果组成的列表该处索引对应的查询结果为 null。
     *
     * @param statisticsSettingKeys 指定的统计设置对应的主键组成的列表。
     * @return 指定的统计设置的最新数据组成的列表。
     * @throws HandlerException 处理器异常。
     */
    List<BridgeData> latest(List<LongIdKey> statisticsSettingKeys) throws HandlerException;

    /**
     * 查看。
     *
     * @param lookupInfo 查看信息。
     * @return 查看结果。
     * @throws HandlerException 处理器异常。
     */
    LookupResult lookup(LookupInfo lookupInfo) throws HandlerException;

    /**
     * 查看。
     *
     * @param lookupInfos 查看信息组成的列表。
     * @return 查看结果组成的列表。
     * @throws HandlerException 处理器异常。
     */
    List<LookupResult> lookup(List<LookupInfo> lookupInfos) throws HandlerException;

    /**
     * 原生查询。
     *
     * @param queryInfo 原生查询信息。
     * @return 查询结果。
     * @throws HandlerException 处理器异常。
     */
    QueryResult nativeQuery(NativeQueryInfo queryInfo) throws HandlerException;

    /**
     * 原生查询。
     *
     * @param queryInfos 原生查询信息组成的列表。
     * @return 查询结果组成的列表。
     * @throws HandlerException 处理器异常。
     */
    List<QueryResult> nativeQuery(List<NativeQueryInfo> queryInfos) throws HandlerException;

    /**
     * 查询。
     *
     * @param queryInfo 查询信息。
     * @return 查询结果。
     * @throws HandlerException 处理器异常。
     */
    QueryResult query(QueryInfo queryInfo) throws HandlerException;

    /**
     * 查询。
     *
     * @param queryInfos 查询信息组成的列表。
     * @return 查询结果组成的列表。
     * @throws HandlerException 处理器异常。
     */
    List<QueryResult> query(List<QueryInfo> queryInfos) throws HandlerException;

    /**
     * 异步查询统计设置的最新数据。
     *
     * @param statisticsSettingKey 指定的统计设置对应的主键。
     * @return 指定的统计设置的最新数据。
     * @throws HandlerException 处理器异常。
     */
    CompletableFuture<BridgeData> latestAsync(LongIdKey statisticsSettingKey) throws HandlerException;

    /**
     * 异步查询统计设置的最新数据。
     *
     * @param statisticsSettingKeys 指定的统计设置对应的主键组成的列表。
     * @return 指定的统计设置的最新数据组成的列表。
     * @throws HandlerException 处理器异常。
     */
    CompletableFuture<List<BridgeData>> latestAsync(List<LongIdKey> statisticsSettingKeys) throws HandlerException;

    /**
     * 异步查看。
     *
     * @param lookupInfo 查看信息。
     * @return 查看结果。
     * @throws HandlerException 处理器异常。
     */
    CompletableFuture<LookupResult> lookupAsync(LookupInfo lookupInfo) throws HandlerException;

    /**
     * 异步查看。
     *
     * @param lookupInfos 查看信息组成的列表。
     * @return 查看结果组成的列表。
     * @throws HandlerException 处理器异常。
     */
    CompletableFuture<List<LookupResult>> lookupAsync(List<LookupInfo> lookupInfos) throws HandlerException;

    /**
     * 异步原生查询。
     *
     * @param queryInfo 原生查询信息。
     * @return 查询结果。
     * @throws HandlerException 处理器异常。
     */
    CompletableFuture<QueryResult> nativeQueryAsync(NativeQueryInfo queryInfo) throws HandlerException;

    /**
     * 异步原生查询。
     *
     * @param queryInfos 原生查询信息组成的列表。
     * @return 查询结果组成的列表。
     * @throws HandlerException 处理器异常。
     */
    CompletableFuture<List<QueryResult>> nativeQueryAsync(List<NativeQueryInfo> queryInfos) throws HandlerException;

    /**
     * 异步查询。
     *
     * @param queryInfo 查询信息。
     * @return 查询结果。
     * @throws HandlerException 处理器异常。
     */
    CompletableFuture<QueryResult> queryAsync(QueryInfo queryInfo) throws HandlerException;

    /**
     * 异步查询。
     *
     * @param queryInfos 查询信息组成的列表。
     * @return 查询结果组成的列表。
     * @throws HandlerException 处理器异常。
     */
    CompletableFuture<List<QueryResult>> queryAsync(List<QueryInfo> queryInfos) throws HandlerException;
}
