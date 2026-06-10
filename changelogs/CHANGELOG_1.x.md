# ChangeLog

## Release_1.5.1_20260604_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh-CN/OptDirectory.md。

- 新增 WebInput Bean。
  - com.dwarfeng.statistics.sdk.bean.key.WebInputTagDefinitionKey。
  - com.dwarfeng.statistics.sdk.bean.entity.WebInputTagDefinition。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.8.3.a` 以规避漏洞。

### Bug 修复

- 修复部分 Hibernate 实体中的字段问题。
  - com.dwarfeng.statistics.impl.bean.entity.HibernateTagDefinition。

### 功能移除

- (无)

---

## Release_1.5.0_20260512_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh-CN/CompileBySource.md。

- `statistics-impl` 子模块类优化注释、文档注释格式、代码换行格式。
  - com.dwarfeng.statistics.impl.bean.BeanMapper。
  - com.dwarfeng.statistics.impl.bean.entity.HibernateDriverInfo。
  - com.dwarfeng.statistics.impl.bean.entity.HibernateDriverSupport。
  - com.dwarfeng.statistics.impl.bean.entity.HibernateFilterInfo。
  - com.dwarfeng.statistics.impl.bean.entity.HibernateFilterSupport。
  - com.dwarfeng.statistics.impl.bean.entity.HibernateHistoryTask。
  - com.dwarfeng.statistics.impl.bean.entity.HibernateHistoryTaskEvent。
  - com.dwarfeng.statistics.impl.bean.entity.HibernateMapperSupport。
  - com.dwarfeng.statistics.impl.bean.entity.HibernateProviderInfo。
  - com.dwarfeng.statistics.impl.bean.entity.HibernateProviderSupport。
  - com.dwarfeng.statistics.impl.bean.entity.HibernateStatisticsExecutionProfile。
  - com.dwarfeng.statistics.impl.bean.entity.HibernateStatisticsSetting。
  - com.dwarfeng.statistics.impl.bean.entity.HibernateTagDefinition。
  - com.dwarfeng.statistics.impl.bean.entity.HibernateTask。
  - com.dwarfeng.statistics.impl.bean.entity.HibernateTaskEvent。
  - com.dwarfeng.statistics.impl.bean.entity.HibernateVariable。

- `statistics-sdk` 子模块类优化注释、文档注释格式、代码换行格式。
  - com.dwarfeng.statistics.sdk.bean.BeanMapper。

- `statistics-stack` 子模块类优化注释、文档注释格式、代码换行格式。
  - com.dwarfeng.statistics.stack.service.DriverSupportMaintainService。
  - com.dwarfeng.statistics.stack.service.FilterSupportMaintainService。
  - com.dwarfeng.statistics.stack.service.MapperSupportMaintainService。
  - com.dwarfeng.statistics.stack.service.ProviderSupportMaintainService。

- 依赖升级。
  - 升级 `jedis` 依赖版本为 `3.10.0` 以规避漏洞。
  - 升级 `spring-data-redis` 依赖版本为 `2.7.18` 以规避漏洞。
  - 升级 `kafka` 依赖版本为 `3.9.2` 以规避漏洞。
  - 升级 `dubbo` 依赖版本为 `2.7.23` 以规避漏洞。
  - 升级 `zookeeper` 依赖版本为 `3.9.5` 以规避漏洞。
  - 升级 `log4j2` 依赖版本为 `2.25.4` 以规避漏洞。
  - 升级 `mapstruct` 依赖版本为 `1.5.5.Final` 以规避漏洞。
  - 升级 `dutil` 依赖版本为 `0.4.2.a-beta` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.9.0.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.8.2.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.1.0.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.2.0.a` 以规避漏洞。
  - 升级 `dcti` 依赖版本为 `2.0.1.a` 以规避漏洞。
  - 升级 `dwarfeng-dct` 依赖版本为 `2.0.1.a` 以规避漏洞。
  - 升级 `jackson` 依赖版本为 `2.21.2` 以规避漏洞。
  - 升级 `groovy` 依赖版本为 `4.0.31` 以规避漏洞。
  - 升级 `dwarfeng-datamark` 依赖版本为 `1.1.3.a` 以规避漏洞。

