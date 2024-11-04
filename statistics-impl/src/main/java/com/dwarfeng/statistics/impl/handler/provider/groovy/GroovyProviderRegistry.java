package com.dwarfeng.statistics.impl.handler.provider.groovy;

import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.dutil.basic.io.StringOutputStream;
import com.dwarfeng.statistics.impl.handler.provider.AbstractProviderRegistry;
import com.dwarfeng.statistics.stack.exception.ProviderException;
import com.dwarfeng.statistics.stack.exception.ProviderMakeException;
import com.dwarfeng.statistics.stack.handler.Provider;
import groovy.lang.GroovyClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Groovy 提供器注册。
 *
 * @author DwArFeng
 * @since 1.1.1
 */
@Component
public class GroovyProviderRegistry extends AbstractProviderRegistry {

    public static final String PROVIDER_TYPE = "groovy_provider";

    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyProviderRegistry.class);

    private final ApplicationContext ctx;

    public GroovyProviderRegistry(ApplicationContext ctx) {
        super(PROVIDER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "Groovy 提供器";
    }

    @Override
    public String provideDescription() {
        return "通过自定义的 groovy 脚本，进行数据提供。";
    }

    @Override
    public String provideExampleParam() {
        try {
            Resource resource = ctx.getResource("classpath:groovy/ExampleProviderProcessor.groovy");
            String example;
            try (InputStream sin = resource.getInputStream();
                 StringOutputStream sout = new StringOutputStream(StandardCharsets.UTF_8, true)) {
                IOUtil.trans(sin, sout, 4096);
                sout.flush();
                example = sout.toString();
            }
            return example;
        } catch (Exception e) {
            LOGGER.warn("读取文件 classpath:groovy/ExampleProviderProcessor.groovy 时出现异常", e);
            return "";
        }
    }

    @Override
    public Provider makeProvider(String type, String param) throws ProviderException {
        try (GroovyClassLoader classLoader = new GroovyClassLoader()) {
            // 通过Groovy脚本生成处理器。
            Class<?> aClass = classLoader.parseClass(param);
            Processor processor = (Processor) aClass.newInstance();
            // 生成并返回提供器。
            return ctx.getBean(GroovyProvider.class, ctx, processor);
        } catch (Exception e) {
            throw new ProviderMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "GroovyProviderRegistry{" +
                "ctx=" + ctx +
                ", providerType='" + providerType + '\'' +
                '}';
    }
}
