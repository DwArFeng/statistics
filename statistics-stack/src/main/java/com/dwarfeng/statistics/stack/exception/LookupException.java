package com.dwarfeng.statistics.stack.exception;

import com.dwarfeng.statistics.stack.bean.dto.LookupInfo;

import java.util.List;

/**
 * 查看异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class LookupException extends PersistException {

    private static final long serialVersionUID = -1060362049456230122L;

    private final List<LookupInfo> lookupInfos;

    public LookupException(List<LookupInfo> lookupInfos) {
        this.lookupInfos = lookupInfos;
    }

    public LookupException(Throwable cause, List<LookupInfo> lookupInfos) {
        super(cause);
        this.lookupInfos = lookupInfos;
    }

    @Override
    public String getMessage() {
        // 如果 lookupInfos 数量为 1，则返回单数形式的消息；否则，只输出数量。
        if (lookupInfos.size() == 1) {
            return "查看数据点最新数据时发生异常: " + lookupInfos.get(0);
        } else {
            return "查看数据点最新数据时发生异常: " + lookupInfos.size() + " 条数据";
        }
    }
}
