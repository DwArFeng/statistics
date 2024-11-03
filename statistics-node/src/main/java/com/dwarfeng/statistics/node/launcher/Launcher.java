package com.dwarfeng.statistics.node.launcher;

import com.dwarfeng.springterminator.sdk.util.ApplicationUtil;
import com.dwarfeng.statistics.node.handler.LauncherSettingHandler;
import com.dwarfeng.statistics.stack.service.*;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Date;

/**
 * 程序启动器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class Launcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        ApplicationUtil.launch(new String[]{
                "classpath:spring/application-context*.xml",
                "file:opt/opt*.xml",
                "file:optext/opt*.xml"
        }, ctx -> {
            // 根据启动器设置处理器的设置，选择性重置驱动器。
            mayResetDriver(ctx);

            // 根据启动器设置处理器的设置，选择性重置提供器。
            mayResetProvider(ctx);

            // 根据启动器设置处理器的设置，选择性重置过滤器。
            mayResetFilter(ctx);

            // 根据启动器设置处理器的设置，选择性重置映射器。
            mayResetMapper(ctx);

            // 根据启动器设置处理器的设置，选择性启动接收服务。
            mayStartReceive(ctx);

            // 根据启动器设置处理器的设置，选择性上线主管服务。
            mayOnlineSupervise(ctx);
            // 根据启动器设置处理器的设置，选择性启动主管服务。
            mayEnableSupervise(ctx);

            // 根据启动器设置处理器的设置，选择性开启重置服务。
            mayStartReset(ctx);

            // 根据启动器设置处理器的设置，选择性上线任务检查服务。
            mayOnlineTaskCheck(ctx);
            // 根据启动器设置处理器的设置，选择性启动任务检查服务。
            mayEnableTaskCheck(ctx);
        });
    }

    private static void mayResetDriver(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 判断是否重置驱动器支持，并按条件执行重置操作。
        if (launcherSettingHandler.isResetDriverSupport()) {
            LOGGER.info("重置驱动器支持...");
            DriverSupportMaintainService maintainService = ctx.getBean(DriverSupportMaintainService.class);
            try {
                maintainService.reset();
            } catch (ServiceException e) {
                LOGGER.warn("驱动器支持重置失败，异常信息如下", e);
            }
        }
    }

    private static void mayResetProvider(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 判断是否重置提供器支持，并按条件执行重置操作。
        if (launcherSettingHandler.isResetProviderSupport()) {
            LOGGER.info("重置提供器支持...");
            ProviderSupportMaintainService maintainService = ctx.getBean(ProviderSupportMaintainService.class);
            try {
                maintainService.reset();
            } catch (ServiceException e) {
                LOGGER.warn("提供器支持重置失败，异常信息如下", e);
            }
        }
    }

    private static void mayResetFilter(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 判断是否重置过滤器支持，并按条件执行重置操作。
        if (launcherSettingHandler.isResetFilterSupport()) {
            LOGGER.info("重置过滤器支持...");
            FilterSupportMaintainService maintainService = ctx.getBean(FilterSupportMaintainService.class);
            try {
                maintainService.reset();
            } catch (ServiceException e) {
                LOGGER.warn("过滤器支持重置失败，异常信息如下", e);
            }
        }
    }

    private static void mayResetMapper(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 判断是否重置映射器支持，并按条件执行重置操作。
        if (launcherSettingHandler.isResetMapperSupport()) {
            LOGGER.info("重置映射器支持...");
            MapperSupportMaintainService maintainService = ctx.getBean(MapperSupportMaintainService.class);
            try {
                maintainService.reset();
            } catch (ServiceException e) {
                LOGGER.warn("映射器支持重置失败，异常信息如下", e);
            }
        }
    }

    private static void mayOnlineSupervise(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 获取主管 QOS 服务。
        SuperviseQosService superviseQosService = ctx.getBean(SuperviseQosService.class);

        // 判断主管处理器是否上线主管服务，并按条件执行不同的操作。
        long onlineSuperviseDelay = launcherSettingHandler.getOnlineSuperviseDelay();
        if (onlineSuperviseDelay == 0) {
            LOGGER.info("立即上线主管服务...");
            try {
                superviseQosService.online();
            } catch (ServiceException e) {
                LOGGER.error("无法上线主管服务，异常原因如下", e);
            }
        } else if (onlineSuperviseDelay > 0) {
            LOGGER.info("{} 毫秒后上线主管服务...", onlineSuperviseDelay);
            scheduler.schedule(
                    () -> {
                        LOGGER.info("上线主管服务...");
                        try {
                            superviseQosService.online();
                        } catch (ServiceException e) {
                            LOGGER.error("无法上线主管服务，异常原因如下", e);
                        }
                    },
                    new Date(System.currentTimeMillis() + onlineSuperviseDelay)
            );
        }
    }

    private static void mayEnableSupervise(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 获取主管 QOS 服务。
        SuperviseQosService superviseQosService = ctx.getBean(SuperviseQosService.class);

        // 判断主管处理器是否启动主管服务，并按条件执行不同的操作。
        long enableSuperviseDelay = launcherSettingHandler.getEnableSuperviseDelay();
        if (enableSuperviseDelay == 0) {
            LOGGER.info("立即启动主管服务...");
            try {
                superviseQosService.start();
            } catch (ServiceException e) {
                LOGGER.error("无法启动主管服务，异常原因如下", e);
            }
        } else if (enableSuperviseDelay > 0) {
            LOGGER.info("{} 毫秒后启动主管服务...", enableSuperviseDelay);
            scheduler.schedule(
                    () -> {
                        LOGGER.info("启动主管服务...");
                        try {
                            superviseQosService.start();
                        } catch (ServiceException e) {
                            LOGGER.error("无法启动主管服务，异常原因如下", e);
                        }
                    },
                    new Date(System.currentTimeMillis() + enableSuperviseDelay)
            );
        }
    }

    private static void mayStartReceive(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 获取接收 QOS 服务。
        ReceiveQosService receiveQosService = ctx.getBean(ReceiveQosService.class);

        // 判断接收处理器是否启动接收服务，并按条件执行不同的操作。
        long startReceiveDelay = launcherSettingHandler.getStartReceiveDelay();
        if (startReceiveDelay == 0) {
            LOGGER.info("立即启动接收服务...");
            try {
                receiveQosService.start();
            } catch (ServiceException e) {
                LOGGER.error("无法启动接收服务，异常原因如下", e);
            }
        } else if (startReceiveDelay > 0) {
            LOGGER.info("{} 毫秒后启动接收服务...", startReceiveDelay);
            scheduler.schedule(
                    () -> {
                        LOGGER.info("启动接收服务...");
                        try {
                            receiveQosService.start();
                        } catch (ServiceException e) {
                            LOGGER.error("无法启动接收服务，异常原因如下", e);
                        }
                    },
                    new Date(System.currentTimeMillis() + startReceiveDelay)
            );
        }
    }

    private static void mayStartReset(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 处理重置处理器的启动选项。
        ResetQosService resetQosService = ctx.getBean(ResetQosService.class);

        // 重置处理器是否启动重置服务。
        long startResetDelay = launcherSettingHandler.getStartResetDelay();
        if (startResetDelay == 0) {
            LOGGER.info("立即启动重置服务...");
            try {
                resetQosService.start();
            } catch (ServiceException e) {
                LOGGER.error("无法启动重置服务，异常原因如下", e);
            }
        } else if (startResetDelay > 0) {
            LOGGER.info("{} 毫秒后启动重置服务...", startResetDelay);
            scheduler.schedule(
                    () -> {
                        LOGGER.info("启动重置服务...");
                        try {
                            resetQosService.start();
                        } catch (ServiceException e) {
                            LOGGER.error("无法启动重置服务，异常原因如下", e);
                        }
                    },
                    new Date(System.currentTimeMillis() + startResetDelay)
            );
        }
    }

    private static void mayOnlineTaskCheck(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 获取任务检查 QOS 服务。
        TaskCheckQosService taskCheckQosService = ctx.getBean(TaskCheckQosService.class);

        // 判断任务检查处理器是否上线任务检查服务，并按条件执行不同的操作。
        long onlineTaskCheckDelay = launcherSettingHandler.getOnlineTaskCheckDelay();
        if (onlineTaskCheckDelay == 0) {
            LOGGER.info("立即上线任务检查服务...");
            try {
                taskCheckQosService.online();
            } catch (ServiceException e) {
                LOGGER.error("无法上线任务检查服务，异常原因如下", e);
            }
        } else if (onlineTaskCheckDelay > 0) {
            LOGGER.info("{} 毫秒后上线任务检查服务...", onlineTaskCheckDelay);
            scheduler.schedule(
                    () -> {
                        LOGGER.info("上线任务检查服务...");
                        try {
                            taskCheckQosService.online();
                        } catch (ServiceException e) {
                            LOGGER.error("无法上线任务检查服务，异常原因如下", e);
                        }
                    },
                    new Date(System.currentTimeMillis() + onlineTaskCheckDelay)
            );
        }
    }

    private static void mayEnableTaskCheck(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 获取任务检查 QOS 服务。
        TaskCheckQosService taskCheckQosService = ctx.getBean(TaskCheckQosService.class);

        // 判断任务检查处理器是否启动任务检查服务，并按条件执行不同的操作。
        long enableTaskCheckDelay = launcherSettingHandler.getEnableTaskCheckDelay();
        if (enableTaskCheckDelay == 0) {
            LOGGER.info("立即启动任务检查服务...");
            try {
                taskCheckQosService.start();
            } catch (ServiceException e) {
                LOGGER.error("无法启动任务检查服务，异常原因如下", e);
            }
        } else if (enableTaskCheckDelay > 0) {
            LOGGER.info("{} 毫秒后启动任务检查服务...", enableTaskCheckDelay);
            scheduler.schedule(
                    () -> {
                        LOGGER.info("启动任务检查服务...");
                        try {
                            taskCheckQosService.start();
                        } catch (ServiceException e) {
                            LOGGER.error("无法启动任务检查服务，异常原因如下", e);
                        }
                    },
                    new Date(System.currentTimeMillis() + enableTaskCheckDelay)
            );
        }
    }
}
