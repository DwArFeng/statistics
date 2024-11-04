package groovy

import com.dwarfeng.statistics.impl.handler.provider.groovy.Processor
import com.dwarfeng.statistics.stack.bean.dto.ProviderData
import com.dwarfeng.statistics.stack.handler.Provider

/**
 * 提供包含一条数据的 ProviderData 列表。
 *
 * <p>
 * 提供的数据：
 * <ul>
 *     <li>标签是 #empty。</li>
 *     <li>数据是一个随机整型数据。</li>
 *     <li>发生时间是当前时间。</li>
 * </ul>
 *
 * @author Dwarfeng
 * @since 1.1.1
 */
@SuppressWarnings('GrPackage')
class ExampleProviderProcessor implements Processor {

    @Override
    void start(Provider.Context context) throws Exception {
    }

    @Override
    void stop(Provider.Context context) throws Exception {
    }

    @Override
    List<ProviderData> provide(Provider.Context context) throws Exception {
        ProviderData providerData = new ProviderData(
                '#empty', new Random().nextInt(), new Date()
        )
        return Collections.singletonList(providerData)
    }
}
