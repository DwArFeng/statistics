package com.dwarfeng.statistics.stack.exception;

/**
 * 接收器未启动异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ReceiverNotStartException extends ReceiverException {

    private static final long serialVersionUID = -4872032518265138827L;

    public ReceiverNotStartException() {
    }

    public ReceiverNotStartException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "接收器未启动";
    }
}
