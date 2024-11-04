package groovy

import com.dwarfeng.statistics.impl.handler.filter.groovy.Processor
import com.dwarfeng.statistics.stack.bean.dto.ProviderData
import com.dwarfeng.statistics.stack.handler.Filter

/**
 * 过滤所有的偶数条数据。
 *
 * @author Dwarfeng
 * @since 1.1.1
 */
@SuppressWarnings('GrPackage')
class ExampleFilterProcessor implements Processor {

    @Override
    void start(Filter.Context context) throws Exception {
    }

    @Override
    void stop(Filter.Context context) throws Exception {
    }

    @Override
    List<ProviderData> filter(Filter.Context context, List<ProviderData> providerDatas) throws Exception {
        // 过滤所有的偶数条数据。
        List<ProviderData> result = new ArrayList<>()
        for (int i = 0; i < providerDatas.size(); i++) {
            if (i % 2 == 0) {
                result.add(providerDatas.get(i))
            }
        }
        return result
    }
}
