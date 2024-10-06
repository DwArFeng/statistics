package com.dwarfeng.statistics.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 无效的变量类型异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class InvalidVariableValueTypeException extends HandlerException {

    private static final long serialVersionUID = -1899171005491964190L;

    private final int valueType;

    public InvalidVariableValueTypeException(int valueType) {
        this.valueType = valueType;
    }

    public InvalidVariableValueTypeException(Throwable cause, int valueType) {
        super(cause);
        this.valueType = valueType;
    }

    @Override
    public String getMessage() {
        return "无效的变量值类型: " + valueType;
    }
}
