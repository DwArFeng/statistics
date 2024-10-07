package com.dwarfeng.statistics.impl.handler.bridge.multi;

import com.dwarfeng.statistics.impl.handler.Bridge;
import com.dwarfeng.statistics.impl.handler.Bridge.Keeper;
import com.dwarfeng.statistics.impl.handler.bridge.AbstractKeeper;
import com.dwarfeng.statistics.stack.bean.dto.BridgeData;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * 多重桥接器保持器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class MultiBridgeKeeper extends AbstractKeeper implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiBridgeKeeper.class);

    private final ApplicationContext ctx;

    private final ThreadPoolTaskExecutor executor;

    private Keeper primaryKeeper;
    private List<Keeper> delegateKeepers;

    @Value("${bridge.multi.delegates.keep}")
    private String delegateConfig;


    public MultiBridgeKeeper(ApplicationContext ctx, ThreadPoolTaskExecutor executor) {
        this.ctx = ctx;
        this.executor = executor;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void afterPropertiesSet() throws Exception {
        // 获取注册的所有桥接器。
        List<Bridge> bridges = new ArrayList<>(ctx.getBeansOfType(Bridge.class).values());
        // 获取并解析配置。
        List<String> bridgeTypes = ConfigUtil.parseConfigToTypes(delegateConfig);
        // 基于配置获取桥接器的代理列表。
        List<Bridge> delegateBridges = new ArrayList<>();
        for (String bridgeType : bridgeTypes) {
            Bridge bridge = bridges.stream().filter(b -> b.supportType(bridgeType)).findAny()
                    .orElseThrow(() -> new HandlerException("未知的 bridge 类型: " + bridgeType));
            delegateBridges.add(bridge);
        }
        // 基于桥接器获取持久器。
        List<Keeper> delegateKeepers = new ArrayList<>(delegateBridges.size());
        for (int i = 0; i < delegateBridges.size(); i++) {
            Keeper delegateKeeper = delegateBridges.get(i).getKeeper();
            if (i == 0) {
                this.primaryKeeper = delegateKeeper;
            }
            delegateKeepers.add(delegateKeeper);
        }
        this.delegateKeepers = delegateKeepers;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void update(BridgeData data) throws HandlerException {
        try {
            // 对代理的所有持久器执行异步更新操作。
            List<CompletableFuture<?>> futures = new ArrayList<>(delegateKeepers.size());
            for (final Keeper delegateKeeper : delegateKeepers) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(
                        () -> wrappedDoUpdate(delegateKeeper, data),
                        executor
                );
                futures.add(future);
            }
            try {
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            } catch (CompletionException e) {
                throw (Exception) e.getCause();
            }
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void wrappedDoUpdate(Keeper delegateKeeper, BridgeData data) throws CompletionException {
        try {
            delegateKeeper.update(data);
        } catch (Exception e) {
            String message = "持久器 " + delegateKeeper + " 更新数据时发生异常, 数据为 " + data + ", 异常信息如下";
            LOGGER.warn(message, e);
            throw new CompletionException(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void update(List<BridgeData> datas) throws HandlerException {
        try {
            // 对代理的所有持久器执行异步更新操作。
            List<CompletableFuture<?>> futures = new ArrayList<>(delegateKeepers.size());
            for (final Keeper delegateKeeper : delegateKeepers) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(
                        () -> wrappedDoUpdate(delegateKeeper, datas),
                        executor
                );
                futures.add(future);
            }
            try {
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            } catch (CompletionException e) {
                throw (Exception) e.getCause();
            }
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void wrappedDoUpdate(Keeper delegateKeeper, List<BridgeData> datas) throws CompletionException {
        try {
            delegateKeeper.update(datas);
        } catch (Exception e) {
            String message = "持久器 " + delegateKeeper + " 更新数据时发生异常, 共 " +
                    datas.size() + " 条数据, 异常信息如下";
            LOGGER.warn(message, e);
            throw new CompletionException(e);
        }
    }

    @Override
    public BridgeData latest(LongIdKey statisticsSettingKey) throws HandlerException {
        try {
            return primaryKeeper.latest(statisticsSettingKey);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public List<BridgeData> latest(List<LongIdKey> statisticsSettingKeys) throws HandlerException {
        try {
            return primaryKeeper.latest(statisticsSettingKeys);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public String toString() {
        return "MultiBridgeKeeper{" +
                "ctx=" + ctx +
                ", executor=" + executor +
                ", primaryKeeper=" + primaryKeeper +
                ", delegateKeepers=" + delegateKeepers +
                '}';
    }
}
