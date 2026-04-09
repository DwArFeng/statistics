# ConfDirectory - 配置目录

## 总览

本项目的配置文件位于 `conf/` 目录下，包括：

```text
conf
│
├─curator
│      connection.properties
│      latch-path.properties
│
├─database
│      connection.properties
│      performance.properties
│
├─datamark
│      settings.properties
│
├─dubbo
│      connection.properties
│
├─logging
│      README.md
│      settings.xml
│      settings-ref-linux.xml
│      settings-ref-windows.xml
│
├─redis
│      connection.properties
│      prefix.properties
│      timeout.properties
│
├─statistics
│      background.properties
│      bridge.properties
│      consume.properties
│      dispatch.properties
│      driver.properties
│      exception.properties
│      launcher.properties
│      push.properties
│      query.properties
│      receive.properties
│      reset.properties
│      task.properties
│
└─telqos
       connection.properties
```

鉴于大部分配置文件的配置项中都有详细地注释，此处将展示默认的配置，并重点说明一些必须要修改的配置项，
省略的部分将会使用 `etc...` 进行标注。

## curator 目录

| 文件名                   | 说明            |
|-----------------------|---------------|
| connection.properties | Curator 连接配置  |
| latch-path.properties | Curator 互斥锁路径 |

### connection.properties

Curator 连接配置。

```properties
# 连接字符，即 zookeeper 地址。
curator.connect.connect_string=your-host-here:2181
# 会话超时时间。
curator.connect.session_timeout=60000
# 连接超时时间。
curator.connect.connection_timeout=15000
# 第一次重试时的间隔时间，每重试一次，间隔时间都会指数增加，直到最大的间隔时间。
curator.retry_policy.base_sleep_time=1000
# 最大重试次数。
curator.retry_policy.max_retries=10
# 单次重试最大的间隔时间。
curator.retry_policy.max_sleep=60000
```

Curator 连接配置文件，包括 Zookeeper 连接地址，超时时间，重试策略。

### latch-path.properties

Curator 互斥锁路径。

```properties
# 主管服务的领导者锁存的路径。
curator.latch_path.supervise.leader_latch=/statistics/supervise/leader_latch
# 任务检查服务的领导者锁存的路径。
curator.latch_path.task_check.leader_latch=/statistics/task_check/leader_latch
```

如果您在本机上部署了多个项目，每个项目中都使用本服务，那么需要为每个项目配置不同的互斥锁路径，以避免项目之间不必要的互斥。

## database 目录

| 文件名                    | 说明        |
|------------------------|-----------|
| connection.properties  | 数据库连接配置文件 |
| performance.properties | 数据库性能配置文件 |

### connection.properties

数据库连接配置文件，除了标准的数据库配置四要素之外，还包括 Hibernate 的方言配置。

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://your-host-here:3306/statistics?serverTimezone=Asia/Shanghai&autoReconnect=true
jdbc.username=root
jdbc.password=your-password-here
hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### performance.properties

数据库性能配置文件，使用默认值即可，或按照实际情况进行修改。

```properties
# 数据库的批量写入量，设置激进的值以提高数据库的写入效率。
hibernate.jdbc.batch_size=100
# 数据库的批量抓取量，设置激进的值以提高数据库的读取效率。
hibernate.jdbc.fetch_size=50
# 连接池最大活动连接数量
data_source.max_active=20
# 连接池最小空闲连接数量
data_source.min_idle=0
```

## datamark 目录

| 文件名                 | 说明        |
|---------------------|-----------|
| settings.properties | 数据标记的配置文件 |

### settings.properties

数据标记的配置文件。

数据标记是本项目的一个运维与安全机制，它使用 `dwarfeng-datamark` 实现，其主要的功能是在重要数据插入/更改时，
向数据库特定的数据标记字段写入特定值，
这个特定值被记录在 `dwarfeng-datamark` 中的 `resource` 中 - 可以是 spring 框架支持的任何资源类型，
支持运行时修改，并对前端完全不可见。

运维人员可以用这个机制降低运维的工作量 - 尤其是从测试环境向正式环境迁移数据时，也可以用这个机制进行数据非法篡改的检测与取证。

```properties
#---------------------------------配置说明----------------------------------------
# 数据标记资源的 URL，格式参考 Spring 资源路径。
# datamark.xxx.resource_url=classpath:datamark/default.storage
# 数据标记资源的字符集。
# datamark.xxx.resource_charset=UTF-8
# 数据标记服务是否允许更新。
# datamark.xxx.update_allowed=true
#
#---------------------------------StatisticsSetting-------------------------------------
# etc...
```

## dubbo 目录

| 文件名                   | 说明           |
|-----------------------|--------------|
| connection.properties | Dubbo 连接配置文件 |

### connection.properties

Dubbo 连接配置文件。

