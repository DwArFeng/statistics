package com.dwarfeng.statistics.impl.handler.bridge.hibernate.dao.nativelookup;

/**
 * Mysql8 原生查询工具类。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class Mysql8NativeLookupUtil {

    public static void selectColumnsFromTable(
            StringBuilder sqlBuilder, String tableName, String tableNameAlias, String... columnNames
    ) {
        sqlBuilder.append("SELECT ");
        for (int i = 0; i < columnNames.length; i++) {
            sqlBuilder.append(columnNames[i]);
            if (i != columnNames.length - 1) {
                sqlBuilder.append(", ");
            }
        }
        sqlBuilder.append(" FROM ");
        sqlBuilder.append(tableName);
        sqlBuilder.append(" AS ");
        sqlBuilder.append(tableNameAlias);
    }

    public static void selectCountFromTable(
            StringBuilder sqlBuilder, String tableName, String tableNameAlias, String indexColumnName
    ) {
        sqlBuilder.append("SELECT COUNT(");
        sqlBuilder.append(tableNameAlias);
        sqlBuilder.append(".");
        sqlBuilder.append(indexColumnName);
        sqlBuilder.append(") FROM ");
        sqlBuilder.append(tableName);
        sqlBuilder.append(" AS ");
        sqlBuilder.append(tableNameAlias);
    }

    public static void forceIndex(StringBuilder sqlBuilder, String indexName) {
        sqlBuilder.append(" FORCE INDEX (");
        sqlBuilder.append(indexName);
        sqlBuilder.append(")");
    }

    public static void where(StringBuilder sqlBuilder) {
        sqlBuilder.append(" WHERE");
    }

    public static void and(StringBuilder sqlBuilder) {
        sqlBuilder.append(" AND");
    }

    @SuppressWarnings("JavaExistingMethodCanBeUsed")
    public static void statisticsSettingLongIdEquals(
            StringBuilder sqlBuilder, String tableNameAlias, String statisticsSettingLongIdColumnName
    ) {
        sqlBuilder.append(" ");
        sqlBuilder.append(tableNameAlias);
        sqlBuilder.append(".");
        sqlBuilder.append(statisticsSettingLongIdColumnName);
        sqlBuilder.append(" = ?");
    }

    public static void tagEquals(
            StringBuilder sqlBuilder, String tableNameAlias, String tagColumnName
    ) {
        sqlBuilder.append(" ");
        sqlBuilder.append(tableNameAlias);
        sqlBuilder.append(".");
        sqlBuilder.append(tagColumnName);
        sqlBuilder.append(" = ?");
    }

    @SuppressWarnings("DuplicatedCode")
    public static void happenedDateBetweenCloseClose(
            StringBuilder sqlBuilder, String tableNameAlias, String happenedDateColumnName
    ) {
        sqlBuilder.append(" ");
        sqlBuilder.append(tableNameAlias);
        sqlBuilder.append(".");
        sqlBuilder.append(happenedDateColumnName);
        sqlBuilder.append(" >= ? AND ");
        sqlBuilder.append(tableNameAlias);
        sqlBuilder.append(".");
        sqlBuilder.append(happenedDateColumnName);
        sqlBuilder.append(" <= ?");
    }

    @SuppressWarnings("DuplicatedCode")
    public static void happenedDateBetweenCloseOpen(
            StringBuilder sqlBuilder, String tableNameAlias, String happenedDateColumnName
    ) {
        sqlBuilder.append(" ");
        sqlBuilder.append(tableNameAlias);
        sqlBuilder.append(".");
        sqlBuilder.append(happenedDateColumnName);
        sqlBuilder.append(" >= ? AND ");
        sqlBuilder.append(tableNameAlias);
        sqlBuilder.append(".");
        sqlBuilder.append(happenedDateColumnName);
        sqlBuilder.append(" < ?");
    }

    @SuppressWarnings("DuplicatedCode")
    public static void happenedDateBetweenOpenClose(
            StringBuilder sqlBuilder, String tableNameAlias, String happenedDateColumnName
    ) {
        sqlBuilder.append(" ");
        sqlBuilder.append(tableNameAlias);
        sqlBuilder.append(".");
        sqlBuilder.append(happenedDateColumnName);
        sqlBuilder.append(" > ? AND ");
        sqlBuilder.append(tableNameAlias);
        sqlBuilder.append(".");
        sqlBuilder.append(happenedDateColumnName);
        sqlBuilder.append(" <= ?");
    }

    @SuppressWarnings("DuplicatedCode")
    public static void happenedDateBetweenOpenOpen(
            StringBuilder sqlBuilder, String tableNameAlias, String happenedDateColumnName
    ) {
        sqlBuilder.append(" ");
        sqlBuilder.append(tableNameAlias);
        sqlBuilder.append(".");
        sqlBuilder.append(happenedDateColumnName);
        sqlBuilder.append(" > ? AND ");
        sqlBuilder.append(tableNameAlias);
        sqlBuilder.append(".");
        sqlBuilder.append(happenedDateColumnName);
        sqlBuilder.append(" < ?");
    }

    public static void orderByHappenedDateAsc(
            StringBuilder sqlBuilder, String tableNameAlias, String happenedDateColumnName
    ) {
        sqlBuilder.append(" ORDER BY ");
        sqlBuilder.append(tableNameAlias);
        sqlBuilder.append(".");
        sqlBuilder.append(happenedDateColumnName);
        sqlBuilder.append(" ASC");
    }

    public static void limit(StringBuilder sqlBuilder) {
        sqlBuilder.append(" LIMIT ?, ?");
    }

    private Mysql8NativeLookupUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