- 优化文件格式。
  - 优化 `application-context-*.xml` 文件的格式。

- 优化开发环境支持。
  - 在 .gitignore 中添加 Vibe Coding 相关文件的忽略规则。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.4.0_20260409_build_A

### 功能构建

- Wiki 更新。
  - docs/wiki/zh-CN/ConfDirectory.md。
  - docs/wiki/zh-CN/BatchScripts.md。

- 启停脚本优化。
  - binres/statistics-start.bat。
  - binres/statistics-start.sh。

- 导入运维指令。
  - com.dwarfeng.datamark.service.telqos.*。

- 增加 Hibernate 实体数据标记字段，并应用相关实体侦听器。
  - com.dwarfeng.statistics.impl.bean.entity.HibernateDriverInfo。
  - com.dwarfeng.statistics.impl.bean.entity.HibernateFilterInfo。
  - com.dwarfeng.statistics.impl.bean.entity.HibernateProviderInfo。
  - com.dwarfeng.statistics.impl.bean.entity.HibernateStatisticsSetting。
  - com.dwarfeng.statistics.impl.bean.entity.HibernateTagDefinition。

- 增加依赖。
  - 增加依赖 `dwarfeng-datamark` 以应用其新功能，版本为 `1.0.5.a`。

- 优化部分类的代码结构。
  - com.dwarfeng.statistics.node.launcher.Launcher。

- 建立实体以及维护服务，并通过单元测试。
  - com.dwarfeng.statistics.stack.bean.entity.TagDefinition。

- 更改实体字段。
  - com.dwarfeng.statistics.stack.bean.entity.Task.message。
  - com.dwarfeng.statistics.stack.bean.entity.HistoryTask.message。
  - com.dwarfeng.statistics.stack.bean.dto.TaskUpdateModalInfo.message。

- 添加实体字段。
  - com.dwarfeng.statistics.stack.bean.entity.StatisticsSetting.description。
  - com.dwarfeng.statistics.stack.bean.dto.TaskUpdateModalInfo.messageUpdateFlag。

- SPI 目录结构优化。
  - 将桥接机制的 SPI 接口与抽象类提相关代码文件提升至 `sdk` 模块中。
  - 将调度机制的 SPI 抽象类提相关代码文件提升至 `sdk` 模块中。
  - 将驱动机制的 SPI 抽象类提相关代码文件提升至 `sdk` 模块中。
  - 将过滤机制的 SPI 接口与抽象类提相关代码文件提升至 `sdk` 模块中。
  - 将映射机制的 SPI 接口与抽象类提相关代码文件提升至 `sdk` 模块中。
  - 将提供机制的 SPI 接口与抽象类提相关代码文件提升至 `sdk` 模块中。
  - 将推送机制的 SPI 接口与抽象类提相关代码文件提升至 `sdk` 模块中。
  - 将接收机制的 SPI 抽象类提相关代码文件提升至 `sdk` 模块中。
  - 将重置机制的 SPI 抽象类提相关代码文件提升至 `sdk` 模块中。

- `statistics-impl` 子模块类优化注释、文档注释格式、代码换行格式。
  - com.dwarfeng.statistics.impl.handler.receiver.KafkaReceiver。

- 优化文件格式。
  - 优化 `application-context-*.xml` 文件的格式。

### Bug 修复

- 修复 `assembly.xml` 中的配置错误。

### 功能移除

- 去除 `executionNodeld` 机制。
  - com.dwarfeng.statistics.stack.bean.entity.Task.executionNodeld。
  - com.dwarfeng.statistics.stack.bean.entity.TaskHistory.executionNodeld。

- 删除实体字段。
  - com.dwarfeng.statistics.stack.bean.entity.Task.remark。
  - com.dwarfeng.statistics.stack.bean.entity.TaskEvent.remark。
  - com.dwarfeng.statistics.stack.bean.entity.HistoryTask.remark。
  - com.dwarfeng.statistics.stack.bean.entity.HistoryTaskEvent.remark。

