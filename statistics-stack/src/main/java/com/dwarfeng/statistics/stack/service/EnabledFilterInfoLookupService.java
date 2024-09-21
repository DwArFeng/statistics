package com.dwarfeng.statistics.stack.service;

import com.dwarfeng.statistics.stack.bean.entity.FilterInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 使能的过滤器信息查询服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface EnabledFilterInfoLookupService extends Service {

    /**
     * 获取指定的统计设置所属的使能的过滤器信息。
     *
     * @param statisticsSettingKey 统计设置主键。
     * @return 指定的数据点所属的使能的过滤器信息。
     * @throws ServiceException 服务异常。
     */
    List<FilterInfo> getEnabledFilterInfos(LongIdKey statisticsSettingKey) throws ServiceException;
}
