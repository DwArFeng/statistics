package com.dwarfeng.statistics.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 驱动器异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class DriverException extends HandlerException {

    private static final long serialVersionUID = 5887472633084114099L;

    public DriverException() {
    }

    public DriverException(String message, Throwable cause) {
        super(message, cause);
    }

    public DriverException(String message) {
        super(message);
    }

    public DriverException(Throwable cause) {
        super(cause);
    }
}
