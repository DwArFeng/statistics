package com.dwarfeng.statistics.node.handler;

import com.dwarfeng.subgrade.stack.handler.Handler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LauncherSettingHandler implements Handler {

    @Value("${launcher.reset_driver_support}")
    private boolean resetDriverSupport;
    @Value("${launcher.reset_provider_support}")
    private boolean resetProviderSupport;
    @Value("${launcher.reset_filter_support}")
    private boolean resetFilterSupport;
    @Value("${launcher.reset_mapper_support}")
    private boolean resetMapperSupport;

    public boolean isResetDriverSupport() {
        return resetDriverSupport;
    }

    public boolean isResetProviderSupport() {
        return resetProviderSupport;
    }

    public boolean isResetFilterSupport() {
        return resetFilterSupport;
    }

    public boolean isResetMapperSupport() {
        return resetMapperSupport;
    }

    @Override
    public String toString() {
        return "LauncherSettingHandler{" +
                "resetDriverSupport=" + resetDriverSupport +
                ", resetProviderSupport=" + resetProviderSupport +
                ", resetFilterSupport=" + resetFilterSupport +
                ", resetMapperSupport=" + resetMapperSupport +
                '}';
    }
}
