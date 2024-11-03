package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.stack.bean.dto.*;
import com.dwarfeng.statistics.stack.bean.key.BridgeDataKey;
import com.dwarfeng.statistics.stack.exception.KeeperNotSupportedException;
import com.dwarfeng.statistics.stack.exception.PersisterNotSupportedException;
import com.dwarfeng.statistics.stack.handler.KeepHandler;
import com.dwarfeng.statistics.stack.handler.PersistHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.List;

/**
 * 桥接器。
 *
 * <p>
 * 用于提供保持器和持久器。
 *
 * <p>
 * 需要注意的是，不是所有的桥接器都支持所有的桥接器数据类型，因此如果 {@link #getKeeper()} 或
 * {@link #getPersister()} 被调用时，如果桥接器不支持持久器/保持器，则可以抛出相应的异常。<br>
 * <ul>
 *     <li>如果桥接器不支持保持器，则应抛出 {@link KeeperNotSupportedException}</li>
 *     <li>如果桥接器不支持持久器，则应抛出 {@link PersisterNotSupportedException}</li>
 * </ul>
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface Bridge {

    /**
     * 返回桥接器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 桥接器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 获取桥接器的保持器。
     *
     * @return 桥接器的保持器。
     * @throws HandlerException 处理器异常。
     */
    Keeper getKeeper() throws HandlerException;

    /**
     * 获取桥接器的持久器。
     *
     * @return 桥接器的持久器。
     * @throws HandlerException 处理器异常。
     */
    Persister getPersister() throws HandlerException;

    /**
     * 保持器。
     *
     * @author DwArFeng
     * @see KeepHandler
     * @since 1.0.0
     */
    interface Keeper {

        /**
         * 更新桥接器数据。
         *
         * @param bridgeData 桥接器数据。
         * @throws HandlerException 处理器异常。
         * @see KeepHandler#update(BridgeData)
         */
        void update(BridgeData bridgeData) throws HandlerException;

        /**
         * 更新桥接器数据。
         *
         * @param bridgeDatas 桥接器数据组成的列表。
         * @throws HandlerException 处理器异常。
         * @see KeepHandler#update(List)
         */
        void update(List<BridgeData> bridgeDatas) throws HandlerException;

        /**
         * 查询桥接器数据。
         *
         * <p>
         * 如果桥接器数据主键对应的桥接器数据不存在，则返回的查询结果为 <code>null</code>。
         *
         * @param bridgeDataKey 指定的桥接器数据主键。
         * @return 查询结果。
         * @throws HandlerException 处理器异常。
         * @see KeepHandler#latest(BridgeDataKey)
         */
        BridgeData latest(BridgeDataKey bridgeDataKey) throws HandlerException;

        /**
         * 查询桥接器数据。
         *
         * <p>
         * 如果桥接器数据主键组成的列表中的某个索引处的桥接器数据主键对应的桥接器数据不存在，
         * 则返回的查询结果组成的列表该处索引对应的查询结果为 <code>null</code>。
         *
         * @param bridgeDataKeys 指定的桥接器数据主键组成的列表。
         * @return 查询结果。
         * @throws HandlerException 处理器异常。
         * @see KeepHandler#latest(List)
         */
        List<BridgeData> latest(List<BridgeDataKey> bridgeDataKeys) throws HandlerException;
    }

    /**
     * 持久器。
     *
     * @author DwArFeng
     * @see PersistHandler
     * @since 1.0.0
     */
    interface Persister {

        /**
         * 记录桥接器数据。
         *
         * @param bridgeData 桥接器数据。
         * @throws HandlerException 处理器异常。
         * @see PersistHandler#record(BridgeData)
         */
        void record(BridgeData bridgeData) throws HandlerException;

        /**
         * 记录桥接器数据。
         *
         * @param bridgeDatas 桥接器数据组成的列表。
         * @throws HandlerException 处理器异常。
         * @see PersistHandler#record(List)
         */
        void record(List<BridgeData> bridgeDatas) throws HandlerException;

        /**
         * 查看。
         *
         * @param lookupInfo 查看信息。
         * @return 查看结果。
         * @throws HandlerException 处理器异常。
         * @see PersistHandler#lookup(LookupInfo)
         */
        LookupResult lookup(LookupInfo lookupInfo) throws HandlerException;

        /**
         * 查看。
         *
         * @param lookupInfos 查看信息组成的列表。
         * @return 查看结果组成的列表。
         * @throws HandlerException 处理器异常。
         * @see PersistHandler#lookup(List)
         */
        List<LookupResult> lookup(List<LookupInfo> lookupInfos) throws HandlerException;

        /**
         * 原生查询。
         *
         * @param queryInfo 原生查询信息。
         * @return 查询结果。
         * @throws HandlerException 处理器异常。
         * @see PersistHandler#nativeQuery(NativeQueryInfo)
         */
        QueryResult nativeQuery(NativeQueryInfo queryInfo) throws HandlerException;

        /**
         * 原生查询。
         *
         * @param queryInfos 原生查询信息组成的列表。
         * @return 查询结果组成的列表。
         * @throws HandlerException 处理器异常。
         * @see PersistHandler#nativeQuery(List)
         */
        List<QueryResult> nativeQuery(List<NativeQueryInfo> queryInfos) throws HandlerException;
    }
}
