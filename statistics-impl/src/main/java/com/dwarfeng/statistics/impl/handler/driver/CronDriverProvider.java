package com.dwarfeng.statistics.impl.handler.driver;

import com.dwarfeng.statistics.impl.handler.DriverProvider;
import com.dwarfeng.statistics.stack.bean.entity.DriverInfo;
import com.dwarfeng.statistics.stack.exception.DriverException;
import com.dwarfeng.statistics.stack.handler.Driver;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Cron 驱动提供器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class CronDriverProvider implements DriverProvider {

    public static final String SUPPORT_TYPE = "cron_driver";

    private final CronDriver cronDriver;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public CronDriverProvider(CronDriver cronDriver) {
        this.cronDriver = cronDriver;
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(SUPPORT_TYPE, type);
    }

    @Override
    public Driver provide() {
        return cronDriver;
    }

    @Component
    public static class CronDriver extends AbstractDriver {

        private final ThreadPoolTaskScheduler scheduler;

        private final Lock lock = new ReentrantLock();
        private final Set<ScheduledFuture<?>> scheduledFutures = new HashSet<>();
        private final Set<CronProcessor> cronProcessors = new HashSet<>();

        public CronDriver(ThreadPoolTaskScheduler scheduler) {
            this.scheduler = scheduler;
        }

        @Override
        public void register(DriverInfo driverInfo) throws DriverException {
            lock.lock();
            try {
                LongIdKey statisticsSettingKey = driverInfo.getStatisticsSettingKey();
                String cron = driverInfo.getParam();
                CronProcessor cronProcessor = new CronProcessor(context, statisticsSettingKey);
                CronTrigger cronTrigger = new CronTrigger(cron);
                ScheduledFuture<?> scheduledFuture = scheduler.schedule(cronProcessor, cronTrigger);
                cronProcessors.add(cronProcessor);
                scheduledFutures.add(scheduledFuture);
            } catch (Exception e) {
                throw new DriverException(e);
            } finally {
                lock.unlock();
            }
        }

        @Override
        public void unregisterAll() {
            lock.lock();
            try {
                for (ScheduledFuture<?> scheduledFuture : scheduledFutures) {
                    scheduledFuture.cancel(true);
                }
                for (CronProcessor cronProcessor : cronProcessors) {
                    cronProcessor.shutdown();
                }
            } finally {
                lock.unlock();
            }
        }

        @Override
        public String toString() {
            return "CronDriver{" +
                    "context=" + context +
                    '}';
        }
    }

    private static class CronProcessor implements Runnable {

        private static final Logger LOGGER = LoggerFactory.getLogger(CronProcessor.class);

        private final Driver.Context context;
        private final LongIdKey statisticsSettingKey;

        private final Lock lock = new ReentrantLock();
        private boolean runningFlag = true;

        private CronProcessor(Driver.Context context, LongIdKey statisticsSettingKey) {
            this.context = context;
            this.statisticsSettingKey = statisticsSettingKey;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                if (!runningFlag) {
                    return;
                }

                LOGGER.debug("计划时间已到达, cron 驱动器驱动 {} 记录设置执行动作...", statisticsSettingKey);
                context.execute(statisticsSettingKey);
            } catch (Exception e) {
                LOGGER.warn("记录 {} 时出现异常, 放弃本次记录", statisticsSettingKey, e);
            } finally {
                lock.unlock();
            }
        }

        void shutdown() {
            lock.lock();
            try {
                runningFlag = false;
            } finally {
                lock.unlock();
            }
        }
    }
}
