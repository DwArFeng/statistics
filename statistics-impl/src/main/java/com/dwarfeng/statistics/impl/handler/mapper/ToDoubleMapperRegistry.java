package com.dwarfeng.statistics.impl.handler.mapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.stack.bean.dto.BridgeData;
import com.dwarfeng.statistics.stack.exception.MapperException;
import com.dwarfeng.statistics.stack.exception.MapperMakeException;
import com.dwarfeng.statistics.stack.handler.Mapper;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 转换为双精度浮点数的映射器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class ToDoubleMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "to_double_mapper";

    private final ApplicationContext ctx;

    public ToDoubleMapperRegistry(ApplicationContext ctx) {
        super(MAPPER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "转换为双精度浮点数的映射器";
    }

    @Override
    public String provideDescription() {
        return "保留数据表中所有序列的开始时间和结束时间。对每个序列的数据条目做映射处理: \n" +
                "1. 如果数据条目的值的类型是 Number，那么获得其双精度浮点数值。 \n" +
                "2. 如果数据条目的值的类型是 String，那么尝试将其转换为双精度浮点数。 \n" +
                "3. 如果数据条目的值的类型是布尔值，那么根据配置对布尔值进行映射。 \n" +
                "4. 对于其它的情况，可以进行策略配置，映射为默认值、或 null、或忽略该数据条目。";
    }

    @Override
    public String provideExampleParam() {
        Config config = new Config(1.0, 0.0, 0, 0.0);
        return JSON.toJSONString(config, true);
    }

    @Override
    public Mapper makeMapper() throws MapperException {
        try {
            return ctx.getBean(ToDoubleMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "ToDoubleMapperRegistry{" +
                "mapperType='" + mapperType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class ToDoubleMapper extends OneToOneMapper {

        @Override
        protected Sequence doOneToOneMap(MapParam mapParam, Sequence sequence) {
            // 获得配置。
            Config config = JSON.parseObject(mapParam.getParam(), Config.class);

            // 遍历序列中的每个数据条目。
            List<BridgeData> datas = new ArrayList<>(sequence.getDatas().size());
            for (BridgeData data : sequence.getDatas()) {
                Optional.ofNullable(mapItem(config, data)).ifPresent(datas::add);
            }

            // 返回映射后的序列。
            return new Sequence(
                    sequence.getBridgeDataKey(), datas, sequence.getStartDate(), sequence.getEndDate()
            );
        }

        private BridgeData mapItem(Config config, BridgeData data) {
            // 如果数据条目的值的类型是 Number，那么获得其双精度浮点数值。
            if (data.getValue() instanceof Number) {
                return new BridgeData(
                        data.getKey(),
                        ((Number) data.getValue()).doubleValue(), data.getHappenedDate()
                );
            }

            // 如果数据条目的值的类型是 String，那么尝试将其转换为双精度浮点数。
            if (data.getValue() instanceof String) {
                try {
                    return new BridgeData(
                            data.getKey(),
                            Double.parseDouble((String) data.getValue()),
                            data.getHappenedDate()
                    );
                } catch (Exception e) {
                    // 什么都不做。
                }
            }

            // 如果数据条目的值的类型是布尔值，那么根据配置对布尔值进行映射。
            if (data.getValue() instanceof Boolean) {
                return new BridgeData(
                        data.getKey(),
                        (Boolean) data.getValue() ? config.getBooleanTrueValue() : config.getBooleanFalseValue(),
                        data.getHappenedDate()
                );
            }

            // 对于其它的情况，可以进行策略配置，映射为默认值、或 null、或忽略该数据条目。
            switch (config.getOtherTypeStrategy()) {
                case 0:
                    return new BridgeData(
                            data.getKey(), config.getOtherTypeDefaultValue(), data.getHappenedDate()
                    );
                case 1:
                    return new BridgeData(
                            data.getKey(), null, data.getHappenedDate()
                    );
                case 2:
                    return null;
                default:
                    throw new IllegalStateException("未知的其它类型策略: " + config.getOtherTypeStrategy());
            }
        }

        @Override
        public String toString() {
            return "ToDoubleMapper{}";
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = -3636676076982394138L;

        @JSONField(name = "boolean_true_value", ordinal = 1)
        private double booleanTrueValue;

        @JSONField(name = "boolean_false_value", ordinal = 2)
        private double booleanFalseValue;

        @JSONField(name = "#other_type_strategy", ordinal = 3, deserialize = false)
        private String otherTypeStrategyRem = "0: 默认值, 1: null, 2: 忽略该数据条目。";

        @JSONField(name = "other_type_strategy", ordinal = 4)
        private int otherTypeStrategy;

        @JSONField(name = "other_type_default_value", ordinal = 5)
        private Double otherTypeDefaultValue;

        public Config() {
        }

        public Config(
                double booleanTrueValue, double booleanFalseValue, int otherTypeStrategy, Double otherTypeDefaultValue
        ) {
            this.booleanTrueValue = booleanTrueValue;
            this.booleanFalseValue = booleanFalseValue;
            this.otherTypeStrategy = otherTypeStrategy;
            this.otherTypeDefaultValue = otherTypeDefaultValue;
        }

        public double getBooleanTrueValue() {
            return booleanTrueValue;
        }

        public void setBooleanTrueValue(double booleanTrueValue) {
            this.booleanTrueValue = booleanTrueValue;
        }

        public double getBooleanFalseValue() {
            return booleanFalseValue;
        }

        public void setBooleanFalseValue(double booleanFalseValue) {
            this.booleanFalseValue = booleanFalseValue;
        }

        public String getOtherTypeStrategyRem() {
            return otherTypeStrategyRem;
        }

        public void setOtherTypeStrategyRem(String otherTypeStrategyRem) {
            this.otherTypeStrategyRem = otherTypeStrategyRem;
        }

        public int getOtherTypeStrategy() {
            return otherTypeStrategy;
        }

        public void setOtherTypeStrategy(int otherTypeStrategy) {
            this.otherTypeStrategy = otherTypeStrategy;
        }

        public Double getOtherTypeDefaultValue() {
            return otherTypeDefaultValue;
        }

        public void setOtherTypeDefaultValue(Double otherTypeDefaultValue) {
            this.otherTypeDefaultValue = otherTypeDefaultValue;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "booleanTrueValue=" + booleanTrueValue +
                    ", booleanFalseValue=" + booleanFalseValue +
                    ", otherTypeStrategyRem='" + otherTypeStrategyRem + '\'' +
                    ", otherTypeStrategy=" + otherTypeStrategy +
                    ", otherTypeDefaultValue=" + otherTypeDefaultValue +
                    '}';
        }
    }
}
