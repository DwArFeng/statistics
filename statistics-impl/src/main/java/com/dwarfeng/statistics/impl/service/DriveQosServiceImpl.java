package com.dwarfeng.statistics.impl.service;

import com.dwarfeng.statistics.stack.handler.DriveHandler;
import com.dwarfeng.statistics.stack.handler.DriveLocalCacheHandler;
import com.dwarfeng.statistics.stack.service.DriveQosService;
import com.dwarfeng.statistics.stack.struct.DriveInfo;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class DriveQosServiceImpl implements DriveQosService {

    private final DriveHandler driveHandler;
    private final DriveLocalCacheHandler driveLocalCacheHandler;

    private final ServiceExceptionMapper sem;

    public DriveQosServiceImpl(
            DriveHandler driveHandler,
            DriveLocalCacheHandler driveLocalCacheHandler,
            ServiceExceptionMapper sem
    ) {
        this.driveHandler = driveHandler;
        this.driveLocalCacheHandler = driveLocalCacheHandler;
        this.sem = sem;
    }

    @Override
    public boolean isStarted() throws ServiceException {
        try {
            return driveHandler.isStarted();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断驱动处理器是否启动时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public DriveInfo getDriveInfo(LongIdKey recordSettingKey) throws ServiceException {
        try {
            return driveLocalCacheHandler.get(recordSettingKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取指定记录设置的驱动信息时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void clearLocalCache() throws ServiceException {
        try {
            driveLocalCacheHandler.clear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("清除本地缓存时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
