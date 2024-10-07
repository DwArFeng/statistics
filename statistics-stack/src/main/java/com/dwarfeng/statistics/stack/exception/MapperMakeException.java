package com.dwarfeng.statistics.stack.exception;

/**
 * 映射器构造异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class MapperMakeException extends MapperException {

    private static final long serialVersionUID = -5403896634534851337L;

    public MapperMakeException() {
    }

    public MapperMakeException(String message) {
        super(message);
    }

    public MapperMakeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapperMakeException(Throwable cause) {
        super(cause);
    }
}
