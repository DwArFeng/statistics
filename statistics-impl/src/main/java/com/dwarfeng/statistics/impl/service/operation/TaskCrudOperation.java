package com.dwarfeng.statistics.impl.service.operation;

import com.dwarfeng.statistics.stack.bean.entity.Task;
import com.dwarfeng.statistics.stack.bean.entity.TaskEvent;
import com.dwarfeng.statistics.stack.cache.TaskCache;
import com.dwarfeng.statistics.stack.cache.TaskEventCache;
import com.dwarfeng.statistics.stack.dao.TaskDao;
import com.dwarfeng.statistics.stack.dao.TaskEventDao;
import com.dwarfeng.statistics.stack.service.TaskEventMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskCrudOperation implements BatchCrudOperation<LongIdKey, Task> {

    private final TaskDao taskDao;
    private final TaskCache taskCache;

    private final TaskEventDao taskEventDao;
    private final TaskEventCache taskEventCache;

    @Value("${cache.timeout.entity.task}")
    private long taskTimeout;

    public TaskCrudOperation(
            TaskDao taskDao,
            TaskCache taskCache,
            TaskEventDao taskEventDao,
            TaskEventCache taskEventCache
    ) {
        this.taskDao = taskDao;
        this.taskCache = taskCache;
        this.taskEventDao = taskEventDao;
        this.taskEventCache = taskEventCache;
    }

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return taskCache.exists(key) || taskDao.exists(key);
    }

    @Override
    public Task get(LongIdKey key) throws Exception {
        if (taskCache.exists(key)) {
            return taskCache.get(key);
        } else {
            if (!taskDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            Task task = taskDao.get(key);
            taskCache.push(task, taskTimeout);
            return task;
        }
    }

    @Override
    public LongIdKey insert(Task task) throws Exception {
        taskCache.push(task, taskTimeout);
        return taskDao.insert(task);
    }

    @Override
    public void update(Task task) throws Exception {
        taskCache.push(task, taskTimeout);
        taskDao.update(task);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        // 删除与 任务 相关的 任务事件。
        List<LongIdKey> taskEventKeys = taskEventDao.lookup(
                TaskEventMaintainService.CHILD_FOR_TASK, new Object[]{key}
        ).stream().map(TaskEvent::getKey).collect(Collectors.toList());
        taskEventDao.batchDelete(taskEventKeys);
        taskEventCache.batchDelete(taskEventKeys);

        // 删除 任务 自身。
        taskDao.delete(key);
        taskCache.delete(key);
    }

    @Override
    public boolean allExists(List<LongIdKey> keys) throws Exception {
        return taskCache.allExists(keys) || taskDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<LongIdKey> keys) throws Exception {
        return taskCache.nonExists(keys) && taskDao.nonExists(keys);
    }

    @Override
    public List<Task> batchGet(List<LongIdKey> keys) throws Exception {
        if (taskCache.allExists(keys)) {
            return taskCache.batchGet(keys);
        } else {
            if (!taskDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<Task> tasks = taskDao.batchGet(keys);
            taskCache.batchPush(tasks, taskTimeout);
            return tasks;
        }
    }

    @Override
    public List<LongIdKey> batchInsert(List<Task> tasks) throws Exception {
        taskCache.batchPush(tasks, taskTimeout);
        return taskDao.batchInsert(tasks);
    }

    @Override
    public void batchUpdate(List<Task> tasks) throws Exception {
        taskCache.batchPush(tasks, taskTimeout);
        taskDao.batchUpdate(tasks);
    }

    @Override
    public void batchDelete(List<LongIdKey> keys) throws Exception {
        for (LongIdKey key : keys) {
            delete(key);
        }
    }
}