---

## Release_1.3.1_20260125_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh-CN/VersionBlacklist.md。
  - docs/wiki/zh-CN/ConfDirectory.md。
  - docs/wiki/zh-CN/SystemRequirements.md。

- 依赖优化。
  - 优化部分依赖的排除项，以避免潜在的 `netty` 版本冲突问题。

- 优化部分单元测试代码，以规避潜在的 bug。
  - com.dwarfeng.statistics.impl.service.DriverInfoMaintainServiceImplTest。
  - com.dwarfeng.statistics.impl.service.DriverSupportMaintainServiceImplTest。
  - com.dwarfeng.statistics.impl.service.FilterInfoMaintainServiceImplTest。
  - com.dwarfeng.statistics.impl.service.FilterSupportMaintainServiceImplTest。
  - com.dwarfeng.statistics.impl.service.HistoryTaskEventMaintainServiceImplTest。
  - com.dwarfeng.statistics.impl.service.HistoryTaskMaintainServiceImplTest。
  - com.dwarfeng.statistics.impl.service.MapperSupportMaintainServiceImplTest。
  - com.dwarfeng.statistics.impl.service.ProviderInfoMaintainServiceImplTest。
  - com.dwarfeng.statistics.impl.service.ProviderSupportMaintainServiceImplTest。
  - com.dwarfeng.statistics.impl.service.StatisticsExecutionProfileMaintainServiceImplTest。
  - com.dwarfeng.statistics.impl.service.StatisticsSettingMaintainServiceImplTest。
  - com.dwarfeng.statistics.impl.service.TaskEventMaintainServiceImplTest。
  - com.dwarfeng.statistics.impl.service.TaskMaintainServiceImplTest。
  - com.dwarfeng.statistics.impl.service.VariableMaintainServiceImplTest。

- 为部分工具类中方法的入口参数增加 `@Nonnull` 注解。
  - com.dwarfeng.statistics.impl.service.telqos.CommandUtil。

- 增加预设的运维指令。
  - com.dwarfeng.springtelqos.api.integration.system.UptimeCommand。
  - com.dwarfeng.springtelqos.api.integration.system.JmxRemoteCommand。

- `statistics-impl` 子模块部分类优化字段、方法参数命名。
  - com.dwarfeng.statistics.impl.handler.SuperviseHandlerImpl。
  - com.dwarfeng.statistics.impl.handler.TaskCheckHandlerImpl。

- 部分工具类应用私有构造器方法，防止被实例化。
  - com.dwarfeng.statistics.impl.handler.bridge.influxdb.util.Constants。

- 优化部分类的日志输出内容。
  - com.dwarfeng.statistics.impl.handler.SuperviseHandlerImpl。

- 优化部分类构造器方法中的参数名。
  - com.dwarfeng.statistics.impl.handler.pusher.AbstractPusher。

