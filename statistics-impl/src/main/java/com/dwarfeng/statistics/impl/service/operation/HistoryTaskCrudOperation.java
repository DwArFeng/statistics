package com.dwarfeng.statistics.impl.service.operation;

import com.dwarfeng.statistics.stack.bean.entity.HistoryTask;
import com.dwarfeng.statistics.stack.bean.entity.HistoryTaskEvent;
import com.dwarfeng.statistics.stack.cache.HistoryTaskCache;
import com.dwarfeng.statistics.stack.cache.HistoryTaskEventCache;
import com.dwarfeng.statistics.stack.dao.HistoryTaskDao;
import com.dwarfeng.statistics.stack.dao.HistoryTaskEventDao;
import com.dwarfeng.statistics.stack.service.HistoryTaskEventMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HistoryTaskCrudOperation implements BatchCrudOperation<LongIdKey, HistoryTask> {

    private final HistoryTaskDao historyTaskDao;
    private final HistoryTaskCache historyTaskCache;

    private final HistoryTaskEventDao historyTaskEventDao;
    private final HistoryTaskEventCache historyTaskEventCache;

    @Value("${cache.timeout.entity.history_task}")
    private long historyTaskTimeout;

    public HistoryTaskCrudOperation(
            HistoryTaskDao historyTaskDao,
            HistoryTaskCache historyTaskCache,
            HistoryTaskEventDao historyTaskEventDao,
            HistoryTaskEventCache historyTaskEventCache
    ) {
        this.historyTaskDao = historyTaskDao;
        this.historyTaskCache = historyTaskCache;
        this.historyTaskEventDao = historyTaskEventDao;
        this.historyTaskEventCache = historyTaskEventCache;
    }

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return historyTaskCache.exists(key) || historyTaskDao.exists(key);
    }

    @Override
    public HistoryTask get(LongIdKey key) throws Exception {
        if (historyTaskCache.exists(key)) {
            return historyTaskCache.get(key);
        } else {
            if (!historyTaskDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            HistoryTask historyTask = historyTaskDao.get(key);
            historyTaskCache.push(historyTask, historyTaskTimeout);
            return historyTask;
        }
    }

    @Override
    public LongIdKey insert(HistoryTask historyTask) throws Exception {
        historyTaskCache.push(historyTask, historyTaskTimeout);
        return historyTaskDao.insert(historyTask);
    }

    @Override
    public void update(HistoryTask historyTask) throws Exception {
        historyTaskCache.push(historyTask, historyTaskTimeout);
        historyTaskDao.update(historyTask);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        // 删除与 历史任务 相关的 历史任务事件。
        List<LongIdKey> historyTaskEventKeys = historyTaskEventDao.lookup(
                HistoryTaskEventMaintainService.CHILD_FOR_HISTORY_TASK, new Object[]{key}
        ).stream().map(HistoryTaskEvent::getKey).collect(Collectors.toList());
        historyTaskEventDao.batchDelete(historyTaskEventKeys);
        historyTaskEventCache.batchDelete(historyTaskEventKeys);

        // 删除 历史任务 自身。
        historyTaskDao.delete(key);
        historyTaskCache.delete(key);
    }

    @Override
    public boolean allExists(List<LongIdKey> keys) throws Exception {
        return historyTaskCache.allExists(keys) || historyTaskDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<LongIdKey> keys) throws Exception {
        return historyTaskCache.nonExists(keys) && historyTaskDao.nonExists(keys);
    }

    @Override
    public List<HistoryTask> batchGet(List<LongIdKey> keys) throws Exception {
        if (historyTaskCache.allExists(keys)) {
            return historyTaskCache.batchGet(keys);
        } else {
            if (!historyTaskDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<HistoryTask> historyTasks = historyTaskDao.batchGet(keys);
            historyTaskCache.batchPush(historyTasks, historyTaskTimeout);
            return historyTasks;
        }
    }

    @Override
    public List<LongIdKey> batchInsert(List<HistoryTask> historyTasks) throws Exception {
        historyTaskCache.batchPush(historyTasks, historyTaskTimeout);
        return historyTaskDao.batchInsert(historyTasks);
    }

    @Override
    public void batchUpdate(List<HistoryTask> historyTasks) throws Exception {
        historyTaskCache.batchPush(historyTasks, historyTaskTimeout);
        historyTaskDao.batchUpdate(historyTasks);
    }

    @Override
    public void batchDelete(List<LongIdKey> keys) throws Exception {
        for (LongIdKey key : keys) {
            delete(key);
        }
    }
}
