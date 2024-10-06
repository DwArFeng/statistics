package com.dwarfeng.statistics.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 变量值类型不匹配异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class VariableValueTypeMismatchException extends HandlerException {

    private static final long serialVersionUID = -4679069509074955517L;
    
    private final int valueType;
    private final Class<?> expectedValueClazz;
    private final Class<?> actualValueClazz;

    public VariableValueTypeMismatchException(int valueType, Class<?> expectedValueClazz, Class<?> actualValueClazz) {
        this.valueType = valueType;
        this.expectedValueClazz = expectedValueClazz;
        this.actualValueClazz = actualValueClazz;
    }

    public VariableValueTypeMismatchException(
            Throwable cause, int valueType, Class<?> expectedValueClazz, Class<?> actualValueClazz
    ) {
        super(cause);
        this.valueType = valueType;
        this.expectedValueClazz = expectedValueClazz;
        this.actualValueClazz = actualValueClazz;
    }

    @Override
    public String getMessage() {
        return "变量值类型不匹配, 变量值类型为 " + valueType + ", 期望的值类型为 " + expectedValueClazz +
                ", 实际的值类型为 " + actualValueClazz;
    }
}
