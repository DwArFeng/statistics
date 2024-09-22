package com.dwarfeng.statistics.impl.configuration;

import com.dwarfeng.statistics.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.statistics.stack.exception.*;
import com.dwarfeng.subgrade.impl.exception.MapServiceExceptionMapper;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ServiceExceptionMapperConfiguration {

    @Bean
    public MapServiceExceptionMapper mapServiceExceptionMapper() {
        Map<Class<? extends Exception>, ServiceException.Code> destination = ServiceExceptionHelper.putDefaultDestination(null);
        destination.put(DriverException.class, ServiceExceptionCodes.DRIVER_FAILED);
        destination.put(UnsupportedDriverTypeException.class, ServiceExceptionCodes.DRIVER_TYPE_UNSUPPORTED);
        destination.put(DispatcherException.class, ServiceExceptionCodes.DISPATCHER_FAILED);
        destination.put(DispatcherNotStartException.class, ServiceExceptionCodes.DISPATCHER_NOT_START);
        destination.put(DispatcherExecutionException.class, ServiceExceptionCodes.DISPATCHER_EXECUTION_FAILED);
        return new MapServiceExceptionMapper(destination, com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes.UNDEFINED);
    }
}
