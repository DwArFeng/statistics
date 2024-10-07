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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 裁剪映射器注册。
 *
 * <p>
 * 用于裁剪序列的起始时间和结束时间。映射器工作时会寻找序列中发生时间最早和最晚的数据，
 * 然后将这两个数据的发生时间作为序列的起始时间和结束时间。
 *
 * @author mooyuan
 * @since 1.0.0
 */
@Component
public class TrimMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "trim_mapper";

    private final ApplicationContext ctx;

    public TrimMapperRegistry(ApplicationContext ctx) {
        super(MAPPER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public Mapper makeMapper() throws MapperException {
        try {
            return ctx.getBean(TrimMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String provideLabel() {
        return "裁剪映射器";
    }

    @Override
    public String provideDescription() {
        return "映射器工作时会寻找序列中发生时间最早和最晚的数据，然后将这两个数据的发生时间作为序列的起始时间和结束时间,若使用 only_trim_start 配置项可以只裁剪序列的起始时间。";
    }

    @Override
    public String provideExampleParam() {
        Config config = new Config(false);
        return JSON.toJSONString(config, true);
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class TrimMapper extends OneToOneMapper {

        @Override
        protected Sequence doOneToOneMap(MapParam mapParam, Sequence sequence) {

            // 获得配置。
            Config config = JSON.parseObject(mapParam.getParam(), Config.class);

            return trimSequence(sequence, config.isOnlyTrimStart());
        }

        // 排序并截取序列
        private Sequence trimSequence(Sequence sequence, boolean only_trim_start) {
            // 获取序列的起始时间与结束时间。
            List<BridgeData> datas = new ArrayList<>(sequence.getDatas());
            datas.sort(CompareUtil.BRIDGE_DATA_HAPPENED_DATE_ASC_COMPARATOR);

            Date startDate = datas.get(0).getHappenedDate();
            Date endDate = datas.get(datas.size() - 1).getHappenedDate();

            // 如果起始时间等于结束时间只取一个值即可
            if (only_trim_start) {
                endDate = sequence.getEndDate();
            }

            // 返回新的序列
            return new Sequence(sequence.getStatisticsSettingKey(), datas, startDate, endDate);
        }

        @Override
        public String toString() {
            return "TrimMapper{}";
        }

    }

    public static class Config implements Bean {

        private static final long serialVersionUID = 2929546893944238328L;
        
        @JSONField(name = "#only_trim_start", ordinal = 1, deserialize = false)
        private String onlyTrimStartRem =
                "当 onlyTrimStart 为 true 时只剪裁序列的起始时间，false 裁剪序列的起始时间和结束时间";

        @JSONField(name = "only_trim_start", ordinal = 2)
        private boolean onlyTrimStart = false;

        public Config() {
        }

        public Config(boolean onlyTrimStart) {
            this.onlyTrimStart = onlyTrimStart;
        }

        public String getOnlyTrimStartRem() {
            return onlyTrimStartRem;
        }

        public void setOnlyTrimStartRem(String onlyTrimStartRem) {
            this.onlyTrimStartRem = onlyTrimStartRem;
        }

        public boolean isOnlyTrimStart() {
            return onlyTrimStart;
        }

        public void setOnlyTrimStart(boolean onlyTrimStart) {
            this.onlyTrimStart = onlyTrimStart;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "onlyTrimStartRem='" + onlyTrimStartRem + '\'' +
                    ", onlyTrimStart=" + onlyTrimStart +
                    '}';
        }
    }
}
