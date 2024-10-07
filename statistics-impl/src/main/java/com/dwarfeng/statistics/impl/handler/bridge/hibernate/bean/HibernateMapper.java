package com.dwarfeng.statistics.impl.handler.bridge.hibernate.bean;

import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Hibernate Bean 映射器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Mapper
public interface HibernateMapper {

    HibernateLongIdKey longIdKeyToHibernate(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromHibernate(HibernateLongIdKey hibernateLongIdKey);

    @Mapping(target = "longId", ignore = true)
    @Mapping(target = "statisticsSettingLongId", ignore = true)
    HibernateBridgeHibernateBridgeData hibernateBridgeNormalDataToHibernate(
            HibernateBridgeBridgeData hibernateBridgeNormalData
    );

    @InheritInverseConfiguration
    HibernateBridgeBridgeData hibernateBridgeNormalDataFromHibernate(
            HibernateBridgeHibernateBridgeData hibernateBridgeHibernateBridgeData
    );
}
