package com.dwarfeng.statistics.impl.handler.filter.groovy;

import com.dwarfeng.statistics.impl.handler.filter.AbstractExecutor;
import com.dwarfeng.statistics.stack.bean.dto.ProviderData;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Groovy 过滤器执行器。
 *
 * @author DwArFeng
 * @since 1.1.1
 */
@Component("groovyFilterRegistry.groovyExecutor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GroovyExecutor extends AbstractExecutor {

    private final Processor processor;

    public GroovyExecutor(Processor processor) {
        this.processor = processor;
    }

    @Override
    public void start() throws Exception {
        processor.start(context);
    }

    @Override
    public void stop() throws Exception {
        processor.stop(context);
    }

    @Override
    public List<ProviderData> filter(List<ProviderData> providerDatas) throws Exception {
        return processor.filter(context, providerDatas);
    }

    @Override
    public String toString() {
        return "GroovyExecutor{" +
                "processor=" + processor +
                ", context=" + context +
                '}';
    }
}
