# ChangeLog

### Release_1.0.0_20240918_build_A

#### 功能构建

- 实现预设调度器。
  - com.dwarfeng.statistics.impl.handler.dispatcher.DrainDispatcher。

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

- 实现核心机制。
  - 驱动机制。
  - 调度机制。

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

#### Bug修复

- (无)

#### 功能移除

- (无)
