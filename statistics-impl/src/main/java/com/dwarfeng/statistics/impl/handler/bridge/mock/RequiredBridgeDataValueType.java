package com.dwarfeng.statistics.impl.handler.bridge.mock;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@interface RequiredBridgeDataValueType {

    String value();
}
