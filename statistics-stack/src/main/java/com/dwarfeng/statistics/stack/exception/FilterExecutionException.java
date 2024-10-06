package com.dwarfeng.statistics.stack.exception;

/**
 * 过滤器器提供异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FilterExecutionException extends FilterException {

    private static final long serialVersionUID = -4091564544065058096L;

    public FilterExecutionException() {
    }

    public FilterExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilterExecutionException(String message) {
        super(message);
    }

    public FilterExecutionException(Throwable cause) {
        super(cause);
    }
}
