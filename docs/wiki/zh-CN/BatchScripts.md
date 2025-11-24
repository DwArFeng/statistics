# Batch Scripts - 批处理脚本

## start-start.bat

### 简介

start-start.bat 是本项目的启动脚本，用于启动本项目。 您可以调整脚本中的参数，以适应您的需求。

可调整的脚步参数包括：

- JVM 内存选项。
- JMX 远程选项。

脚本执行后，会以命令提示符的方式启动本项目。

需要注意的是，本项目以命令提示符的方式启动后，如果关闭命令提示符，本项目也会随之关闭。

本项目运行过程中，请不要点击命令提示符窗口中的区域，以免阻塞本项目的输入/输出流。

### 可配置变量

#### jvm_memory_opts

jvm_memory_opts 是本项目的 JVM 内存选项，默认值为：

```bat
SET jvm_memory_opts=^
-Xmx100m ^
-XX:MaxMetaspaceSize=130m ^
-XX:ReservedCodeCacheSize=15m ^
-XX:CompressedClassSpaceSize=15m
```

如果您希望修改本项目的 JVM 内存选项，可以修改此变量，变量中各选项含义如下：

- Xmx：最大堆内存。
- MaxMetaspaceSize：最大元空间大小。
- ReservedCodeCacheSize：保留代码缓存大小。
- CompressedClassSpaceSize：压缩类空间大小。

#### java_jmxremote_opts

java_jmxremote_opts 是本项目的 JMX 远程选项，默认值为：

```bat
SET java_jmxremote_opts=
```

该脚本默认关闭 JMX 远程选项，如果您需要开启 JMX 远程选项，可以修改此选项。

您可以通过该选项注释中的提示，修改该选项的值，以开启 JMX 远程选项。

下面是一种开启 JMX 远程选项的示例：

```bat
SET java_jmxremote_opts=^
-Dcom.sun.management.jmxremote.port=23000 ^
-Dcom.sun.management.jmxremote.authenticate=false ^
-Dcom.sun.management.jmxremote.ssl=false
```

### 关闭程序的技巧

与 Linux 系统不同，本项目在 Windows 系统中，默认是以命令提示符的方式在前台运行的。

请勿直接点击命令提示符窗口的关闭按钮，这样做相当于 Linux 系统中的 kill -9 命令，会强制关闭程序，并造成未消费完毕的元素丢失。

推荐的关闭方式是：

1. 在命令提示符窗口区域内单击，进入输入状态。
2. 输入 `Ctrl + C`，此时程序进入正常退出状态。
3. 观察命令提示符的输出，等待程序正常退出。
