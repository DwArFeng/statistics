package com.dwarfeng.statistics.sdk.util;

import com.dwarfeng.statistics.stack.bean.dto.BridgeData;
import com.dwarfeng.statistics.stack.bean.dto.ProviderData;
import com.dwarfeng.statistics.stack.handler.Mapper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Comparator;
import java.util.Date;

/**
 * 比较工具类。
 *
 * <p>
 * 该工具类提供了一些比较器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class CompareUtil {

    /**
     * 长整型主键比较器，按照主键的升序进行比较。
     */
    public static final Comparator<LongIdKey> LONG_ID_KEY_ASC_COMPARATOR =
            Comparator.comparing(LongIdKey::getLongId);

    /**
     * 长整型主键比较器，按照主键的降序进行比较。
     */
    public static final Comparator<LongIdKey> LONG_ID_KEY_DESC_COMPARATOR =
            LONG_ID_KEY_ASC_COMPARATOR.reversed();

    /**
     * 日期比较器，按照日期的升序进行比较。
     */
    public static final Comparator<Date> DATE_ASC_COMPARATOR =
            Comparator.comparing(Date::getTime);

    /**
     * 日期比较器，按照日期的降序进行比较。
     */
    public static final Comparator<Date> DATE_DESC_COMPARATOR =
            DATE_ASC_COMPARATOR.reversed();

    /**
     * 桥接器数据比较器，按照桥接器数据的默认顺序进行比较。
     */
    public static final Comparator<BridgeData> BRIDGE_DATA_DEFAULT_COMPARATOR =
            Comparator.comparing(BridgeData::getStatisticsSettingKey, LONG_ID_KEY_ASC_COMPARATOR)
                    .thenComparing(BridgeData::getHappenedDate, DATE_ASC_COMPARATOR);

    /**
     * 桥接器数据比较器，按照桥接器数据的发生日期的升序进行比较。
     */
    public static final Comparator<BridgeData> BRIDGE_DATA_HAPPENED_DATE_ASC_COMPARATOR =
            Comparator.comparing(BridgeData::getHappenedDate, DATE_ASC_COMPARATOR);

    /**
     * 桥接器数据比较器，按照桥接器数据的发生日期的降序进行比较。
     */
    public static final Comparator<BridgeData> BRIDGE_HAPPENED_DATE_DESC_COMPARATOR =
            Comparator.comparing(BridgeData::getHappenedDate, DATE_DESC_COMPARATOR);

    /**
     * 提供器数据比较器，按照提供器数据的默认顺序进行比较。
     */
    public static final Comparator<ProviderData> PROVIDER_DATA_DEFAULT_COMPARATOR =
            Comparator.comparing(ProviderData::getHappenedDate, DATE_ASC_COMPARATOR);

    /**
     * 提供器数据比较器，按照提供器数据的发生日期的升序进行比较。
     */
    public static final Comparator<ProviderData> PROVIDER_DATA_HAPPENED_DATE_ASC_COMPARATOR =
            Comparator.comparing(ProviderData::getHappenedDate, DATE_ASC_COMPARATOR);

    /**
     * 提供器数据比较器，按照提供器数据的发生日期的降序进行比较。
     */
    public static final Comparator<ProviderData> PROVIDER_HAPPENED_DATE_DESC_COMPARATOR =
            Comparator.comparing(ProviderData::getHappenedDate, DATE_DESC_COMPARATOR);

    /**
     * 序列比较器，按照序列的默认顺序进行比较。
     */
    public static final Comparator<Mapper.Sequence> SEQUENCE_DEFAULT_COMPARATOR =
            Comparator.comparing(Mapper.Sequence::getStatisticsSettingKey, LONG_ID_KEY_ASC_COMPARATOR);

    /**
     * 序列比较器，按照序列的统计设置主键的升序进行比较。
     */
    public static final Comparator<Mapper.Sequence> SEQUENCE_POINT_KEY_ASC_COMPARATOR =
            Comparator.comparing(Mapper.Sequence::getStatisticsSettingKey, LONG_ID_KEY_ASC_COMPARATOR);

    public static final Comparator<Mapper.Sequence> SEQUENCE_POINT_KEY_DESC_COMPARATOR =
            Comparator.comparing(Mapper.Sequence::getStatisticsSettingKey, LONG_ID_KEY_DESC_COMPARATOR);

    private CompareUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
