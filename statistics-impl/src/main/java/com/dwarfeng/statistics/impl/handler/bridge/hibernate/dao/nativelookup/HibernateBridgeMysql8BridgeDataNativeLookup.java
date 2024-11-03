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
    protected List<HibernateBridgeBridgeData> lookupLookupDefaultCloseClose(
            Connection connection, long statisticsSettingLongId, String tag,
            Date startDate, Date endDate, int offset, int limit
    ) throws SQLException {
        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectColumnsFromTable(
                sqlBuilder, "tbl_bridge_data", "tbl",
                "id", "statistics_setting_long_id", "tag", "value", "happened_date"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_statistics_setting_long_id_tag_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.statisticsSettingLongIdEquals(sqlBuilder, "tbl", "statistics_setting_long_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.tagEquals(sqlBuilder, "tbl", "tag");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenCloseClose(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.orderByHappenedDateAsc(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.limit(sqlBuilder);

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, statisticsSettingLongId);
        preparedStatement.setString(2, tag);
        preparedStatement.setTimestamp(3, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(4, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(5, offset);
        preparedStatement.setInt(6, limit);

        // 执行查询，构建结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        List<HibernateBridgeBridgeData> HibernateBridgeBridgeDatas = new ArrayList<>();
        while (resultSet.next()) {
            HibernateBridgeBridgeDatas.add(new HibernateBridgeBridgeData(
                    new LongIdKey(resultSet.getLong(1)),
                    resultSet.getLong(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    new Date(resultSet.getTimestamp(5).getTime())
            ));
        }
        return HibernateBridgeBridgeDatas;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<HibernateBridgeBridgeData> lookupLookupDefaultCloseOpen(
            Connection connection, long statisticsSettingLongId, String tag,
            Date startDate, Date endDate, int offset, int limit
    ) throws SQLException {
        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectColumnsFromTable(
                sqlBuilder, "tbl_bridge_data", "tbl",
                "id", "statistics_setting_long_id", "tag", "value", "happened_date"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_statistics_setting_long_id_tag_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.statisticsSettingLongIdEquals(sqlBuilder, "tbl", "statistics_setting_long_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.tagEquals(sqlBuilder, "tbl", "tag");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenCloseOpen(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.orderByHappenedDateAsc(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.limit(sqlBuilder);

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, statisticsSettingLongId);
        preparedStatement.setString(2, tag);
        preparedStatement.setTimestamp(3, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(4, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(5, offset);
        preparedStatement.setInt(6, limit);

        // 执行查询，构建结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        List<HibernateBridgeBridgeData> HibernateBridgeBridgeDatas = new ArrayList<>();
        while (resultSet.next()) {
            HibernateBridgeBridgeDatas.add(new HibernateBridgeBridgeData(
                    new LongIdKey(resultSet.getLong(1)),
                    resultSet.getLong(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    new Date(resultSet.getTimestamp(5).getTime())
            ));
        }
        return HibernateBridgeBridgeDatas;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<HibernateBridgeBridgeData> lookupLookupDefaultOpenClose(
            Connection connection, long statisticsSettingLongId, String tag,
            Date startDate, Date endDate, int offset, int limit
    ) throws SQLException {
        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectColumnsFromTable(
                sqlBuilder, "tbl_bridge_data", "tbl",
                "id", "statistics_setting_long_id", "tag", "value", "happened_date"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_statistics_setting_long_id_tag_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.statisticsSettingLongIdEquals(sqlBuilder, "tbl", "statistics_setting_long_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.tagEquals(sqlBuilder, "tbl", "tag");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenOpenClose(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.orderByHappenedDateAsc(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.limit(sqlBuilder);

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, statisticsSettingLongId);
        preparedStatement.setString(2, tag);
        preparedStatement.setTimestamp(3, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(4, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(5, offset);
        preparedStatement.setInt(6, limit);

        // 执行查询，构建结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        List<HibernateBridgeBridgeData> HibernateBridgeBridgeDatas = new ArrayList<>();
        while (resultSet.next()) {
            HibernateBridgeBridgeDatas.add(new HibernateBridgeBridgeData(
                    new LongIdKey(resultSet.getLong(1)),
                    resultSet.getLong(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    new Date(resultSet.getTimestamp(5).getTime())
            ));
        }
        return HibernateBridgeBridgeDatas;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<HibernateBridgeBridgeData> lookupLookupDefaultOpenOpen(
            Connection connection, long statisticsSettingLongId, String tag,
            Date startDate, Date endDate, int offset, int limit
    ) throws SQLException {
        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectColumnsFromTable(
                sqlBuilder, "tbl_bridge_data", "tbl",
                "id", "statistics_setting_long_id", "tag", "value", "happened_date"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_statistics_setting_long_id_tag_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.statisticsSettingLongIdEquals(sqlBuilder, "tbl", "statistics_setting_long_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.tagEquals(sqlBuilder, "tbl", "tag");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenOpenOpen(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.orderByHappenedDateAsc(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.limit(sqlBuilder);

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, statisticsSettingLongId);
        preparedStatement.setString(2, tag);
        preparedStatement.setTimestamp(3, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(4, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(5, offset);
        preparedStatement.setInt(6, limit);

        // 执行查询，构建结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        List<HibernateBridgeBridgeData> HibernateBridgeBridgeDatas = new ArrayList<>();
        while (resultSet.next()) {
            HibernateBridgeBridgeDatas.add(new HibernateBridgeBridgeData(
                    new LongIdKey(resultSet.getLong(1)),
                    resultSet.getLong(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    new Date(resultSet.getTimestamp(5).getTime())
            ));
        }
        return HibernateBridgeBridgeDatas;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected int lookupLookupDefaultCloseCloseCount(
            Connection connection, long statisticsSettingLongId, String tag, Date startDate, Date endDate
    ) throws SQLException {
        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectCountFromTable(
                sqlBuilder, "tbl_bridge_data", "tbl", "id"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_statistics_setting_long_id_tag_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.statisticsSettingLongIdEquals(sqlBuilder, "tbl", "statistics_setting_long_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.tagEquals(sqlBuilder, "tbl", "tag");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenCloseClose(sqlBuilder, "tbl", "happened_date");

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, statisticsSettingLongId);
        preparedStatement.setString(2, tag);
        preparedStatement.setTimestamp(3, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(4, new Timestamp(endDate.getTime()));

        // 执行查询，返回结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return Long.valueOf(resultSet.getLong(1)).intValue();
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected int lookupLookupDefaultCloseOpenCount(
            Connection connection, long statisticsSettingLongId, String tag, Date startDate, Date endDate
    ) throws SQLException {
        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectCountFromTable(
                sqlBuilder, "tbl_bridge_data", "tbl", "id"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_statistics_setting_long_id_tag_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.statisticsSettingLongIdEquals(sqlBuilder, "tbl", "statistics_setting_long_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.tagEquals(sqlBuilder, "tbl", "tag");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenCloseOpen(sqlBuilder, "tbl", "happened_date");

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, statisticsSettingLongId);
        preparedStatement.setString(2, tag);
        preparedStatement.setTimestamp(3, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(4, new Timestamp(endDate.getTime()));

        // 执行查询，返回结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return Long.valueOf(resultSet.getLong(1)).intValue();
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected int lookupLookupDefaultOpenCloseCount(
            Connection connection, long statisticsSettingLongId, String tag, Date startDate, Date endDate
    ) throws SQLException {
        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectCountFromTable(
                sqlBuilder, "tbl_bridge_data", "tbl", "id"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_statistics_setting_long_id_tag_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.statisticsSettingLongIdEquals(sqlBuilder, "tbl", "statistics_setting_long_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.tagEquals(sqlBuilder, "tbl", "tag");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenOpenClose(sqlBuilder, "tbl", "happened_date");

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, statisticsSettingLongId);
        preparedStatement.setString(2, tag);
        preparedStatement.setTimestamp(3, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(4, new Timestamp(endDate.getTime()));

        // 执行查询，返回结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return Long.valueOf(resultSet.getLong(1)).intValue();
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected int lookupLookupDefaultOpenOpenCount(
            Connection connection, long statisticsSettingLongId, String tag, Date startDate, Date endDate
    ) throws SQLException {
        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectCountFromTable(
                sqlBuilder, "tbl_bridge_data", "tbl", "id"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_statistics_setting_long_id_tag_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.statisticsSettingLongIdEquals(sqlBuilder, "tbl", "statistics_setting_long_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.tagEquals(sqlBuilder, "tbl", "tag");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenOpenOpen(sqlBuilder, "tbl", "happened_date");

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, statisticsSettingLongId);
        preparedStatement.setString(2, tag);
        preparedStatement.setTimestamp(3, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(4, new Timestamp(endDate.getTime()));

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
