package com.dwarfeng.statistics.impl.configuration;

import com.alibaba.fastjson.parser.ParserConfig;
import com.dwarfeng.statistics.sdk.bean.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FastJsonConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(FastJsonConfiguration.class);

    public FastJsonConfiguration() {
        LOGGER.info("正在配置 FastJson autotype 白名单");
        ParserConfig.getGlobalInstance().addAccept(FastJsonStatisticsSetting.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonStatisticsExecutionProfile.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonVariable.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonDriverInfo.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonFilterInfo.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonProviderInfo.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonDriverSupport.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonFilterSupport.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonProviderSupport.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonMapperSupport.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonTask.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonTaskEvent.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonHistoryTask.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonHistoryTaskEvent.class.getCanonicalName());
        LOGGER.debug("FastJson autotype 白名单配置完毕");
    }
}
