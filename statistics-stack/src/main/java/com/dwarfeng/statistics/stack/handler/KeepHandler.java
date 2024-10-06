package com.dwarfeng.statistics.stack.handler;

import com.dwarfeng.statistics.stack.bean.dto.BridgeData;
import com.dwarfeng.statistics.stack.exception.LatestNotSupportedException;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.List;

/**
 * 保持处理器。
 *
 * <p>
 * 该处理器用于保持桥接器数据，实现类似于实时桥接器数据的查询与更新。
 *
 * <p>
 * 保持处理器为每个统计设置维护一个最新桥接器数据，该桥接器数据可以被查询和更新。
 *
 * <p>
 * 部分保持处理器可能只支持写入，不支持查询。<br>
 * 对于只写的保持处理器，其 {@link #latest(LongIdKey)} 和 {@link #latest(List)} 方法应该抛出
 * {@link LatestNotSupportedException} 异常。
 *
 * <p>
 * 有关保持的详细信息，请参阅术语。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface KeepHandler extends Handler {

    /**
     * 更新桥接器数据。
     *
     * <p>
     * 调用此方法时，{@link BridgeData#getHappenedDate()} 的值有可能小于保持器中对应桥接器数据的发生时间。<br>
     * 不同的保持器对于该情形的处理方式不同，具体的处理方式由保持器的实现者决定。
     *
     * @param bridgeData 桥接器数据。
     * @throws HandlerException 处理器异常。
     */
    void update(BridgeData bridgeData) throws HandlerException;

    /**
     * 更新桥接器数据。
     *
     * <p>
     * 调用此方法时，需要保证参数列表中所有的桥接器数据的 {@link BridgeData#getStatisticsSettingKey()} 相互不同。<br>
     * 该规约通常由记录处理器保证。
     *
     * <p>
     * 调用此方法时，需要保证参数列表中有可能存在桥接器数据的 {@link BridgeData#getHappenedDate()}
     * 的值小于保持器中对应桥接器数据的发生时间。
     * <br>
     * 不同的保持器对于该情形的处理方式不同，具体的处理方式由保持器的实现者决定。
     *
     * @param bridgeDatas 桥接器数据组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void update(List<BridgeData> bridgeDatas) throws HandlerException;

    /**
     * 查询统计设置的最新桥接器数据。
     *
     * <p>
     * 如果统计设置主键对应的桥接器数据不存在，则返回的查询结果为 null。
     *
     * @param statisticsSettingKey 指定的统计设置对应的主键。
     * @return 指定的统计设置的最新桥接器数据。
     * @throws HandlerException 处理器异常。
     */
    BridgeData latest(LongIdKey statisticsSettingKey) throws HandlerException;

    /**
     * 查询统计设置的最新桥接器数据。
     *
     * <p>
     * 如果统计设置主键组成的列表中的某个索引处的统计设置主键对应的桥接器数据不存在，
     * 则返回的查询结果组成的列表该处索引对应的查询结果为 <code>null</code>。
     *
     * @param statisticsSettingKeys 指定的统计设置对应的主键组成的列表。
     * @return 指定的统计设置的最新桥接器数据组成的列表。
     * @throws HandlerException 处理器异常。
     */
    List<BridgeData> latest(List<LongIdKey> statisticsSettingKeys) throws HandlerException;
}
