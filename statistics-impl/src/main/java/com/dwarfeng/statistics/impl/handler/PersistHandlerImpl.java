package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.stack.bean.dto.*;
import com.dwarfeng.statistics.stack.exception.LookupException;
import com.dwarfeng.statistics.stack.exception.NativeQueryException;
import com.dwarfeng.statistics.stack.exception.RecordException;
import com.dwarfeng.statistics.stack.handler.PersistHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class PersistHandlerImpl implements PersistHandler {

    private final List<Bridge> bridges;

    @Value("${bridge.persister.type}")
    private String persisterType;

    private Bridge.Persister persister;

    protected PersistHandlerImpl(List<Bridge> bridges) {
        this.bridges = Optional.ofNullable(bridges).orElse(Collections.emptyList());
    }

    @PostConstruct
    public void init() throws Exception {
        // 从持久器列表中找到对应类型的持久器。
        Bridge bridge = bridges.stream().filter(b -> b.supportType(persisterType)).findAny()
                .orElseThrow(() -> new HandlerException("未知的 bridge 类型: " + persisterType));
        // 如果桥接器支持持久器，则获取持久器。
        persister = bridge.getPersister();
    }

    @Override
    public void record(BridgeData bridgeData) throws HandlerException {
        try {
            persister.record(bridgeData);
        } catch (RecordException e) {
            throw e;
        } catch (Exception e) {
            throw new RecordException(e, Collections.singletonList(bridgeData));
        }
    }

    @Override
    public void record(List<BridgeData> bridgeDatas) throws HandlerException {
        try {
            persister.record(bridgeDatas);
        } catch (RecordException e) {
            throw e;
        } catch (Exception e) {
            throw new RecordException(e, bridgeDatas);
        }
    }

    @Override
    public LookupResult lookup(LookupInfo lookupInfo) throws HandlerException {
        try {
            return persister.lookup(lookupInfo);
        } catch (LookupException e) {
            throw e;
        } catch (Exception e) {
            throw new LookupException(e, Collections.singletonList(lookupInfo));
        }
    }

    @Override
    public List<LookupResult> lookup(List<LookupInfo> lookupInfos) throws HandlerException {
        try {
            return persister.lookup(lookupInfos);
        } catch (LookupException e) {
            throw e;
        } catch (Exception e) {
            throw new LookupException(e, lookupInfos);
        }
    }

    @Override
    public QueryResult nativeQuery(NativeQueryInfo queryInfo) throws HandlerException {
        try {
            return persister.nativeQuery(queryInfo);
        } catch (NativeQueryException e) {
            throw e;
        } catch (Exception e) {
            throw new NativeQueryException(e, Collections.singletonList(queryInfo));
        }
    }

    @Override
    public List<QueryResult> nativeQuery(List<NativeQueryInfo> queryInfos) throws HandlerException {
        try {
            return persister.nativeQuery(queryInfos);
        } catch (NativeQueryException e) {
            throw e;
        } catch (Exception e) {
            throw new NativeQueryException(e, queryInfos);
        }
    }

    @Override
    public String
    toString() {
        return "PersistHandlerImpl{" +
                "bridges=" + bridges +
                ", persisterType='" + persisterType + '\'' +
                ", persister=" + persister +
                '}';
    }
}
