package com.dwarfeng.statistics.stack.exception;

import com.dwarfeng.statistics.stack.bean.dto.QueryInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.List;

/**
 * 查询异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class QueryException extends HandlerException {

    private static final long serialVersionUID = 2759672323144116418L;

    private final List<QueryInfo> queryInfos;

    public QueryException(List<QueryInfo> queryInfos) {
        this.queryInfos = queryInfos;
    }

    public QueryException(Throwable cause, List<QueryInfo> queryInfos) {
        super(cause);
        this.queryInfos = queryInfos;
    }

    @Override
    public String getMessage() {
        // 如果 queryInfos 数量为 1，则返回单数形式的消息；否则，只输出数量。
        if (queryInfos.size() == 1) {
            return "查询数据时发生异常: " + queryInfos.get(0);
        } else {
            return "查询数据时发生异常: " + queryInfos.size() + " 条数据";
        }
    }
}
