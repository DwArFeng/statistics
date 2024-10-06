package com.dwarfeng.statistics.stack.exception;

/**
 * 原生查询不支持异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class NativeQueryNotSupportedException extends FunctionNotSupportedException {

    private static final long serialVersionUID = -7370960460260700997L;

    private static final String FUNCTION_NAME = "native_query";

    public NativeQueryNotSupportedException() {
        super(FUNCTION_NAME);
    }

    public NativeQueryNotSupportedException(Throwable cause) {
        super(cause, FUNCTION_NAME);
    }
}
