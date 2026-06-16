# Quick Start - 快速开始

## 确认系统需求

- CPU：2 核以上。
- 内存：4G 以上。
- 硬盘：100G 以上。
- CentOS 7。
- JRE 1.8。
- MySQL 8.0.19。
- Redis 5.0.7。
- Zookeeper 3.5.5。
- snowflake-distributed-service 1.8.3.a。

## 获取软件包

从 Github 上获取软件包，软件包可以从 Github 的 Release 页面下载。

## 解压软件包

软件包的名称格式为 `statistics-all-he-${version}-release.tar.gz`，其中 `${version}` 为软件包的版本号。

使用工具软件，将软件包上传至服务器 `/usr/local` 目录下，解压软件包。

```shell
cd /usr/local
tar -zxvf statistics-all-he-${version}-release.tar.gz
mv statistics-all-he-${version} statistics
```

## 数据库初始化

连接到 MySQL 数据库，执行如下 SQL 语句：

```sql
# noinspection SpellCheckingInspectionForFile
-- QuickStart 最小链路初始化脚本（FixedDelay Driver -> Mock Provider -> Identity Filter -> Hibernate Bridge）
-- 适用数据库：MySQL 8+
-- 说明：
-- 1. 本脚本用于在空库或新库中构建最小可跑通链路。
-- 2. 若与现有数据并存，请先评估主键冲突风险。

CREATE DATABASE IF NOT EXISTS `statistics` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_bin;
USE `statistics`;

-- -----------------------------------------------------
-- 基础表结构（最小子集）
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tbl_statistics_setting`
(
    `id`                bigint      NOT NULL,
    `enabled`           bit(1)      NOT NULL,
    `name`              varchar(50) NOT NULL,
    `remark`            varchar(200) DEFAULT NULL,
    `description`       varchar(200) DEFAULT NULL,
    `created_datamark`  varchar(100) DEFAULT NULL,
    `modified_datamark` varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_bin;

CREATE TABLE IF NOT EXISTS `tbl_driver_info`
(
    `id`                    bigint NOT NULL,
    `enabled`               bit(1) NOT NULL,
    `param`                 text,
    `remark`                varchar(200) DEFAULT NULL,
    `statistics_setting_id` bigint       DEFAULT NULL,
    `type`                  varchar(50)  DEFAULT NULL,
    `created_datamark`      varchar(100) DEFAULT NULL,
    `modified_datamark`     varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK2w8b8bnem34tf81b0njlatq92` (`statistics_setting_id`),
    CONSTRAINT `FK2w8b8bnem34tf81b0njlatq92` FOREIGN KEY (`statistics_setting_id`) REFERENCES `tbl_statistics_setting` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_bin;

CREATE TABLE IF NOT EXISTS `tbl_provider_info`
(
    `id`                    bigint NOT NULL,
    `enabled`               bit(1) NOT NULL,
    `param`                 text,
    `remark`                varchar(200) DEFAULT NULL,
    `statistics_setting_id` bigint       DEFAULT NULL,
    `type`                  varchar(50)  DEFAULT NULL,
    `created_datamark`      varchar(100) DEFAULT NULL,
    `modified_datamark`     varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKsw3ejkdilx61guywqt98f9n7v` (`statistics_setting_id`),
    CONSTRAINT `FKsw3ejkdilx61guywqt98f9n7v` FOREIGN KEY (`statistics_setting_id`) REFERENCES `tbl_statistics_setting` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_bin;

CREATE TABLE IF NOT EXISTS `tbl_filter_info`
(
    `id`                    bigint NOT NULL,
    `enabled`               bit(1) NOT NULL,
    `column_index`          int    NOT NULL,
    `param`                 text,
    `remark`                varchar(200) DEFAULT NULL,
    `statistics_setting_id` bigint       DEFAULT NULL,
    `type`                  varchar(50)  DEFAULT NULL,
    `created_datamark`      varchar(100) DEFAULT NULL,
    `modified_datamark`     varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKfmlxejx9p4h8b571ctourj1q8` (`statistics_setting_id`),
    CONSTRAINT `FKfmlxejx9p4h8b571ctourj1q8` FOREIGN KEY (`statistics_setting_id`) REFERENCES `tbl_statistics_setting` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_bin;

CREATE TABLE IF NOT EXISTS `tbl_tag_definition`
(
    `statistics_setting_id` bigint       NOT NULL,
    `tag_definition_id`     varchar(100) NOT NULL,
    `remark`                varchar(200) DEFAULT NULL,
    `created_datamark`      varchar(100) DEFAULT NULL,
    `modified_datamark`     varchar(100) DEFAULT NULL,
    `description`           varchar(200) DEFAULT NULL,
    PRIMARY KEY (`statistics_setting_id`, `tag_definition_id`),
    CONSTRAINT `FKfnq0jw9hqud7jukw8crcgmy36` FOREIGN KEY (`statistics_setting_id`) REFERENCES `tbl_statistics_setting` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_bin;

CREATE TABLE IF NOT EXISTS `tbl_variable`
(
    `statistics_setting_id` bigint       NOT NULL,
    `variable_id`           varchar(100) NOT NULL,
    `boolean_value`         bit(1)       DEFAULT NULL,
    `date_value`            datetime(6)  DEFAULT NULL,
    `double_value`          double       DEFAULT NULL,
    `long_value`            bigint       DEFAULT NULL,
    `string_value`          text,
    `value_type`            int          NOT NULL,
    `created_datamark`      varchar(100) DEFAULT NULL,
    `modified_datamark`     varchar(100) DEFAULT NULL,
    PRIMARY KEY (`statistics_setting_id`, `variable_id`),
    CONSTRAINT `FKslmgcn2e9tyl6qx30pwj6pcpe` FOREIGN KEY (`statistics_setting_id`) REFERENCES `tbl_statistics_setting` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_bin;

CREATE TABLE IF NOT EXISTS `tbl_bridge_data`
(
    `id`                         bigint       NOT NULL,
    `happened_date`              datetime(6)  NOT NULL,
    `statistics_setting_long_id` bigint       NOT NULL,
    `tag`                        varchar(100) NOT NULL,
    `value`                      text         NOT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_statistics_setting_long_id_tag_happened_date` (`statistics_setting_long_id`, `tag`, `happened_date`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_bin;

-- -----------------------------------------------------
-- 最小链路数据（1 个统计设置，1 个驱动器，1 个提供器，1 个过滤器，1 个标签定义）
-- -----------------------------------------------------
INSERT INTO `tbl_statistics_setting` (`id`, `enabled`, `name`, `remark`, `description`, `created_datamark`,
                                      `modified_datamark`)
VALUES (10001, b'1', 'QuickStart 统计设置', 'QuickStart 最小链路', 'QuickStart 最小链路', NULL, NULL)
ON DUPLICATE KEY UPDATE `enabled`     = VALUES(`enabled`),
                        `name`        = VALUES(`name`),
                        `remark`      = VALUES(`remark`),
                        `description` = VALUES(`description`);

INSERT INTO `tbl_driver_info` (`id`, `enabled`, `param`, `remark`, `statistics_setting_id`, `type`,
                               `created_datamark`, `modified_datamark`)
VALUES (10001, b'1', '10000', 'QuickStart 最小链路', 10001, 'fixed_delay_driver', NULL, NULL)
ON DUPLICATE KEY UPDATE `enabled`               = VALUES(`enabled`),
                        `param`                 = VALUES(`param`),
                        `remark`                = VALUES(`remark`),
                        `statistics_setting_id` = VALUES(`statistics_setting_id`),
                        `type`                  = VALUES(`type`);

INSERT INTO `tbl_provider_info` (`id`, `enabled`, `param`, `remark`, `statistics_setting_id`, `type`,
                                 `created_datamark`, `modified_datamark`)
VALUES (10001, b'1',
        '{"random_seed":12450,"data_size":10,"data_type":"int_string","last_provided_date_variable_id":"last_provided_date","tag":"tag","delay":0}',
        'QuickStart 最小链路', 10001, 'mock_provider', NULL, NULL)
ON DUPLICATE KEY UPDATE `enabled`               = VALUES(`enabled`),
                        `param`                 = VALUES(`param`),
                        `remark`                = VALUES(`remark`),
                        `statistics_setting_id` = VALUES(`statistics_setting_id`),
                        `type`                  = VALUES(`type`);

INSERT INTO `tbl_filter_info` (`id`, `enabled`, `column_index`, `param`, `remark`, `statistics_setting_id`, `type`,
                               `created_datamark`, `modified_datamark`)
VALUES (10001, b'1', 0, '', 'QuickStart 最小链路', 10001, 'identity_filter', NULL, NULL)
ON DUPLICATE KEY UPDATE `enabled`               = VALUES(`enabled`),
                        `column_index`          = VALUES(`column_index`),
                        `param`                 = VALUES(`param`),
                        `remark`                = VALUES(`remark`),
                        `statistics_setting_id` = VALUES(`statistics_setting_id`),
                        `type`                  = VALUES(`type`);

INSERT INTO `tbl_tag_definition` (`statistics_setting_id`, `tag_definition_id`, `remark`, `created_datamark`,
                                  `modified_datamark`, `description`)
VALUES (10001, 'tag', 'QuickStart 最小链路', NULL, NULL, 'QuickStart 最小链路')
ON DUPLICATE KEY UPDATE `remark`      = VALUES(`remark`),
                        `description` = VALUES(`description`);

INSERT INTO `tbl_variable` (`statistics_setting_id`, `variable_id`, `boolean_value`, `date_value`, `double_value`,
                            `long_value`, `string_value`, `value_type`, `created_datamark`, `modified_datamark`)
VALUES (10001, 'last_provided_date', NULL, NOW(6), NULL, NULL, NULL, 4, NULL, NULL)
ON DUPLICATE KEY UPDATE `date_value` = VALUES(`date_value`),
                        `value_type` = VALUES(`value_type`);
```

说明：

- 上述 SQL 用于构建 QuickStart 最小链路（FixedDelay Driver -> Mock Provider -> Identity Filter -> Hibernate Bridge）。
- 若使用的是全新数据库，程序启动后 Hibernate 仍会根据实体自动补齐其它业务表（`hibernate.hbm2ddl.auto=update`）。

## 最小化配置

下文列出了启动程序需要改动的最少配置文件，每个配置文件中仅展示需要改动的配置项。

`conf/curator/connection.properties` 文件中配置 curator 连接信息。

```properties
com.dwarfeng.statistics.curator.connect.connect_string=your-host-here:2181
```

`conf/database/connection.properties` 文件中配置数据库连接信息。

```properties
com.dwarfeng.statistics.jdbc.url=jdbc:mysql://your-host-here:3306/statistics?serverTimezone=Asia/Shanghai&autoReconnect=true
com.dwarfeng.statistics.jdbc.username=root
com.dwarfeng.statistics.jdbc.password=your-password-here
```

`conf/dubbo/connection.properties` 文件中配置 dubbo 连接信息。

```properties
com.dwarfeng.statistics.dubbo.registry.zookeeper.address=zookeeper://your-host-here:2181
com.dwarfeng.statistics.dubbo.protocol.dubbo.host=your-host-here
```

`conf/redis/connection.properties` 文件中配置 redis 连接信息。

```properties
com.dwarfeng.statistics.redis.hostName=your-host-here
com.dwarfeng.statistics.redis.port=6379
com.dwarfeng.statistics.redis.password=your-password-here
```

`conf/statistics/bridge.properties` 文件中配置如下桥接项，使程序将统计结果写入数据库。

```properties
com.dwarfeng.statistics.bridge.keeper.type=drain
com.dwarfeng.statistics.bridge.persister.type=hibernate
com.dwarfeng.statistics.bridge.hibernate.use_project_config=true
```

`conf/statistics/dispatch.properties` 文件中配置如下调度器类型。

```properties
com.dwarfeng.statistics.dispatcher.type=injvm
```

`conf/statistics/receive.properties` 文件中配置如下接收器类型。

```properties
com.dwarfeng.statistics.receiver.type=injvm
```

`conf/statistics/push.properties` 文件中配置如下推送器类型。

```properties
com.dwarfeng.statistics.pusher.type=log
```

`conf/statistics/launcher.properties` 文件中配置如下启动项，使程序启动后自动进入统计链路。

```properties
com.dwarfeng.statistics.launcher.start_receive_delay=4000
com.dwarfeng.statistics.launcher.online_supervise_delay=4500
com.dwarfeng.statistics.launcher.enable_supervise_delay=5000
```

## 修改可选配置

下文列出了启动程序需要改动的可选的配置文件，每个配置文件中仅展示需要改动的配置项。

`opt/opt-driver.xml` 驱动器可选配置。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- ↓ 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<!-- ↑ 以上注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringFacetInspection, XmlUnusedNamespaceDeclaration -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 扫描 handler 的实现包。 -->
    <context:component-scan base-package="com.dwarfeng.statistics.impl.handler.driver" use-default-filters="false">
        <!-- 加载 FixedDelayDriver -->
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.driver.FixedDelayDriverProvider"
        />
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.driver.FixedDelayDriverSupporter"
        />
    </context:component-scan>
</beans>
```

`opt/opt-provider.xml` 提供器可选配置。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- ↓ 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<!-- ↑ 以上注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringFacetInspection, XmlUnusedNamespaceDeclaration -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 加载 MockProvider -->
    <context:component-scan base-package="com.dwarfeng.statistics.impl.handler.provider.mock"/>
</beans>
```

`opt/opt-filter.xml` 过滤器可选配置。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- ↓ 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<!-- ↑ 以上注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringFacetInspection, XmlUnusedNamespaceDeclaration -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 加载 IdentityFilter -->
    <context:component-scan base-package="com.dwarfeng.statistics.impl.handler.filter.identity"/>
</beans>
```

`opt/opt-bridge.xml` 桥接器可选配置。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- ↓ 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<!-- ↑ 以上注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringFacetInspection, XmlUnusedNamespaceDeclaration -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 加载 DrainBridge -->
    <context:component-scan base-package="com.dwarfeng.statistics.impl.handler.bridge.drain"/>

    <!-- 加载 HibernateBridge -->
    <context:component-scan base-package="com.dwarfeng.statistics.impl.handler.bridge.hibernate"/>
</beans>
```

`opt/opt-dispatcher.xml` 调度器可选配置。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- ↓ 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<!-- ↑ 以上注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringFacetInspection, XmlUnusedNamespaceDeclaration -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 扫描 handler 的实现包。 -->
    <context:component-scan
            base-package="com.dwarfeng.statistics.impl.handler.dispatcher" use-default-filters="false"
    >
        <!-- 加载 InjvmDispatcher -->
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.dispatcher.InjvmDispatcher"
        />
    </context:component-scan>
</beans>
```

`opt/opt-receiver.xml` 接收器可选配置。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- ↓ 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<!-- ↑ 以上注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringFacetInspection, XmlUnusedNamespaceDeclaration -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 扫描 handler 的实现包。 -->
    <context:component-scan
            base-package="com.dwarfeng.statistics.impl.handler.receiver" use-default-filters="false"
    >
        <!-- 加载 InjvmReceiver -->
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.receiver.InjvmReceiver"
        />
    </context:component-scan>
</beans>
```

`opt/opt-pusher.xml` 推送器可选配置。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- ↓ 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<!-- ↑ 以上注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringFacetInspection, XmlUnusedNamespaceDeclaration -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 扫描 handler 的实现包。 -->
    <context:component-scan base-package="com.dwarfeng.statistics.impl.handler.pusher" use-default-filters="false">
        <!-- 加载 LogPusher -->
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.pusher.LogPusher"
        />
    </context:component-scan>
</beans>
```

## 启动程序

在 `/usr/local/statistics` 目录下执行如下命令：

```shell
sh bin/statistics-start.sh
```

1. 观察数据库，数据库将会自动生成 `tbl_` 前缀的表，并具有部分数据。
2. 观察 Redis，Redis 将会自动生成 `com.dwarfeng.statistics.entity.` 前缀的缓存键，并具有部分数据。
3. 观察日志，日志中持续出现 LogPusher 的推送日志。
4. 在数据库中可查询到已写入的统计结果数据（`tbl_bridge_data`）。

## 停止程序

在 `/usr/local/statistics` 目录下执行如下命令：

```shell
sh bin/statistics-stop.sh
```
