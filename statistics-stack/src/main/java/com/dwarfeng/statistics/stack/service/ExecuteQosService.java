package com.dwarfeng.statistics.stack.service;

import com.dwarfeng.statistics.stack.struct.ExecuteInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 执行 QOS 服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExecuteQosService extends Service {

    /**
     * 获取指定记录设置的执行信息。
     *
     * @param recordSettingKey 指定记录设置的主键。
     * @return 指定记录设置的执行信息。
     * @throws ServiceException 服务异常。
     */
    ExecuteInfo getExecuteInfo(LongIdKey recordSettingKey) throws ServiceException;

    /**
     * 清除本地缓存。
     *
     * @throws ServiceException 服务异常。
     */
    void clearLocalCache() throws ServiceException;
}
