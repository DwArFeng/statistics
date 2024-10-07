package com.dwarfeng.statistics.impl.handler.bridge.multi;

import com.dwarfeng.statistics.impl.handler.Bridge;
import com.dwarfeng.statistics.impl.handler.Bridge.Persister;
import com.dwarfeng.statistics.impl.handler.bridge.AbstractPersister;
import com.dwarfeng.statistics.stack.bean.dto.*;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
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
 * 多重桥接器持久器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class MultiBridgePersister extends AbstractPersister implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiBridgePersister.class);

    private final ApplicationContext ctx;

    private final ThreadPoolTaskExecutor executor;

    private Persister primaryPersister;
    private List<Persister> delegatePersisters;

    @Value("${bridge.multi.delegates.persist}")
    private String delegateConfig;

    protected MultiBridgePersister(ApplicationContext ctx, ThreadPoolTaskExecutor executor) {
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
        List<Persister> delegatePersisters = new ArrayList<>(delegateBridges.size());
        for (int i = 0; i < delegateBridges.size(); i++) {
            Persister delegatePersister = delegateBridges.get(i).getPersister();
            if (i == 0) {
                this.primaryPersister = delegatePersister;
            }
            delegatePersisters.add(delegatePersister);
        }
        this.delegatePersisters = delegatePersisters;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void record(BridgeData data) throws HandlerException {
        try {
            // 对代理的所有持久器执行异步更新操作。
            List<CompletableFuture<?>> futures = new ArrayList<>(delegatePersisters.size());
            for (final Persister delegatePersister : delegatePersisters) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(
                        () -> wrappedDoRecord(delegatePersister, data),
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

    private void wrappedDoRecord(Persister delegatePersister, BridgeData data) throws CompletionException {
        try {
            delegatePersister.record(data);
        } catch (Exception e) {
            String message = "持久器 " + delegatePersister + " 记录数据时发生异常, 数据为 " + data + ", 异常信息如下";
            LOGGER.warn(message, e);
            throw new CompletionException(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void record(List<BridgeData> datas) throws HandlerException {
        try {
            // 对代理的所有持久器执行异步更新操作。
            List<CompletableFuture<?>> futures = new ArrayList<>(delegatePersisters.size());
            for (final Persister delegatePersister : delegatePersisters) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(
                        () -> wrappedDoRecord(delegatePersister, datas),
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

    private void wrappedDoRecord(Persister delegatePersister, List<BridgeData> datas) throws CompletionException {
        try {
            delegatePersister.record(datas);
        } catch (Exception e) {
            String message = "持久器 " + delegatePersister + " 记录数据时发生异常, 共 " +
                    datas.size() + " 条数据, 异常信息如下";
            LOGGER.warn(message, e);
            throw new CompletionException(e);
        }
    }

    @Override
    public LookupResult lookup(LookupInfo lookupInfo) throws HandlerException {
        try {
            return primaryPersister.lookup(lookupInfo);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public List<LookupResult> lookup(List<LookupInfo> lookupInfos) throws HandlerException {
        try {
            return primaryPersister.lookup(lookupInfos);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public QueryResult nativeQuery(NativeQueryInfo queryInfo) throws HandlerException {
        try {
            return primaryPersister.nativeQuery(queryInfo);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public List<QueryResult> nativeQuery(List<NativeQueryInfo> queryInfos) throws HandlerException {
        try {
            return primaryPersister.nativeQuery(queryInfos);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public String toString() {
        return "MultiBridgePersister{" +
                "ctx=" + ctx +
                ", executor=" + executor +
                ", primaryPersister=" + primaryPersister +
                ", delegatePersisters=" + delegatePersisters +
                '}';
    }
}
