package com.dwarfeng.statistics.impl.handler.mapper;

import com.dwarfeng.statistics.stack.bean.dto.BridgeData;
import com.dwarfeng.statistics.stack.exception.MapperException;
import com.dwarfeng.statistics.stack.exception.MapperMakeException;
import com.dwarfeng.statistics.stack.handler.Mapper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 合并映射器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class MergeMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "merge_mapper";

    private final ApplicationContext ctx;

    public MergeMapperRegistry(ApplicationContext ctx) {
        super(MAPPER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "合并映射器";
    }

    @Override
    public String provideDescription() {
        return "将 Sequence.getPointKey() 相同的序列进行合并。\n" +
                "合并后的序列起始时间与结束时间分别为合并序列中最早的起始时间与最晚的结束时间。";
    }

    @Override
    public String provideExampleParam() {
        return "";
    }

    @Override
    public Mapper makeMapper() throws MapperException {
        try {
            return ctx.getBean(MergeMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "MergeMapperRegistry{" +
                "mapperType='" + mapperType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class MergeMapper extends AbstractMapper {

        @Override
        protected List<Sequence> doMap(MapParam mapParam, List<Sequence> sequences) {
            // 构造合并信息映射。
            Map<LongIdKey, MergeInfo> mergeInfoMap = new HashMap<>();

            // 遍历序列列表，将序列中的数据条目添加到合并信息映射中。
            for (Sequence sequence : sequences) {
                LongIdKey statisticsSettingKey = sequence.getStatisticsSettingKey();
                Date startDate = sequence.getStartDate();
                Date endDate = sequence.getEndDate();
                List<BridgeData> datas = sequence.getDatas();

                if (mergeInfoMap.containsKey(statisticsSettingKey)) {
                    MergeInfo mergeInfo = mergeInfoMap.get(statisticsSettingKey);
                    if (mergeInfo.getStartDate().compareTo(startDate) > 0) {
                        mergeInfo.setStartDate(startDate);
                    }
                    if (mergeInfo.getEndDate().compareTo(endDate) < 0) {
                        mergeInfo.setEndDate(endDate);
                    }
                    mergeInfo.getDatas().addAll(datas);
                } else {
                    mergeInfoMap.put(statisticsSettingKey, new MergeInfo(statisticsSettingKey, startDate, endDate, datas));
                }
            }

            // 根据合并信息映射构造序列列表，并返回。
            return mergeInfoMap.values().stream().map(mergeInfo -> new Sequence(
                    mergeInfo.getPointKey(),
                    mergeInfo.getDatas(),
                    mergeInfo.getStartDate(),
                    mergeInfo.getEndDate()
            )).collect(Collectors.toList());
        }

        @Override
        public String toString() {
            return "MergeMapper{}";
        }
    }

    private static final class MergeInfo {

        private LongIdKey pointKey;
        private Date startDate;
        private Date endDate;
        private List<BridgeData> datas;

        public MergeInfo(LongIdKey pointKey, Date startDate, Date endDate, List<BridgeData> datas) {
            this.pointKey = pointKey;
            this.startDate = startDate;
            this.endDate = endDate;
            this.datas = datas;
        }

        public LongIdKey getPointKey() {
            return pointKey;
        }

        public void setPointKey(LongIdKey pointKey) {
            this.pointKey = pointKey;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        public List<BridgeData> getDatas() {
            return datas;
        }

        public void setDatas(List<BridgeData> datas) {
            this.datas = datas;
        }

        @Override
        public String toString() {
            return "MergeInfo{" +
                    "pointKey=" + pointKey +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", datas=" + datas +
                    '}';
        }
    }
}
