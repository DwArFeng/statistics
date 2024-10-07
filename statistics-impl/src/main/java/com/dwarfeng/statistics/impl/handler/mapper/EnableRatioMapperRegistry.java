package com.dwarfeng.statistics.impl.handler.mapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.sdk.util.CompareUtil;
import com.dwarfeng.statistics.stack.bean.dto.BridgeData;
import com.dwarfeng.statistics.stack.exception.MapperException;
import com.dwarfeng.statistics.stack.exception.MapperMakeException;
import com.dwarfeng.statistics.stack.handler.Mapper;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

/**
 * 使能比例映射器注册。
 *
 * @author mooyuan
 * @since 1.0.0
 */
@Component
public class EnableRatioMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "enable_ratio_mapper";

    private final ApplicationContext ctx;

    public EnableRatioMapperRegistry(ApplicationContext ctx) {
        super(MAPPER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "使能比例映射器";
    }

    @Override
    public String provideDescription() {
        return "用于计算布尔类型数据的使能比例，即数据中真值的占用时间与序列的总时间的比值: \n" +
                "invert 用于控制计算的是真值的比例还是假值的比例： \n" +
                "  false：计算的是真值的比例 \n" +
                "  true：计算的是假值的比例 \n";
    }

    @Override
    public String provideExampleParam() {
        Config config = new Config(false);
        return JSON.toJSONString(config, true);
    }

    @Override
    public Mapper makeMapper() throws MapperException {
        try {
            return ctx.getBean(EnableRatioMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "EnableRatioMapper{" +
                "mapperType='" + mapperType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class EnableRatioMapper extends AggregateMapper {

        @Override
        protected Object doAggregate(MapParam mapParam, List<BridgeData> datas, Date startDate, Date endDate) {

            // 获得配置。
            Config config = JSON.parseObject(mapParam.getParam(), Config.class);

            boolean invert = config.isInvert();

            // 对统计设置进行时间排序(正序)
            datas.sort(CompareUtil.BRIDGE_DATA_HAPPENED_DATE_ASC_COMPARATOR);

            //更新开始时间，去掉真空期
            startDate = datas.get(0).getHappenedDate();

            // 判断中间存在不为boolean类型的数据抛出异常
            for (BridgeData data : datas) {
                if (!(data.getValue() instanceof Boolean)) {
                    throw new IllegalStateException("存在统计设置值不为boolean类型");
                }
            }

            // 计算占比
            return calRatioByItems(datas, startDate, endDate, invert);
        }

        /**
         * 计算占比
         *
         * @param datas     排完序的统计设置数组
         * @param startDate 序列开始时间
         * @param endDate   序列结束时间
         * @param invert    true 计算false的占比、false 计算true的占比
         * @return 获取占比
         */
        private double calRatioByItems(List<BridgeData> datas, Date startDate, Date endDate, boolean invert) {
            // 符合时间
            BigDecimal calTime = BigDecimal.ZERO;
            boolean calFlag = false;

            BigDecimal startDateTime = BigDecimal.valueOf(startDate.getTime());
            BigDecimal endDateTime = BigDecimal.valueOf(endDate.getTime());
            // 总时间
            BigDecimal allTime = endDateTime.subtract(startDateTime);

            // 上一次符合的时间
            BigDecimal preTime = BigDecimal.ZERO;

            for (BridgeData data : datas) {
                if (data.getValue() instanceof Boolean && !invert == (Boolean) data.getValue()) {

                    if (calFlag) {
                        continue;
                    }

                    preTime = BigDecimal.valueOf(data.getHappenedDate().getTime());
                    calFlag = true;
                } else {
                    if (!calFlag) {
                        continue;
                    }

                    calTime = calTime.add(BigDecimal.valueOf(data.getHappenedDate().getTime()).subtract(preTime));
                    calFlag = false;
                }
            }

            // 处理结束的时间，当最后时间的calFlag还是true时处理
            if (calFlag) {
                calTime = calTime.add(endDateTime.subtract(preTime));
            }

            return calTime.divide(allTime, 4, RoundingMode.HALF_UP).doubleValue();
        }

        @Override
        public String toString() {
            return "EnableRatioMapper{}";
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = 8139233591752939834L;

        @JSONField(name = "#invert", ordinal = 1, deserialize = false)
        private String invertRem = "true：计算的是假值的比例;false：计算的是真值的比例";

        @JSONField(name = "invert", ordinal = 2)
        private boolean invert;

        public Config() {
        }

        public Config(boolean invert) {
            this.invert = invert;
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
                    "invertRem='" + invertRem + '\'' +
                    ", invert=" + invert +
                    '}';
        }
    }
}
