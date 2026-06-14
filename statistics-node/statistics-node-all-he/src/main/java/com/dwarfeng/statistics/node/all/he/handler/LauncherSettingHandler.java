package com.dwarfeng.statistics.node.all.he.handler;

import com.dwarfeng.subgrade.stack.handler.Handler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LauncherSettingHandler implements Handler {

    @Value("${com.dwarfeng.statistics.launcher.reset_driver_support}")
    private boolean resetDriverSupport;
    @Value("${com.dwarfeng.statistics.launcher.reset_provider_support}")
    private boolean resetProviderSupport;
    @Value("${com.dwarfeng.statistics.launcher.reset_filter_support}")
    private boolean resetFilterSupport;
    @Value("${com.dwarfeng.statistics.launcher.reset_mapper_support}")
    private boolean resetMapperSupport;

    @Value("${com.dwarfeng.statistics.launcher.start_receive_delay}")
    private long startReceiveDelay;

    @Value("${com.dwarfeng.statistics.launcher.online_supervise_delay}")
    private long onlineSuperviseDelay;
    @Value("${com.dwarfeng.statistics.launcher.enable_supervise_delay}")
    private long enableSuperviseDelay;

    @Value("${com.dwarfeng.statistics.launcher.start_reset_delay}")
    private long startResetDelay;

    @Value("${com.dwarfeng.statistics.launcher.online_task_check_delay}")
    private long onlineTaskCheckDelay;
    @Value("${com.dwarfeng.statistics.launcher.enable_task_check_delay}")
    private long enableTaskCheckDelay;

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

    public long getStartReceiveDelay() {
        return startReceiveDelay;
    }

    public long getOnlineSuperviseDelay() {
        return onlineSuperviseDelay;
    }

    public long getEnableSuperviseDelay() {
        return enableSuperviseDelay;
    }

    public long getStartResetDelay() {
        return startResetDelay;
    }

    public long getOnlineTaskCheckDelay() {
        return onlineTaskCheckDelay;
    }

    public long getEnableTaskCheckDelay() {
        return enableTaskCheckDelay;
    }

    @Override
    public String toString() {
        return "LauncherSettingHandler{" +
                "resetDriverSupport=" + resetDriverSupport +
                ", resetProviderSupport=" + resetProviderSupport +
                ", resetFilterSupport=" + resetFilterSupport +
                ", resetMapperSupport=" + resetMapperSupport +
                ", startReceiveDelay=" + startReceiveDelay +
                ", onlineSuperviseDelay=" + onlineSuperviseDelay +
                ", enableSuperviseDelay=" + enableSuperviseDelay +
                ", startResetDelay=" + startResetDelay +
                ", onlineTaskCheckDelay=" + onlineTaskCheckDelay +
                ", enableTaskCheckDelay=" + enableTaskCheckDelay +
                '}';
    }
}
