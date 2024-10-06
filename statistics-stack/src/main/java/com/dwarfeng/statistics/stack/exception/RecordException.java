package com.dwarfeng.statistics.stack.exception;

import com.dwarfeng.statistics.stack.bean.dto.BridgeData;

import java.util.List;

/**
 * 记录异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class RecordException extends PersistException {

    private static final long serialVersionUID = 1399697611467264491L;

    private final List<? extends BridgeData> bridgeDatas;

    public RecordException(List<? extends BridgeData> bridgeDatas) {
        this.bridgeDatas = bridgeDatas;
    }

    public RecordException(Throwable cause, List<? extends BridgeData> bridgeDatas) {
        super(cause);
        this.bridgeDatas = bridgeDatas;
    }

    @Override
    public String getMessage() {
        // 如果 bridgeDatas 数量为 1，则返回单数形式的消息；否则，只输出数量。
        if (bridgeDatas.size() == 1) {
            return "记录桥接器数据时发生异常: " + bridgeDatas.get(0);
        } else {
            return "记录桥接器数据时发生异常: " + bridgeDatas.size() + " 条桥接器数据";
        }
    }
}
