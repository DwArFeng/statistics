package com.dwarfeng.statistics.stack.struct;

import com.dwarfeng.statistics.stack.bean.entity.DriverInfo;
import com.dwarfeng.statistics.stack.bean.entity.StatisticsSetting;
import com.dwarfeng.statistics.stack.handler.Driver;

import java.util.Map;

/**
 * 驱动信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class DriveInfo {

    private final StatisticsSetting statisticsSetting;
    private final Map<DriverInfo, Driver> driverMap;

    public DriveInfo(StatisticsSetting statisticsSetting, Map<DriverInfo, Driver> driverMap) {
        this.statisticsSetting = statisticsSetting;
        this.driverMap = driverMap;
    }

    public StatisticsSetting getStatisticsSetting() {
        return statisticsSetting;
    }

    public Map<DriverInfo, Driver> getDriverMap() {
        return driverMap;
    }

    @Override
    public String toString() {
        return "DriveInfo{" +
                "statisticsSetting=" + statisticsSetting +
                ", driverMap=" + driverMap +
                '}';
    }
}
