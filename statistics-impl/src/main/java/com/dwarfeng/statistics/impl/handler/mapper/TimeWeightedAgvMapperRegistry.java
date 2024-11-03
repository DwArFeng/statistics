package com.dwarfeng.statistics.impl.handler.mapper;

import com.dwarfeng.statistics.stack.bean.dto.BridgeData;
import com.dwarfeng.statistics.stack.exception.MapperException;
import com.dwarfeng.statistics.stack.exception.MapperMakeException;
import com.dwarfeng.statistics.stack.handler.Mapper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 时间加权平均映射器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class TimeWeightedAgvMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "time_weighted_agv_mapper";

    private final ApplicationContext ctx;

    public TimeWeightedAgvMapperRegistry(ApplicationContext ctx) {
        super(MAPPER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "时间加权平均映射器";
    }

    @Override
    public String provideDescription() {
        return "对序列中的所有数据条目进行加权平均值计算，" +
                "得到的值作为新的序列中的数据条目。新的数据条目的发生时间为序列开始时间和结束时间的中间值，" +
                "并且数据条目的统计设置主键会被替换为序列的统计设置主键。\n" +
                "如果序列中没有数据条目，则平均值为 0。\n" +
                "如果序列的统计设置主键为 null，则抛出异常。\n" +
                "如果序列中有任何一个数据条目的值是 null，或其类型不是 Number，则抛出异常。\n" +
                "该映射器将数据转换为 double 类型，因此，该映射器适用于精度要求不高且数据不越界的情况。\n" +
                "该映射器是聚合映射器。";
    }

    @Override
    public String provideExampleParam() {
        return "";
    }

    @Override
    public Mapper makeMapper() throws MapperException {
        try {
            return ctx.getBean(TimeWeightedAgvMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "TimeWeightedAgvMapperRegistry{" +
                "mapperType='" + mapperType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class TimeWeightedAgvMapper extends AggregateMapper {

        @Override
        protected Object doAggregate(MapParam mapParam, List<BridgeData> datas, Date startDate, Date endDate) {
            // 特殊情况判断结果。
            SpecialCaseAssertResult specialCaseAssertResult;

            // 判断特殊情况。
            specialCaseAssertResult = assertSpecialCase(datas);
            if (specialCaseAssertResult.isSpecialCase()) {
                return specialCaseAssertResult.getValue();
            }

            // 排序以及过滤数据。
            datas = MapperUtil.sortAndFilterDatas(datas, startDate, endDate, true);

            // 再次判断特殊情况。
            specialCaseAssertResult = assertSpecialCase(datas);
            if (specialCaseAssertResult.isSpecialCase()) {
                return specialCaseAssertResult.getValue();
            }

            // 如果第一个数据条目的发生时间小于 startDate，则将其发生时间设置为 startDate。
            BridgeData oldData;
            if ((oldData = datas.get(0)).getHappenedDate().compareTo(startDate) < 0) {
                datas.set(
                        0,
                        new BridgeData(oldData.getKey(), oldData.getValue(), startDate)
                );
            }
            // 如果最后一个数据条目的发生时间大于 endDate，则将其发生时间设置为 endDate。
            if ((oldData = datas.get(datas.size() - 1)).getHappenedDate().compareTo(endDate) > 0) {
                datas.set(
                        datas.size() - 1,
                        new BridgeData(oldData.getKey(), oldData.getValue(), endDate)
                );
            }

            // 定义总和变量。
            double sum = 0;

            // 遍历序列中的所有数据条目，计算时间加权总和。
            for (int i = 0; i < datas.size() - 1; i++) {
                BridgeData data = datas.get(i);
                BridgeData nextData = datas.get(i + 1);

                // 获取当前数据条目的值。
                Object value = data.getValue();
                double doubleValue = Optional.ofNullable(value).map(v -> ((Number) v).doubleValue()).orElse(0.0);

                // 计算时间加权总和。
                long duration = nextData.getHappenedDate().getTime() - data.getHappenedDate().getTime();
                sum += doubleValue * duration;
            }

            // 计算并返回加权平均值：加权总和 / 最后一个元素的时间戳 - 第一个元素的时间戳。
            long firstTimestamp = datas.get(0).getHappenedDate().getTime();
            long lastTimestamp = datas.get(datas.size() - 1).getHappenedDate().getTime();
            return sum / (lastTimestamp - firstTimestamp);
        }

        private SpecialCaseAssertResult assertSpecialCase(List<BridgeData> datas) {
            if (datas.isEmpty()) {
                return new SpecialCaseAssertResult(true, 0.0);
            }
            if (datas.size() == 1) {
                BridgeData data = datas.get(0);
                if (data.getValue() == null || !(data.getValue() instanceof Number)) {
                    throw new IllegalArgumentException("数据条目的值为 null，或其类型不是 Number");
                }
                return new SpecialCaseAssertResult(true, ((Number) data.getValue()).doubleValue());
            }
            return new SpecialCaseAssertResult(false, 0.0);
        }
    }

    private static final class SpecialCaseAssertResult {

        private final boolean specialCase;
        private final double value;

        public SpecialCaseAssertResult(boolean specialCase, double value) {
            this.specialCase = specialCase;
            this.value = value;
        }

        public boolean isSpecialCase() {
            return specialCase;
        }

        public double getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "SpecialCaseAssertResult{" +
                    "specialCase=" + specialCase +
                    ", value=" + value +
                    '}';
        }
    }
}
