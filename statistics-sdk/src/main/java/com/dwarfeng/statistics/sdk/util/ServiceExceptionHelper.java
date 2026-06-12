package com.dwarfeng.statistics.sdk.util;

import com.dwarfeng.statistics.stack.exception.*;
import com.dwarfeng.subgrade.stack.exception.ServiceException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 异常的帮助工具类。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public final class ServiceExceptionHelper {

    /**
     * 向指定的映射中添加 statistics 默认的目标映射。
     *
     * <p>
     * 该方法可以在配置类中快速的搭建目标映射。
     *
     * @param map 指定的映射，允许为 <code>null</code>。
     * @return 添加了默认目标的映射。
     */
    public static Map<Class<? extends Exception>, ServiceException.Code> putDefaultDestination(
            Map<Class<? extends Exception>, ServiceException.Code> map
    ) {
        if (Objects.isNull(map)) {
            map = new HashMap<>();
        }

        map.put(DriverException.class, ServiceExceptionCodes.DRIVER_FAILED);
        map.put(UnsupportedDriverTypeException.class, ServiceExceptionCodes.DRIVER_TYPE_UNSUPPORTED);
        map.put(DispatcherException.class, ServiceExceptionCodes.DISPATCHER_FAILED);
        map.put(DispatcherNotStartException.class, ServiceExceptionCodes.DISPATCHER_NOT_START);
        map.put(DispatcherExecutionException.class, ServiceExceptionCodes.DISPATCHER_EXECUTION_FAILED);
        map.put(ReceiverException.class, ServiceExceptionCodes.RECEIVER_FAILED);
        map.put(ReceiverNotStartException.class, ServiceExceptionCodes.RECEIVER_NOT_START);
        map.put(ReceiverExecutionException.class, ServiceExceptionCodes.RECEIVER_EXECUTION_FAILED);
        map.put(FunctionNotSupportedException.class, ServiceExceptionCodes.FUNCTION_NOT_SUPPORTED);
        map.put(LatestNotSupportedException.class, ServiceExceptionCodes.LATEST_NOT_SUPPORTED);
        map.put(LookupNotSupportedException.class, ServiceExceptionCodes.LOOKUP_NOT_SUPPORTED);
        map.put(QueryNotSupportedException.class, ServiceExceptionCodes.QUERY_NOT_SUPPORTED);
        map.put(NativeQueryNotSupportedException.class, ServiceExceptionCodes.NATIVE_QUERY_NOT_SUPPORTED);
        map.put(KeeperNotSupportedException.class, ServiceExceptionCodes.KEEPER_NOT_SUPPORTED);
        map.put(PersisterNotSupportedException.class, ServiceExceptionCodes.PERSISTER_NOT_SUPPORTED);
        map.put(KeepException.class, ServiceExceptionCodes.KEEP_EXCEPTION);
        map.put(LatestException.class, ServiceExceptionCodes.LATEST_EXCEPTION);
        map.put(UpdateException.class, ServiceExceptionCodes.UPDATE_EXCEPTION);
        map.put(PersistException.class, ServiceExceptionCodes.PERSIST_EXCEPTION);
        map.put(RecordException.class, ServiceExceptionCodes.RECORD_EXCEPTION);
        map.put(LookupException.class, ServiceExceptionCodes.LOOKUP_EXCEPTION);
        map.put(NativeQueryException.class, ServiceExceptionCodes.NATIVE_QUERY_EXCEPTION);
        map.put(StatisticsSettingNotExistsException.class, ServiceExceptionCodes.STATISTICS_SETTING_NOT_EXISTS);
        map.put(TaskNotExistsException.class, ServiceExceptionCodes.TASK_NOT_EXISTS);
        map.put(InvalidTaskStatusException.class, ServiceExceptionCodes.INVALID_TASK_STATUS);
        map.put(TaskStatusMismatchException.class, ServiceExceptionCodes.TASK_STATUS_MISMATCH);
        map.put(ProviderException.class, ServiceExceptionCodes.PROVIDER_FAILED);
        map.put(ProviderMakeException.class, ServiceExceptionCodes.PROVIDER_MAKE_FAILED);
        map.put(ProviderExecutionException.class, ServiceExceptionCodes.PROVIDER_EXECUTION_FAILED);
        map.put(UnsupportedProviderTypeException.class, ServiceExceptionCodes.PROVIDER_TYPE_UNSUPPORTED);
        map.put(FilterException.class, ServiceExceptionCodes.FILTER_FAILED);
        map.put(FilterMakeException.class, ServiceExceptionCodes.FILTER_MAKE_FAILED);
        map.put(FilterExecutionException.class, ServiceExceptionCodes.FILTER_EXECUTION_FAILED);
        map.put(UnsupportedFilterTypeException.class, ServiceExceptionCodes.FILTER_TYPE_UNSUPPORTED);
        map.put(InvalidVariableValueTypeException.class, ServiceExceptionCodes.INVALID_VARIABLE_VALUE_TYPE);
        map.put(VariableNotExistsException.class, ServiceExceptionCodes.VARIABLE_NOT_EXISTS);
        map.put(VariableValueTypeMismatchException.class, ServiceExceptionCodes.VARIABLE_VALUE_TYPE_MISMATCH);
        map.put(ProviderDataExceededException.class, ServiceExceptionCodes.PROVIDER_DATA_EXCEEDED);
        map.put(QueryException.class, ServiceExceptionCodes.QUERY_FAILED);
        map.put(MapperException.class, ServiceExceptionCodes.MAPPER_FAILED);
        map.put(MapperMakeException.class, ServiceExceptionCodes.MAPPER_MAKE_FAILED);
        map.put(MapperExecutionException.class, ServiceExceptionCodes.MAPPER_EXECUTION_FAILED);
        map.put(UnsupportedMapperTypeException.class, ServiceExceptionCodes.MAPPER_TYPE_UNSUPPORTED);
        return map;
    }

    private ServiceExceptionHelper() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
