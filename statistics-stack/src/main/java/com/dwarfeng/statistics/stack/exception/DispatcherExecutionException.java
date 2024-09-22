package com.dwarfeng.statistics.stack.exception;

/**
 * 调度器执行异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class DispatcherExecutionException extends DispatcherException {

    private static final long serialVersionUID = 500547448903937462L;

    public DispatcherExecutionException() {
    }

    public DispatcherExecutionException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "调度器执行异常";
    }
}
