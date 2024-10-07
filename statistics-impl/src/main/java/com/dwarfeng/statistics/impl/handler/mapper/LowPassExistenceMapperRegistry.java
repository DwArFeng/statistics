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

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 低通存在映射器注册。
 *
 * <p>
 * 低通存在映射器，用于判断是否存在低于阈值的数据。
 *
 * @author mooyuan
 * @since 1.0.0
 */
@Component
public class LowPassExistenceMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "low_pass_existence_mapper";

    private final ApplicationContext ctx;

    public LowPassExistenceMapperRegistry(ApplicationContext ctx) {
        super(MAPPER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "低通存在映射器";
    }

    @Override
    public String provideDescription() {
        return "用于判断是否存在低于阈值的数据: \n" +
                "invert 用于控制计算的是低于阈值的数据还是高于阈值的数据: \n" +
                "  false：过滤的是低于阈值的数据 \n" +
                "  true：过滤的是高于阈值的数据 \n" +
                "threshold用于过滤的阈值 \n" +
                "can_equal是否包含等于阈值的数据";
    }

    @Override
    public String provideExampleParam() {
        Config config = new Config(0.00, true, false);
        return JSON.toJSONString(config, true);
    }

    @Override
    public Mapper makeMapper() throws MapperException {
        try {
            return ctx.getBean(LowPassExistenceMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "LowPassExistenceMapper{" +
                "mapperType='" + mapperType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class LowPassExistenceMapper extends AggregateMapper {

        @Override
        protected Object doAggregate(MapParam mapParam, List<BridgeData> datas, Date startDate, Date endDate) {
            // 获得配置。
            Config config = JSON.parseObject(mapParam.getParam(), Config.class);

            double threshold = config.getThreshold();

            boolean canEqual = config.isCanEqual();

            boolean invert = config.isInvert();

            return doDetermine(datas, threshold, invert, canEqual);
        }

        // 为了保证代码的可读性，此处代码不做简化。
        @SuppressWarnings("ConstantValue")
        private boolean doDetermine(List<BridgeData> datas, double threshold, boolean invert, boolean canEqual) {
            for (BridgeData data : datas) {
                if (invert && canEqual) {
                    if ((Objects.isNull(data.getValue()) ? 0.00 : (double) data.getValue()) >= threshold) {
                        return true;
                    }
                } else if (invert && !canEqual) {
                    if ((Objects.isNull(data.getValue()) ? 0.00 : (double) data.getValue()) > threshold) {
                        return true;
                    }
                } else if (!invert && canEqual) {
                    if ((Objects.isNull(data.getValue()) ? 0.00 : (double) data.getValue()) <= threshold) {
                        return true;
                    }
                } else {
                    if ((Objects.isNull(data.getValue()) ? 0.00 : (double) data.getValue()) < threshold) {
                        return true;
                    }
                }
            }

            return false;
        }

        @Override
        public String toString() {
            return "LowPassExistenceMapper{}";
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = 2387062837372304072L;

        @JSONField(name = "#threshold", ordinal = 1, deserialize = false)
        private String thresholdRem = "阈值，对数据进行筛选的标准";

        @JSONField(name = "threshold", ordinal = 2)
        private double threshold;

        @JSONField(name = "#can_equal", ordinal = 3, deserialize = false)
        private String canEqualRem = "true：包含阈值，false：不包含阈值";

        @JSONField(name = "can_equal", ordinal = 4)
        private boolean canEqual;

        @JSONField(name = "#invert", ordinal = 5, deserialize = false)
        private String invertRem = "true：过滤掉低于阈值的数据，false：过滤掉高于阈值的数据";

        @JSONField(name = "invert", ordinal = 6)
        private boolean invert;

        public Config() {
        }

        public Config(double threshold, boolean canEqual, boolean invert) {
            this.threshold = threshold;
            this.canEqual = canEqual;
            this.invert = invert;
        }

        public String getThresholdRem() {
            return thresholdRem;
        }

        public void setThresholdRem(String thresholdRem) {
            this.thresholdRem = thresholdRem;
        }

        public double getThreshold() {
            return threshold;
        }

        public void setThreshold(double threshold) {
            this.threshold = threshold;
        }

        public String getCanEqualRem() {
            return canEqualRem;
        }

        public void setCanEqualRem(String canEqualRem) {
            this.canEqualRem = canEqualRem;
        }

        public boolean isCanEqual() {
            return canEqual;
        }

        public void setCanEqual(boolean canEqual) {
            this.canEqual = canEqual;
        }

        public String getInvertRem() {
            return invertRem;
        }

        public void setInvertRem(String invertRem) {
            this.invertRem = invertRem;
        }

        public boolean isInvert() {
            return invert;
        }

        public void setInvert(boolean invert) {
            this.invert = invert;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "thresholdRem='" + thresholdRem + '\'' +
                    ", threshold=" + threshold +
                    ", canEqualRem='" + canEqualRem + '\'' +
                    ", canEqual=" + canEqual +
                    ", invertRem='" + invertRem + '\'' +
                    ", invert=" + invert +
                    '}';
        }
    }
}