- 依赖升级。
  - 升级 `kafka` 依赖版本为 `3.9.1` 以规避漏洞。
  - 升级 `spring-kafka` 依赖版本为 `2.9.13` 以规避漏洞。
  - 升级 `netty` 依赖版本为 `4.2.9.final` 以规避漏洞。
  - 升级 `log4j2` 依赖版本为 `2.25.3` 以规避漏洞。
  - 升级 `dutil` 依赖版本为 `0.4.0.a-beta` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.7.3.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.6.2.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.15.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.16.a` 以规避漏洞。
  - 升级 `dcti` 依赖版本为 `1.1.14.a` 以规避漏洞。
  - 升级 `dwarfeng-dct` 依赖版本为 `1.0.5.a` 以规避漏洞。

- 优化部分说明文件中的格式。
  - libext/README.md。
  - optext/README.md。

- `statistics-node` 子模块类优化注释、文档注释格式、代码换行格式。
  - com.dwarfeng.statistics.node.handler.ValueScanHandler。

- `statistics-impl` 子模块类优化注释、文档注释格式、代码换行格式。
  - com.dwarfeng.statistics.impl.handler.bridge.hibernate.configuration.HibernateBridgeBaseConfiguration。
  - com.dwarfeng.statistics.impl.handler.bridge.hibernate.dao.nativelookup.HibernateBridgeMysql8BridgeDataNativeLookup。
  - com.dwarfeng.statistics.impl.handler.bridge.influxdb.handler.InfluxdbBridgeDataHandler。
  - com.dwarfeng.statistics.impl.handler.bridge.mock.MockBridgePersister。
  - com.dwarfeng.statistics.impl.handler.dispatcher.KafkaDispatcher。
  - com.dwarfeng.statistics.impl.handler.driver.DctiKafkaDriverProvider。
  - com.dwarfeng.statistics.impl.handler.filter.groovy.GroovyFilterRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.EnableRatioMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.GroovyMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.HighPassCounterMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.HighPassExistenceMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.HighPassMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.LowPassCounterMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.LowPassExistenceMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.LowPassMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.ToBooleanMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.TrimMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.provider.groovy.GroovyProviderRegistry。
  - com.dwarfeng.statistics.impl.handler.pusher.NativeKafkaPusher。
  - com.dwarfeng.statistics.impl.handler.receiver.KafkaReceiver。
  - com.dwarfeng.statistics.impl.service.telqos.ViewCommand。

- `statistics-sdk` 子模块类优化注释、文档注释格式、代码换行格式。
  - com.dwarfeng.statistics.sdk.bean.dto.JSFixedFastJsonLookupInfo。
  - com.dwarfeng.statistics.sdk.bean.dto.JSFixedFastJsonLookupResult。
  - com.dwarfeng.statistics.sdk.bean.dto.JSFixedFastJsonNativeQueryInfo。
  - com.dwarfeng.statistics.sdk.bean.dto.JSFixedFastJsonQueryInfo。
  - com.dwarfeng.statistics.sdk.bean.dto.JSFixedFastJsonQueryResult。

- 优化文件格式。
  - 优化 `opt-*.xml` 文件的格式。
  - 优化 `*.properties` 文件的格式。
  - 优化 `application-context-*.xml` 文件的格式。
  - 优化 `pom.xml` 文件的格式。

- 优化开发环境支持。
  - 在 .gitignore 中添加 VSCode 相关文件的忽略规则。
  - 在 .gitignore 中添加 Cursor IDE 相关文件的忽略规则。

### Bug 修复

- 补全 `statistics-sdk` 模块 BeanMapper 中缺失的接口方法。

- `statistics-impl` 子模块部分 DTO 错误字段修复。
  - com.dwarfeng.statistics.sdk.bean.dto.FastJsonQueryInfo。
  - com.dwarfeng.statistics.sdk.bean.dto.JSFixedFastJsonQueryInfo。

- 修复 `opt-*.xml` 文件中的错误配置。
  - opt/opt-pusher.xml。

- 修复任务检查处理器对于死亡任务处理中存在的 bug。

- 修复程序启动时不能正确解析 `opt\opt-*.xml` 的 bug。

- 修复部分异常未配置服务异常映射的 bug。
  - com.dwarfeng.statistics.stack.exception.UnsupportedFilterTypeException。。
  - com.dwarfeng.statistics.stack.exception.MapperException。
  - com.dwarfeng.statistics.stack.exception.MapperExecutionException。
  - com.dwarfeng.statistics.stack.exception.MapperMakeException。
  - com.dwarfeng.statistics.stack.exception.UnsupportedMapperTypeException。

### 功能移除

- (无)

---

## Release_1.3.0_20251124_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh-CN/ConfDirectory.md。

- 可执行制品目录结构优化。
  - 增加 `docs` 目录，以提供项目的文档和指南。

- 优化 `docs/wiki` 目录结构。
  - 将 `docs/wiki/en_US` 目录重命名为 `en-US`，以符合 rfc5646 规范。
  - 将 `docs/wiki/zh_CN` 目录重命名为 `zh-CN`，以符合 rfc5646 规范。
  - 更新 `docs/wiki/README.md` 中的链接指向。
  - 更新 `README.md` 中的链接指向。

- 实现运维指令。
  - com.dwarfeng.statistics.impl.service.telqos.SupportCommand。

- 优化支持实体机制。
  - 新建支持 QoS 服务 com.dwarfeng.statistics.stack.service.SupportQosService。
  - 将支持实体维护服务的重置功能迁移至 QoS 服务。

- 日志结构升级。
  - 修改 logging 目录下配置文件名称。

- 修改 `dubbo:application` 的 `name` 属性。

- 优化实体映射器机制。

- 依赖升级。
  - 升级 `spring` 依赖版本为 `5.3.39` 以规避漏洞。
  - 升级 `kafka` 依赖版本为 `3.9.0` 以规避漏洞。
  - 升级 `netty` 依赖版本为 `4.1.119.Final` 以规避漏洞。
  - 升级 `zookeeper` 依赖版本为 `3.9.4` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.6.0.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.14.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.14.a` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.7.2.a` 以规避漏洞。
  - 升级 `jackson` 依赖版本为 `2.18.3` 以规避漏洞。
  - 升级 `groovy` 依赖版本为 `4.0.26` 以规避漏洞。
  - 升级 `dcti` 依赖版本为 `1.1.13.a` 以规避漏洞。
  - 升级 `dwarfeng-dct` 依赖版本为 `1.0.4.a` 以规避漏洞。

- 启停脚本优化。
  - 优化 Windows 系统的启动脚本。
  - 优化 Linux 系统的启停脚本。

### Bug 修复

- (无)

### 功能移除

- 去除支持实体维护服务的重置功能。
  - com.dwarfeng.statistics.stack.service.DriverSupportMaintainService。
  - com.dwarfeng.statistics.stack.service.FilterSupportMaintainService。
  - com.dwarfeng.statistics.stack.service.MapperSupportMaintainService。
  - com.dwarfeng.statistics.stack.service.ProviderSupportMaintainService。

---

## Release_1.2.1_20251114_build_A

### 功能构建

- 新增 BeanMapper

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.2.0_20241109_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh_CN/ShellScripts.md。
  - docs/wiki/zh_CN/BatchScripts.md。

- 增加部分提供器的功能。
  - com.dwarfeng.statistics.impl.handler.provider.mock.MockProviderRegistry。

- Wiki 更新。
  - docs/wiki/zh_CN/images/SystemArchitecture.png。

- 实现运维指令。
  - com.dwarfeng.statistics.impl.service.telqos.ConsumeCommand。

- 实现核心机制。
  - 消费机制。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.1.2_20241108_build_A

### 功能构建

- 优化项目的启停脚本，以规避潜在的路径问题。
  - binres/statistics-stop.sh。
  - binres/statistics-start.sh。

- Wiki 编写。
  - docs/wiki/zh_CN/Contents.md。

- 优化部分类中的方法签名。
  - com.dwarfeng.statistics.stack.handler.ExecuteHandler。

- 优化部分类中的日志文案。
  - com.dwarfeng.statistics.impl.handler.driver.CronDriverProvider。
  - com.dwarfeng.statistics.impl.handler.driver.DctiKafkaDriverProvider。
  - com.dwarfeng.statistics.impl.handler.driver.FixedDelayDriverProvider。
  - com.dwarfeng.statistics.impl.handler.driver.FixedRateDriverProvider。

### Bug 修复

- 修复部分配置文件中的错误。
  - statistics/bridge.properties。

### 功能移除

- (无)

---

## Release_1.1.1_20241104_build_A

### 功能构建

- 实现预设提供器。
  - com.dwarfeng.statistics.impl.handler.provider.groovy.GroovyProviderRegistry。

- 实现预设过滤器。
  - com.dwarfeng.statistics.impl.handler.filter.groovy.GroovyFilterRegistry。

- Wiki 编写。
  - docs/wiki/zh_CN/Contents.md。
  - docs/wiki/zh_CN/Introduction.md。
  - docs/wiki/zh_CN/README.md。
  - docs/wiki/en_US/Contents.md。
  - docs/wiki/en_US/Introduction.md。
  - docs/wiki/en_US/README.md。

- `README.md` 更新。

### Bug 修复

- 文档注释错误修复。
  - 修正部分类中的文档注释 `@since` 标签中的错误。
  - 修正本征过滤器中部分类的文档注释。

### 功能移除

- (无)

---

## Release_1.1.0_20241103_build_A

### 功能构建

- 依赖升级。
  - 升级 `protobuf` 依赖版本为 `3.25.5` 以规避漏洞。

- 实现预设推送器。
  - com.dwarfeng.statistics.impl.handler.pusher.DrainPusher。
  - com.dwarfeng.statistics.impl.handler.pusher.LogPusher。
  - com.dwarfeng.statistics.impl.handler.pusher.MultiPusher。
  - com.dwarfeng.statistics.impl.handler.pusher.NativeKafkaPusher。

- 实现运维指令。
  - com.dwarfeng.statistics.impl.service.telqos.ResetCommand。
  - com.dwarfeng.statistics.impl.service.telqos.TaskCheckCommand。

- 实现核心机制。
  - 重置机制。
  - 任务检查机制。
  - 推送机制。

- 实现预设过滤器。
  - com.dwarfeng.statistics.impl.handler.filter.identity.IdentityFilterRegistry。

- 优化统计功能。
  - 增加统计数据的标签功能。

### Bug 修复

- 修复包扫描 bug。
  - 修复程序启动时，无视 `opt-filter.xml` 文件中的配置，而是扫描所有过滤器的 bug。

### 功能移除

- (无)

---

## Release_1.0.0_20241102_build_A

### 功能构建

- 实现预设提供器。
  - com.dwarfeng.statistics.impl.handler.provider.mock.MockProviderRegistry。

- 实现预设映射器。
  - com.dwarfeng.statistics.impl.handler.mapper.WindowMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.HighPassCounterMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.OneToManyMapper。
  - com.dwarfeng.statistics.impl.handler.mapper.LowPassMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.HighPassMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.TrimMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.MergeMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.AlignMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.FirstMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.IdentityMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.GroovyMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.TimeWeightedAgvMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.LastMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.CountMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.AvgMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.LowPassExistenceMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.ToBooleanMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.LowPassCounterMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.HighPassExistenceMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.ToDoubleMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.OneToOneMapper。
  - com.dwarfeng.statistics.impl.handler.mapper.AggregateMapper。
  - com.dwarfeng.statistics.impl.handler.mapper.SortMapperRegistry。
  - com.dwarfeng.statistics.impl.handler.mapper.EnableRatioMapperRegistry。

- 实现预设桥接器。
  - com.dwarfeng.statistics.impl.handler.bridge.drain.DrainBridge。
  - com.dwarfeng.statistics.impl.handler.bridge.hibernate.HibernateBridge。
  - com.dwarfeng.statistics.impl.handler.bridge.influxdb.InfluxdbBridge。
  - com.dwarfeng.statistics.impl.handler.bridge.mock.MockBridge。
  - com.dwarfeng.statistics.impl.handler.bridge.multi.MultiBridge。
  - com.dwarfeng.statistics.impl.handler.bridge.redis.RedisBridge。

- 实现预设接收器。
  - com.dwarfeng.statistics.impl.handler.receiver.DoNothingReceiver。
  - com.dwarfeng.statistics.impl.handler.receiver.InjvmReceiver。
  - com.dwarfeng.statistics.impl.handler.receiver.KafkaReceiver。
  - com.dwarfeng.statistics.impl.handler.receiver.DubboReceiver。

- 实现预设调度器。
  - com.dwarfeng.statistics.impl.handler.dispatcher.DrainDispatcher。
  - com.dwarfeng.statistics.impl.handler.dispatcher.InjvmDispatcher。
  - com.dwarfeng.statistics.impl.handler.dispatcher.KafkaDispatcher。
  - com.dwarfeng.statistics.impl.handler.dispatcher.DubboDispatcher。

- 实现预设驱动器。
  - com.dwarfeng.statistics.impl.handler.driver.CronDriverProvider。
  - com.dwarfeng.statistics.impl.handler.driver.CronDriverSupporter。
  - com.dwarfeng.statistics.impl.handler.driver.DctiKafkaDriverProvider。
  - com.dwarfeng.statistics.impl.handler.driver.DctiKafkaDriverSupporter。
  - com.dwarfeng.statistics.impl.handler.driver.FixedDelayDriverProvider。
  - com.dwarfeng.statistics.impl.handler.driver.FixedDelayDriverSupporter。
  - com.dwarfeng.statistics.impl.handler.driver.FixedRateDriverProvider。
  - com.dwarfeng.statistics.impl.handler.driver.FixedRateDriverSupporter。

- 实现运维指令。
  - com.dwarfeng.statistics.impl.service.telqos.DriveCommand。
  - com.dwarfeng.statistics.impl.service.telqos.DriveLocalCacheCommand。
  - com.dwarfeng.statistics.impl.service.telqos.DispatchCommand。
  - com.dwarfeng.statistics.impl.service.telqos.DispatcherCommand。
  - com.dwarfeng.statistics.impl.service.telqos.SuperviseCommand。
  - com.dwarfeng.statistics.impl.service.telqos.ReceiverCommand。
  - com.dwarfeng.statistics.impl.service.telqos.ReceiveCommand。
  - com.dwarfeng.statistics.impl.service.telqos.ExecuteLocalCacheCommand。
  - com.dwarfeng.statistics.impl.service.telqos.MapLocalCacheCommand。
  - com.dwarfeng.statistics.impl.service.telqos.ViewCommand。

- 实现核心机制。
  - 驱动机制。
  - 调度机制。
  - 主管机制。
  - 接收机制。
  - 桥接机制。
  - 提供机制。
  - 过滤机制。
  - 映射机制。
  - 查询机制。
  - 观察机制。

- 定义预设服务。
  - com.dwarfeng.statistics.impl.service.EnabledDriverInfoLookupServiceImpl。
  - com.dwarfeng.statistics.impl.service.EnabledFilterInfoLookupServiceImpl。
  - com.dwarfeng.statistics.impl.service.EnabledProviderInfoLookupServiceImpl。

- 为支持维护服务提供重置方法。
  - com.dwarfeng.statistics.stack.service.DriverSupportMaintainService。
  - com.dwarfeng.statistics.stack.service.FilterSupportMaintainService。
  - com.dwarfeng.statistics.stack.service.MapperSupportMaintainService。
  - com.dwarfeng.statistics.stack.service.ProviderSupportMaintainService。

- 完成 node 模块，打包测试及启动测试通过。

- 定义实体及其维护服务，并通过单元测试。
  - com.dwarfeng.statistics.stack.service.DriverInfoMaintainService。
  - com.dwarfeng.statistics.stack.service.DriverSupportMaintainService。
  - com.dwarfeng.statistics.stack.service.FilterInfoMaintainService。
  - com.dwarfeng.statistics.stack.service.FilterSupportMaintainService。
  - com.dwarfeng.statistics.stack.service.HistoryTaskEventMaintainService。
  - com.dwarfeng.statistics.stack.service.HistoryTaskMaintainService。
  - com.dwarfeng.statistics.stack.service.MapperSupportMaintainService。
  - com.dwarfeng.statistics.stack.service.ProviderInfoMaintainService。
  - com.dwarfeng.statistics.stack.service.ProviderSupportMaintainService。
  - com.dwarfeng.statistics.stack.service.StatisticsExecutionProfileMaintainService。
  - com.dwarfeng.statistics.stack.service.StatisticsSettingMaintainService。
  - com.dwarfeng.statistics.stack.service.TaskEventMaintainService。
  - com.dwarfeng.statistics.stack.service.TaskMaintainService。
  - com.dwarfeng.statistics.stack.service.VariableMaintainService。

- 项目结构建立，清理测试通过。

### Bug 修复

- (无)

### 功能移除

- (无)
