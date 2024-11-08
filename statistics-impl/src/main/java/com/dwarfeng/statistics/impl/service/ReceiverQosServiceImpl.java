package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.handler.ConsumeHandler;
import com.dwarfeng.statistics.stack.handler.ReceiveHandler;
import com.dwarfeng.statistics.stack.handler.Receiver;
import com.dwarfeng.statistics.stack.handler.ReceiverHandler;
import com.dwarfeng.statistics.stack.service.ReceiveQosService;
import com.dwarfeng.statistics.stack.struct.ConsumerStatus;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ReceiverQosServiceImpl implements ReceiveQosService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiverQosServiceImpl.class);

    private final ReceiveHandler receiveHandler;
    private final ReceiverHandler receiverHandler;
    private final ConsumeHandler consumeHandler;

    private final ServiceExceptionMapper sem;

    private final Lock lock = new ReentrantLock();

    private boolean startFlag = false;

    public ReceiverQosServiceImpl(
            ReceiveHandler receiveHandler,
            ReceiverHandler receiverHandler,
            ConsumeHandler consumeHandler,
            ServiceExceptionMapper sem
    ) {
        this.receiveHandler = receiveHandler;
        this.receiverHandler = receiverHandler;
        this.consumeHandler = consumeHandler;
        this.sem = sem;
    }

    @PreDestroy
    private void dispose() throws Exception {
        lock.lock();
        try {
            stop0();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isStarted() throws ServiceException {
        lock.lock();
        try {
            return startFlag;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断接收处理器是否启动时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void start() throws ServiceException {
        lock.lock();
        try {
            start0();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("启动接收处理器时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }

    private void start0() throws Exception {
        if (startFlag) {
            return;
        }

        try {
            LOGGER.info("开启接收服务...");
            consumeHandler.start();
            receiveHandler.start();
            startFlag = true;
        } catch (Exception e) {
            LOGGER.warn("开启接收服务时发生异常, 将尝试关闭消费服务, 异常信息如下: ", e);
            startFlag = false;
            try {
                consumeHandler.stop();
                receiveHandler.stop();
            } catch (Exception e1) {
                LOGGER.warn("开启接收服务时发生异常, 且关闭消费服务时发生异常, 关闭服务的异常信息如下: ", e1);
            }
            throw e;
        }
    }

    @Override
    public void stop() throws ServiceException {
        lock.lock();
        try {
            stop0();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("停止接收处理器时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }

    private void stop0() throws Exception {
        if (!startFlag) {
            return;
        }

        try {
            LOGGER.info("关闭接收服务...");
            receiveHandler.stop();
            try {
                Thread.sleep(1000);
            } catch (Exception ignored) {
            }
            consumeHandler.stop();
            startFlag = false;
        } catch (Exception e) {
            LOGGER.warn("关闭接收服务时发生异常, 异常信息如下: ", e);
            throw e;
        }
    }

    @Override
    public Receiver currentReceiver() throws ServiceException {
        lock.lock();
        try {
            return receiverHandler.current();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取当前正在使用的接收器时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<Receiver> allReceivers() throws ServiceException {
        lock.lock();
        try {
            return receiverHandler.all();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取全部接收器时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public ConsumerStatus getConsumerStatus() throws ServiceException {
        lock.lock();
        try {
            return new ConsumerStatus(
                    consumeHandler.bufferedSize(),
                    consumeHandler.getBufferSize(),
                    consumeHandler.getThread(),
                    consumeHandler.isIdle()
            );
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取消费者状态时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setConsumerParameters(Integer bufferSize, Integer thread) throws ServiceException {
        lock.lock();
        try {
            consumeHandler.setBufferSize(
                    Objects.isNull(bufferSize) ? consumeHandler.getBufferSize() : bufferSize
            );
            consumeHandler.setThread(
                    Objects.isNull(thread) ? consumeHandler.getThread() : thread
            );
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("设置消费者参数时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "ReceiverQosServiceImpl{" +
                "receiveHandler=" + receiveHandler +
                ", receiverHandler=" + receiverHandler +
                ", consumeHandler=" + consumeHandler +
                ", sem=" + sem +
                ", lock=" + lock +
                ", startFlag=" + startFlag +
                '}';
    }
}
