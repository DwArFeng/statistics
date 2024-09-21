package com.dwarfeng.statistics.impl.handler.driver;

import com.dwarfeng.statistics.impl.handler.DriverSupporter;
import org.springframework.stereotype.Component;

/**
 * 固定间隔驱动支持器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class FixedRateDriverSupporter implements DriverSupporter {

    public static final String SUPPORT_TYPE = "fixed_rate_driver";

    @Override
    public String provideType() {
        return SUPPORT_TYPE;
    }

    @Override
    public String provideLabel() {
        return "固定频率驱动器";
    }

    @Override
    public String provideDescription() {
        return "根据指定的间隔定时驱动，如果某一次驱动晚于间隔，则后续驱动的时间会提前，以保持频率不变。";
    }

    @Override
    public String provideExampleParam() {
        return "60000";
    }
}
