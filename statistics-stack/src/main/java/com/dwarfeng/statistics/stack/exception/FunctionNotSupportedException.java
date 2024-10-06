package com.dwarfeng.statistics.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 功能不支持异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FunctionNotSupportedException extends HandlerException {

    private static final long serialVersionUID = 8418714948287466975L;

    private final String functionName;

    public FunctionNotSupportedException(String functionName) {
        this.functionName = functionName;
    }

    public FunctionNotSupportedException(Throwable cause, String functionName) {
        super(cause);
        this.functionName = functionName;
    }

    @Override
    public String getMessage() {
        return "功能不支持: " + functionName;
    }
}
