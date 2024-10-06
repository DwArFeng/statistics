package com.dwarfeng.statistics.stack.handler;

import com.dwarfeng.statistics.stack.bean.dto.VariableInspectInfo;
import com.dwarfeng.statistics.stack.bean.dto.VariableInspectResult;
import com.dwarfeng.statistics.stack.bean.dto.VariableRemoveInfo;
import com.dwarfeng.statistics.stack.bean.dto.VariableUpsertInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 变量操作处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface VariableOperateHandler extends Handler {

    /**
     * 查看变量。
     *
     * <p>
     * 该方法返回指定的变量查看信息对应的变量的查看结果。<br>
     * 如果指定的变量不存在，则返回 null。
     *
     * @param info 变量查看信息。
     * @return 变量的查看结果。
     * @throws HandlerException 处理器异常。
     */
    VariableInspectResult inspect(VariableInspectInfo info) throws HandlerException;

    /**
     * 插入/更新变量。
     *
     * @param info 变量插入/更新信息。
     * @throws HandlerException 处理器异常。
     */
    void upsert(VariableUpsertInfo info) throws HandlerException;

    /**
     * 删除变量。
     *
     * @param info 变量删除信息。
     * @throws HandlerException 处理器异常。
     */
    void remove(VariableRemoveInfo info) throws HandlerException;
}
