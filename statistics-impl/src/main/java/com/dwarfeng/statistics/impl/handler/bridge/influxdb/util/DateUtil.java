package com.dwarfeng.statistics.impl.handler.bridge.influxdb.util;

import java.time.Instant;
import java.util.Date;

/**
 * 日期工具类。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class DateUtil {

    /**
     * 将指定的日期转换为 Instant。
     *
     * @param date 指定的日期。
     * @return 指定的日期对应的 Instant。
     */
    public static Instant date2Instant(Date date) {
        return Instant.ofEpochMilli(date.getTime());
    }

    /**
     * 将指定的 Instant 转换为 Date。
     *
     * @param instant 指定的 Instant。
     * @return 指定的 Instant 对应的 Date。
     */
    public static Date instant2Date(Instant instant) {
        return new Date(instant.toEpochMilli());
    }

    /**
     * 将指定的日期偏移指定的毫秒数。
     *
     * @param date   指定的日期。
     * @param offset 指定的毫秒数。
     * @return 偏移后的日期。
     */
    public static Date offsetDate(Date date, long offset) {
        return new Date(date.getTime() + offset);
    }

    private DateUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
