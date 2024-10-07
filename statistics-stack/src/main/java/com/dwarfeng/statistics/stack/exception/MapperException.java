package com.dwarfeng.statistics.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 映射器异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class MapperException extends HandlerException {

    private static final long serialVersionUID = 3773994112498621056L;

    public MapperException() {
    }

    public MapperException(String message) {
        super(message);
    }

    public MapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapperException(Throwable cause) {
        super(cause);
    }
}
