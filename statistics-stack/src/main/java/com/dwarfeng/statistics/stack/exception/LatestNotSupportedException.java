package com.dwarfeng.statistics.stack.exception;

/**
 * 查询最新数据不支持异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class LatestNotSupportedException extends FunctionNotSupportedException {

    private static final long serialVersionUID = 8393938032379427822L;

    private static final String FUNCTION_NAME = "latest";

    public LatestNotSupportedException() {
        super(FUNCTION_NAME);
    }

    public LatestNotSupportedException(Throwable cause) {
        super(cause, FUNCTION_NAME);
    }
}
