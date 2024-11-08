package com.dwarfeng.statistics.sdk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 常量类。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class Constants {

    private static final Logger LOGGER = LoggerFactory.getLogger(Constants.class);

    @TaskStatusItem
    public static final int TASK_STATUS_CREATED = 0;
    @TaskStatusItem
    public static final int TASK_STATUS_PROCESSING = 1;
    @TaskStatusItem
    public static final int TASK_STATUS_FINISHED = 2;
    @TaskStatusItem
    public static final int TASK_STATUS_FAILED = 3;
    @TaskStatusItem
    public static final int TASK_STATUS_EXPIRED = 4;
    @TaskStatusItem
    public static final int TASK_STATUS_DIED = 5;

    @VariableValueTypeItem
    public static final int VARIABLE_VALUE_TYPE_STRING = 0;
    @VariableValueTypeItem
    public static final int VARIABLE_VALUE_TYPE_LONG = 1;
    @VariableValueTypeItem
    public static final int VARIABLE_VALUE_TYPE_DOUBLE = 2;
    @VariableValueTypeItem
    public static final int VARIABLE_VALUE_TYPE_BOOLEAN = 3;
    @VariableValueTypeItem
    public static final int VARIABLE_VALUE_TYPE_DATE = 4;

    /**
     * 观察操作：缺省起始日期。
     */
    public static final Date WATCH_DEFAULT_START_DATE = new Date(0);

    /**
     * 观察操作：缺省结束日期。
     *
     * <p>
     * 该日期为 4423-01-01 00:00:00.000。
     */
    public static final Date WATCH_DEFAULT_END_DATE = new Date(77409216000000L);

    /**
     * 观察操作：缺省页码。
     */
    public static final int WATCH_DEFAULT_PAGE = 0;

    /**
     * 观察操作：缺省每页的最大行数。
     */
    public static final int WATCH_DEFAULT_ROWS = Integer.MAX_VALUE;

    /**
     * 消费者处理器的检查间隔。
     */
    public static final long CONSUMER_HANDLER_CHECK_INTERVAL = 5000L;

    private static final Lock LOCK = new ReentrantLock();

    private static List<Integer> taskStatusSpace = null;
    private static List<Integer> variableValueTypeSpace = null;

    /**
     * 任务状态空间。
     *
     * @return 任务状态空间。
     */
    public static List<Integer> taskStatusSpace() {
        if (Objects.nonNull(taskStatusSpace)) {
            return taskStatusSpace;
        }
        // 基于线程安全的懒加载初始化结果列表。
        LOCK.lock();
        try {
            if (Objects.nonNull(taskStatusSpace)) {
                return taskStatusSpace;
            }
            initTaskStatusSpace();
            return taskStatusSpace;
        } finally {
            LOCK.unlock();
        }
    }

    private static void initTaskStatusSpace() {
        List<Integer> result = new ArrayList<>();

        Field[] declaredFields = Constants.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (!declaredField.isAnnotationPresent(TaskStatusItem.class)) {
                continue;
            }
            Integer value;
            try {
                value = (Integer) declaredField.get(null);
                result.add(value);
            } catch (Exception e) {
                LOGGER.error("初始化异常, 请检查代码, 信息如下: ", e);
            }
        }

        taskStatusSpace = Collections.unmodifiableList(result);
    }

    /**
     * 变量值类型空间。
     *
     * @return 变量值类型空间。
     */
    public static List<Integer> variableValueTypeSpace() {
        if (Objects.nonNull(variableValueTypeSpace)) {
            return variableValueTypeSpace;
        }
        // 基于线程安全的懒加载初始化结果列表。
        LOCK.lock();
        try {
            if (Objects.nonNull(variableValueTypeSpace)) {
                return variableValueTypeSpace;
            }
            initVariableValueTypeSpace();
            return variableValueTypeSpace;
        } finally {
            LOCK.unlock();
        }
    }

    private static void initVariableValueTypeSpace() {
        List<Integer> result = new ArrayList<>();

        Field[] declaredFields = Constants.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (!declaredField.isAnnotationPresent(VariableValueTypeItem.class)) {
                continue;
            }
            Integer value;
            try {
                value = (Integer) declaredField.get(null);
                result.add(value);
            } catch (Exception e) {
                LOGGER.error("初始化异常, 请检查代码, 信息如下: ", e);
            }
        }

        variableValueTypeSpace = Collections.unmodifiableList(result);
    }

    private Constants() {
        throw new IllegalStateException("禁止实例化");
    }
}
