package com.dwarfeng.statistics.node.launcher;

import com.dwarfeng.springterminator.sdk.util.ApplicationUtil;
import com.dwarfeng.statistics.node.handler.LauncherSettingHandler;
import com.dwarfeng.statistics.stack.service.DriverSupportMaintainService;
import com.dwarfeng.statistics.stack.service.FilterSupportMaintainService;
import com.dwarfeng.statistics.stack.service.MapperSupportMaintainService;
import com.dwarfeng.statistics.stack.service.ProviderSupportMaintainService;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

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
}
