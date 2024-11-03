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

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 开窗映射器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class WindowMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "window_mapper";

    private final ApplicationContext ctx;

    public WindowMapperRegistry(ApplicationContext ctx) {
        super(MAPPER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "开窗映射器";
    }

    @Override
    public String provideDescription() {
        return "根据窗口大小、锚点时间，针对每个序列进行开窗操作，将每个序列分为若干个长度为窗口大小的窗口，" +
                "每个窗口的起始时间与锚点时间的距离都是窗口步长的整数倍";
    }

    @Override
    public String provideExampleParam() {
        Config config = new Config(600000L, 0, false, false);
        return JSON.toJSONString(config, true);
    }

    @Override
    public Mapper makeMapper() throws MapperException {
        try {
            return ctx.getBean(WindowMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "WindowMapperRegistry{" +
                "mapperType='" + mapperType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class WindowMapper extends OneToManyMapper {

        @Override
        protected List<Sequence> doOneToManyMap(MapParam mapParam, Sequence sequence) {
            // 获得配置。
            Config config = JSON.parseObject(mapParam.getParam(), Config.class);
            // 展开配置。
            long duration = config.getDuration();
            long anchorTimestamp = config.getAnchorTimestamp();
            boolean removeEdges = config.isRemoveEdges();
            boolean extendItem = config.isExtendItem();
            // 验证配置的合法性。
            if (duration <= 0) {
                throw new IllegalArgumentException("窗口大小必须大于 0");
            }

            // 对序列进行开窗操作。
            return windowSequence(duration, anchorTimestamp, removeEdges, extendItem, sequence);
        }

        @Nonnull
        private List<Sequence> windowSequence(
                long duration, long anchorTimestamp, boolean removeEdges, boolean extendItem, Sequence sequence
        ) {
            // 取出 sequence 中的数据条目。
            List<BridgeData> data = new ArrayList<>(sequence.getDatas());

            // 如果 data 为空，则直接返回空的序列。
            if (data.isEmpty()) {
                return Collections.emptyList();
            }

            // 根据 duration 和 anchorTimestamp 计算出第一个窗口的开始时间。
            // 第一个窗口的开始时间小于等于第一个 item 的发生时间，且与 anchorTimestamp 的距离是 duration 的整数倍。
            long firstHappenedTimestamp = data.get(0).getHappenedDate().getTime();
            long firstTimestamp = firstHappenedTimestamp - Math.abs(firstHappenedTimestamp - anchorTimestamp) % duration;

            // 根据 duration 和 anchorTimestamp 计算出最后一个窗口的开始时间。
            // 最后一个窗口的开始时间小于等于最后一个 item 的发生时间，且与 anchorTimestamp 的距离是 duration 的整数倍。
            long lastHappenedTimestamp = data.get(data.size() - 1).getHappenedDate().getTime();
            long lastTimestamp = lastHappenedTimestamp - Math.abs(lastHappenedTimestamp - anchorTimestamp) % duration;

            // 定义一个 int 变量，用于记录当前循环到的数据条目的索引。
            int itemIndex = 0;

            // 定义返回结果。
            List<Sequence> result = new ArrayList<>();

            // 开始循环开窗。
            long timestamp = firstTimestamp;
            while (timestamp <= lastTimestamp) {
                // 定义当前窗口的开始时间和结束时间。
                long start = timestamp;
                long end = timestamp + duration;

                // 定义当前窗口的数据条目。
                List<BridgeData> subItems = new ArrayList<>();

                // 如果 extendItem 为 true，则需要将当前窗口的开始时间之前的数据条目添加到当前窗口的数据条目中。
                if (extendItem && itemIndex - 1 >= 0) {
                    subItems.add(data.get(itemIndex - 1));
                }

                // 从 itemIndex 开始遍历数据条目，直到遍历到的数据条目的 happenedDate 超过了当前窗口的结束时间。
                // 将遍历到的数据条目添加到当前窗口的数据条目中。
                // 最后更新 itemIndex。
                while (itemIndex < data.size() && data.get(itemIndex).getHappenedDate().getTime() < end) {
                    subItems.add(data.get(itemIndex));
                    itemIndex++;
                }

                // 如果 extendItem 为 true，则需要将当前窗口的结束时间之后的数据条目添加到当前窗口的数据条目中。
                if (extendItem && itemIndex < data.size()) {
                    subItems.add(data.get(itemIndex));
                }

                // 将当前窗口的数据条目添加到结果中。
                result.add(new Sequence(sequence.getBridgeDataKey(), subItems, new Date(start), new Date(end)));

                // 将 timestamp 增加 duration，进入下一个窗口。
                timestamp += duration;
            }

            // 如果 removeEdges 为 true，则需要去除第一个和最后一个窗口，当结果的数量小于等于 2 时，直接返回空的序列。
            if (removeEdges) {
                if (result.size() <= 2) {
                    result.clear();
                }
                result.remove(0);
                result.remove(result.size() - 1);
            }
            return result;
        }

        @Override
        public String toString() {
            return "WindowMapper{}";
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = -1009603562955825840L;

        @JSONField(name = "duration", ordinal = 1)
        private long duration;

        @JSONField(name = "anchor_timestamp", ordinal = 2)
        private long anchorTimestamp;

        @JSONField(name = "#remove_edges", ordinal = 3, deserialize = false)
        private String removeEdgesRem = "每个序列开窗后是否去除第一个和最后一个窗口。" +
                "在部分情况下，第一个和最后一个窗口的数据可能不完整，可能会导致后续的聚合操作出现不准确的结果。" +
                "因此，可以通过设置该参数为 true 来去除第一个和最后一个窗口。";

        @JSONField(name = "remove_edges", ordinal = 4)
        private boolean removeEdges;

        @JSONField(name = "#extend_item", ordinal = 5, deserialize = false)
        private String extendItemRem = "是否扩展每个窗口的数据条目。" +
                "如果该参数为 true，则会在每个窗口（第一个窗口除外）的起始时间处插入一个数据条目，" +
                "其值为上一个窗口的最后一个数据条目的值；" +
                "同时会在每个窗口（最后一个窗口除外）的结束时间处插入一个数据条目，" +
                "其值为下一个窗口的第一个数据条目的值。" +
                "将该参数设置为 true 可以提高部分聚合映射器的准确性，如加权平均值映射器。";

        @JSONField(name = "extend_item", ordinal = 6)
        private boolean extendItem;

        public Config() {
        }

        public Config(long duration, long anchorTimestamp, boolean removeEdges, boolean extendItem) {
            this.duration = duration;
            this.anchorTimestamp = anchorTimestamp;
            this.removeEdges = removeEdges;
            this.extendItem = extendItem;
        }

        public long getDuration() {
            return duration;
        }

        public void setDuration(long duration) {
            this.duration = duration;
        }

        public long getAnchorTimestamp() {
            return anchorTimestamp;
        }

        public void setAnchorTimestamp(long anchorTimestamp) {
            this.anchorTimestamp = anchorTimestamp;
        }

        public String getRemoveEdgesRem() {
            return removeEdgesRem;
        }

        public void setRemoveEdgesRem(String removeEdgesRem) {
            this.removeEdgesRem = removeEdgesRem;
        }

        public boolean isRemoveEdges() {
            return removeEdges;
        }

        public void setRemoveEdges(boolean removeEdges) {
            this.removeEdges = removeEdges;
        }

        public String getExtendItemRem() {
            return extendItemRem;
        }

        public void setExtendItemRem(String extendItemRem) {
            this.extendItemRem = extendItemRem;
        }

        public boolean isExtendItem() {
            return extendItem;
        }

        public void setExtendItem(boolean extendItem) {
            this.extendItem = extendItem;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "duration=" + duration +
                    ", anchorTimestamp=" + anchorTimestamp +
                    ", removeEdgesRem='" + removeEdgesRem + '\'' +
                    ", removeEdges=" + removeEdges +
                    ", extendItemRem='" + extendItemRem + '\'' +
                    ", extendItem=" + extendItem +
                    '}';
        }
    }
}
