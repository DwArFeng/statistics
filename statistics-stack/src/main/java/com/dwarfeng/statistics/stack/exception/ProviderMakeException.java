package com.dwarfeng.statistics.stack.exception;

/**
 * 提供器构造异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ProviderMakeException extends ProviderException {

    private static final long serialVersionUID = 2409489560324209817L;

    public ProviderMakeException() {
    }

    public ProviderMakeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProviderMakeException(String message) {
        super(message);
    }

    public ProviderMakeException(Throwable cause) {
        super(cause);
    }
}
