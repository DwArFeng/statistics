package com.dwarfeng.statistics.impl.handler.driver;

import com.dwarfeng.statistics.impl.handler.DriverSupporter;
import org.springframework.stereotype.Component;

/**
 * Cron 驱动器支持器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class CronDriverSupporter implements DriverSupporter {

    public static final String SUPPORT_TYPE = "cron_driver";

    @Override
    public String provideType() {
        return SUPPORT_TYPE;
    }

    @Override
    public String provideLabel() {
        return "Cron 驱动器";
    }

    @Override
    public String provideDescription() {
        return "根据指定的 Cron 表达式定时驱动的驱动器";
    }

    @Override
    public String provideExampleParam() {
        return "0/2 * * * * *";
    }
}
