package com.dwarfeng.statistics.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.Set;

/**
 * 任务状态不匹配异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskStatusMismatchException extends HandlerException {

    private static final long serialVersionUID = 6926358199463873372L;

    private final Set<Integer> exceptedStatus;
    private final int actualStatus;

    public TaskStatusMismatchException(Set<Integer> exceptedStatus, int actualStatus) {
        this.exceptedStatus = exceptedStatus;
        this.actualStatus = actualStatus;
    }

    public TaskStatusMismatchException(Throwable cause, Set<Integer> exceptedStatus, int actualStatus) {
        super(cause);
        this.exceptedStatus = exceptedStatus;
        this.actualStatus = actualStatus;
    }

    @Override
    public String getMessage() {
        return "任务状态不匹配, 期望的状态为 " + exceptedStatus + ", 实际的状态为 " + actualStatus;
    }
}
