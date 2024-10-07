package groovy

import com.dwarfeng.statistics.impl.handler.mapper.GroovyMapperRegistry
import com.dwarfeng.statistics.stack.bean.dto.BridgeData
import com.dwarfeng.statistics.stack.handler.Mapper

/**
 * 截取 timedValues 前几个值的映射器脚本。
 *
 * <p>
 * 截取的个数由 params[0] 确定。
 *
 * @author Dwarfeng
 * @since 1.0.0
 */
@SuppressWarnings('GrPackage')
class ExampleMapperProcessor implements GroovyMapperRegistry.Processor {

    @Override
    List<Mapper.Sequence> map(String[] params, List<Mapper.Sequence> sequences) throws Exception {
        // 对每一个序列进行 map 操作。
        List<Mapper.Sequence> result = new ArrayList<>()
        for (Mapper.Sequence sequence : sequences) {
            result.add(mapSingleSequence(params, sequence))
        }
        return result
    }

    @SuppressWarnings('GrMethodMayBeStatic')
    private Mapper.Sequence mapSingleSequence(String[] params, Mapper.Sequence sequence) throws Exception {
        // 获取数据条目列表。
        List<BridgeData> datas = sequence.getDatas()

        // 根据 params[0] 的值，对数据条目进行截取操作。
        int size = params[0] as int
        size = Math.min(datas.size(), size)
        datas = datas.subList(0, size)

        // 返回新的序列。
        return new Mapper.Sequence(
                sequence.getStatisticsSettingKey(), Collections.unmodifiableList(datas),
                sequence.getStartDate(), sequence.getEndDate()
        )
    }
}
