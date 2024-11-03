package com.dwarfeng.statistics.stack.exception;

import com.dwarfeng.statistics.stack.bean.key.BridgeDataKey;

import java.util.List;

/**
 * 查询统计设置最新数据异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class LatestException extends KeepException {

    private static final long serialVersionUID = -8860855890857370228L;

    private final List<BridgeDataKey> bridgeDataKeys;

    public LatestException(List<BridgeDataKey> bridgeDataKeys) {
        this.bridgeDataKeys = bridgeDataKeys;
    }

    public LatestException(Throwable cause, List<BridgeDataKey> bridgeDataKeys) {
        super(cause);
        this.bridgeDataKeys = bridgeDataKeys;
    }

    @Override
    public String getMessage() {
        // 如果 bridgeDataKeys 数量为 1，则返回单数形式的消息；否则，只输出数量。
        if (bridgeDataKeys.size() == 1) {
            return "查询统计设置最新数据时发生异常: " + bridgeDataKeys.get(0);
        } else {
            return "查询统计设置最新数据时发生异常: " + bridgeDataKeys.size() + " 条数据";
        }
    }
}
