package com.dwarfeng.statistics.impl.handler.provider.mock;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.statistics.impl.handler.provider.AbstractProviderRegistry;
import com.dwarfeng.statistics.stack.exception.ProviderException;
import com.dwarfeng.statistics.stack.handler.Provider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 模拟提供器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class MockProviderRegistry extends AbstractProviderRegistry {

    public static final String PROVIDER_TYPE = "mock_provider";

    private final ApplicationContext ctx;

    public MockProviderRegistry(ApplicationContext ctx) {
        super(PROVIDER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "模拟提供器";
    }

    @Override
    public String provideDescription() {
        return "提供模拟的随机数据, 用于测试";
    }

    @Override
    public String provideExampleParam() {
        Config config = new Config(12450L, 10, "int_string", "last_provided_date", "tag");
        return JSON.toJSONString(config, true);
    }

    @Override
    public Provider makeProvider(String type, String param) throws ProviderException {
        try {
            Config config = JSON.parseObject(param, Config.class);
            return ctx.getBean(MockProvider.class, ctx, config);
        } catch (Exception e) {
            throw new ProviderException(e);
        }
    }

    @Override
    public String toString() {
        return "MockProviderRegistry{" +
                "ctx=" + ctx +
                ", providerType='" + providerType + '\'' +
                '}';
    }
}
