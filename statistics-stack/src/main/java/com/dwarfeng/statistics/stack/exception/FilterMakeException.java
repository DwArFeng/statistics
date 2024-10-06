package com.dwarfeng.statistics.stack.exception;

/**
 * 过滤器构造异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FilterMakeException extends FilterException {

    private static final long serialVersionUID = -6158569520796760365L;

    public FilterMakeException() {
    }

    public FilterMakeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilterMakeException(String message) {
        super(message);
    }

    public FilterMakeException(Throwable cause) {
        super(cause);
    }
}
