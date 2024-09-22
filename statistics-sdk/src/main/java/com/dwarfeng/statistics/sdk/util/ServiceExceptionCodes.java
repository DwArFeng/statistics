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
    }

    private ServiceExceptionCodes() {
        throw new IllegalStateException("禁止实例化");
    }
}
