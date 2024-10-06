package com.dwarfeng.statistics.stack.exception;

/**
 * 不支持的提供器类型异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class UnsupportedProviderTypeException extends ProviderException {

    private static final long serialVersionUID = 1243341038911494087L;

    private final String type;

    public UnsupportedProviderTypeException(String type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        return "不支持的提供器类型: " + type;
    }
}
