package com.dwarfeng.statistics.stack.exception;

/**
 * 查看不支持异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class LookupNotSupportedException extends FunctionNotSupportedException {

    private static final long serialVersionUID = 2479490170918192296L;

    private static final String FUNCTION_NAME = "lookup";

    public LookupNotSupportedException() {
        super(FUNCTION_NAME);
    }

    public LookupNotSupportedException(Throwable cause) {
        super(cause, FUNCTION_NAME);
    }
}
