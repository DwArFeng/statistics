package com.dwarfeng.statistics.stack.exception;

/**
 * 不支持的过滤器类型异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class UnsupportedFilterTypeException extends FilterException {

    private static final long serialVersionUID = -3077318725801170519L;

    private final String type;

    public UnsupportedFilterTypeException(String type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        return "不支持的过滤器类型: " + type;
    }
}
