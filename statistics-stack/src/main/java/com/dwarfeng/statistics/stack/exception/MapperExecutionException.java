package com.dwarfeng.statistics.stack.exception;

/**
 * 映射器执行异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class MapperExecutionException extends MapperException {

    private static final long serialVersionUID = 7915854420311921806L;

    public MapperExecutionException() {
    }

    public MapperExecutionException(String message) {
        super(message);
    }

    public MapperExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapperExecutionException(Throwable cause) {
        super(cause);
    }
}
