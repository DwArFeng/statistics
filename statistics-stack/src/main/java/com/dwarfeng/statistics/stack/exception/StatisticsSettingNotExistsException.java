package com.dwarfeng.statistics.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 统计设置不存在异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class StatisticsSettingNotExistsException extends HandlerException {

    private static final long serialVersionUID = -4286300784186483624L;

    private final LongIdKey statisticsSettingKey;

    public StatisticsSettingNotExistsException(LongIdKey statisticsSettingKey) {
        this.statisticsSettingKey = statisticsSettingKey;
    }

    public StatisticsSettingNotExistsException(Throwable cause, LongIdKey statisticsSettingKey) {
        super(cause);
        this.statisticsSettingKey = statisticsSettingKey;
    }

    @Override
    public String getMessage() {
        return "统计设置 " + statisticsSettingKey + " 不存在";
    }
}
