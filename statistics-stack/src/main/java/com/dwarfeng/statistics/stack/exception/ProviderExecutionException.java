package com.dwarfeng.statistics.stack.exception;

/**
 * 提供器器提供异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ProviderExecutionException extends ProviderException {

    private static final long serialVersionUID = -6021034515542920461L;

    public ProviderExecutionException() {
    }

    public ProviderExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProviderExecutionException(String message) {
        super(message);
    }

    public ProviderExecutionException(Throwable cause) {
        super(cause);
    }
}
