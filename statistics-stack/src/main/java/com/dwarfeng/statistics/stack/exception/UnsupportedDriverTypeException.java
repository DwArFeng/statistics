package com.dwarfeng.statistics.stack.exception;

/**
 * 不支持的驱动器类型异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class UnsupportedDriverTypeException extends DriverException {

    private static final long serialVersionUID = 340581327300469603L;

    private final String type;

    public UnsupportedDriverTypeException(String type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        return "不支持的驱动器类型: " + type;
    }
}
