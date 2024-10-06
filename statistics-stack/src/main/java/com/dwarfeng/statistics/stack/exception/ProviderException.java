package com.dwarfeng.statistics.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 提供器异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ProviderException extends HandlerException {

    private static final long serialVersionUID = -1943343403876073118L;

    public ProviderException() {
    }

    public ProviderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProviderException(String message) {
        super(message);
    }

    public ProviderException(Throwable cause) {
        super(cause);
    }
}
