package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.stack.exception.ProviderException;
import com.dwarfeng.statistics.stack.exception.UnsupportedProviderTypeException;
import com.dwarfeng.statistics.stack.handler.Provider;
import com.dwarfeng.statistics.stack.handler.ProviderHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class ProviderHandlerImpl implements ProviderHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProviderHandlerImpl.class);

    private final List<ProviderMaker> providerMakers;

    public ProviderHandlerImpl(List<ProviderMaker> providerMakers) {
        this.providerMakers = Optional.ofNullable(providerMakers).orElse(Collections.emptyList());
    }

    @Override
    public Provider make(String type, String param) throws ProviderException {
        try {
            // 生成执行器。
            LOGGER.debug("通过执行器信息构建新的的执行器...");
            ProviderMaker providerMaker = providerMakers.stream().filter(maker -> maker.supportType(type))
                    .findFirst().orElseThrow(() -> new UnsupportedProviderTypeException(type));
            Provider provider = providerMaker.makeProvider(type, param);
            LOGGER.debug("执行器构建成功!");
            LOGGER.debug("执行器: {}", provider);
            return provider;
        } catch (ProviderException e) {
            throw e;
        } catch (Exception e) {
            throw new ProviderException(e);
        }
    }
}
