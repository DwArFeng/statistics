package com.dwarfeng.statistics.impl.handler.bridge.hibernate.dao.nativelookup;

import com.dwarfeng.statistics.impl.handler.bridge.hibernate.bean.HibernateBridgeBridgeData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class HibernateBridgeMysql8BridgeDataNativeLookup extends HibernateBridgeBridgeDataNativeLookup {

    public static final String SUPPORT_TYPE = "org.hibernate.dialect.MySQL8Dialect";

    public HibernateBridgeMysql8BridgeDataNativeLookup() {
        super(SUPPORT_TYPE);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<HibernateBridgeBridgeData> lookupChildForStatisticsSettingBetweenCloseClose(
            Connection connection, long statisticsSettingLongId, Date startDate, Date endDate, int offset, int limit
    ) throws SQLException {
        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectColumnsFromTable(
                sqlBuilder, "tbl_hibernate_bridge_bridge_data", "tbl",
                "id", "statistics_setting_id", "value", "happened_date"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_statistics_setting_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.statisticsSettingLongIdEquals(sqlBuilder, "tbl", "statistics_setting_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenCloseClose(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.orderByHappenedDateAsc(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.limit(sqlBuilder);

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, statisticsSettingLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(4, offset);
        preparedStatement.setInt(5, limit);

        // 执行查询，构建结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        List<HibernateBridgeBridgeData> HibernateBridgeBridgeDatas = new ArrayList<>();
        while (resultSet.next()) {
            HibernateBridgeBridgeDatas.add(new HibernateBridgeBridgeData(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    resultSet.getString(3),
                    new Date(resultSet.getTimestamp(4).getTime())
            ));
        }
        return HibernateBridgeBridgeDatas;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<HibernateBridgeBridgeData> lookupChildForStatisticsSettingBetweenCloseOpen(
            Connection connection, long statisticsSettingLongId, Date startDate, Date endDate, int offset, int limit
    ) throws SQLException {
        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectColumnsFromTable(
                sqlBuilder, "tbl_hibernate_bridge_bridge_data", "tbl",
                "id", "statistics_setting_id", "value", "happened_date"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_statistics_setting_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.statisticsSettingLongIdEquals(sqlBuilder, "tbl", "statistics_setting_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenCloseOpen(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.orderByHappenedDateAsc(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.limit(sqlBuilder);

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, statisticsSettingLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(4, offset);
        preparedStatement.setInt(5, limit);

        // 执行查询，构建结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        List<HibernateBridgeBridgeData> HibernateBridgeBridgeDatas = new ArrayList<>();
        while (resultSet.next()) {
            HibernateBridgeBridgeDatas.add(new HibernateBridgeBridgeData(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    resultSet.getString(3),
                    new Date(resultSet.getTimestamp(4).getTime())
            ));
        }
        return HibernateBridgeBridgeDatas;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<HibernateBridgeBridgeData> lookupChildForStatisticsSettingBetweenOpenClose(
            Connection connection, long statisticsSettingLongId, Date startDate, Date endDate, int offset, int limit
    ) throws SQLException {
        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectColumnsFromTable(
                sqlBuilder, "tbl_hibernate_bridge_bridge_data", "tbl",
                "id", "statistics_setting_id", "value", "happened_date"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_statistics_setting_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.statisticsSettingLongIdEquals(sqlBuilder, "tbl", "statistics_setting_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenOpenClose(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.orderByHappenedDateAsc(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.limit(sqlBuilder);

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, statisticsSettingLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(4, offset);
        preparedStatement.setInt(5, limit);

        // 执行查询，构建结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        List<HibernateBridgeBridgeData> HibernateBridgeBridgeDatas = new ArrayList<>();
        while (resultSet.next()) {
            HibernateBridgeBridgeDatas.add(new HibernateBridgeBridgeData(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    resultSet.getString(3),
                    new Date(resultSet.getTimestamp(4).getTime())
            ));
        }
        return HibernateBridgeBridgeDatas;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<HibernateBridgeBridgeData> lookupChildForStatisticsSettingBetweenOpenOpen(
            Connection connection, long statisticsSettingLongId, Date startDate, Date endDate, int offset, int limit
    ) throws SQLException {
        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectColumnsFromTable(
                sqlBuilder, "tbl_hibernate_bridge_bridge_data", "tbl",
                "id", "statistics_setting_id", "value", "happened_date"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_statistics_setting_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.statisticsSettingLongIdEquals(sqlBuilder, "tbl", "statistics_setting_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenOpenOpen(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.orderByHappenedDateAsc(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.limit(sqlBuilder);

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, statisticsSettingLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(4, offset);
        preparedStatement.setInt(5, limit);

        // 执行查询，构建结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        List<HibernateBridgeBridgeData> HibernateBridgeBridgeDatas = new ArrayList<>();
        while (resultSet.next()) {
            HibernateBridgeBridgeDatas.add(new HibernateBridgeBridgeData(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    resultSet.getString(3),
                    new Date(resultSet.getTimestamp(4).getTime())
            ));
        }
        return HibernateBridgeBridgeDatas;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected int lookupChildForStatisticsSettingBetweenCloseCloseCount(
            Connection connection, long statisticsSettingLongId, Date startDate, Date endDate
    ) throws SQLException {
        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectCountFromTable(
                sqlBuilder, "tbl_hibernate_bridge_bridge_data", "tbl", "id"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_statistics_setting_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.statisticsSettingLongIdEquals(sqlBuilder, "tbl", "statistics_setting_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenCloseClose(sqlBuilder, "tbl", "happened_date");

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, statisticsSettingLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));

        // 执行查询，返回结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return Long.valueOf(resultSet.getLong(1)).intValue();
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected int lookupChildForStatisticsSettingBetweenCloseOpenCount(
            Connection connection, long statisticsSettingLongId, Date startDate, Date endDate
    ) throws SQLException {
        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectCountFromTable(
                sqlBuilder, "tbl_hibernate_bridge_bridge_data", "tbl", "id"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_statistics_setting_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.statisticsSettingLongIdEquals(sqlBuilder, "tbl", "statistics_setting_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenCloseOpen(sqlBuilder, "tbl", "happened_date");

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, statisticsSettingLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));

        // 执行查询，返回结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return Long.valueOf(resultSet.getLong(1)).intValue();
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected int lookupChildForStatisticsSettingBetweenOpenCloseCount(
            Connection connection, long statisticsSettingLongId, Date startDate, Date endDate
    ) throws SQLException {
        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectCountFromTable(
                sqlBuilder, "tbl_hibernate_bridge_bridge_data", "tbl", "id"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_statistics_setting_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.statisticsSettingLongIdEquals(sqlBuilder, "tbl", "statistics_setting_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenOpenClose(sqlBuilder, "tbl", "happened_date");

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, statisticsSettingLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));

        // 执行查询，返回结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return Long.valueOf(resultSet.getLong(1)).intValue();
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected int lookupChildForStatisticsSettingBetweenOpenOpenCount(
            Connection connection, long statisticsSettingLongId, Date startDate, Date endDate
    ) throws SQLException {
        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectCountFromTable(
                sqlBuilder, "tbl_hibernate_bridge_bridge_data", "tbl", "id"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_statistics_setting_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.statisticsSettingLongIdEquals(sqlBuilder, "tbl", "statistics_setting_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenOpenOpen(sqlBuilder, "tbl", "happened_date");

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, statisticsSettingLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));

        // 执行查询，返回结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return Long.valueOf(resultSet.getLong(1)).intValue();
    }

    @Override
    public String toString() {
        return "HibernateBridgeMysql8BridgeDataNativeLookup{}";
    }
}