```properties
dubbo.registry.zookeeper.address=zookeeper://your-host-here:2181
dubbo.registry.zookeeper.timeout=3000
dubbo.protocol.dubbo.port=20000
dubbo.protocol.dubbo.host=your-host-here
dubbo.provider.group=
dubbo.consumer.snowflake.group=
```

其中，`dubbo.registry.zookeeper.address` 需要配置为 ZooKeeper 的地址，
`dubbo.protocol.dubbo.host` 需要配置为本机的 IP 地址。

如果您需要在本机启动多个 Statistics 实例，那么需要为每个实例配置不同的 `dubbo.protocol.dubbo.port`。

如果您在本机上部署了多个项目，每个项目中都使用了 Statistics，那么需要为每个项目配置不同的 `dubbo.provider.group`，
以避免微服务错误的调用。

## logging 目录

| 文件名                      | 说明                     |
|--------------------------|------------------------|
| README.md                | 说明文件                   |
| settings.xml             | 日志配置的配置文件              |
| settings-ref-linux.xml   | Linux 系统中日志配置的配置参考文件   |
| settings-ref-windows.xml | Windows 系统中日志配置的配置参考文件 |

### settings.xml

日志配置及其参考文件。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
    <properties>
        <!--############################################### Console ###############################################-->
        <!-- 控制台输出文本的编码 -->
        <property name="console.encoding">UTF-8</property>
        <!-- 控制台输出的日志级别 -->
        <property name="console.level">INFO</property>
        <!--############################################# Rolling file ############################################-->
        <!-- 滚动文件的目录 -->
        <property name="rolling_file.dir">logs</property>
        <!-- 滚动文件的编码 -->
        <property name="rolling_file.encoding">UTF-8</property>
        <!-- 滚动文件的触发间隔（小时） -->
        <property name="rolling_file.triggering.interval">1</property>
        <!-- 滚动文件的触发大小 -->
        <property name="rolling_file.triggering.size">40MB</property>
        <!-- 滚动文件的最大数量 -->
        <property name="rolling_file.rollover.max">100</property>
        <!-- 滚动文件的删除时间 -->
        <property name="rolling_file.rollover.delete_age">7D</property>
    </properties>

    <Appenders>
        <!-- etc... -->
    </Appenders>

    <Loggers>
        <!-- etc... -->
    </Loggers>
