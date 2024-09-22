package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.stack.bean.entity.DriverInfo;
import com.dwarfeng.statistics.stack.bean.entity.StatisticsSetting;
import com.dwarfeng.statistics.stack.exception.DriverException;
import com.dwarfeng.statistics.stack.handler.DriveHandler;
import com.dwarfeng.statistics.stack.handler.DriveLocalCacheHandler;
import com.dwarfeng.statistics.stack.handler.Driver;
import com.dwarfeng.statistics.stack.service.StatisticsSettingMaintainService;
import com.dwarfeng.statistics.stack.struct.DriveInfo;
import com.dwarfeng.subgrade.impl.handler.GeneralStartableHandler;
import com.dwarfeng.subgrade.impl.handler.Worker;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DriveHandlerImpl implements DriveHandler {

    private final GeneralStartableHandler handler;

    public DriveHandlerImpl(DriveWorker driveWorker) {
        handler = new GeneralStartableHandler(driveWorker);
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

    @Component
    public static class DriveWorker implements Worker {

        private static final Logger LOGGER = LoggerFactory.getLogger(DriveWorker.class);

        private final StatisticsSettingMaintainService statisticsSettingMaintainService;

        private final DriveLocalCacheHandler driveLocalCacheHandler;

        private final Set<Driver> usedDrivers = new HashSet<>();

        public DriveWorker(
                StatisticsSettingMaintainService statisticsSettingMaintainService,
                DriveLocalCacheHandler driveLocalCacheHandler
        ) {
            this.statisticsSettingMaintainService = statisticsSettingMaintainService;
            this.driveLocalCacheHandler = driveLocalCacheHandler;
        }

        @Override
        public void work() throws Exception {
            // 记录日志。
            LOGGER.info("驱动器开始工作...");

            List<StatisticsSetting> statisticsSettings = statisticsSettingMaintainService.lookupAsList(
                    StatisticsSettingMaintainService.ENABLED, new Object[]{}
            );
            // 注册所有驱动成功标志。
            boolean successFlag = true;
            // 获取所有驱动信息。
            for (StatisticsSetting statisticsSetting : statisticsSettings) {
                DriveInfo driveInfo = driveLocalCacheHandler.get(statisticsSetting.getKey());
                if (Objects.isNull(driveInfo)) {
                    throw new DriverException("无法在本地缓存中找到有效的驱动上下文: " + statisticsSetting.getKey());
                }
                if (!registerDriver(driveInfo)) {
                    successFlag = false;
                }
            }
            if (successFlag) {
                LOGGER.info("所有驱动信息注册成功");
            } else {
                LOGGER.warn("至少一条驱动信息注册失败，请查看警报日志以了解详细原因");
            }
        }

        private boolean registerDriver(DriveInfo driveInfo) {
            boolean successFlag = true;
            Map<DriverInfo, Driver> driverMap = driveInfo.getDriverMap();
            for (Map.Entry<DriverInfo, Driver> entry : driverMap.entrySet()) {
                DriverInfo driverInfo = entry.getKey();
                Driver driver = entry.getValue();
                try {
                    driver.register(driverInfo);
                    usedDrivers.add(driver);
                } catch (Exception e) {
                    successFlag = false;
                    LOGGER.warn("驱动信息 {} 注册失败，将忽略此条注册信息", driverInfo, e);
                }
            }
            return successFlag;
        }

        @Override
        public void rest() throws Exception {
            // 记录日志。
            LOGGER.info("驱动器停止工作...");

            for (Iterator<Driver> iterator = usedDrivers.iterator(); iterator.hasNext(); ) {
                Driver driver = iterator.next();
                driver.unregisterAll();
                iterator.remove();
            }
        }
    }
}
