package com.dwarfeng.statistics.stack.handler;

import com.dwarfeng.statistics.stack.bean.entity.StatisticsSetting;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 推送器处理器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface PushHandler extends Handler {

    /**
     * 主管功能重置时执行的广播操作。
     *
     * @throws HandlerException 处理器异常。
     */
    void superviseReset() throws HandlerException;

    /**
     * 执行功能重置时执行的广播操作。
     *
     * @throws HandlerException 处理器异常。
     */
    void executeReset() throws HandlerException;

    /**
     * 任务完成时执行的推送操作。
     *
     * @param statisticsSetting 相关的统计设置。
     * @throws HandlerException 处理器异常。
     */
    void taskFinished(StatisticsSetting statisticsSetting) throws HandlerException;

    /**
     * 任务失败时执行的推送操作。
     *
     * @param statisticsSetting 相关的统计设置。
     * @throws HandlerException 处理器异常。
     */
    void taskFailed(StatisticsSetting statisticsSetting) throws HandlerException;

    /**
     * 任务过期时执行的推送操作。
     *
     * @param statisticsSetting 相关的统计设置。
     * @throws HandlerException 处理器异常。
     */
    void taskExpired(StatisticsSetting statisticsSetting) throws HandlerException;

    /**
     * 任务死亡时执行的推送操作。
     *
     * @param statisticsSetting 相关的统计设置。
     * @throws HandlerException 处理器异常。
     */
    void taskDied(StatisticsSetting statisticsSetting) throws HandlerException;
}
