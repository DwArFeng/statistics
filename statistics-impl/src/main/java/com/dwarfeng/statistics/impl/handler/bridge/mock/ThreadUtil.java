package com.dwarfeng.statistics.impl.handler.bridge.mock;

/**
 * 线程工具类。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
final class ThreadUtil {

    /**
     * 线程休眠到指定的时间戳。
     *
     * @param timestamp 指定的时间戳。
     */
    public static void sleepUntil(long timestamp) {
        long now = System.currentTimeMillis();
        if (now >= timestamp) {
            return;
        }
        try {
            Thread.sleep(timestamp - now);
        } catch (InterruptedException ignored) {
        }
    }

    private ThreadUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
