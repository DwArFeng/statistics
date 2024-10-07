package com.dwarfeng.statistics.stack.exception;

/**
 * 不支持的映射器类型。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class UnsupportedMapperTypeException extends MapperException {

    private static final long serialVersionUID = -2848482779602807029L;

    private final String type;

    public UnsupportedMapperTypeException(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return "不支持的映射器类型: " + type;
    }
}
