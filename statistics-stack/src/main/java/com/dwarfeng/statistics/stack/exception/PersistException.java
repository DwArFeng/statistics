package com.dwarfeng.statistics.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 持久异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class PersistException extends HandlerException {

    private static final long serialVersionUID = 7662514158746034675L;

    public PersistException() {
    }

    public PersistException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "持久异常";
    }
}
