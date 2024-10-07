package com.dwarfeng.statistics.impl.handler.mapper;

import com.dwarfeng.statistics.sdk.util.CompareUtil;
import com.dwarfeng.statistics.stack.bean.dto.BridgeData;

import java.util.Date;
import java.util.List;

/**
 * 映射器工具类。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
final class MapperUtil {

    /**
     * 对 datas 按照时间进行升序排序，并且过滤在 startDate 和 endDate 之外的数据。
     *
     * <p>
     * 如果 extendItem 为 true，则会保留至多一个发生时间在 startDate 之前的桥接器数据，
     * 以及至多一个发生时间在 endDate 之后的桥接器数据。
     *
     * @param datas      指定的桥接器数据序列。
     * @param startDate  指定的开始时间。
     * @param endDate    指定的结束时间。
     * @param extendItem 指定是否扩展桥接器数据。
     * @return 排序并且过滤后的桥接器数据序列。
     */
    public static List<BridgeData> sortAndFilterDatas(
            List<BridgeData> datas, Date startDate, Date endDate, boolean extendItem
    ) {
        // 对 datas 按照时间进行排序。
        datas.sort(CompareUtil.BRIDGE_DATA_HAPPENED_DATE_ASC_COMPARATOR);

        // 从前向后遍历 datas。
        int firstIndex = 0;
        for (int i = 0; i < datas.size(); i++) {
            BridgeData data = datas.get(i);
            // 如果 extendItem 为 true，保证至多只有一个元素的发生时间小于等于 startDate。
            if (extendItem) {
                if (data.getHappenedDate().compareTo(startDate) <= 0) {
                    firstIndex = i;
                } else {
                    break;
                }
            }
            // 如果 extendItem 为 false，保证所有元素的发生时间大于等于 startDate。
            else {
                if (data.getHappenedDate().compareTo(startDate) >= 0) {
                    firstIndex = i;
                    break;
                }
            }
        }
        // 从后向前遍历 datas。
        int lastIndex = datas.size() - 1;
        for (int i = datas.size() - 1; i >= 0; i--) {
            BridgeData data = datas.get(i);
            // 如果 extendItem 为 true，保证至多只有一个元素的发生时间大于等于 endDate。
            if (extendItem) {
                if (data.getHappenedDate().compareTo(endDate) >= 0) {
                    lastIndex = i;
                } else {
                    break;
                }
            }
            // 如果 extendItem 为 false，保证所有元素的发生时间小于等于 endDate。
            else {
                if (data.getHappenedDate().compareTo(endDate) <= 0) {
                    lastIndex = i;
                    break;
                }
            }
        }

        // 判断特殊情况：如果 firstIndex 大于 lastIndex，则返回空列表。
        if (firstIndex > lastIndex) {
            return datas.subList(0, 0);
        }

        // 返回过滤后的桥接器数据序列。
        return datas.subList(firstIndex, lastIndex + 1);
    }

    private MapperUtil() {
        throw new IllegalStateException("禁止实例化");
    }
}
