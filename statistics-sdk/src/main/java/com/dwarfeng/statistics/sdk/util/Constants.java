package com.dwarfeng.statistics.sdk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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

    private static final Lock LOCK = new ReentrantLock();

    private static List<Integer> taskStatusSpace = null;

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

    private Constants() {
        throw new IllegalStateException("禁止实例化");
    }
}
