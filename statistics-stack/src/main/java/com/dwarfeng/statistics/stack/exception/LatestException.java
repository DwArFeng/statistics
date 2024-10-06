package com.dwarfeng.statistics.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.List;

/**
 * 查询统计设置最新数据异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class LatestException extends KeepException {

    private static final long serialVersionUID = 8272750061565167474L;

    private final List<LongIdKey> statisticsSettingKeys;

    public LatestException(List<LongIdKey> statisticsSettingKeys) {
        this.statisticsSettingKeys = statisticsSettingKeys;
    }

    public LatestException(Throwable cause, List<LongIdKey> statisticsSettingKeys) {
        super(cause);
        this.statisticsSettingKeys = statisticsSettingKeys;
    }

    @Override
    public String getMessage() {
        // 如果 statisticsSettingKeys 数量为 1，则返回单数形式的消息；否则，只输出数量。
        if (statisticsSettingKeys.size() == 1) {
            return "查询统计设置最新数据时发生异常: " + statisticsSettingKeys.get(0);
        } else {
            return "查询统计设置最新数据时发生异常: " + statisticsSettingKeys.size() + " 条数据";
        }
    }
}
