package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.stack.exception.ReceiverException;
import com.dwarfeng.statistics.stack.handler.Receiver;
import com.dwarfeng.statistics.stack.handler.ReceiverHandler;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class ReceiverHandlerImpl implements ReceiverHandler {

    private final List<Receiver> receivers;

    @Value("${receiver.type}")
    private String receiverType;

    private Receiver receiver;

    private final InternalReceiverContext receiverContext = new InternalReceiverContext();

    public ReceiverHandlerImpl(List<Receiver> receivers) {
        this.receivers = Optional.ofNullable(receivers).orElse(Collections.emptyList());
    }

    @PostConstruct
    public void init() throws HandlerException {
        receivers.forEach(receiver -> receiver.init(receiverContext));
        this.receiver = receivers.stream().filter(p -> p.supportType(receiverType)).findAny().orElseThrow(
                () -> new HandlerException("未知的 receiver 类型: " + receiverType)
        );
    }

    @Override
    public Receiver current() {
        return receiver;
    }

    @Override
    public List<Receiver> all() {
        return receivers;
    }

    // 该类实现后，不能作为静态类，因此忽略掉相关警告。
    @SuppressWarnings("InnerClassMayBeStatic")
    private class InternalReceiverContext implements Receiver.Context {

        @Override
        public void execute(LongIdKey statisticsSettingKey) throws ReceiverException {
            // TODO 未实现。
            throw new ReceiverException("未实现");
        }
    }
}
