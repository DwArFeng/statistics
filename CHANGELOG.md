# ChangeLog

## Release_2.0.0_20260610_build_A

### 功能构建

- 部分代理类实现中的字段类型提升为对应的接口，与具体实现解耦。
  - com.dwarfeng.statistics.impl.cache.DriverInfoCacheImpl。
  - com.dwarfeng.statistics.impl.cache.DriverSupportCacheImpl。
  - com.dwarfeng.statistics.impl.cache.FilterInfoCacheImpl。
  - com.dwarfeng.statistics.impl.cache.FilterSupportCacheImpl。
  - com.dwarfeng.statistics.impl.cache.HistoryTaskCacheImpl。
  - com.dwarfeng.statistics.impl.cache.HistoryTaskEventCacheImpl。
  - com.dwarfeng.statistics.impl.cache.MapperSupportCacheImpl。
  - com.dwarfeng.statistics.impl.cache.ProviderInfoCacheImpl。
  - com.dwarfeng.statistics.impl.cache.ProviderSupportCacheImpl。
  - com.dwarfeng.statistics.impl.cache.StatisticsExecutionProfileCacheImpl。
  - com.dwarfeng.statistics.impl.cache.StatisticsSettingCacheImpl。
  - com.dwarfeng.statistics.impl.cache.TagDefinitionCacheImpl。
  - com.dwarfeng.statistics.impl.cache.TaskCacheImpl。
  - com.dwarfeng.statistics.impl.cache.TaskEventCacheImpl。
  - com.dwarfeng.statistics.impl.cache.VariableCacheImpl。
  - com.dwarfeng.statistics.impl.dao.DriverInfoDaoImpl。
  - com.dwarfeng.statistics.impl.dao.DriverSupportDaoImpl。
  - com.dwarfeng.statistics.impl.dao.FilterInfoDaoImpl。
  - com.dwarfeng.statistics.impl.dao.FilterSupportDaoImpl。
  - com.dwarfeng.statistics.impl.dao.HistoryTaskDaoImpl。
  - com.dwarfeng.statistics.impl.dao.HistoryTaskEventDaoImpl。
  - com.dwarfeng.statistics.impl.dao.MapperSupportDaoImpl。
  - com.dwarfeng.statistics.impl.dao.ProviderInfoDaoImpl。
  - com.dwarfeng.statistics.impl.dao.ProviderSupportDaoImpl。
  - com.dwarfeng.statistics.impl.dao.StatisticsExecutionProfileDaoImpl。
  - com.dwarfeng.statistics.impl.dao.StatisticsSettingDaoImpl。
  - com.dwarfeng.statistics.impl.dao.TagDefinitionDaoImpl。
  - com.dwarfeng.statistics.impl.dao.TaskDaoImpl。
  - com.dwarfeng.statistics.impl.dao.TaskEventDaoImpl。
  - com.dwarfeng.statistics.impl.dao.VariableDaoImpl。
  - com.dwarfeng.statistics.impl.service.DriverInfoMaintainServiceImpl。
  - com.dwarfeng.statistics.impl.service.DriverSupportMaintainServiceImpl。
  - com.dwarfeng.statistics.impl.service.FilterInfoMaintainServiceImpl。
  - com.dwarfeng.statistics.impl.service.FilterSupportMaintainServiceImpl。
  - com.dwarfeng.statistics.impl.service.HistoryTaskEventMaintainServiceImpl。
  - com.dwarfeng.statistics.impl.service.HistoryTaskMaintainServiceImpl。
  - com.dwarfeng.statistics.impl.service.MapperSupportMaintainServiceImpl。
  - com.dwarfeng.statistics.impl.service.ProviderInfoMaintainServiceImpl。
  - com.dwarfeng.statistics.impl.service.ProviderSupportMaintainServiceImpl。
  - com.dwarfeng.statistics.impl.service.StatisticsExecutionProfileMaintainServiceImpl。
  - com.dwarfeng.statistics.impl.service.StatisticsSettingMaintainServiceImpl。
  - com.dwarfeng.statistics.impl.service.TagDefinitionMaintainServiceImpl。
  - com.dwarfeng.statistics.impl.service.TaskEventMaintainServiceImpl。
  - com.dwarfeng.statistics.impl.service.TaskMaintainServiceImpl。
  - com.dwarfeng.statistics.impl.service.VariableMaintainServiceImpl。

- 优化项目的异常处理机制。
  - `statistics-sdk` 子模块新增 `ServiceExceptionHelper` 工具类，统一维护项目自身的异常映射关系。
  - `statistics-impl` 子模块 `ServiceExceptionMapperConfiguration` 配置类的异常映射处理逻辑优化。
  - `statistics-node` 子模块 `ServiceExceptionMapperConfiguration` 配置类的异常映射处理逻辑优化。

- 依赖升级。
  - 升级 `spring-telqos` 依赖版本为 `2.0.2.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `spring-terminator` 依赖版本为 `2.0.2.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `dwarfeng-datamark` 依赖版本为 `2.2.0.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `dcti` 依赖版本为 `3.0.1.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `dwarfeng-dct` 依赖版本为 `3.0.2.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `snowflake` 依赖版本为 `2.0.2.a` 以规避漏洞。

- 优化文件格式。
  - 优化 `assembly.xml` 文件的格式。
  - 优化 `*.properties` 文件的格式。
  - 优化 `opt-*.xml` 文件的格式。
  - 优化 `application-context-*.xml` 文件的格式。
  - 优化 `pom.xml` 文件的格式。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## 更早的版本

[View all changelogs](./changelogs)
