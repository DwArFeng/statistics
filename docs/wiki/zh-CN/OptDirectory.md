# Opt Directory - 可选配置目录

## 总览

本项目的可选配置位于 `opt/` 目录下，包括：

```text
opt
├─ opt-bridge.xml
├─ opt-dispatcher.xml
├─ opt-driver.xml
├─ opt-filter.xml
├─ opt-mapper.xml
├─ opt-provider.xml
├─ opt-pusher.xml
├─ opt-receiver.xml
└─ opt-resetter.xml
```

所有的可选配置都为每个单独的可选项提供了加载配置，默认是注释的，如果用户需要使用某个可选项，
只需要将其对应的配置项取消注释即可。

此处展示默认的可选配置文件。

## opt-bridge.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
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
    <!--
    <context:component-scan base-package="com.dwarfeng.statistics.impl.handler.bridge.drain"/>
    -->

    <!-- 加载 MockBridge -->
    <!--
    <context:component-scan base-package="com.dwarfeng.statistics.impl.handler.bridge.mock"/>
    -->

    <!-- 加载 HibernateBridge -->
    <!--
    <context:component-scan base-package="com.dwarfeng.statistics.impl.handler.bridge.hibernate"/>
    -->

    <!-- 加载 RedisBridge -->
    <!--
    <context:component-scan base-package="com.dwarfeng.statistics.impl.handler.bridge.redis"/>
    -->

    <!-- 加载 InfluxdbBridge -->
    <!--
    <context:component-scan base-package="com.dwarfeng.statistics.impl.handler.bridge.influxdb"/>
    -->

    <!-- 加载 MultiBridge -->
    <!--
    <context:component-scan base-package="com.dwarfeng.statistics.impl.handler.bridge.multi"/>
    -->
</beans>
```

## opt-dispatcher.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--suppress SpringFacetInspection -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!--扫描 handler 的实现包。 -->
    <context:component-scan
            base-package="com.dwarfeng.statistics.impl.handler.dispatcher" use-default-filters="false"
    >
        <!-- 加载 DrainDispatcher -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.dispatcher.DrainDispatcher"
        />
        -->

        <!-- 加载 InjvmDispatcher -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.dispatcher.InjvmDispatcher"
        />
        -->

        <!-- 加载 KafkaDispatcher -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.dispatcher.KafkaDispatcher"
        />
        -->

        <!-- 加载 DubboDispatcher -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.dispatcher.DubboDispatcher"
        />
        -->
    </context:component-scan>
</beans>
```

## opt-driver.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--suppress SpringFacetInspection -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!--扫描 handler 的实现包。 -->
    <context:component-scan
            base-package="com.dwarfeng.statistics.impl.handler.driver" use-default-filters="false"
    >
        <!-- 加载 CronDriver -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.driver.CronDriverProvider"
        />
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.driver.CronDriverSupporter"
        />
        -->

        <!-- 加载 DctiKafkaDriver -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.driver.DctiKafkaDriverProvider"
        />
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.driver.DctiKafkaDriverSupporter"
        />
        -->

        <!-- 加载 FixedDelayDriver -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.driver.FixedDelayDriverProvider"
        />
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.driver.FixedDelayDriverSupporter"
        />
        -->

        <!-- 加载 FixedRateDriver -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.driver.FixedRateDriverProvider"
        />
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.driver.FixedRateDriverSupporter"
        />
        -->
    </context:component-scan>
</beans>
```

## opt-filter.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
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
    <!--
    <context:component-scan base-package="com.dwarfeng.statistics.impl.handler.filter.identity"/>
    -->

    <!-- 加载 GroovyFilter -->
    <!--
    <context:component-scan base-package="com.dwarfeng.statistics.impl.handler.filter.groovy"/>
    -->
</beans>
```

