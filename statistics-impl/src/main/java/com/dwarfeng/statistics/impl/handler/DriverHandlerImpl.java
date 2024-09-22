package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.stack.exception.DriverException;
import com.dwarfeng.statistics.stack.exception.UnsupportedDriverTypeException;
import com.dwarfeng.statistics.stack.handler.Driver;
import com.dwarfeng.statistics.stack.handler.DriverHandler;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class DriverHandlerImpl implements DriverHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverHandlerImpl.class);

    private final DispatcherHandlerImpl dispatcherHandler;

    private final List<DriverProvider> driverProviders;

    private final InternalDriverContext driverContext = new InternalDriverContext();

    public DriverHandlerImpl(
            DispatcherHandlerImpl dispatcherHandler,
            List<DriverProvider> driverProviders
    ) {
        this.dispatcherHandler = dispatcherHandler;
        this.driverProviders = Optional.ofNullable(driverProviders).orElse(Collections.emptyList());
    }

    @PostConstruct
    public void init() {
        LOGGER.info("初始化驱动器...");
        driverProviders.stream().map(DriverProvider::provide).forEach(driver -> driver.init(driverContext));
    }

    @Override
    public Driver find(String type) throws HandlerException {
        return driverProviders.stream().filter(dp -> dp.supportType(type)).map(DriverProvider::provide)
                .findAny().orElseThrow(() -> new UnsupportedDriverTypeException(type));
    }

    private class InternalDriverContext implements Driver.Context {

        @Override
        public void execute(LongIdKey statisticsSettingKey) throws DriverException {
            try {
                dispatcherHandler.current().dispatch(statisticsSettingKey);
            } catch (Exception e) {
                throw new DriverException(e);
            }
        }
    }
}
