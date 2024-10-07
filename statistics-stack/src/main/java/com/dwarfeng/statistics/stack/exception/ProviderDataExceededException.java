package com.dwarfeng.statistics.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 提供器数据超限异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ProviderDataExceededException extends HandlerException {

    private static final long serialVersionUID = 6777552891785766795L;

    private final int maxSize;
    private final int actualSize;

    public ProviderDataExceededException(int maxSize, int actualSize) {
        this.maxSize = maxSize;
        this.actualSize = actualSize;
    }

    public ProviderDataExceededException(Throwable cause, int maxSize, int actualSize) {
        super(cause);
        this.maxSize = maxSize;
        this.actualSize = actualSize;
    }

    @Override
    public String getMessage() {
        return "提供器数据超限, 最大数量为 " + maxSize + ", 实际数量为 " + actualSize;
    }
}
