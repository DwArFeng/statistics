package com.dwarfeng.statistics.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 调度器异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class DispatcherException extends HandlerException {

    private static final long serialVersionUID = 3530028860996998999L;

    public DispatcherException() {
    }

    public DispatcherException(String message, Throwable cause) {
        super(message, cause);
    }

    public DispatcherException(String message) {
        super(message);
    }

    public DispatcherException(Throwable cause) {
        super(cause);
    }
}
