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

    @Value("${launcher.start_receive_delay}")
    private long startReceiveDelay;

    @Value("${launcher.online_supervise_delay}")
    private long onlineSuperviseDelay;
    @Value("${launcher.enable_supervise_delay}")
    private long enableSuperviseDelay;

    @Value("${launcher.start_reset_delay}")
    private long startResetDelay;

    @Value("${launcher.online_task_check_delay}")
    private long onlineTaskCheckDelay;
    @Value("${launcher.enable_task_check_delay}")
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
