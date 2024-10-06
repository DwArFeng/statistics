package com.dwarfeng.statistics.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 保持器不支持异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class KeeperNotSupportedException extends HandlerException {

    private static final long serialVersionUID = -3208417299064991828L;

    public KeeperNotSupportedException() {
    }

    public KeeperNotSupportedException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "保持器不支持";
    }
}
