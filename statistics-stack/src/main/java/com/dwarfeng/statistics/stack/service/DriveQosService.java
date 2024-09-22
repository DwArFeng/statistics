package com.dwarfeng.statistics.stack.service;

import com.dwarfeng.statistics.stack.struct.DriveInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 驱动 QOS 服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface DriveQosService extends Service {

    /**
     * 驱动服务是否启动。
     *
     * @return 驱动服务是否启动。
     * @throws ServiceException 服务异常。
     */
    boolean isStarted() throws ServiceException;

    /**
     * 获取指定记录设置的驱动信息。
     *
     * @param recordSettingKey 指定记录设置的主键。
     * @return 指定记录设置的驱动信息。
     * @throws ServiceException 服务异常。
     */
    DriveInfo getDriveInfo(LongIdKey recordSettingKey) throws ServiceException;

    /**
     * 清除本地缓存。
     *
     * @throws ServiceException 服务异常。
     */
    void clearLocalCache() throws ServiceException;
}
