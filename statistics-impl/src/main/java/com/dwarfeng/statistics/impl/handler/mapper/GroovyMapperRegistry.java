package com.dwarfeng.statistics.impl.handler.mapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.dutil.basic.io.StringOutputStream;
import com.dwarfeng.statistics.stack.exception.MapperException;
import com.dwarfeng.statistics.stack.exception.MapperMakeException;
import com.dwarfeng.statistics.stack.handler.Mapper;
import com.dwarfeng.subgrade.stack.bean.Bean;
import groovy.lang.GroovyClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * 使用 Groovy 脚本的过滤器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class GroovyMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "groovy_mapper";

    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyMapperRegistry.class);

    private final ApplicationContext ctx;

    public GroovyMapperRegistry(ApplicationContext ctx) {
        super(MAPPER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "Groovy映射器";
    }

    @Override
    public String provideDescription() {
        return "通过自定义的groovy脚本，实现对带有时间数据的映射";
    }

    @Override
    public String provideExampleParam() {
        try {
            String groovyScript;
            Resource resource = ctx.getResource("classpath:groovy/ExampleMapperProcessor.groovy");
            try (InputStream in = resource.getInputStream();
                 StringOutputStream out = new StringOutputStream(StandardCharsets.UTF_8, true)) {
                IOUtil.trans(in, out, 4096);
                out.flush();
                groovyScript = out.toString();
            }
            String[] params = new String[]{"0"};
            Config config = new Config(groovyScript, params);
            return JSON.toJSONString(config, true);
        } catch (Exception e) {
            LOGGER.warn("读取文件 classpath:groovy/ExampleMapperProcessor.groovy 时出现异常", e);
            return "";
        }
    }

    @Override
    public Mapper makeMapper() throws MapperException {
        try {
            return ctx.getBean(GroovyMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "GroovyMapperRegistry{" +
                "ctx=" + ctx +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class GroovyMapper extends AbstractMapper {

        @Override
        protected List<Sequence> doMap(MapParam mapParam, List<Sequence> sequences) throws Exception {
            // 获得配置。
            Config config = JSON.parseObject(mapParam.getParam(), Config.class);

            // 将配置中的 groovyScript 解析为 Processor。
            Processor processor;
            try (GroovyClassLoader classLoader = new GroovyClassLoader()) {
                // 通过Groovy脚本生成处理器。
                Class<?> aClass = classLoader.parseClass(config.getGroovyScript());
                processor = (Processor) aClass.newInstance();
            }

            // 调用处理器执行映射方法，返回映射后的序列。
            return processor.map(config.getParams(), sequences);
        }
    }

    /**
     * Groovy处理器。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    public interface Processor {

        /**
         * 映射序列列表。
         *
         * @param params    映射器的参数。
         * @param sequences 待映射的序列组成的列表。
         * @return 映射后的序列组成的列表。
         * @throws MapperException 映射器异常。
         */
        List<Mapper.Sequence> map(String[] params, List<Mapper.Sequence> sequences) throws Exception;
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = 7604693175843294636L;

        @JSONField(name = "groovy_script", ordinal = 1)
        private String groovyScript;

        @JSONField(name = "params", ordinal = 2)
        private String[] params;

        public Config() {
        }

        public Config(String groovyScript, String[] params) {
            this.groovyScript = groovyScript;
            this.params = params;
        }

        public String getGroovyScript() {
            return groovyScript;
        }

        public void setGroovyScript(String groovyScript) {
            this.groovyScript = groovyScript;
        }

        public String[] getParams() {
            return params;
        }

        public void setParams(String[] params) {
            this.params = params;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "groovyScript='" + groovyScript + '\'' +
                    ", params=" + Arrays.toString(params) +
                    '}';
        }
    }
}
