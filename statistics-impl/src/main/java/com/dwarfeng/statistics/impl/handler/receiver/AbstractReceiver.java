package com.dwarfeng.statistics.impl.handler.receiver;

/**
 * 接收器的抽象实现。
 *
 * @author DwArFeng
 * @see com.dwarfeng.statistics.sdk.handler.receiver.AbstractReceiver
 * @since 1.0.0
 * @deprecated 该对象已经被废弃，请使用 sdk 模块下的对应对象代替。
 */
@Deprecated
public abstract class AbstractReceiver extends com.dwarfeng.statistics.sdk.handler.receiver.AbstractReceiver {

    public AbstractReceiver() {
    }

    public AbstractReceiver(String receiverType) {
        super(receiverType);
    }

}
