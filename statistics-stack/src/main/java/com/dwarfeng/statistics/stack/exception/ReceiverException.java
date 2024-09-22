package com.dwarfeng.statistics.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 接收器异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ReceiverException extends HandlerException {

    private static final long serialVersionUID = 3741239188044132512L;

    public ReceiverException() {
    }

    public ReceiverException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReceiverException(String message) {
        super(message);
    }

    public ReceiverException(Throwable cause) {
        super(cause);
    }
}
