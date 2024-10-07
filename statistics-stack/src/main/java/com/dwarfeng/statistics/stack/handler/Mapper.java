package com.dwarfeng.statistics.stack.handler;

import com.dwarfeng.statistics.stack.bean.dto.BridgeData;
import com.dwarfeng.statistics.stack.exception.MapperException;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.List;

/**
 * 映射器。
 *
 * <p>
 * 映射器在数据查看过程中的第二步被调用，用于将传入的序列映射为新的序列。
 *
 * <p>
 * 每个映射器可以接受一个映射参数对象，该对象用于控制映射器的行为。
 *
 * <p>
 * 关于查看的详细信息，请参阅术语。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface Mapper {

    /**
     * 映射序列列表。
     *
     * @param mapParam  映射参数。
     * @param sequences 待映射的序列组成的列表。
     * @return 映射后的序列组成的列表。
     * @throws MapperException 映射器异常。
     */
    List<Sequence> map(MapParam mapParam, List<Sequence> sequences) throws MapperException;

    /**
     * 映射参数。
     *
     * <p>
     * 映射参数用于控制映射器的行为。
     *
     * @author DwArFeng
     */
    final class MapParam {

        private final String param;

        public MapParam(String param) {
            this.param = param;
        }

        public String getParam() {
            return param;
        }

        @Override
        public String toString() {
            return "MapParam{" +
                    "param='" + param + '\'' +
                    '}';
        }
    }

    /**
     * 数据序列。
     *
     * <p>
     * 数据序列是一个由数据组成的列表，每个数据都包含了条目、开始时间、结束时间。
     *
     * <p>
     * 数据序列的开始时间和结束时间是指该序列参与映射的参照时间，序列中的条目列表中的所有数据都应该在该时间段内。<br>
     * 在部分情况下，数据序列中的条目不恰好发生在起始时间或结束时间点上。
     *
     * <p>
     * 需要注意的是：
     * <ul>
     *     <li>
     *         数据序列应保证其中所有的数据条目的统计设置主键都与序列本身的统计设置主键相同。
     *     </li>
     *     <li>
     *         数据序列中的条目列表中的数据不一定是按照时间顺序排列的。数据序列的顺序和查询结果以及之前的映射结果有关。<br>
     *         可以使用 sdk 模块中的数据工具类获取比较器，对数据进行排序。
     *     </li>
     * </ul>
     */
    final class Sequence {

        @Nonnull
        private final LongIdKey statisticsSettingKey;
        @Nonnull
        private final List<BridgeData> datas;
        @Nonnull
        private final Date startDate;
        @Nonnull
        private final Date endDate;

        public Sequence(
                @Nonnull LongIdKey statisticsSettingKey,
                @Nonnull List<BridgeData> datas,
                @Nonnull Date startDate,
                @Nonnull Date endDate
        ) {
            this.statisticsSettingKey = statisticsSettingKey;
            this.datas = datas;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        @Nonnull
        public LongIdKey getStatisticsSettingKey() {
            return statisticsSettingKey;
        }

        @Nonnull
        public List<BridgeData> getDatas() {
            return datas;
        }

        @Nonnull
        public Date getStartDate() {
            return startDate;
        }

        @Nonnull
        public Date getEndDate() {
            return endDate;
        }

        @Override
        public String toString() {
            return "Sequence{" +
                    "statisticsSettingKey=" + statisticsSettingKey +
                    ", datas=" + datas +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    '}';
        }
    }
}
