package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.stack.bean.entity.StatisticsSetting;
import com.dwarfeng.statistics.stack.handler.PushHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class PushHandlerImpl implements PushHandler {

    private final List<Pusher> pushers;

    @Value("${pusher.type}")
    private String pusherType;

    private Pusher pusher;

    public PushHandlerImpl(List<Pusher> pushers) {
        this.pushers = Optional.ofNullable(pushers).orElse(Collections.emptyList());
    }

    @PostConstruct
    public void init() throws HandlerException {
        this.pusher = pushers.stream().filter(p -> p.supportType(pusherType)).findAny().orElseThrow(
                () -> new HandlerException("未知的 pusher 类型: " + pusherType)
        );
    }

    @Override
    public void superviseReset() throws HandlerException {
        pusher.superviseReset();
    }

    @Override
    public void executeReset() throws HandlerException {
        pusher.executeReset();
    }

    @Override
    public void taskFinished(StatisticsSetting statisticsSetting) throws HandlerException {
        pusher.taskFinished(statisticsSetting);
    }

    @Override
    public void taskFailed(StatisticsSetting statisticsSetting) throws HandlerException {
        pusher.taskFailed(statisticsSetting);
    }

    @Override
    public void taskExpired(StatisticsSetting statisticsSetting) throws HandlerException {
        pusher.taskExpired(statisticsSetting);
    }

    @Override
    public void taskDied(StatisticsSetting statisticsSetting) throws HandlerException {
        pusher.taskDied(statisticsSetting);
    }

    @Override
    public String toString() {
        return "PushHandlerImpl{" +
                "pushers=" + pushers +
                ", pusherType='" + pusherType + '\'' +
                ", pusher=" + pusher +
                '}';
    }
}
