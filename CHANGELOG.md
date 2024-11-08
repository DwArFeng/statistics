# ChangeLog

### Release_1.2.0_20241108_build_A

#### 功能构建

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

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.1.2_20241108_build_A

#### 功能构建

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

#### Bug修复

- 修复部分配置文件中的错误。
  - statistics/bridge.properties。

#### 功能移除

- (无)

---

### Release_1.1.1_20241104_build_A

#### 功能构建

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

#### Bug修复

- 文档注释错误修复。
  - 修正部分类中的文档注释 `@since` 标签中的错误。
  - 修正本征过滤器中部分类的文档注释。

#### 功能移除

- (无)

---

### Release_1.1.0_20241103_build_A

#### 功能构建

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

#### Bug修复

- 修复包扫描 bug。
  - 修复程序启动时，无视 `opt-filter.xml` 文件中的配置，而是扫描所有过滤器的 bug。

#### 功能移除

- (无)

---

### Release_1.0.0_20241102_build_A

#### 功能构建

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

#### Bug修复

- (无)

#### 功能移除

- (无)
