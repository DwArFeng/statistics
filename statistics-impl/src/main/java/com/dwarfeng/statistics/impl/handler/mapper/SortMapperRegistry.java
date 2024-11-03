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
import java.util.Comparator;
import java.util.List;

/**
 * 排序映射器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class SortMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "sort_mapper";
    public static final int ORDER_ASC = 0;
    public static final int ORDER_DESC = 1;

    private final ApplicationContext ctx;

    public SortMapperRegistry(ApplicationContext ctx) {
        super(MAPPER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "排序映射器";
    }

    @Override
    public String provideDescription() {
        return "对序列列表按照 Sequence.getBridgeDataKey() 进行排序，同时对序列中的所有数据条目进行排序。\n" +
                "排序方式由参数决定。";
    }

    @Override
    public String provideExampleParam() {
        Config config = new Config(ORDER_ASC, ORDER_ASC);
        return JSON.toJSONString(config, true);
    }

    @Override
    public Mapper makeMapper() throws MapperException {
        try {
            return ctx.getBean(SortMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "SortMapperRegistry{" +
                "mapperType='" + mapperType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class SortMapper extends AbstractMapper {

        @Override
        protected List<Sequence> doMap(MapParam mapParam, List<Sequence> sequences) {
            // 解析配置。
            Config config = JSON.parseObject(mapParam.getParam(), Config.class);

            // 复制 sequences。
            sequences = new ArrayList<>(sequences);

            // 对 sequences 进行排序。
            Comparator<Sequence> sequenceComparator;
            switch (config.getSequenceBridgeDataKeyOrder()) {
                case ORDER_ASC:
                    sequenceComparator = CompareUtil.SEQUENCE_BRIDGE_DATA_KEY_ASC_COMPARATOR;
                    break;
                case ORDER_DESC:
                    sequenceComparator = CompareUtil.SEQUENCE_BRIDGE_DATA_KEY_DESC_COMPARATOR;
                    break;
                default:
                    throw new IllegalArgumentException(
                            "未知的 sequenceBridgeDataKeyOrder: " + config.getSequenceBridgeDataKeyOrder()
                    );
            }
            sequences.sort(sequenceComparator);

            // 遍历 sequences，对其中的数据条目进行排序。
            for (int i = 0; i < sequences.size(); i++) {
                // 获取 sequence。
                Sequence sequence = sequences.get(i);
                // 对 datas 进行排序。
                Comparator<BridgeData> dataComparator;
                switch (config.getItemHappenedDateOrder()) {
                    case ORDER_ASC:
                        dataComparator = CompareUtil.BRIDGE_DATA_HAPPENED_DATE_ASC_COMPARATOR;
                        break;
                    case ORDER_DESC:
                        dataComparator = CompareUtil.BRIDGE_HAPPENED_DATE_DESC_COMPARATOR;
                        break;
                    default:
                        throw new IllegalArgumentException(
                                "未知的 itemHappenedDateOrder: " + config.getItemHappenedDateOrder()
                        );
                }
                sequence.getDatas().sort(dataComparator);
                sequences.set(i, sequence);
            }

            // 返回排序之后的 sequences。
            return sequences;
        }

        @Override
        public String toString() {
            return "SortMapper{}";
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = -5008314865829066948L;

        @JSONField(name = "#sequence_bridge_data_key_order", ordinal = 1, deserialize = false)
        private String sequenceBridgeDataKeyOrderRem = String.format("%d: 升序, %d: 降序", ORDER_ASC, ORDER_DESC);

        @JSONField(name = "sequence_bridge_data_key_order", ordinal = 2)
        private int sequenceBridgeDataKeyOrder;

        @JSONField(name = "#item_happened_date_order", ordinal = 3, deserialize = false)
        private String itemHappenedDateOrderRem = String.format("%d: 升序, %d: 降序", ORDER_ASC, ORDER_DESC);

        @JSONField(name = "item_happened_date_order", ordinal = 4)
        private int itemHappenedDateOrder;

        public Config() {
        }

        public Config(int sequenceBridgeDataKeyOrder, int itemHappenedDateOrder) {
            this.sequenceBridgeDataKeyOrder = sequenceBridgeDataKeyOrder;
            this.itemHappenedDateOrder = itemHappenedDateOrder;
        }

        public String getSequenceBridgeDataKeyOrderRem() {
            return sequenceBridgeDataKeyOrderRem;
        }

        public void setSequenceBridgeDataKeyOrderRem(String sequenceBridgeDataKeyOrderRem) {
            this.sequenceBridgeDataKeyOrderRem = sequenceBridgeDataKeyOrderRem;
        }

        public int getSequenceBridgeDataKeyOrder() {
            return sequenceBridgeDataKeyOrder;
        }

        public void setSequenceBridgeDataKeyOrder(int sequenceBridgeDataKeyOrder) {
            this.sequenceBridgeDataKeyOrder = sequenceBridgeDataKeyOrder;
        }

        public String getItemHappenedDateOrderRem() {
            return itemHappenedDateOrderRem;
        }

        public void setItemHappenedDateOrderRem(String itemHappenedDateOrderRem) {
            this.itemHappenedDateOrderRem = itemHappenedDateOrderRem;
        }

        public int getItemHappenedDateOrder() {
            return itemHappenedDateOrder;
        }

        public void setItemHappenedDateOrder(int itemHappenedDateOrder) {
            this.itemHappenedDateOrder = itemHappenedDateOrder;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "sequenceBridgeDataKeyOrderRem='" + sequenceBridgeDataKeyOrderRem + '\'' +
                    ", sequenceBridgeDataKeyOrder=" + sequenceBridgeDataKeyOrder +
                    ", itemHappenedDateOrderRem='" + itemHappenedDateOrderRem + '\'' +
                    ", itemHappenedDateOrder=" + itemHappenedDateOrder +
                    '}';
        }
    }
}
