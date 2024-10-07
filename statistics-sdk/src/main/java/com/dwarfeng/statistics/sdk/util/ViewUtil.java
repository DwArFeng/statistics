package com.dwarfeng.statistics.sdk.util;

import java.util.Date;
import java.util.Optional;

/**
 * 观察工具类。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class ViewUtil {

    /**
     * 根据指定的日期，返回一个有效的起始日期。
     *
     * @param startDate 指定的日期。
     * @return 有效的起始日期。
     */
    public static Date validStartDate(Date startDate) {
        return Optional.ofNullable(startDate).orElse(Constants.WATCH_DEFAULT_START_DATE);
    }

    /**
     * 根据指定的日期，返回一个有效的结束日期。
     *
     * @param endDate 指定的日期。
     * @return 有效的结束日期。
     */
    public static Date validEndDate(Date endDate) {
        return Optional.ofNullable(endDate).orElse(Constants.WATCH_DEFAULT_END_DATE);
    }

    /**
     * 根据指定的页码，返回一个有效的页码。
     *
     * @param page 指定的页码。
     * @return 有效的页码。
     */
    public static int validPage(Integer page) {
        return Optional.ofNullable(page).orElse(Constants.WATCH_DEFAULT_PAGE);
    }

    /**
     * 根据指定的每页的最大行数，返回一个有效的每页的最大行数。
     *
     * @param rows 指定的每页的最大行数。
     * @return 有效的每页的最大行数。
     */
    public static int validRows(Integer rows) {
        return Optional.ofNullable(rows).orElse(Constants.WATCH_DEFAULT_ROWS);
    }

    private ViewUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
