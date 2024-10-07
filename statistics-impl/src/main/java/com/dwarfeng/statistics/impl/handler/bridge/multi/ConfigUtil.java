package com.dwarfeng.statistics.impl.handler.bridge.multi;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 配置工具类。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
final class ConfigUtil {

    /**
     * 将指定的配置解析为类型列表。
     *
     * @param config 指定的配置。
     * @return 解析后的类型列表。
     */
    public static List<String> parseConfigToTypes(String config) {
        // 参数校验。
        if (Objects.isNull(config)) {
            throw new IllegalArgumentException("入口参数 config 不能为 null。");
        }
        config = config.trim();
        if (config.isEmpty()) {
            throw new IllegalArgumentException("入口参数 config 不能为空。");
        }

        // 将 config 按照逗号分割，但是不能分隔转义的逗号（\,）。
        String[] split = config.split("(?<!\\\\),");
        // 将分割后的字符串数组中的每一个元素的转义的逗号（\,）替换为逗号（,），并转换为列表。
        List<String> result = Arrays.stream(split).map(s -> s.replaceAll("\\\\,", ",")).collect(Collectors.toList());

        // 结果校验。
        if (result.isEmpty()) {
            throw new IllegalArgumentException("入口参数 config 至少需要指定一个类型。");
        }

        // 返回结果。
        return result;
    }

    private ConfigUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
