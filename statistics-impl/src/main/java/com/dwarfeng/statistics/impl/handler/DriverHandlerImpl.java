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

    private final List<DriverProvider> driverProviders;

    private final InternalDriverContext driverContext = new InternalDriverContext();

    public DriverHandlerImpl(List<DriverProvider> driverProviders) {
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

    // 该类实现后，不能作为静态类，因此忽略掉相关警告。
    @SuppressWarnings("InnerClassMayBeStatic")
    private class InternalDriverContext implements Driver.Context {

        @Override
        public void execute(LongIdKey statisticsSettingKey) throws DriverException {
            try {
                // TODO 待实现。
                throw new DriverException("未实现");
            } catch (DriverException e) {
                throw e;
            } catch (Exception e) {
                throw new DriverException(e);
            }
        }
    }
}