</Configuration>
```

需要注意的是，日志配置 **必须** 定义在 `settings.xml` 中才能生效，所有的 `settings-ref-xxx.xml` 都是参考文件，
在这些文件中进行任何配置的修改 **均不会生效**。

常用的做法是，针对不同的操作系统，将参考文件中的内容直接复制到 `settings.xml` 中，随后对 `settings.xml` 中的内容进行修改。

- 如果服务运行一天产生的日志超过了配置上限，可上调 `rolling_file.rollover.max` 参数。
- 如果存在等保需求，日志至少需要保留 6 个月，需要调整 `rolling_file.rollover.delete_age` 参数至 `200D`。

## redis 目录

| 文件名                   | 说明   |
|-----------------------|------|
| connection.properties | 连接配置 |
| prefix.properties     | 前缀配置 |
| timeout.properties    | 超时配置 |

### connection.properties

Redis 连接配置文件。

```properties
# ip 地址。
redis.hostName=your-host-here
# 端口号。
redis.port=6379
# 如果有密码。
redis.password=your-password-here
# 客户端超时时间单位是毫秒 默认是 2000。
redis.timeout=10000
# 最大空闲数。
redis.maxIdle=300
# 连接池的最大数据库连接数。设为 0 表示无限制，如果是 jedis 2.4 以后用 redis.maxTotal。
# redis.maxActive=600
# 控制一个 pool 可分配多少个 jedis 实例，用来替换上面的 redis.maxActive，如果是 jedis 2.4 以后用该属性。
redis.maxTotal=1000
# 最大建立连接等待时间。如果超过此时间将接到异常。设为-1 表示无限制。
redis.maxWaitMillis=1000
# 连接的最小空闲时间 默认 1800000 毫秒(30 分钟)。
redis.minEvictableIdleTimeMillis=300000
# 每次释放连接的最大数目，默认 3。
redis.numTestsPerEvictionRun=1024
# 逐出扫描的时间间隔(毫秒) 如果为负数，则不运行逐出线程， 默认 -1。
redis.timeBetweenEvictionRunsMillis=30000
# 是否在从池中取出连接前进行检验，如果检验失败，则从池中去除连接并尝试取出另一个。
redis.testOnBorrow=true
# 在空闲时检查有效性， 默认 false。
redis.testWhileIdle=true
# redis.sentinel.host1=172.20.1.230
# redis.sentinel.port1=26379
# redis.sentinel.host2=172.20.1.231
# redis.sentinel.port2=26379
# redis.sentinel.host3=172.20.1.232
# redis.sentinel.port3=26379
```

### prefix.properties

Redis 前缀配置文件。

```properties
#------------------------------------------------------------------------------------
#  缓存时实体的键的格式
#------------------------------------------------------------------------------------
# 统计设置对象的主键格式。
cache.prefix.entity.statistics_setting=entity.statistics_setting.
# 统计执行简报对象的主键格式。
cache.prefix.entity.statistics_execution_profile=entity.statistics_execution_profile.
# 变量对象的主键格式。
cache.prefix.entity.variable=entity.variable.
# 驱动器信息对象的主键格式。
cache.prefix.entity.driver_info=entity.driver_info.
# 过滤器信息对象的主键格式。
cache.prefix.entity.filter_info=entity.filter_info.
# 提供器信息对象的主键格式。
cache.prefix.entity.provider_info=entity.provider_info.
# 驱动器支持对象的主键格式。
cache.prefix.entity.driver_support=entity.driver_support.
# 过滤器支持对象的主键格式。
cache.prefix.entity.filter_support=entity.filter_support.
# 提供器支持对象的主键格式。
cache.prefix.entity.provider_support=entity.provider_support.
# 映射器支持对象的主键格式。
cache.prefix.entity.mapper_support=entity.mapper_support.
# 任务对象的主键格式。
cache.prefix.entity.task=entity.task.
# 任务事件对象的主键格式。
cache.prefix.entity.task_event=entity.task_event.
# 历史任务对象的主键格式。
cache.prefix.entity.history_task=entity.history_task.
# 历史任务事件对象的主键格式。
cache.prefix.entity.history_task_event=entity.history_task_event.
#------------------------------------------------------------------------------------
#  缓存时列表的键的格式
#------------------------------------------------------------------------------------
# 部件对象对应的使能执行器信息列表的主键格式。
cache.prefix.list.enabled_driver_info=list.enabled_driver_info.
# 部件对象对应的使能执行器信息列表的主键格式。
cache.prefix.list.enabled_provider_info=list.enabled_provider_info.
# 部件对象对应的使能执行器信息列表的主键格式。
cache.prefix.list.enabled_filter_info=list.enabled_filter_info.
```

Redis 利用该配置文件，为缓存的主键添加前缀，以示区分。

如果您的项目包含其它使用 Redis 的模块，您可以修改该配置文件，以避免不同项目的同名实体前缀冲突，相互覆盖。

一个典型的前缀更改方式是在前缀的头部添加项目的名称，如：

```properties
# 统计设置对象的主键格式。
cache.prefix.entity.statistics_setting=statistics.entity.statistics_setting.
# etc...
```

### timeout.properties

Redis 缓存的超时配置文件。

```properties
#------------------------------------------------------------------------------------
#  实体缓存时的超时时间
#------------------------------------------------------------------------------------
# 统计设置对象缓存的超时时间。
cache.timeout.entity.statistics_setting=3600000
# 统计执行简报对象缓存的超时时间。
cache.timeout.entity.statistics_execution_profile=3600000
# 变量对象缓存的超时时间。
cache.timeout.entity.variable=3600000
# 驱动器信息对象缓存的超时时间。
cache.timeout.entity.driver_info=3600000
# 过滤器信息对象缓存的超时时间。
cache.timeout.entity.filter_info=3600000
# 提供器信息对象缓存的超时时间。
cache.timeout.entity.provider_info=3600000
# 驱动器支持对象缓存的超时时间。
cache.timeout.entity.driver_support=3600000
# 过滤器支持对象缓存的超时时间。
cache.timeout.entity.filter_support=3600000
# 提供器支持对象缓存的超时时间。
cache.timeout.entity.provider_support=3600000
# 映射器支持对象缓存的超时时间。
cache.timeout.entity.mapper_support=3600000
# 任务对象缓存的超时时间。
cache.timeout.entity.task=3600000
# 任务事件对象缓存的超时时间。
cache.timeout.entity.task_event=3600000
# 历史任务对象缓存的超时时间。
cache.timeout.entity.history_task=3600000
# 历史任务事件对象缓存的超时时间。
cache.timeout.entity.history_task_event=3600000
#------------------------------------------------------------------------------------
#  键值列表缓存时的超时时间
#------------------------------------------------------------------------------------
# 使能驱动器信息的超时时间。
cache.timeout.key_list.enabled_driver_info=3600000
# 使能提供器信息的超时时间。
cache.timeout.key_list.enabled_provider_info=3600000
# 使能过滤器信息的超时时间。
cache.timeout.key_list.enabled_filter_info=3600000
```

如果您希望缓存更快或更慢地过期，您可以修改该配置文件。

## statistics 目录

| 文件名                   | 说明                           |
|-----------------------|------------------------------|
| background.properties | 后台服务配置文件，包括线程池的线程数及其它        |
| bridge.properties     | 桥接服务配置文件                     |
| consume.properties    | 消费服务配置文件                     |
| dispatch.properties   | 调度服务配置文件                     |
| driver.properties     | 驱动服务配置文件                     |
| exception.properties  | ServiceException 的异常代码的偏移量配置 |
| launcher.properties   | 启动器配置文件                      |
| push.properties       | 推送服务配置文件                     |
| query.properties      | 查询服务配置文件                     |
| receive.properties    | 接收服务配置文件                     |
| reset.properties      | 重置服务配置文件                     |
| task.properties       | 任务配置文件                       |

### background.properties

后台服务配置文件，包括线程池的线程数及其它。

```properties
# 任务执行器的线程池数量范围。
executor.pool_size=20-40
# 任务执行器的队列容量。
executor.queue_capacity=100
# 任务执行器的保活时间（秒）。
executor.keep_alive=120
# 计划执行器的线程池数量范围。
scheduler.pool_size=10
```

### bridge.properties

桥接服务配置文件，核心配置之一。

```properties
###################################################
#                     global                      #
###################################################
# 桥接器的类型。
# 目前支持的类型有:
#   drain: 简单地丢弃所有数据，通常用作占位符（如不想保留某类型的数据，则可以在对应类型上配置 drain）。支持保持器和持久器。
#
# 可以为数据、被过滤数据、被触发数据分别指定保持器类型。
# 对于一个具体的项目，很可能只需要一种保持器。此时如果希望程序加载时只加载这一种保持器，可以通过编辑
# opt/opt-bridge.xml 文件实现。
bridge.keeper.type=drain
bridge.persister.type=drain
#
###################################################
#                      drain                      #
###################################################
# Drain 桥接器不需要进行任何配置。
#
###################################################
#                      mock                       #
###################################################
# Mock 桥接器的随机种子。
bridge.mock.random_seed=0
# etc...
#
###################################################
#                    hibernate                    #
###################################################
# 是否使用项目本身的 hibernate 配置。
bridge.hibernate.use_project_config=true
# etc...
#
###################################################
#                      redis                      #
###################################################
# 是否使用项目本身的 redis 配置。
bridge.redis.use_project_config=true
# etc...
#
###################################################
#                    influxdb                     #
###################################################
# influxdb 的 URL。
bridge.influxdb.url=http://your-host-here:8086
# etc...
#
###################################################
#                      multi                      #
###################################################
# 多重桥接器本身不直接实现数据的桥接方法，而是通过代理的方式实现。数据写入多重桥接器时，
# 会调用对应的代理列表，依次调用写入方法。数据从多重桥接器中读取时，会使用列表中的第一个代理，作为首选代理，调用读取方法。
# 多重桥接器提供的保持器/持久器是否为只写保持器/持久器取决于首选代理对应的保持器/持久器是否为只写保持器/持久器。
# 以下配置均为代理列表配置，所有的配置说明如下:
#   1. 每个代理类型之间用逗号隔开。如果代理类型文本中包含逗号，则使用反斜杠转义。
#   2. 第一个代理类型为首选代理类型。
# ------------------------------
# 数据保持器的代理列表。
bridge.multi.delegates.keep=xxx1,xxx2,xxx3
# 数据持久器的代理列表。
bridge.multi.delegates.persist=xxx1,xxx2,xxx3
```

您不必对所有的配置项进行配置。

在项目第一次启动之前，您需要修改 `opt/opt-bridge.xml`，决定项目中需要使用哪些桥接器。您只需要修改使用的桥接器的配置。

该文档档中省略了各个桥接器的具体配置，您可以参考 `conf/statistics/bridge.properties` 中的完整配置，每个配置项都有详细的注释。

### consume.properties

消费服务配置文件，核心配置之一。

```properties
# 当消费者中的待消费元素超过缓存上限指定比例后，向日志中输入警告信息。
consume.threshold.warn=0.8
# 消费者线程数：线程数越大，处理的能力越强，服务器负荷越重。
consume.consumer_thread=1
# 缓存大小：缓存越大抗波动能力越强，数据实时性越低。
# 当缓存被占满时，会导致消费者阻塞，此时如果 rpc 的超时设置不当，容易引起，数据重复提交或者丢失的问题。
# 数据占满缓存这一现象是需要尽力避免的，程序将在缓存占用量超过指定值的时候发出警报，并在缓存被占满的时候发出 ERROR 提示。
consume.buffer_size=1000
```

本配置中的参数直接决定了消费服务的性能，您需要根据您的实际情况进行调整。

为了更加直观的观察调整后的效果，本服务提供了关于消费服务的 telnet 指令，您可以使用指令在 telnet 运维系统中动态修改参数，
并观察修改后的效果。在运维系统中的更改重启后会失效，因此，当您将参数调整到满意的程度后，您需要将参数修改到配置文件中。

### dispatch.properties

调度服务配置文件。

```properties
###################################################
#                     global                      #
###################################################
# 当前的调度器类型。
# 目前该项目支持的调度器类型有:
#   drain: 丢弃掉所有调度请求（并记录日志）的调度器，用于测试。
#   injvm: 虚拟机内部调度器，用于单节点服务。
#   kafka: 基于 Kafka 实现的调度器，利用 Kafka 的消费者机制实现多个接收节点的负载均衡。
#   dubbo: 基于 Dubbo 实现的调度器，利用 Dubbo 的服务提供者机制实现多个调度节点的负载均衡。
#
# 对于一个具体的项目，很可能只用一个调度器。此时如果希望程序加载时只加载一个调度器，可以通过编辑
# opt/opt-dispatcher.xml 文件实现。
dispatcher.type=drain
#
###################################################
#                      drain                      #
###################################################
# drain 调度器没有任何配置。
#
###################################################
#                      injvm                      #
###################################################
# injvm 调度器没有任何配置。
#
###################################################
#                      kafka                      #
###################################################
# 引导服务器集群。
dispatcher.kafka.bootstrap_servers=your ip here like ip1:9092,ip2:9092,ip3:9092
# 生产者与服务器的确认模式，可选值为: 0, 1, all。
#   0: 生产者不会等待服务器的确认，继续发送下一条消息。
#   1: 生产者会等待服务器的确认，继续发送下一条消息。
#   all: 生产者会等待服务器及其所有副本的确认，继续发送下一条消息
dispatcher.kafka.acks=all
# 发送失败重试次数，acks 设置为 0 时不生效。
dispatcher.kafka.retries=3
# 生产者在发送批处理之前等待更多消息加入批处理的时间，单位为毫秒。该值是一条记录被发送之前的最大延迟。
# 如果记录在等待中达到了 batch_size 的大小，就会立即把它们发送出去。
dispatcher.kafka.linger=10
# 生产者的批处理缓冲区大小。
dispatcher.kafka.buffer_memory=40960
# 批处理条数: 当多个记录被发送到同一个分区时，生产者会尝试将记录合并到更少的请求中。这有助于客户端和服务端的性能。
# 如果记录在等待时间达到了 linger 值，就会被立即发送。
dispatcher.kafka.batch_size=4096
# Kafka 事务的前缀。
dispatcher.kafka.transaction_prefix=statistics.dispatcher.
# 负载均衡策略，可选值为: default, round_robin, random。
#   default: 默认的负载均衡策略。
#   round_robin: 轮询的负载均衡策略。
#   random: 随机的负载均衡策略。
dispatcher.kafka.load_balance_mode=default
# 检查分区的间隔时间，单位为毫秒。
dispatcher.kafka.partition_check_interval=30000
# 执行调度时向 Kafka 发送的主题。
dispatcher.kafka.topic.dispatch=statistics.dispatcher.dispatch
#
###################################################
#                      dubbo                      #
###################################################
# dubbo 调度器没有任何配置。
```

您不必对所有的配置项进行配置。

在项目第一次启动之前，您需要修改 `opt/opt-dispatcher.xml`，决定项目中需要使用哪些调度器。您只需要修改使用的调度器的配置。

### driver.properties

驱动服务配置文件。

```properties
###################################################
#                      cron                       #
###################################################
# Cron 驱动没有任何配置。
#
###################################################
#                   fixed_delay                   #
###################################################
# FixedDelay 驱动没有任何配置。
#
###################################################
#                    fixed_rate                   #
###################################################
# FixedRate 驱动没有任何配置。
#
###################################################
#                    kafka.dcti                   #
###################################################
# 引导服务器集群。
driver.kafka.dcti.bootstrap_servers=your-ip1:9092,your-ip2:9092,your-ip3:9092
# 会话的超时限制: 如果 consumer 在这段时间内没有发送心跳信息，一次 rebalance 将会产生。
# 该值必须在[group.min.session.timeout.ms, group.max.session.timeout.ms]范围内，默认: 10000。
driver.kafka.dcti.session_timeout_ms=10000
# 新的 group 加入 topic 时，从什么位置开始消费。
driver.kafka.dcti.auto_offset_reset=latest
# 监听器启用的消费者的线程数。
# 每一个线程都会启动一个 KafkaConsumer，每个 KafkaConsumer 都会占用一个 partition。
# 程序分布式部署时，所有节点的线程数之和应该小于等于 topic 的 partition 数。
driver.kafka.dcti.concurrency=2
# 监听器调用 KafkaConsumer.poll(Duration) 方法的超时时间，如果超过这个时间还没有拉取到数据，则返回空列表。
driver.kafka.dcti.poll_timeout=3000
# 监听器的 id，每一个节点的监听器 id 都应与该节点的其它 kafka 监听器的 id 不同。
# 该设置会覆盖 kafka 的 group.id 设置，因此无需设置 group.id。
driver.kafka.dcti.listener_id=statistics.driver.dcti
# 监听器的目标 topic。
driver.kafka.dcti.listener_topic=dcti.data_info
# 监听器的最大拉取数据量。当拉取到的数据量达到这个值时，会立即返回，不会等待 poll_timeout。
driver.kafka.dcti.max_poll_records=100
# 监听器的最大拉取间隔。如果当前时间距离监听器上一次拉取数据的时间超过了这个值，一次 rebalance 将会产生。
driver.kafka.dcti.max_poll_interval_ms=300000
```

### exception.properties

ServiceException 的异常代码的偏移量配置。

```properties
# statistics 工程自身的异常代号偏移量。
statistics.exception_code_offset=5000
# statistics 工程中 subgrade 的异常代号偏移量。
statistics.exception_code_offset.subgrade=0
# statistics 工程中 snowflake 的异常代号偏移量。
statistics.exception_code_offset.snowflake=1500
# statistics 工程中 dwarfeng_datamark 的异常代号偏移量。
statistics.exception_code_offset.dwarfeng_datamark=2500
```

Subgrade 框架中，会将微服务抛出的异常映射为 `ServiceException`，每个 `ServiceException` 都有一个异常代码，
用于标识异常的类型。

如果您的项目中使用了多个基于 Subgrade 框架的微服务，那么，您需要为每个微服务配置一个异常代码偏移量，
以免不同的微服务生成异常代码相同的 `ServiceException`。

### launcher.properties

启动器配置文件，决定了启动时的一些行为。

```properties
# 程序启动完成后，是否重置驱动器支持。
launcher.reset_driver_support=true
# 程序启动完成后，是否重置提供器支持。
launcher.reset_provider_support=true
# 程序启动完成后，是否重置过滤器支持。
launcher.reset_filter_support=true
# 程序启动完成后，是否重置映射器支持。
launcher.reset_mapper_support=true
#
# 程序启动完成后，启动消费的延时时间。
# 有些数据仓库以及消费处理器在启动后可能会需要一些时间进行自身的初始化，调整该参数以妥善的处理这些数据源和推送器。
# 该参数等于 0，意味着启动后立即启动消费服务。
# 该参数小于 0，意味着程序不主动启动消费服务，需要手动启动。
launcher.start_consume_delay=3000
#
# 程序启动完成后，启动接收的延时时间。
# 有些数据仓库以及接收处理器在启动后可能会需要一些时间进行自身的初始化，调整该参数以妥善的处理这些数据源和推送器。
# 该参数等于 0，意味着启动后立即启动接收服务。
# 该参数小于 0，意味着程序不主动启动接收服务，需要手动启动。
launcher.start_receive_delay=4000
#
# 程序启动完成后，上线主管的延时时间。
# 有些数据仓库以及主管处理器在启动后可能会需要一些时间进行自身的初始化，调整该参数以妥善的处理这些数据源和推送器。
# 该参数等于 0，意味着启动后立即上线主管服务。
# 该参数小于 0，意味着程序不主动上线主管服务，需要手动上线。
launcher.online_supervise_delay=4500
# 程序启动完成后，启动主管的延时时间。
# 有些数据仓库以及主管处理器在启动后可能会需要一些时间进行自身的初始化，调整该参数以妥善的处理这些数据源和推送器。
# 该参数等于 0，意味着启动后立即启动主管服务。
# 该参数小于 0，意味着程序不主动启动主管服务，需要手动启动。
launcher.enable_supervise_delay=5000
#
# 程序启动完成后，启动重置的延时时间。
# 有些数据仓库以及重置器在启动后可能会需要一些时间进行自身的初始化，调整该参数以妥善的处理这些数据源和推送器。
# 该参数等于 0，意味着启动后立即启动重置服务。
# 该参数小于 0，意味着程序不主动启动重置服务，需要手动启动。
launcher.start_reset_delay=30000
#
# 程序启动完成后，上线任务检查的延时时间。
# 有些数据仓库以及任务检查器在启动后可能会需要一些时间进行自身的初始化，调整该参数以妥善的处理这些数据源和推送器。
# 该参数等于 0，意味着启动后立即上线任务检查服务。
# 该参数小于 0，意味着程序不主动上线任务检查服务，需要手动上线。
launcher.online_task_check_delay=3000
# 程序启动完成后，启动任务检查的延时时间。
# 有些数据仓库以及任务检查器在启动后可能会需要一些时间进行自身的初始化，调整该参数以妥善的处理这些数据源和推送器。
# 该参数等于 0，意味着启动后立即启动任务检查服务。
# 该参数小于 0，意味着程序不主动启动任务检查服务，需要手动启动。
launcher.enable_task_check_delay=3500
```

该配置文件决定了服务被运行后，哪些功能将会自动被执行。

对于负载巨大场景，需要服务集群做读写分离，一部分服务在启动后自动执行数据业务并下线微服务（下线微服务当前还未自动化），
专注与业务的处理； 另一部分则不执行数据业务并上线微服务，专注于响应调用方。

自动执行是可选的配置功能，任何启动时没有自动执行的功能模块，均可以通过服务的 telqos 系统随时进行启用。

### push.properties

推送服务配置文件。

```properties
###################################################
#                     global                      #
###################################################
# 当前的推送器类型。
# 目前该项目支持的推送器类型有:
#   drain: 简单的丢弃掉所有消息的推送器。
#   multi: 同时将消息推送给所有代理的多重推送器。
#   kafka.native: 使用原生数据的基于 Kafka 消息队列的推送器。
#   log: 将消息输出到日志中的推送器。
#
# 对于一个具体的项目，很可能只用一个推送器。此时如果希望程序加载时只加载一个推送器，可以通过编辑
# opt/opt-push.xml 文件实现。
# 可以通过编辑 application-context-scan.xml 实现。
pusher.type=drain
#
###################################################
#                      drain                      #
###################################################
# drain 推送器没有任何配置。
#
###################################################
#                      multi                      #
###################################################
# 代理的推送器，推送器之间以逗号分隔。
pusher.multi.delegate_types=kafka.native
#
###################################################
#                   kafka.native                  #
###################################################
# 引导服务器集群。
pusher.kafka.native.bootstrap_servers=your-ip1:9092,your-ip2:9092,your-ip3:9092
# 连接属性。
pusher.kafka.native.acks=all
# 发送失败重试次数。
pusher.kafka.native.retries=3
pusher.kafka.native.linger=10
# 批处理缓冲区大小。
pusher.kafka.native.buffer_memory=40960
# 批处理条数：当多个主管被发送到同一个分区时，生产者会尝试将主管合并到更少的请求中。这有助于客户端和服务器的性能。
pusher.kafka.native.batch_size=4096
# Kafka 事务的前缀。
pusher.kafka.native.transaction_prefix=statistics.pusher.
# 主管功能重置时向 Kafka 发送的主题。
pusher.kafka.native.topic.supervise_reset=statistics.pusher.supervise_reset
# 执行功能重置时向 Kafka 发送的主题。
pusher.kafka.native.topic.execute_reset=statistics.pusher.execute_reset
# 任务完成时向 Kafka 发送消息的主题。
pusher.kafka.native.topic.task_finished=statistics.pusher.task_finished
# 任务失败时向 Kafka 发送消息的主题。
pusher.kafka.native.topic.task_failed=statistics.pusher.task_failed
# 任务过期时向 Kafka 发送消息的主题。
pusher.kafka.native.topic.task_expired=statistics.pusher.task_expired
# 任务死亡时向 Kafka 发送消息的主题。
pusher.kafka.native.topic.task_died=statistics.pusher.task_died
#
###################################################
#                       log                       #
###################################################
# 主管日志的等级，由低到高依次是 TRACE, DEBUG, INFO, WARN, ERROR。
pusher.log.log_level=INFO
```

您不必对所有的配置项进行配置。

在项目第一次启动之前，您需要修改 `opt/opt-push.xml`，决定项目中需要使用哪些推送器。您只需要修改使用的推送器的配置。

### query.properties

查询服务配置文件。

```properties
#--------------------------------------------查询参数配置说明--------------------------------------------
# 查询流程示意图。
# |startDate                     endDate'|startDate'                    endDate'|startDate'      endDate|
# |----------------period----------------|----------------period----------------|--------period---------|
# |-----page-----|-----page-----|--page--|-----page-----|-----page-----|--page--|-----page-----|--page--|
# 其中，period 使用不同的 startDate 和 endDate 对数据库做 between ，这样可以避免 offset 过大导致的性能问题。
# 对于每一个 period，使用分页控制数据的最大量，避免一个查看方法执行时间过长。
#
# 查询时每一个 period 的最大跨度。
# query.max_period_span=86400000
# 查询是每一页的最大数据量。
# query.max_page_size=1000
#
#--------------------------------------------查询参数配置--------------------------------------------
query.max_period_span=86400000
query.max_page_size=1000
```

本配置中的参数可以优化查询时的性能，避免服务请求持久器时一次性请求过多数据导致的性能问题。
您可以根据自身服务器性能调节该参数。

如果您的服务只使用了持久器的本地查询功能，您可以不必修改该配置。

### receive.properties

接收服务配置文件。

```properties
###################################################
#                     global                      #
###################################################
# 当前的接收器类型。
# 目前该项目支持的接收器类型有:
#   do_nothing: 什么也不做的接收器，用于测试。
#   injvm: 虚拟机内部接收器，用于单节点服务。
#   kafka: 基于 Kafka 实现的接收器，利用 Kafka 的消费者机制实现多个接收节点的负载均衡。
#   dubbo: 基于 Dubbo 实现的接收器，利用 Dubbo 的服务提供者机制实现多个接收节点的负载均衡。
#
# 对于一个具体的项目，很可能只用一个接收器。此时如果希望程序加载时只加载一个接收器，可以通过编辑
# opt/opt-receiver.xml 文件实现。
receiver.type=do_nothing
#
###################################################
#                   do_nothing                    #
###################################################
# do_nothing 接收器没有任何配置。
#
###################################################
#                      injvm                      #
###################################################
# injvm 接收器没有任何配置。
#
###################################################
#                      kafka                      #
###################################################
# 引导服务器集群。
receiver.kafka.bootstrap_servers=your-ip1:9092,your-ip2:9092,your-ip3:9092
# 会话的超时限制: 如果 consumer 在这段时间内没有发送心跳信息，一次 rebalance 将会产生。
# 该值必须在[group.min.session.timeout.ms, group.max.session.timeout.ms]范围内，默认: 10000。
receiver.kafka.session_timeout_ms=10000
# 新的 group 加入 topic 时，从什么位置开始消费。
receiver.kafka.auto_offset_reset=latest
# 监听器启用的消费者的线程数。
# 每一个线程都会启动一个 KafkaConsumer，每个 KafkaConsumer 都会占用一个 partition。
# 程序分布式部署时，所有节点的线程数之和应该小于等于 topic 的 partition 数。
receiver.kafka.concurrency=1
# 监听器调用 KafkaConsumer.poll(Duration) 方法的超时时间，如果超过这个时间还没有拉取到数据，则返回空列表。
receiver.kafka.poll_timeout=3000
# 监听器的 id，每一个节点的监听器 id 都应与该节点的其它 kafka 监听器的 id 不同。
# 该设置会覆盖 kafka 的 group.id 设置，因此无需设置 group.id。
receiver.kafka.listener_id=statistics.receiver
# 监听器的目标 topic。
receiver.kafka.listener_topic=statistics.dispatcher.dispatch
# 监听器的最大拉取数据量。当拉取到的数据量达到这个值时，会立即返回，不会等待 poll_timeout。
receiver.kafka.max_poll_records=100
# 监听器的最大拉取间隔。如果当前时间距离监听器上一次拉取数据的时间超过了这个值，一次 rebalance 将会产生。
receiver.kafka.max_poll_interval_ms=300000
#
###################################################
#                      dubbo                      #
###################################################
# dubbo 接收器没有任何配置。
```

您不必对所有的配置项进行配置。

在项目第一次启动之前，您需要修改 `opt/opt-receiver.xml`，决定项目中需要使用哪些接收器。您只需要修改使用的接收器的配置。

### reset.properties

重置服务配置文件。

```properties
###################################################
#                      never                      #
###################################################
# Never 推送器没有任何配置。
#
###################################################
#                   fixed_delay                   #
###################################################
# 重置的间隔。
resetter.fixed_delay.delay=43200000
#
###################################################
#                   fixed_rate                    #
###################################################
# 重置的间隔。
resetter.fixed_rate.rate=43200000
#
###################################################
#                      cron                       #
###################################################
# 执行重置的 CRON 表达式。
resetter.cron.cron=0 0 1 * * *
#
###################################################
#                      dubbo                      #
###################################################
# Dubbo 推送器没有任何配置。
```

您不必对所有的配置项进行配置。

在项目第一次启动之前，您需要修改 `opt/opt-resetter.xml`，决定项目中需要使用哪些重置器。您只需要修改使用的重置器的配置。

### task.properties

任务配置文件。

```properties
# 任务的超时时间。
task.expire_timeout=3600000
# 任务死亡的超时时间。
task.die_timeout=3600000
# 任务的心跳间隔。
task.beat_interval=10000
# 任务的最大提供器数据大小。
task.max_provider_data_size=100
#
# 任务超时检查的 cron 表达式。
task.check.expire_check.cron=0 * * * * ?
# 任务死亡检查的 cron 表达式。
task.check.die_check.cron=0 * * * * ?
```

## telqos 目录

| 文件名                   | 说明   |
|-----------------------|------|
| connection.properties | 连接配置 |

### connection.properties

Telqos 连接配置文件。

```properties
# Telnet 端口。
telqos.port=23
# 字符集。
telqos.charset=UTF-8
# 白名单表达式。
telqos.whitelist_regex=
# 黑名单表达式。
telqos.blacklist_regex=
```

如果您的项目中有多个包含 Telqos 模块的服务，您应该修改 `telqos.port` 的值，以避免端口冲突。

请根据操作系统的默认字符集，修改 `telqos.charset` 的值，以避免乱码。一般情况下，Windows 系统的默认字符集为 `GBK`，
Linux 系统的默认字符集为 `UTF-8`。

如果您希望限制 Telqos 的使用范围，您可以修改 `telqos.whitelist_regex` 和 `telqos.blacklist_regex` 的值。
