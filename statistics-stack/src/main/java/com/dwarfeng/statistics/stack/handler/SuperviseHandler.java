package com.dwarfeng.statistics.stack.handler;

import com.dwarfeng.subgrade.stack.handler.DistributedLockHandler;

/**
 * 主管处理器。
 *
 * <p>
 * 该处理器用于处理统计任务的驱动和调度。
 *
 * <p>
 * 在统计服务集群中，最多只有一个主管处理器在运行。<br>
 * 当运行的主管处理器发生故障时，集群会自动选举新的主管处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface SuperviseHandler extends DistributedLockHandler {
}
