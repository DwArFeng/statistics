package com.dwarfeng.statistics.impl.handler.bridge.mock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 桥接器配置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class MockBridgeConfig {

    @Value("${bridge.mock.update.delay}")
    private long updateDelay;
    @Value("${bridge.mock.update.before_delay}")
    private long updateBeforeDelay;
    @Value("${bridge.mock.update.after_delay}")
    private long updateAfterDelay;

    @Value("${bridge.mock.latest.delay}")
    private long latestDelay;
    @Value("${bridge.mock.latest.before_delay}")
    private long latestBeforeDelay;
    @Value("${bridge.mock.latest.after_delay}")
    private long latestAfterDelay;

    @Value("${bridge.mock.record.delay}")
    private long recordDelay;
    @Value("${bridge.mock.record.before_delay}")
    private long recordBeforeDelay;
    @Value("${bridge.mock.record.after_delay}")
    private long recordAfterDelay;

    @Value("${bridge.mock.lookup.data_interval}")
    private long lookupDataInterval;
    @Value("${bridge.mock.lookup.delay}")
    private long lookupDelay;
    @Value("${bridge.mock.lookup.offset_delay}")
    private long lookupOffsetDelay;
    @Value("${bridge.mock.lookup.before_delay}")
    private long lookupBeforeDelay;
    @Value("${bridge.mock.lookup.after_delay}")
    private long lookupAfterDelay;

    @Value("${bridge.mock.native_query.delay_per_second}")
    private long nativeQueryDelayPerSecond;
    @Value("${bridge.mock.native_query.before_delay}")
    private long nativeQueryBeforeDelay;
    @Value("${bridge.mock.native_query.after_delay}")
    private long nativeQueryAfterDelay;

    public long getUpdateDelay() {
        return updateDelay;
    }

    public void setUpdateDelay(long updateDelay) {
        this.updateDelay = updateDelay;
    }

    public long getUpdateBeforeDelay() {
        return updateBeforeDelay;
    }

    public void setUpdateBeforeDelay(long updateBeforeDelay) {
        this.updateBeforeDelay = updateBeforeDelay;
    }

    public long getUpdateAfterDelay() {
        return updateAfterDelay;
    }

    public void setUpdateAfterDelay(long updateAfterDelay) {
        this.updateAfterDelay = updateAfterDelay;
    }

    public long getLatestDelay() {
        return latestDelay;
    }

    public void setLatestDelay(long latestDelay) {
        this.latestDelay = latestDelay;
    }

    public long getLatestBeforeDelay() {
        return latestBeforeDelay;
    }

    public void setLatestBeforeDelay(long latestBeforeDelay) {
        this.latestBeforeDelay = latestBeforeDelay;
    }

    public long getLatestAfterDelay() {
        return latestAfterDelay;
    }

    public void setLatestAfterDelay(long latestAfterDelay) {
        this.latestAfterDelay = latestAfterDelay;
    }

    public long getRecordDelay() {
        return recordDelay;
    }

    public void setRecordDelay(long recordDelay) {
        this.recordDelay = recordDelay;
    }

    public long getRecordBeforeDelay() {
        return recordBeforeDelay;
    }

    public void setRecordBeforeDelay(long recordBeforeDelay) {
        this.recordBeforeDelay = recordBeforeDelay;
    }

    public long getRecordAfterDelay() {
        return recordAfterDelay;
    }

    public void setRecordAfterDelay(long recordAfterDelay) {
        this.recordAfterDelay = recordAfterDelay;
    }

    public long getLookupDataInterval() {
        return lookupDataInterval;
    }

    public void setLookupDataInterval(long lookupDataInterval) {
        this.lookupDataInterval = lookupDataInterval;
    }

    public long getLookupDelay() {
        return lookupDelay;
    }

    public void setLookupDelay(long lookupDelay) {
        this.lookupDelay = lookupDelay;
    }

    public long getLookupOffsetDelay() {
        return lookupOffsetDelay;
    }

    public void setLookupOffsetDelay(long lookupOffsetDelay) {
        this.lookupOffsetDelay = lookupOffsetDelay;
    }

    public long getLookupBeforeDelay() {
        return lookupBeforeDelay;
    }

    public void setLookupBeforeDelay(long lookupBeforeDelay) {
        this.lookupBeforeDelay = lookupBeforeDelay;
    }

    public long getLookupAfterDelay() {
        return lookupAfterDelay;
    }

    public void setLookupAfterDelay(long lookupAfterDelay) {
        this.lookupAfterDelay = lookupAfterDelay;
    }

    public long getNativeQueryDelayPerSecond() {
        return nativeQueryDelayPerSecond;
    }

    public void setNativeQueryDelayPerSecond(long nativeQueryDelayPerSecond) {
        this.nativeQueryDelayPerSecond = nativeQueryDelayPerSecond;
    }

    public long getNativeQueryBeforeDelay() {
        return nativeQueryBeforeDelay;
    }

    public void setNativeQueryBeforeDelay(long nativeQueryBeforeDelay) {
        this.nativeQueryBeforeDelay = nativeQueryBeforeDelay;
    }

    public long getNativeQueryAfterDelay() {
        return nativeQueryAfterDelay;
    }

    public void setNativeQueryAfterDelay(long nativeQueryAfterDelay) {
        this.nativeQueryAfterDelay = nativeQueryAfterDelay;
    }

    @Override
    public String toString() {
        return "MockBridgeConfig{" +
                "updateDelay=" + updateDelay +
                ", updateBeforeDelay=" + updateBeforeDelay +
                ", updateAfterDelay=" + updateAfterDelay +
                ", latestDelay=" + latestDelay +
                ", latestBeforeDelay=" + latestBeforeDelay +
                ", latestAfterDelay=" + latestAfterDelay +
                ", recordDelay=" + recordDelay +
                ", recordBeforeDelay=" + recordBeforeDelay +
                ", recordAfterDelay=" + recordAfterDelay +
                ", lookupDataInterval=" + lookupDataInterval +
                ", lookupDelay=" + lookupDelay +
                ", lookupOffsetDelay=" + lookupOffsetDelay +
                ", lookupBeforeDelay=" + lookupBeforeDelay +
                ", lookupAfterDelay=" + lookupAfterDelay +
                ", nativeQueryDelayPerSecond=" + nativeQueryDelayPerSecond +
                ", nativeQueryBeforeDelay=" + nativeQueryBeforeDelay +
                ", nativeQueryAfterDelay=" + nativeQueryAfterDelay +
                '}';
    }
}
