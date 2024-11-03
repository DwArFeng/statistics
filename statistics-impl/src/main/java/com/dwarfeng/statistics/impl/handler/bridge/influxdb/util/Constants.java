package com.dwarfeng.statistics.impl.handler.bridge.influxdb.util;

/**
 * 常量类。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class Constants {

    /**
     * InfluxDB 数据写入 标签名：标签。
     *
     * @since 1.1.0
     */
    public static final String DATA_WRITE_TAG_NAME_TAG = "tag";

    /**
     * InfluxDB 数据写入 字段名：值。
     *
     * @since 1.1.0
     */
    public static final String DATA_WRITE_FIELD_NAME_VALUE = "value";

    /**
     * InfluxDB 数据读取 值键：标签。
     *
     * @since 1.1.0
     */
    public static final String DATA_READ_VALUE_KEY_TAG = "tag";

    /**
     * InfluxDB 数据读取 值键：值。
     *
     * @since 1.1.0
     */
    public static final String DATA_READ_VALUE_KEY_VALUE = "_value";
}
