package com.dwarfeng.statistics.stack.exception;

/**
 * 查询不支持异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class QueryNotSupportedException extends FunctionNotSupportedException {

    private static final long serialVersionUID = 5854315416008537943L;

    private static final String FUNCTION_NAME = "query";

    public QueryNotSupportedException() {
        super(FUNCTION_NAME);
    }

    public QueryNotSupportedException(Throwable cause) {
        super(cause, FUNCTION_NAME);
    }
}
