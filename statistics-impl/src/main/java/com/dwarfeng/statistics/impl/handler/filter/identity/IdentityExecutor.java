package com.dwarfeng.statistics.impl.handler.filter.identity;

import com.dwarfeng.statistics.impl.handler.filter.AbstractExecutor;
import com.dwarfeng.statistics.stack.bean.dto.ProviderData;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 本征过滤器执行器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component("identityFilterRegistry.identityExecutor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IdentityExecutor extends AbstractExecutor {

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    @Override
    public List<ProviderData> filter(List<ProviderData> providerDatas) {
        return providerDatas;
    }

    @Override
    public String toString() {
        return "IdentityExecutor{" +
                "context=" + context +
                '}';
    }
}
