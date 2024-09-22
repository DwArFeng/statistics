package com.dwarfeng.statistics.stack.exception;

/**
 * 调度器未启动异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class DispatcherNotStartException extends DispatcherException {

    private static final long serialVersionUID = 189667052474021086L;

    public DispatcherNotStartException() {
    }

    public DispatcherNotStartException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "调度器未启动";
    }
}
