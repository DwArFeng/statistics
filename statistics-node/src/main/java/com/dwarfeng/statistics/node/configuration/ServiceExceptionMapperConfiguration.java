package com.dwarfeng.statistics.node.configuration;

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
        destination.put(ReceiverException.class, ServiceExceptionCodes.RECEIVER_FAILED);
        destination.put(ReceiverNotStartException.class, ServiceExceptionCodes.RECEIVER_NOT_START);
        destination.put(ReceiverExecutionException.class, ServiceExceptionCodes.RECEIVER_EXECUTION_FAILED);
        destination.put(FunctionNotSupportedException.class, ServiceExceptionCodes.FUNCTION_NOT_SUPPORTED);
        destination.put(LatestNotSupportedException.class, ServiceExceptionCodes.LATEST_NOT_SUPPORTED);
        destination.put(LookupNotSupportedException.class, ServiceExceptionCodes.LOOKUP_NOT_SUPPORTED);
        destination.put(QueryNotSupportedException.class, ServiceExceptionCodes.QUERY_NOT_SUPPORTED);
        destination.put(NativeQueryNotSupportedException.class, ServiceExceptionCodes.NATIVE_QUERY_NOT_SUPPORTED);
        destination.put(KeeperNotSupportedException.class, ServiceExceptionCodes.KEEPER_NOT_SUPPORTED);
        destination.put(PersisterNotSupportedException.class, ServiceExceptionCodes.PERSISTER_NOT_SUPPORTED);
        destination.put(KeepException.class, ServiceExceptionCodes.KEEP_EXCEPTION);
        destination.put(LatestException.class, ServiceExceptionCodes.LATEST_EXCEPTION);
        destination.put(UpdateException.class, ServiceExceptionCodes.UPDATE_EXCEPTION);
        destination.put(PersistException.class, ServiceExceptionCodes.PERSIST_EXCEPTION);
        destination.put(RecordException.class, ServiceExceptionCodes.RECORD_EXCEPTION);
        destination.put(LookupException.class, ServiceExceptionCodes.LOOKUP_EXCEPTION);
        destination.put(NativeQueryException.class, ServiceExceptionCodes.NATIVE_QUERY_EXCEPTION);
        destination.put(StatisticsSettingNotExistsException.class, ServiceExceptionCodes.STATISTICS_SETTING_NOT_EXISTS);
        destination.put(TaskNotExistsException.class, ServiceExceptionCodes.TASK_NOT_EXISTS);
        destination.put(InvalidTaskStatusException.class, ServiceExceptionCodes.INVALID_TASK_STATUS);
        destination.put(TaskStatusMismatchException.class, ServiceExceptionCodes.TASK_STATUS_MISMATCH);
        destination.put(ProviderException.class, ServiceExceptionCodes.PROVIDER_FAILED);
        destination.put(ProviderMakeException.class, ServiceExceptionCodes.PROVIDER_MAKE_FAILED);
        destination.put(ProviderExecutionException.class, ServiceExceptionCodes.PROVIDER_EXECUTION_FAILED);
        destination.put(UnsupportedProviderTypeException.class, ServiceExceptionCodes.PROVIDER_TYPE_UNSUPPORTED);
        destination.put(FilterException.class, ServiceExceptionCodes.FILTER_FAILED);
        destination.put(FilterMakeException.class, ServiceExceptionCodes.FILTER_MAKE_FAILED);
        destination.put(FilterExecutionException.class, ServiceExceptionCodes.FILTER_EXECUTION_FAILED);
        destination.put(InvalidVariableValueTypeException.class, ServiceExceptionCodes.INVALID_VARIABLE_VALUE_TYPE);
        destination.put(VariableNotExistsException.class, ServiceExceptionCodes.VARIABLE_NOT_EXISTS);
        destination.put(VariableValueTypeMismatchException.class, ServiceExceptionCodes.VARIABLE_VALUE_TYPE_MISMATCH);
        destination.put(ProviderDataExceededException.class, ServiceExceptionCodes.PROVIDER_DATA_EXCEEDED);
        destination.put(QueryException.class, ServiceExceptionCodes.QUERY_FAILED);
        return new MapServiceExceptionMapper(destination, com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes.UNDEFINED);
    }
}
