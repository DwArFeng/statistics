package com.dwarfeng.statistics.stack.handler;

import com.dwarfeng.statistics.stack.bean.dto.*;
import com.dwarfeng.statistics.stack.exception.LookupNotSupportedException;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.List;

/**
 * 持久处理器。
 *
 * <p>
 * 该处理器用于持久桥接器数据，实现类似于历史桥接器数据的查询与更新。
 *
 * <p>
 * 持久处理器为每个统计设置维护一系列历史桥接器数据，这些桥接器数据可以被查询。同时，新的历史桥接器数据可以被记录。
 *
 * <p>
 * 部分持久处理器可能只支持写入，不支持查询。<br>
 * 对于只写的持久处理器，其 {@link #lookup(LookupInfo)} 方法以及 {@link #lookup(List)} 方法应该抛出
 * {@link LookupNotSupportedException} 异常。
 *
 * <p>
 * 有关持久的详细信息，请参阅术语。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface PersistHandler extends Handler {

    /**
     * 记录桥接器数据。
     *
     * @param bridgeData 桥接器数据。
     * @throws HandlerException 处理器异常。
     */
    void record(BridgeData bridgeData) throws HandlerException;

    /**
     * 记录桥接器数据。
     *
     * @param bridgeDatas 桥接器数据组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void record(List<BridgeData> bridgeDatas) throws HandlerException;

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
}
