package com.dwarfeng.statistics.impl.handler.driver;

import com.dwarfeng.statistics.impl.handler.DriverSupporter;
import org.springframework.stereotype.Component;

/**
 * Dcti 标准数据采集接口 Kafka 驱动支持器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class DctiKafkaDriverSupporter implements DriverSupporter {

    public static final String SUPPORT_TYPE = "dcti_kafka_driver";

    @Override
    public String provideType() {
        return SUPPORT_TYPE;
    }

    @Override
    public String provideLabel() {
        return "Dcti 标准数据采集接口 Kafka 驱动器";
    }

    @Override
    public String provideDescription() {
        return "从 Kafka 中接收到标准 Dcti 数据，并根据接收到的数据的主键对不同的记录设置进行触发。";
    }

    @Override
    public String provideExampleParam() {
        return "692653993448435712";
    }
}
