package com.dwarfeng.statistics.stack.exception;

import com.dwarfeng.statistics.stack.bean.dto.BridgeData;

import java.util.List;

/**
 * 更新异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class UpdateException extends KeepException {

    private static final long serialVersionUID = 5504660425538557784L;

    private final List<BridgeData> bridgeDatas;

    public UpdateException(List<BridgeData> bridgeDatas) {
        this.bridgeDatas = bridgeDatas;
    }

    public UpdateException(Throwable cause, List<BridgeData> bridgeDatas) {
        super(cause);
        this.bridgeDatas = bridgeDatas;
    }

    @Override
    public String getMessage() {
        // 如果 bridgeDatas 数量为 1，则返回单数形式的消息；否则，只输出数量。
        if (bridgeDatas.size() == 1) {
            return "更新桥接器数据时发生异常: " + bridgeDatas.get(0);
        } else {
            return "更新桥接器数据时发生异常: " + bridgeDatas.size() + " 条桥接器数据";
        }
    }
}
