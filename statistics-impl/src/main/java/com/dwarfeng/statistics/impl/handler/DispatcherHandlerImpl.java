package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.stack.handler.Dispatcher;
import com.dwarfeng.statistics.stack.handler.DispatcherHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class DispatcherHandlerImpl implements DispatcherHandler {

    private final List<Dispatcher> dispatchers;

    @Value("${dispatcher.type}")
    private String dispatcherType;

    private Dispatcher dispatcher;

    public DispatcherHandlerImpl(List<Dispatcher> dispatchers) {
        this.dispatchers = Optional.ofNullable(dispatchers).orElse(Collections.emptyList());
    }

    @PostConstruct
    public void init() throws HandlerException {
        this.dispatcher = dispatchers.stream().filter(p -> p.supportType(dispatcherType)).findAny().orElseThrow(
                () -> new HandlerException("未知的 dispatcher 类型: " + dispatcherType)
        );
    }

    @Override
    public Dispatcher current() {
        return dispatcher;
    }

    @Override
    public List<Dispatcher> all() {
        return dispatchers;
    }
}
