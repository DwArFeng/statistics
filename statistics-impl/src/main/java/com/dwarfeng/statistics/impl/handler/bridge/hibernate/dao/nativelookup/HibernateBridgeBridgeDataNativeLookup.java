package com.dwarfeng.statistics.impl.handler.bridge.hibernate.dao.nativelookup;

import com.dwarfeng.statistics.impl.handler.bridge.hibernate.bean.HibernateBridgeBridgeData;
import com.dwarfeng.statistics.impl.handler.bridge.hibernate.service.HibernateBridgeBridgeDataMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.nativelookup.AbstractDialectNativeLookup;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Hibernate 桥接器数据本地查询。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class HibernateBridgeBridgeDataNativeLookup extends
        AbstractDialectNativeLookup<HibernateBridgeBridgeData> {

    public HibernateBridgeBridgeDataNativeLookup(String supportDialect) {
        super(supportDialect);
    }

    /*
     * 在 HibernateBridge 中，所有的数据查询业务均不需要调用该方法。
     * 因此为了简化实现，该方法直接抛出异常。
     */
    @Override
    public List<HibernateBridgeBridgeData> lookupEntity(Connection connection, String preset, Object[] args) {
        throw new IllegalArgumentException("不支持不带分页信息的查询");
    }

    @Override
    public boolean supportPreset(String preset) {
        switch (preset) {
            case HibernateBridgeBridgeDataMaintainService.CHILD_FOR_STATISTICS_SETTING_BETWEEN_CLOSE_CLOSE:
            case HibernateBridgeBridgeDataMaintainService.CHILD_FOR_STATISTICS_SETTING_BETWEEN_CLOSE_OPEN:
            case HibernateBridgeBridgeDataMaintainService.CHILD_FOR_STATISTICS_SETTING_BETWEEN_OPEN_CLOSE:
            case HibernateBridgeBridgeDataMaintainService.CHILD_FOR_STATISTICS_SETTING_BETWEEN_OPEN_OPEN:
                return true;
            default:
                return false;
        }
    }

    @Override
    public List<HibernateBridgeBridgeData> lookupEntity(
            Connection connection, String preset, Object[] args, PagingInfo pagingInfo
    ) throws SQLException {
        switch (preset) {
            case HibernateBridgeBridgeDataMaintainService.CHILD_FOR_STATISTICS_SETTING_BETWEEN_CLOSE_CLOSE:
                return childForStatisticsSettingBetweenCloseClose(connection, args, pagingInfo);
            case HibernateBridgeBridgeDataMaintainService.CHILD_FOR_STATISTICS_SETTING_BETWEEN_CLOSE_OPEN:
                return childForStatisticsSettingBetweenCloseOpen(connection, args, pagingInfo);
            case HibernateBridgeBridgeDataMaintainService.CHILD_FOR_STATISTICS_SETTING_BETWEEN_OPEN_CLOSE:
                return childForStatisticsSettingBetweenOpenClose(connection, args, pagingInfo);
            case HibernateBridgeBridgeDataMaintainService.CHILD_FOR_STATISTICS_SETTING_BETWEEN_OPEN_OPEN:
                return childForStatisticsSettingBetweenOpenOpen(connection, args, pagingInfo);
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private List<HibernateBridgeBridgeData> childForStatisticsSettingBetweenCloseClose(
            Connection connection, Object[] args, PagingInfo pagingInfo
    ) throws SQLException {
        // 展开参数。
        long statisticsSettingLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];
        int offset = pagingInfo.getPage() * pagingInfo.getRows();
        int limit = pagingInfo.getRows();

        // 查询数据。
        return lookupChildForStatisticsSettingBetweenCloseClose(
                connection, statisticsSettingLongId, startDate, endDate, offset, limit
        );
    }

    /**
     * 查询指定统计设置键在指定时间范围内的数据。
     *
     * <p>
     * 该方法查询的数据的时间范围为 [startDate, endDate]，即包含起始时间，包含结束时间。
     *
     * <p>
     * 返回的数据从 offset（以 0 开始） 开始，最多返回 limit 条数据。
     *
     * @param connection              数据库连接。
     * @param statisticsSettingLongId 统计设置 ID。
     * @param startDate               起始时间。
     * @param endDate                 结束时间。
     * @param offset                  偏移量。
     * @param limit                   限制量。
     * @return 查询得到的数据。
     * @throws SQLException SQL 异常。
     */
    protected abstract List<HibernateBridgeBridgeData> lookupChildForStatisticsSettingBetweenCloseClose(
            Connection connection, long statisticsSettingLongId, Date startDate, Date endDate, int offset, int limit
    ) throws SQLException;

    private List<HibernateBridgeBridgeData> childForStatisticsSettingBetweenCloseOpen(
            Connection connection, Object[] args, PagingInfo pagingInfo
    ) throws SQLException {
        // 展开参数。
        long statisticsSettingLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];
        int offset = pagingInfo.getPage() * pagingInfo.getRows();
        int limit = pagingInfo.getRows();

        // 查询数据。
        return lookupChildForStatisticsSettingBetweenCloseOpen(
                connection, statisticsSettingLongId, startDate, endDate, offset, limit
        );
    }

    /**
     * 查询指定统计设置键在指定时间范围内的数据。
     *
     * <p>
     * 该方法查询的数据的时间范围为 [startDate, endDate)，即包含起始时间，不包含结束时间。
     *
     * <p>
     * 返回的数据从 offset（以 0 开始） 开始，最多返回 limit 条数据。
     *
     * @param connection              数据库连接。
     * @param statisticsSettingLongId 统计设置 ID。
     * @param startDate               起始时间。
     * @param endDate                 结束时间。
     * @param offset                  偏移量。
     * @param limit                   限制量。
     * @return 查询得到的数据。
     * @throws SQLException SQL 异常。
     */
    protected abstract List<HibernateBridgeBridgeData> lookupChildForStatisticsSettingBetweenCloseOpen(
            Connection connection, long statisticsSettingLongId, Date startDate, Date endDate, int offset, int limit
    ) throws SQLException;

    private List<HibernateBridgeBridgeData> childForStatisticsSettingBetweenOpenClose(
            Connection connection, Object[] args, PagingInfo pagingInfo
    ) throws SQLException {
        // 展开参数。
        long statisticsSettingLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];
        int offset = pagingInfo.getPage() * pagingInfo.getRows();
        int limit = pagingInfo.getRows();

        // 查询数据。
        return lookupChildForStatisticsSettingBetweenOpenClose(
                connection, statisticsSettingLongId, startDate, endDate, offset, limit
        );
    }

    /**
     * 查询指定统计设置键在指定时间范围内的数据。
     *
     * <p>
     * 该方法查询的数据的时间范围为 (startDate, endDate]，即不包含起始时间，包含结束时间。
     *
     * <p>
     * 返回的数据从 offset（以 0 开始） 开始，最多返回 limit 条数据。
     *
     * @param connection              数据库连接。
     * @param statisticsSettingLongId 统计设置 ID。
     * @param startDate               起始时间。
     * @param endDate                 结束时间。
     * @param offset                  偏移量。
     * @param limit                   限制量。
     * @return 查询得到的数据。
     * @throws SQLException SQL 异常。
     */
    protected abstract List<HibernateBridgeBridgeData> lookupChildForStatisticsSettingBetweenOpenClose(
            Connection connection, long statisticsSettingLongId, Date startDate, Date endDate, int offset, int limit
    ) throws SQLException;

    private List<HibernateBridgeBridgeData> childForStatisticsSettingBetweenOpenOpen(
            Connection connection, Object[] args, PagingInfo pagingInfo
    ) throws SQLException {
        // 展开参数。
        long statisticsSettingLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];
        int offset = pagingInfo.getPage() * pagingInfo.getRows();
        int limit = pagingInfo.getRows();

        // 查询数据。
        return lookupChildForStatisticsSettingBetweenOpenOpen(
                connection, statisticsSettingLongId, startDate, endDate, offset, limit
        );
    }

    /**
     * 查询指定统计设置键在指定时间范围内的数据。
     *
     * <p>
     * 该方法查询的数据的时间范围为 (startDate, endDate)，即不包含起始时间，不包含结束时间。
     *
     * <p>
     * 返回的数据从 offset（以 0 开始） 开始，最多返回 limit 条数据。
     *
     * @param connection              数据库连接。
     * @param statisticsSettingLongId 统计设置 ID。
     * @param startDate               起始时间。
     * @param endDate                 结束时间。
     * @param offset                  偏移量。
     * @param limit                   限制量。
     * @return 查询得到的数据。
     * @throws SQLException SQL 异常。
     */
    protected abstract List<HibernateBridgeBridgeData> lookupChildForStatisticsSettingBetweenOpenOpen(
            Connection connection, long statisticsSettingLongId, Date startDate, Date endDate, int offset, int limit
    ) throws SQLException;

    @Override
    public int lookupCount(Connection connection, String preset, Object[] args) throws SQLException {
        switch (preset) {
            case HibernateBridgeBridgeDataMaintainService.CHILD_FOR_STATISTICS_SETTING_BETWEEN_CLOSE_CLOSE:
                return childForStatisticsSettingBetweenCloseCloseCount(connection, args);
            case HibernateBridgeBridgeDataMaintainService.CHILD_FOR_STATISTICS_SETTING_BETWEEN_CLOSE_OPEN:
                return childForStatisticsSettingBetweenCloseOpenCount(connection, args);
            case HibernateBridgeBridgeDataMaintainService.CHILD_FOR_STATISTICS_SETTING_BETWEEN_OPEN_CLOSE:
                return childForStatisticsSettingBetweenOpenCloseCount(connection, args);
            case HibernateBridgeBridgeDataMaintainService.CHILD_FOR_STATISTICS_SETTING_BETWEEN_OPEN_OPEN:
                return childForStatisticsSettingBetweenOpenOpenCount(connection, args);
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private int childForStatisticsSettingBetweenCloseCloseCount(Connection connection, Object[] args)
            throws SQLException {
        // 展开参数。
        long statisticsSettingLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        // 查询数据。
        return lookupChildForStatisticsSettingBetweenCloseCloseCount(
                connection, statisticsSettingLongId, startDate, endDate
        );
    }

    /**
     * 查询指定统计设置键在指定时间范围内的数据数量。
     *
     * <p>
     * 该方法查询的数据的时间范围为 [startDate, endDate]，即包含起始时间，包含结束时间。
     *
     * @param connection              数据库连接。
     * @param statisticsSettingLongId 统计设置 ID。
     * @param startDate               起始时间。
     * @param endDate                 结束时间。
     * @return 查询得到的数据数量。
     * @throws SQLException SQL 异常。
     */
    protected abstract int lookupChildForStatisticsSettingBetweenCloseCloseCount(
            Connection connection, long statisticsSettingLongId, Date startDate, Date endDate
    ) throws SQLException;

    private int childForStatisticsSettingBetweenCloseOpenCount(Connection connection, Object[] args)
            throws SQLException {
        // 展开参数。
        long statisticsSettingLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        // 查询数据。
        return lookupChildForStatisticsSettingBetweenCloseOpenCount(
                connection, statisticsSettingLongId, startDate, endDate
        );
    }

    /**
     * 查询指定统计设置键在指定时间范围内的数据数量。
     *
     * <p>
     * 该方法查询的数据的时间范围为 [startDate, endDate)，即包含起始时间，不包含结束时间。
     *
     * @param connection              数据库连接。
     * @param statisticsSettingLongId 统计设置 ID。
     * @param startDate               起始时间。
     * @param endDate                 结束时间。
     * @return 查询得到的数据数量。
     * @throws SQLException SQL 异常。
     */
    protected abstract int lookupChildForStatisticsSettingBetweenCloseOpenCount(
            Connection connection, long statisticsSettingLongId, Date startDate, Date endDate
    ) throws SQLException;

    private int childForStatisticsSettingBetweenOpenCloseCount(Connection connection, Object[] args)
            throws SQLException {
        // 展开参数。
        long statisticsSettingLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        // 查询数据。
        return lookupChildForStatisticsSettingBetweenOpenCloseCount(
                connection, statisticsSettingLongId, startDate, endDate
        );
    }

    /**
     * 查询指定统计设置键在指定时间范围内的数据数量。
     *
     * <p>
     * 该方法查询的数据的时间范围为 (startDate, endDate]，即不包含起始时间，包含结束时间。
     *
     * @param connection              数据库连接。
     * @param statisticsSettingLongId 统计设置 ID。
     * @param startDate               起始时间。
     * @param endDate                 结束时间。
     * @return 查询得到的数据数量。
     * @throws SQLException SQL 异常。
     */
    protected abstract int lookupChildForStatisticsSettingBetweenOpenCloseCount(
            Connection connection, long statisticsSettingLongId, Date startDate, Date endDate
    ) throws SQLException;

    private int childForStatisticsSettingBetweenOpenOpenCount(Connection connection, Object[] args)
            throws SQLException {
        // 展开参数。
        long statisticsSettingLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        // 查询数据。
        return lookupChildForStatisticsSettingBetweenOpenOpenCount(
                connection, statisticsSettingLongId, startDate, endDate
        );
    }

    /**
     * 查询指定统计设置键在指定时间范围内的数据数量。
     *
     * <p>
     * 该方法查询的数据的时间范围为 (startDate, endDate)，即不包含起始时间，不包含结束时间。
     *
     * @param connection              数据库连接。
     * @param statisticsSettingLongId 统计设置 ID。
     * @param startDate               起始时间。
     * @param endDate                 结束时间。
     * @return 查询得到的数据数量。
     * @throws SQLException SQL 异常。
     */
    protected abstract int lookupChildForStatisticsSettingBetweenOpenOpenCount(
            Connection connection, long statisticsSettingLongId, Date startDate, Date endDate
    ) throws SQLException;

    @Override
    public String toString() {
        return "HibernateBridgeBridgeDataNativeLookup{" +
                "supportDialect='" + supportDialect + '\'' +
                '}';
    }
}
