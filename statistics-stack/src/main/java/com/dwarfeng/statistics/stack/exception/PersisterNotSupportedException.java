package com.dwarfeng.statistics.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 持久器不支持异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class PersisterNotSupportedException extends HandlerException {

    private static final long serialVersionUID = -3673541257300474789L;

    public PersisterNotSupportedException() {
    }

    public PersisterNotSupportedException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "持久器不支持";
    }
}
