package com.dwarfeng.statistics.impl.handler.receiver;

import com.dwarfeng.statistics.stack.exception.ReceiverException;
import com.dwarfeng.statistics.stack.exception.ReceiverExecutionException;
import com.dwarfeng.statistics.stack.handler.Receiver;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 接收器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractReceiver implements Receiver {

    protected String receiverType;
    protected Context context;

    private final Lock lock = new ReentrantLock();

    /**
     * 启动标记。
     *
     * <p>
     * 用于标记接收处理器是否已经启动。
     *
     * <p>
     * 实现类可以通过调用 {@link #isStarted()} 方法来获取该标记，以判断接收处理器是否已经启动。<br>
     * 实现类不允许直接修改该标记。
     */
    private boolean startFlag = false;

    public AbstractReceiver() {
    }

    public AbstractReceiver(String receiverType) {
        this.receiverType = receiverType;
    }

    @Override
    public boolean supportType(String type) {
        lock.lock();
        try {
            return receiverType.equals(type);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void init(Context context) {
        lock.lock();
        try {
            this.context = context;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void start() throws ReceiverException {
        lock.lock();
        try {
            if (startFlag) {
                return;
            }
            try {
                doStart();
            } catch (ReceiverExecutionException e) {
                throw e;
            } catch (Exception e) {
                throw new ReceiverExecutionException(e);
            }
            startFlag = true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 启动接收处理器。
     *
     * <p>
     * 实现该方法时只需要实现启动逻辑，无需进行启动状态的检查及维护，无需考虑线程安全问题。
     *
     * @throws Exception 启动过程中抛出的任何异常。
     */
    protected abstract void doStart() throws Exception;

    @Override
    public void stop() throws ReceiverException {
        lock.lock();
        try {
            if (!startFlag) {
                return;
            }
            try {
                doStop();
            } catch (ReceiverExecutionException e) {
                throw e;
            } catch (Exception e) {
                throw new ReceiverExecutionException(e);
            }
            startFlag = false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 停止接收处理器。
     *
     * <p>
     * 实现该方法时只需要实现停止逻辑，无需进行启动状态的检查及维护，无需考虑线程安全问题。
     *
     * @throws Exception 停止过程中抛出的任何异常。
     */
    protected abstract void doStop() throws Exception;

    /**
     * 返回接收处理器是否已经启动。
     *
     * @return 接收处理器是否已经启动。
     */
    protected boolean isStarted() {
        lock.lock();
        try {
            return startFlag;
        } finally {
            lock.unlock();
        }
    }

    public String getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }

    @Override
    public String toString() {
        return "AbstractReceiver{" +
                "receiverType='" + receiverType + '\'' +
                ", lock=" + lock +
                ", startFlag=" + startFlag +
                '}';
    }
}
