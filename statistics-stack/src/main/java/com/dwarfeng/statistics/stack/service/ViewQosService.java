package com.dwarfeng.statistics.stack.service;

import com.dwarfeng.statistics.stack.bean.dto.*;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 观察 QOS 服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ViewQosService extends Service {

    /**
     * 查询统计设置的最新数据。
     *
     * <p>
     * 如果统计设置主键组成的列表中的某个索引处的统计设置主键对应的数据不存在，
     * 则返回的查询结果组成的列表该处索引对应的查询结果为 null。
     *
     * @param statisticsSettingKeys 指定的统计设置对应的主键组成的列表。
     * @return 指定的统计设置的最新数据组成的列表。
     * @throws ServiceException 服务异常。
     */
    List<BridgeData> latest(List<LongIdKey> statisticsSettingKeys) throws ServiceException;

    /**
     * 查看。
     *
     * @param queryInfo 查看信息。
     * @return 查看结果。
     * @throws ServiceException 服务异常。
     */
    QueryResult lookup(QueryInfo queryInfo) throws ServiceException;

    /**
     * 原生查询。
     *
     * @param queryInfo 原生查询信息。
     * @return 查询结果。
     * @throws ServiceException 服务异常。
     */
    QueryResult nativeQuery(NativeQueryInfo queryInfo) throws ServiceException;

    /**
     * 查询。
     *
     * @param lookupInfo 查询信息。
     * @return 查询结果。
     * @throws ServiceException 服务异常。
     */
    LookupResult query(LookupInfo lookupInfo) throws ServiceException;
}
