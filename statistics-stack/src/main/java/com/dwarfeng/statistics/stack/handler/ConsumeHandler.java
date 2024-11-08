package com.dwarfeng.statistics.stack.handler;

import com.dwarfeng.statistics.stack.struct.ConsumeInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 消费处理器。
 *
 * @author DwArFeng
 * @since 1.2.0
 */
public interface ConsumeHandler extends Handler {

    /**
     * 消费处理器是否启动。
     *
     * @return 消费处理器是否启动。
     * @throws HandlerException 处理器异常。
     */
    boolean isStart() throws HandlerException;

    /**
     * 开启消费处理器。
     *
     * @throws HandlerException 处理器异常。
     */
    void start() throws HandlerException;

    /**
     * 关闭消费处理器。
     *
     * @throws HandlerException 处理器异常。
     */
    void stop() throws HandlerException;

    /**
     * 使消费处理器接受指定的消费信息。
     *
     * @param consumeInfo 指定的消费信息。
     * @throws HandlerException 处理器异常。
     */
    void accept(ConsumeInfo consumeInfo) throws HandlerException;

    /**
     * 获取缓冲器已经缓冲的容量。
     *
     * @return 缓冲器已经缓冲的容量。
     * @throws HandlerException 处理器异常。
     */
    int bufferedSize() throws HandlerException;

    /**
     * 获取缓冲器的容量。
     *
     * @return 缓冲器的容量。
     * @throws HandlerException 处理器异常。
     */
    int getBufferSize() throws HandlerException;

    /**
     * 设置缓冲器的容量。
     *
     * @param size 缓冲器的容量。
     * @throws HandlerException 处理器异常。
     */
    void setBufferSize(int size) throws HandlerException;

    /**
     * 获取消费者的线程数量。
     *
     * @return 消费者的线程数量。
     * @throws HandlerException 处理器异常。
     */
    int getThread() throws HandlerException;

    /**
     * 设置消费者的线程数量。
     *
     * @param thread 消费者的线程数量。
     * @throws HandlerException 处理器异常。
     */
    void setThread(int thread) throws HandlerException;

    /**
     * 获取消费者是否空闲。
     *
     * @return 消费者是否空闲。
     * @throws HandlerException 处理器异常。
     */
    boolean isIdle() throws HandlerException;
}