## opt-mapper.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--suppress SpringFacetInspection -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!--扫描 handler 的实现包。 -->
    <context:component-scan
            base-package="com.dwarfeng.statistics.impl.handler.mapper" use-default-filters="false"
    >
        <!-- 加载 AlignMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.mapper.AlignMapperRegistry"
        />
        -->

        <!-- 加载 AvgMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.mapper.AvgMapperRegistry"
        />
        -->

        <!-- 加载 CountMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.mapper.CountMapperRegistry"
        />
        -->

        <!-- 加载 FirstMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.mapper.FirstMapperRegistry"
        />
        -->

        <!-- 加载 GroovyMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.mapper.GroovyMapperRegistry"
        />
        -->

        <!-- 加载 IdentityMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.mapper.IdentityMapperRegistry"
        />
        -->

        <!-- 加载 LastMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.mapper.LastMapperRegistry"
        />
        -->

        <!-- 加载 SortMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.mapper.SortMapperRegistry"
        />
        -->

        <!-- 加载 TimeWeightedAgvMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.mapper.TimeWeightedAgvMapperRegistry"
        />
        -->

        <!-- 加载 ToDoubleMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.mapper.ToDoubleMapperRegistry"
        />
        -->

        <!-- 加载 WindowMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.mapper.WindowMapperRegistry"
        />
        -->

        <!-- 加载 MergeMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.mapper.MergeMapperRegistry"
        />
        -->

        <!-- 加载 TrimMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.mapper.TrimMapperRegistry"
        />
        -->

        <!-- 加载 ToBooleanMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.mapper.ToBooleanMapperRegistry"
        />
        -->

        <!-- 加载 EnableRatioMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.mapper.EnableRatioMapperRegistry"
        />
        -->

        <!-- 加载 HighPassMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.mapper.HighPassMapperRegistry"
        />
        -->

        <!-- 加载 LowPassMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.mapper.LowPassMapperRegistry"
        />
        -->

        <!-- 加载 HighPassCounterMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.mapper.HighPassCounterMapperRegistry"
        />
        -->

        <!-- 加载 LowPassCounterMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.mapper.LowPassCounterMapperRegistry"
        />
        -->

        <!-- 加载 HighPassExistenceMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.mapper.HighPassExistenceMapperRegistry"
        />
        -->

        <!-- 加载 LowPassExistenceMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.mapper.LowPassExistenceMapperRegistry"
        />
        -->
    </context:component-scan>
</beans>
```

## opt-provider.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
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
    <!--
    <context:component-scan base-package="com.dwarfeng.statistics.impl.handler.provider.mock"/>
    -->

    <!-- 加载 GroovyProvider -->
    <!--
    <context:component-scan base-package="com.dwarfeng.statistics.impl.handler.provider.groovy"/>
    -->
</beans>
```

## opt-pusher.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--suppress SpringFacetInspection -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!--扫描 handler 的实现包。 -->
    <context:component-scan base-package="com.dwarfeng.statistics.impl.handler.pusher" use-default-filters="false">
        <!-- 加载 DctiKafkaPusher -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.pusher.DctiKafkaPusher"
        />
        -->

        <!-- 加载 DrainPusher -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.pusher.DrainPusher"
        />
        -->

        <!-- 加载 LogPusher -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.pusher.LogPusher"
        />
        -->

        <!-- 加载 MultiPusher -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.pusher.MultiPusher"
        />
        -->

        <!-- 加载 NativeKafkaPusher -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.pusher.NativeKafkaPusher"
        />
        -->
    </context:component-scan>
</beans>
```

## opt-receiver.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--suppress SpringFacetInspection -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!--扫描 handler 的实现包。 -->
    <context:component-scan
            base-package="com.dwarfeng.statistics.impl.handler.receiver" use-default-filters="false"
    >
        <!-- 加载 DoNothingReceiver -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.receiver.DoNothingReceiver"
        />
        -->

        <!-- 加载 InjvmReceiver -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.receiver.InjvmReceiver"
        />
        -->

        <!-- 加载 KafkaReceiver -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.receiver.KafkaReceiver"
        />
        -->

        <!-- 加载 DubboReceiver -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.receiver.DubboReceiver"
        />
        -->
    </context:component-scan>
</beans>
```

## opt-resetter.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--suppress SpringFacetInspection -->
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
    <context:component-scan base-package="com.dwarfeng.statistics.impl.handler.resetter" use-default-filters="false">
        <!-- 加载 NeverResetter -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.resetter.NeverResetter"
        />
        -->

        <!-- 加载 FixedDelayResetter -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.resetter.FixedDelayResetter"
        />
        -->

        <!-- 加载 FixedRateResetter -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.resetter.FixedRateResetter"
        />
        -->

        <!-- 加载 CronResetter -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.resetter.CronResetter"
        />
        -->

        <!-- 加载 DubboResetter -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.statistics.impl.handler.resetter.DubboResetter"
        />
        -->
    </context:component-scan>
</beans>
```
