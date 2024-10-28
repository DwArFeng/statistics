package com.dwarfeng.statistics.stack.handler;

import com.dwarfeng.statistics.stack.bean.dto.QueryInfo;
import com.dwarfeng.statistics.stack.bean.dto.QueryResult;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.List;

/**
 * 组织处理器。
 *
 * <p>
 * 组织处理器用于组织数据，实现数据查看。
 *
 * <p>
 * 数据查看分两步：
 * <ol>
 *     <li>
 *         原始数据查询：对一个或多个数据点进行数据查询，返回第一个序列列表。
 *     </li>
 *     <li>
 *         数据表映射：使用映射器对数据表进行链式映射，返回最终的数据表。<br>
 *         将最终数据表整合为 {@link QueryResult} 对象，返回给调用者。
 *     </li>
 * </ol>
 *
 * <p>
 * 该处理器仅对数据进行读取与处理，不对数据进行写入。
 *
 * <p>
 * 有关组织的详细信息，请参阅术语。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface QueryHandler extends Handler {

    /**
     * 查看。
     *
     * @param queryInfo 查看信息。
     * @return 查看结果。
     * @throws HandlerException 处理器异常。
     */
    QueryResult query(QueryInfo queryInfo) throws HandlerException;

    /**
     * 查看。
     *
     * @param queryInfos 查看信息组成的列表。
     * @return 查看结果组成的列表。
     * @throws HandlerException 处理器异常。
     */
    List<QueryResult> query(List<QueryInfo> queryInfos) throws HandlerException;
}
