package com.dwarfeng.statistics.sdk.util;

import com.dwarfeng.subgrade.stack.exception.ServiceException;

/**
 * 服务异常代码。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class ServiceExceptionCodes {

    private static int EXCEPTION_CODE_OFFSET = 40000;

    public static final ServiceException.Code DRIVER_FAILED =
            new ServiceException.Code(offset(0), "driver failed");
    public static final ServiceException.Code DRIVER_TYPE_UNSUPPORTED =
            new ServiceException.Code(offset(1), "driver type unsupported");
    public static final ServiceException.Code DISPATCHER_FAILED =
            new ServiceException.Code(offset(10), "dispatcher failed");
    public static final ServiceException.Code DISPATCHER_NOT_START =
            new ServiceException.Code(offset(11), "dispatcher not start");
    public static final ServiceException.Code DISPATCHER_EXECUTION_FAILED =
            new ServiceException.Code(offset(12), "dispatcher execution failed");
    public static final ServiceException.Code RECEIVER_FAILED =
            new ServiceException.Code(offset(21), "receiver failed");
    public static final ServiceException.Code RECEIVER_NOT_START =
            new ServiceException.Code(offset(22), "receiver not start");
    public static final ServiceException.Code RECEIVER_EXECUTION_FAILED =
            new ServiceException.Code(offset(23), "receiver execution failed");
    public static final ServiceException.Code FUNCTION_NOT_SUPPORTED =
            new ServiceException.Code(offset(30), "function not supported");
    public static final ServiceException.Code LATEST_NOT_SUPPORTED =
            new ServiceException.Code(offset(31), "latest not supported");
    public static final ServiceException.Code LOOKUP_NOT_SUPPORTED =
            new ServiceException.Code(offset(32), "lookup not supported");
    public static final ServiceException.Code QUERY_NOT_SUPPORTED =
            new ServiceException.Code(offset(33), "query not supported");
    public static final ServiceException.Code NATIVE_QUERY_NOT_SUPPORTED =
            new ServiceException.Code(offset(34), "native query not supported");
    public static final ServiceException.Code KEEPER_NOT_SUPPORTED =
            new ServiceException.Code(offset(40), "keeper not supported");
    public static final ServiceException.Code PERSISTER_NOT_SUPPORTED =
            new ServiceException.Code(offset(50), "persister not supported");
    public static final ServiceException.Code KEEP_EXCEPTION =
            new ServiceException.Code(offset(60), "keep exception");
    public static final ServiceException.Code LATEST_EXCEPTION =
            new ServiceException.Code(offset(61), "latest exception");
    public static final ServiceException.Code UPDATE_EXCEPTION =
            new ServiceException.Code(offset(62), "update exception");
    public static final ServiceException.Code PERSIST_EXCEPTION =
            new ServiceException.Code(offset(70), "persist exception");
    public static final ServiceException.Code RECORD_EXCEPTION =
            new ServiceException.Code(offset(71), "record exception");
    public static final ServiceException.Code LOOKUP_EXCEPTION =
            new ServiceException.Code(offset(72), "lookup exception");
    public static final ServiceException.Code NATIVE_QUERY_EXCEPTION =
            new ServiceException.Code(offset(73), "native query exception");
    public static final ServiceException.Code STATISTICS_SETTING_NOT_EXISTS =
            new ServiceException.Code(offset(80), "statistics setting not exists");
    public static final ServiceException.Code TASK_NOT_EXISTS =
            new ServiceException.Code(offset(90), "task not exists");
    public static final ServiceException.Code INVALID_TASK_STATUS =
            new ServiceException.Code(offset(100), "invalid task status");
    public static final ServiceException.Code TASK_STATUS_MISMATCH =
            new ServiceException.Code(offset(110), "task status mismatch");
    public static final ServiceException.Code PROVIDER_FAILED =
            new ServiceException.Code(offset(120), "provider failed");
    public static final ServiceException.Code PROVIDER_MAKE_FAILED =
            new ServiceException.Code(offset(121), "provider make failed");
    public static final ServiceException.Code PROVIDER_EXECUTION_FAILED =
            new ServiceException.Code(offset(122), "provider execution failed");
    public static final ServiceException.Code PROVIDER_TYPE_UNSUPPORTED =
            new ServiceException.Code(offset(123), "provider type unsupported");
    public static final ServiceException.Code FILTER_FAILED =
            new ServiceException.Code(offset(130), "filter failed");
    public static final ServiceException.Code FILTER_MAKE_FAILED =
            new ServiceException.Code(offset(131), "filter make failed");
    public static final ServiceException.Code FILTER_EXECUTION_FAILED =
            new ServiceException.Code(offset(132), "filter execution failed");
    public static final ServiceException.Code FILTER_TYPE_UNSUPPORTED =
            new ServiceException.Code(offset(133), "filter type unsupported");

    private static int offset(int i) {
        return EXCEPTION_CODE_OFFSET + i;
    }

    /**
     * 获取异常代号的偏移量。
     *
     * @return 异常代号的偏移量。
     */
    public static int getExceptionCodeOffset() {
        return EXCEPTION_CODE_OFFSET;
    }

    /**
     * 设置异常代号的偏移量。
     *
     * @param exceptionCodeOffset 指定的异常代号的偏移量。
     */
    public static void setExceptionCodeOffset(int exceptionCodeOffset) {
        // 设置 EXCEPTION_CODE_OFFSET 的值。
        EXCEPTION_CODE_OFFSET = exceptionCodeOffset;

        // 以新的 EXCEPTION_CODE_OFFSET 为基准，更新异常代码的值。
        DRIVER_FAILED.setCode(offset(0));
        DRIVER_TYPE_UNSUPPORTED.setCode(offset(1));
        DISPATCHER_FAILED.setCode(offset(10));
        DISPATCHER_NOT_START.setCode(offset(11));
        DISPATCHER_EXECUTION_FAILED.setCode(offset(12));
        RECEIVER_FAILED.setCode(offset(21));
        RECEIVER_NOT_START.setCode(offset(22));
        RECEIVER_EXECUTION_FAILED.setCode(offset(23));
        FUNCTION_NOT_SUPPORTED.setCode(offset(30));
        LATEST_NOT_SUPPORTED.setCode(offset(31));
        LOOKUP_NOT_SUPPORTED.setCode(offset(32));
        QUERY_NOT_SUPPORTED.setCode(offset(33));
        NATIVE_QUERY_NOT_SUPPORTED.setCode(offset(34));
        KEEPER_NOT_SUPPORTED.setCode(offset(40));
        PERSISTER_NOT_SUPPORTED.setCode(offset(50));
        KEEP_EXCEPTION.setCode(offset(60));
        LATEST_EXCEPTION.setCode(offset(61));
        UPDATE_EXCEPTION.setCode(offset(62));
        PERSIST_EXCEPTION.setCode(offset(70));
        RECORD_EXCEPTION.setCode(offset(71));
        LOOKUP_EXCEPTION.setCode(offset(72));
        NATIVE_QUERY_EXCEPTION.setCode(offset(73));
        STATISTICS_SETTING_NOT_EXISTS.setCode(offset(80));
        TASK_NOT_EXISTS.setCode(offset(90));
        INVALID_TASK_STATUS.setCode(offset(100));
        TASK_STATUS_MISMATCH.setCode(offset(110));
        PROVIDER_FAILED.setCode(offset(120));
        PROVIDER_MAKE_FAILED.setCode(offset(121));
        PROVIDER_EXECUTION_FAILED.setCode(offset(122));
        PROVIDER_TYPE_UNSUPPORTED.setCode(offset(123));
        FILTER_FAILED.setCode(offset(130));
        FILTER_MAKE_FAILED.setCode(offset(131));
        FILTER_EXECUTION_FAILED.setCode(offset(132));
        FILTER_TYPE_UNSUPPORTED.setCode(offset(133));
    }

    private ServiceExceptionCodes() {
        throw new IllegalStateException("禁止实例化");
    }
}
