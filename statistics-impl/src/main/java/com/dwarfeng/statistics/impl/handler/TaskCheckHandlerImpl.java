package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.stack.bean.dto.TaskExpireInfo;
import com.dwarfeng.statistics.stack.bean.entity.Task;
import com.dwarfeng.statistics.stack.handler.TaskCheckHandler;
import com.dwarfeng.statistics.stack.handler.TaskOperateHandler;
import com.dwarfeng.statistics.stack.service.TaskMaintainService;
import com.dwarfeng.subgrade.impl.handler.CuratorDistributedLockHandler;
import com.dwarfeng.subgrade.impl.handler.Worker;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;

@Component
public class TaskCheckHandlerImpl implements TaskCheckHandler {

    private final CuratorDistributedLockHandler handler;

    public TaskCheckHandlerImpl(
            CuratorFramework curatorFramework,
            @Value("${curator.latch_path.task_check.leader_latch}") String leaserLatchPath,
            TaskCheckWorker taskCheckWorker
    ) {
        handler = new CuratorDistributedLockHandler(curatorFramework, leaserLatchPath, taskCheckWorker);
    }

    @BehaviorAnalyse
    @Override
    public boolean isOnline() {
        return handler.isOnline();
    }

    @BehaviorAnalyse
    @Override
    public void online() throws HandlerException {
        handler.online();
    }

    @BehaviorAnalyse
    @Override
    public void offline() throws HandlerException {
        handler.offline();
    }

    @BehaviorAnalyse
    @Override
    public boolean isStarted() {
        return handler.isStarted();
    }

    @BehaviorAnalyse
    @Override
    public void start() throws HandlerException {
        handler.start();
    }

    @BehaviorAnalyse
    @Override
    public void stop() throws HandlerException {
        handler.stop();
    }

    @BehaviorAnalyse
    @Override
    public boolean isLockHolding() {
        return handler.isLockHolding();
    }

    @BehaviorAnalyse
    @Override
    public boolean isWorking() {
        return handler.isWorking();
    }

    @Component
    public static class TaskCheckWorker implements Worker {

        private static final Logger LOGGER = LoggerFactory.getLogger(TaskCheckWorker.class);

        private final TaskMaintainService taskMaintainService;

        private final TaskOperateHandler taskOperateHandler;

        private final ThreadPoolTaskScheduler scheduler;

        @Value("${task.check.expire_check.cron}")
        private String expireCheckCron;
        @Value("${task.check.die_check.cron}")
        private String dieCheckCron;

        private Future<?> expireCheckFuture;
        private Future<?> dieCheckFuture;

        public TaskCheckWorker(
                TaskMaintainService taskMaintainService,
                TaskOperateHandler taskOperateHandler,
                ThreadPoolTaskScheduler scheduler
        ) {
            this.taskMaintainService = taskMaintainService;
            this.taskOperateHandler = taskOperateHandler;
            this.scheduler = scheduler;
        }

        @Override
        public void work() {
            if (Objects.isNull(expireCheckFuture)) {
                expireCheckFuture = scheduler.schedule(this::expireCheck, new CronTrigger(expireCheckCron));
            }
            if (Objects.isNull(dieCheckFuture)) {
                dieCheckFuture = scheduler.schedule(this::dieCheck, new CronTrigger(dieCheckCron));
            }
        }

        @Override
        public void rest() {
            if (Objects.nonNull(expireCheckFuture)) {
                expireCheckFuture.cancel(true);
                expireCheckFuture = null;
            }
            if (Objects.nonNull(dieCheckFuture)) {
                dieCheckFuture.cancel(true);
                dieCheckFuture = null;
            }
        }

        public void expireCheck() {
            try {
                LOGGER.info("检查过期任务...");

                // 获取所有过期的任务。
                List<Task> tasksToExpire = taskMaintainService.lookupAsList(
                        TaskMaintainService.SHOULD_EXPIRE, new Object[0]
                );
                // 调用操作处理器，将过期的任务设置为过期。
                for (Task task : tasksToExpire) {
                    // 调用操作处理器过期任务。
                    taskOperateHandler.expire(new TaskExpireInfo(task.getKey()));
                }
            } catch (Exception e) {
                LOGGER.warn("检查过期任务时发生异常，异常信息如下", e);
            }
        }

        public void dieCheck() {
            try {
                LOGGER.info("检查死亡任务...");

                // 获取所有死亡的任务。
                List<Task> tasksToExpire = taskMaintainService.lookupAsList(
                        TaskMaintainService.SHOULD_DIE, new Object[0]
                );
                // 调用操作处理器，将死亡的任务设置为死亡。
                for (Task task : tasksToExpire) {
                    // 调用操作处理器过期任务。
                    taskOperateHandler.expire(new TaskExpireInfo(task.getKey()));
                }
            } catch (Exception e) {
                LOGGER.warn("检查死亡任务时发生异常，异常信息如下", e);
            }
        }
    }
}
