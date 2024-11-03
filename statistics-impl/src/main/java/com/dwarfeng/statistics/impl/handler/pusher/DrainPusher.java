package com.dwarfeng.statistics.impl.handler.pusher;

import com.dwarfeng.statistics.stack.bean.entity.StatisticsSetting;
import org.springframework.stereotype.Component;

/**
 * 简单的丢弃掉所有信息的推送器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class DrainPusher extends AbstractPusher {

    public static final String PUSHER_TYPE = "drain";

    public DrainPusher() {
        super(PUSHER_TYPE);
    }

    @Override
    public void superviseReset() {
    }

    @Override
    public void executeReset() {
    }

    @Override
    public void taskFinished(StatisticsSetting statisticsSetting) {
    }

    @Override
    public void taskFailed(StatisticsSetting statisticsSetting) {
    }

    @Override
    public void taskExpired(StatisticsSetting statisticsSetting) {
    }

    @Override
    public void taskDied(StatisticsSetting statisticsSetting) {
    }

    @Override
    public String toString() {
        return "DrainPusher{" +
                "pusherType='" + pusherType + '\'' +
                '}';
    }
}
