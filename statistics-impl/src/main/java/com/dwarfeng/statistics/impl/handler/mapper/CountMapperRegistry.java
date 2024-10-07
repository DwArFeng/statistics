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

/**
 * 计数映射器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class CountMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "count_mapper";

    private final ApplicationContext ctx;

    public CountMapperRegistry(ApplicationContext ctx) {
        super(MAPPER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "计数映射器";
    }

    @Override
    public String provideDescription() {
        return "对序列中的所有数据条目进行计数。";
    }

    @Override
    public String provideExampleParam() {
        return "";
    }

    @Override
    public Mapper makeMapper() throws MapperException {
        try {
            return ctx.getBean(CountMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "CountMapperRegistry{" +
                "mapperType='" + mapperType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class CountMapper extends AggregateMapper {

        @Override
        protected Object doAggregate(MapParam mapParam, List<BridgeData> datas, Date startDate, Date endDate) {
            // 排序以及过滤数据。
            datas = MapperUtil.sortAndFilterDatas(datas, startDate, endDate, false);

            // 返回 items 的长度。
            return datas.size();
        }

        @Override
        public String toString() {
            return "CountMapper{}";
        }
    }
}
