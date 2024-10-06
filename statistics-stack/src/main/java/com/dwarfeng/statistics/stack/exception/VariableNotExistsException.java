package com.dwarfeng.statistics.stack.exception;

import com.dwarfeng.statistics.stack.bean.key.VariableKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 变量不存在异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class VariableNotExistsException extends HandlerException {

    private static final long serialVersionUID = 8774545086986823128L;
    
    private final VariableKey variableKey;

    public VariableNotExistsException(VariableKey variableKey) {
        this.variableKey = variableKey;
    }

    public VariableNotExistsException(Throwable cause, VariableKey variableKey) {
        super(cause);
        this.variableKey = variableKey;
    }

    @Override
    public String getMessage() {
        return "变量 " + variableKey + " 不存在";
    }
}
