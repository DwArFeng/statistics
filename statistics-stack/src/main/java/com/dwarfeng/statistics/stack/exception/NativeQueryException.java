package com.dwarfeng.statistics.stack.exception;

import com.dwarfeng.statistics.stack.bean.dto.NativeQueryInfo;

import java.util.List;

/**
 * 原生查询异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class NativeQueryException extends PersistException {

    private static final long serialVersionUID = 13725299526134853L;

    private final List<NativeQueryInfo> nativeQueryInfos;

    public NativeQueryException(List<NativeQueryInfo> nativeQueryInfos) {
        this.nativeQueryInfos = nativeQueryInfos;
    }

    public NativeQueryException(Throwable cause, List<NativeQueryInfo> nativeQueryInfos) {
        super(cause);
        this.nativeQueryInfos = nativeQueryInfos;
    }

    @Override
    public String getMessage() {
        // 如果 nativeQueryInfos 数量为 1，则返回单数形式的消息；否则，只输出数量。
        if (nativeQueryInfos.size() == 1) {
            return "原生查询数据时发生异常: " + nativeQueryInfos.get(0);
        } else {
            return "原生查询数据时发生异常: " + nativeQueryInfos.size() + " 条数据";
        }
    }
}
