package com.dwarfeng.statistics.impl.handler.filter.groovy;

import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.dutil.basic.io.StringOutputStream;
import com.dwarfeng.statistics.impl.handler.filter.AbstractFilterRegistry;
import com.dwarfeng.statistics.stack.exception.FilterException;
import com.dwarfeng.statistics.stack.exception.FilterMakeException;
import com.dwarfeng.statistics.stack.handler.Filter;
import groovy.lang.GroovyClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Groovy 过滤器注册。
 *
 * @author DwArFeng
 * @since 1.1.1
 */
@Component
public class GroovyFilterRegistry extends AbstractFilterRegistry {

    public static final String FILTER_TYPE = "groovy_filter";

    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyFilterRegistry.class);

    private final ApplicationContext ctx;

    public GroovyFilterRegistry(ApplicationContext ctx) {
        super(FILTER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "Groovy 过滤器";
    }

    @Override
    public String provideDescription() {
        return "通过自定义的 groovy 脚本，进行数据过滤。";
    }

    @Override
    public String provideExampleParam() {
        try {
            Resource resource = ctx.getResource("classpath:groovy/ExampleFilterProcessor.groovy");
            String example;
            try (InputStream sin = resource.getInputStream();
                 StringOutputStream sout = new StringOutputStream(StandardCharsets.UTF_8, true)) {
                IOUtil.trans(sin, sout, 4096);
                sout.flush();
                example = sout.toString();
            }
            return example;
        } catch (Exception e) {
            LOGGER.warn("读取文件 classpath:groovy/ExampleFilterProcessor.groovy 时出现异常", e);
            return "";
        }
    }

    @Override
    public Filter makeFilter(String type, String param) throws FilterException {
        try (GroovyClassLoader classLoader = new GroovyClassLoader()) {
            // 通过Groovy脚本生成处理器。
            Class<?> aClass = classLoader.parseClass(param);
            Processor processor = (Processor) aClass.newInstance();
            // 生成并返回过滤器。
            return ctx.getBean(GroovyFilter.class, ctx, processor);
        } catch (Exception e) {
            throw new FilterMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "GroovyFilterRegistry{" +
                "ctx=" + ctx +
                ", filterType='" + filterType + '\'' +
                '}';
    }
}
