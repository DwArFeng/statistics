package com.dwarfeng.statistics.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 保持异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class KeepException extends HandlerException {

    private static final long serialVersionUID = -6226233673311065043L;

    public KeepException() {
    }

    public KeepException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "保持异常";
    }
}
