package com.dwarfeng.statistics.impl.handler;

import com.dwarfeng.statistics.stack.bean.entity.DriverSupport;
import com.dwarfeng.statistics.stack.bean.entity.FilterSupport;
import com.dwarfeng.statistics.stack.bean.entity.MapperSupport;
import com.dwarfeng.statistics.stack.bean.entity.ProviderSupport;
import com.dwarfeng.statistics.stack.handler.SupportHandler;
import com.dwarfeng.statistics.stack.service.DriverSupportMaintainService;
import com.dwarfeng.statistics.stack.service.FilterSupportMaintainService;
import com.dwarfeng.statistics.stack.service.MapperSupportMaintainService;
import com.dwarfeng.statistics.stack.service.ProviderSupportMaintainService;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SupportHandlerImpl implements SupportHandler {

    private final DriverSupportMaintainService driverSupportMaintainService;
    private final FilterSupportMaintainService filterSupportMaintainService;
    private final MapperSupportMaintainService mapperSupportMaintainService;
    private final ProviderSupportMaintainService providerSupportMaintainService;

    private final List<DriverSupporter> driverSupporters;
    private final List<FilterSupporter> filterSupporters;
    private final List<MapperSupporter> mapperSupporters;
    private final List<ProviderSupporter> providerSupporters;

    public SupportHandlerImpl(
            DriverSupportMaintainService driverSupportMaintainService,
            FilterSupportMaintainService filterSupportMaintainService,
            MapperSupportMaintainService mapperSupportMaintainService,
            ProviderSupportMaintainService providerSupportMaintainService,
            List<DriverSupporter> driverSupporters,
            List<FilterSupporter> filterSupporters,
            List<MapperSupporter> mapperSupporters,
            List<ProviderSupporter> providerSupporters
    ) {
        this.driverSupportMaintainService = driverSupportMaintainService;
        this.filterSupportMaintainService = filterSupportMaintainService;
        this.mapperSupportMaintainService = mapperSupportMaintainService;
        this.providerSupportMaintainService = providerSupportMaintainService;

        this.driverSupporters = Optional.ofNullable(driverSupporters).orElse(Collections.emptyList());
        this.filterSupporters = Optional.ofNullable(filterSupporters).orElse(Collections.emptyList());
        this.mapperSupporters = Optional.ofNullable(mapperSupporters).orElse(Collections.emptyList());
        this.providerSupporters = Optional.ofNullable(providerSupporters).orElse(Collections.emptyList());
    }

    @Override
    @BehaviorAnalyse
    public void resetDriver() throws HandlerException {
        try {
            doResetDriver();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void doResetDriver() throws Exception {
        List<StringIdKey> driverKeys = driverSupportMaintainService.lookupAsList().stream()
                .map(DriverSupport::getKey).collect(Collectors.toList());
        driverSupportMaintainService.batchDelete(driverKeys);
        List<DriverSupport> driverSupports = driverSupporters.stream().map(
                supporter -> new DriverSupport(
                        new StringIdKey(supporter.provideType()),
                        supporter.provideLabel(),
                        supporter.provideDescription(),
                        supporter.provideExampleParam()
                )).collect(Collectors.toList());
        driverSupportMaintainService.batchInsert(driverSupports);
    }

    @Override
    @BehaviorAnalyse
    public void resetFilter() throws HandlerException {
        try {
            doResetFilter();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void doResetFilter() throws Exception {
        List<StringIdKey> filterKeys = filterSupportMaintainService.lookupAsList().stream()
                .map(FilterSupport::getKey).collect(Collectors.toList());
        filterSupportMaintainService.batchDelete(filterKeys);
        List<FilterSupport> filterSupports = filterSupporters.stream().map(
                supporter -> new FilterSupport(
                        new StringIdKey(supporter.provideType()),
                        supporter.provideLabel(),
                        supporter.provideDescription(),
                        supporter.provideExampleParam()
                )).collect(Collectors.toList());
        filterSupportMaintainService.batchInsert(filterSupports);
    }

    @Override
    @BehaviorAnalyse
    public void resetMapper() throws HandlerException {
        try {
            doResetMapper();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void doResetMapper() throws Exception {
        List<StringIdKey> mapperKeys = mapperSupportMaintainService.lookupAsList().stream()
                .map(MapperSupport::getKey).collect(Collectors.toList());
        mapperSupportMaintainService.batchDelete(mapperKeys);
        List<MapperSupport> mapperSupports = mapperSupporters.stream().map(
                supporter -> new MapperSupport(
                        new StringIdKey(supporter.provideType()),
                        supporter.provideLabel(),
                        supporter.provideDescription(),
                        supporter.provideExampleParam()
                )).collect(Collectors.toList());
        mapperSupportMaintainService.batchInsert(mapperSupports);
    }

    @Override
    @BehaviorAnalyse
    public void resetProvider() throws HandlerException {
        try {
            doResetProvider();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void doResetProvider() throws Exception {
        List<StringIdKey> providerKeys = providerSupportMaintainService.lookupAsList().stream()
                .map(ProviderSupport::getKey).collect(Collectors.toList());
        providerSupportMaintainService.batchDelete(providerKeys);
        List<ProviderSupport> providerSupports = providerSupporters.stream().map(
                supporter -> new ProviderSupport(
                        new StringIdKey(supporter.provideType()),
                        supporter.provideLabel(),
                        supporter.provideDescription(),
                        supporter.provideExampleParam()
                )).collect(Collectors.toList());
        providerSupportMaintainService.batchInsert(providerSupports);
    }
}
