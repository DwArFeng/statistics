package com.dwarfeng.statistics.stack.exception;

/**
 * 接收器执行异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ReceiverExecutionException extends ReceiverException {

    private static final long serialVersionUID = 5974899389004711713L;

    public ReceiverExecutionException() {
    }

    public ReceiverExecutionException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "接收器执行异常";
    }
}
