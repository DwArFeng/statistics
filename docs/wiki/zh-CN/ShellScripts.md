# Shell Scripts - Shell 脚本

## statistics-start.sh

### 简介

statistics-start.sh 是本项目的启动脚本，用于启动本项目。 您可以调整脚本中的参数，以适应您的需求。

可调整的脚步参数包括：

- JVM 内存选项。
- JMX 远程选项。

脚本执行后，将会以 nohup 方式启动本项目，并将 PID 写入 statistics.pid 文件中。

### 可配置变量

#### jvm_memory_opts

jvm_memory_opts 是本项目的 JVM 内存选项，默认值为：

```bash
jvm_memory_opts="\
-Xmx100m \
-XX:MaxMetaspaceSize=130m \
-XX:ReservedCodeCacheSize=15m \
-XX:CompressedClassSpaceSize=15m"
```

如果您希望修改本项目的 JVM 内存选项，可以修改此变量，变量中各选项含义如下：

- Xmx：最大堆内存。
- MaxMetaspaceSize：最大元空间大小。
- ReservedCodeCacheSize：保留代码缓存大小。
- CompressedClassSpaceSize：压缩类空间大小。

#### java_jmxremote_opts

java_jmxremote_opts 是本项目的 JMX 远程选项，默认值为：

```bash
java_jmxremote_opts=""
```

该脚本默认关闭 JMX 远程选项，如果您需要开启 JMX 远程选项，可以修改此选项。

您可以通过该选项注释中的提示，修改该选项的值，以开启 JMX 远程选项。

下面是一种开启 JMX 远程选项的示例：

```bash
java_jmxremote_opts="\
-Dcom.sun.management.jmxremote.port=23000 \
-Dcom.sun.management.jmxremote.authenticate=false \
-Dcom.sun.management.jmxremote.ssl=false"
```

### 调试技巧

在安装与部署本项目时，您可能会遇到各种问题，其中部分问题是因为 JVM 启动失败导致的。

这些问题导致了项目无法启动，也无法通过追踪日志的方式定位问题，这时您可以通过以下方式重定位脚本的输出，以便于您定位问题。

找到脚本中的以下代码：

```bash
eval \
nohup /bin/java -classpath "lib/*:libext/*" \
"$jvm_memory_opts" \
"$java_jmxremote_opts" \
"$java_logging_opts" \
"${mainClass}" \
>/dev/null 2>&1 "&"
echo $! >"$basedir/statistics.pid"
```

将其修改为：

```bash
eval \
nohup /bin/java -classpath "lib/*:libext/*" \
"$jvm_memory_opts" \
"$java_jmxremote_opts" \
"$java_logging_opts" \
"${mainClass}" \
>out.log 2>&1 "&"
echo $! >"$basedir/statistics.pid"
```

然后重新启动项目，您将会在项目的安装目录下看到一个名为 out.log 的文件，该文件中记录了脚本的输出。

*排查完问题后，记得将脚本还原，因为 out.log 没有滚动机制，如果不还原，该文件会越来越大。*

## statistics-stop.sh

### 简介

statistics-stop.sh 是本项目的停止脚本，用于停止本项目。

脚本执行后，将会读取 statistics.pid 文件中的 PID，并使用 kill 命令停止本项目。

需要注意的是，本项目停止时，会尝试将消费者中的所有元素消费完毕，因此停止可能会耗费一定时间，请监控日志，并耐心等待。
使用 kill -9 命令可以强制停止本项目，但是这样做会导致未消费完毕的元素丢失。

### 可配置变量

该脚本没有可配置变量。
