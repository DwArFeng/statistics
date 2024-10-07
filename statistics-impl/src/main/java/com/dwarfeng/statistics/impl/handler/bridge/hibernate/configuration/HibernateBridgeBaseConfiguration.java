package com.dwarfeng.statistics.impl.handler.bridge.hibernate.configuration;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.pool.DruidDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
public class HibernateBridgeBaseConfiguration {

    @Value("${bridge.hibernate.use_project_config}")
    private boolean useProjectConfig;

    @Value("${jdbc.driver}")
    private String projectJdbcDriver;
    @Value("${jdbc.url}")
    private String projectJdbcUrl;
    @Value("${jdbc.username}")
    private String projectJdbcUsername;
    @Value("${jdbc.password}")
    private String projectJdbcPassword;
    @Value("${hibernate.dialect}")
    private String projectHibernateDialect;

    @Value("${hibernate.jdbc.batch_size}")
    private int projectHibernateJdbcBatchSize;
    @Value("${hibernate.jdbc.fetch_size}")
    private int projectHibernateJdbcFetchSize;
    @Value("${data_source.max_active}")
    private int projectDataSourceMaxActive;
    @Value("${data_source.min_idle}")
    private int projectDataSourceMinIdle;

    @Value("${bridge.hibernate.jdbc.driver}")
    private String bridgeJdbcDriver;
    @Value("${bridge.hibernate.jdbc.url}")
    private String bridgeJdbcUrl;
    @Value("${bridge.hibernate.jdbc.username}")
    private String bridgeJdbcUsername;
    @Value("${bridge.hibernate.jdbc.password}")
    private String bridgeJdbcPassword;
    @Value("${bridge.hibernate.hibernate.dialect}")
    private String bridgeHibernateDialect;

    @Value("${bridge.hibernate.hibernate.jdbc.batch_size}")
    private int bridgeHibernateJdbcBatchSize;
    @Value("${bridge.hibernate.hibernate.jdbc.fetch_size}")
    private int bridgeHibernateJdbcFetchSize;
    @Value("${bridge.hibernate.data_source.max_active}")
    private int bridgeDataSourceMaxActive;
    @Value("${bridge.hibernate.data_source.min_idle}")
    private int bridgeDataSourceMinIdle;

    @Bean(name = "hibernateBridge.dataSource", initMethod = "init", destroyMethod = "close")
    public DruidDataSource dataSource(
            @Qualifier("hibernateBridge.logFilter") Slf4jLogFilter slf4jLogFilter
    ) throws Exception {
        // 创建代理过滤器列表。
        List<Filter> proxyFilters = new ArrayList<>();
        proxyFilters.add(slf4jLogFilter);

        // 创建数据源。
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(jdbcDriver());
        dataSource.setUrl(jdbcUrl());
        dataSource.setUsername(jdbcUsername());
        dataSource.setPassword(jdbcPassword());
        // 初始化连接大小。
        dataSource.setInitialSize(0);
        // 连接池最大活动连接数量。
        dataSource.setMaxActive(dataSourceMaxActive());
        // 连接池最小空闲连接数量。
        dataSource.setMinIdle(dataSourceMinIdle());
        // 连接最大等待时间。
        dataSource.setMaxWait(60000);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒。
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        // 配置一个连接在池中最小生存的时间，单位是毫秒。
        dataSource.setMinEvictableIdleTimeMillis(25200000);
        // 打开removeAbandoned功能。
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(1800);
        // 关闭 abandoned 连接时输出错误日志。
        dataSource.setLogAbandoned(true);
        dataSource.setFilters("stat");
        dataSource.setProxyFilters(proxyFilters);

        return dataSource;
    }

    @Bean("hibernateBridge.logFilter")
    public Slf4jLogFilter logFilter() {
        Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
        slf4jLogFilter.setStatementExecutableSqlLogEnable(false);
        return slf4jLogFilter;
    }

    @Bean("hibernateBridge.sessionFactory")
    public LocalSessionFactoryBean sessionFactory(
            @Qualifier("hibernateBridge.dataSource") DruidDataSource dataSource
    ) {
        // 创建 Hibernate 配置属性。
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", hibernateDialect());
        // 格式化SQL语句。
        hibernateProperties.setProperty("hibernate.format_sql", "true");
        // 自动建表。
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty("hibernate.connection.autocommit", "false");
        // 打印生成的SQL语句。
        hibernateProperties.setProperty("hibernate.show_sql", "false");
        // 禁用二级缓存。
        hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", "false");
        // 数据库的批量写入量，设置激进的值以提高数据库的写入效率。
        hibernateProperties.setProperty("hibernate.jdbc.batch_size", String.valueOf(hibernateJdbcBatchSize()));
        // 数据库的批量抓取量，设置激进的值以提高数据库的读取效率。
        hibernateProperties.setProperty("hibernate.jdbc.fetch_size", String.valueOf(hibernateJdbcFetchSize()));

        // 创建会话工厂。
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setHibernateProperties(hibernateProperties);
        sessionFactoryBean.setPackagesToScan("com.dwarfeng.statistics.impl.handler.bridge.hibernate.bean");
        return sessionFactoryBean;
    }

    @Bean("hibernateBridge.hibernateTemplate")
    public HibernateTemplate hibernateTemplate(
            @Qualifier("hibernateBridge.sessionFactory") SessionFactory sessionFactory
    ) {
        HibernateTemplate hibernateTemplate = new HibernateTemplate();
        hibernateTemplate.setSessionFactory(sessionFactory);
        return hibernateTemplate;
    }

    @Bean("hibernateBridge.hibernateTransactionManager")
    public HibernateTransactionManager hibernateTransactionManager(
            @Qualifier("hibernateBridge.sessionFactory") SessionFactory sessionFactory
    ) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    private String jdbcDriver() {
        return useProjectConfig ? projectJdbcDriver : bridgeJdbcDriver;
    }

    private String jdbcUrl() {
        return useProjectConfig ? projectJdbcUrl : bridgeJdbcUrl;
    }

    private String jdbcUsername() {
        return useProjectConfig ? projectJdbcUsername : bridgeJdbcUsername;
    }

    private String jdbcPassword() {
        return useProjectConfig ? projectJdbcPassword : bridgeJdbcPassword;
    }

    private String hibernateDialect() {
        return useProjectConfig ? projectHibernateDialect : bridgeHibernateDialect;
    }

    private int hibernateJdbcBatchSize() {
        return useProjectConfig ? projectHibernateJdbcBatchSize : bridgeHibernateJdbcBatchSize;
    }

    private int hibernateJdbcFetchSize() {
        return useProjectConfig ? projectHibernateJdbcFetchSize : bridgeHibernateJdbcFetchSize;
    }

    private int dataSourceMaxActive() {
        return useProjectConfig ? projectDataSourceMaxActive : bridgeDataSourceMaxActive;
    }

    private int dataSourceMinIdle() {
        return useProjectConfig ? projectDataSourceMinIdle : bridgeDataSourceMinIdle;
    }
}
