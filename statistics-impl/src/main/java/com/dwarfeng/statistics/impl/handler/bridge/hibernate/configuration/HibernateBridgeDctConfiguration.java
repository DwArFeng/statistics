package com.dwarfeng.statistics.impl.handler.bridge.hibernate.configuration;

import com.dwarfeng.dct.handler.*;
import com.dwarfeng.dct.handler.fdc.FastJsonFlatDataCodec;
import com.dwarfeng.dct.handler.vc.*;
import com.dwarfeng.dct.struct.DataCodingConfig;
import com.dwarfeng.dct.struct.ValueCodingConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class HibernateBridgeDctConfiguration {

    /**
     * 生成值编码器的集合。
     *
     * <p>
     * 为 hibernate 桥接器生成值编码器的集合，支持以下类型：
     * <ul>
     *     <li>Boolean</li>
     *     <li>Byte</li>
     *     <li>Short</li>
     *     <li>Integer</li>
     *     <li>Long</li>
     *     <li>Float</li>
     *     <li>Double</li>
     *     <li>BigDecimal</li>
     *     <li>BigInteger</li>
     *     <li>Character</li>
     *     <li>String</li>
     * </ul>
     *
     * @return 值编解码器的集合 Bean。
     */
    @SuppressWarnings("DuplicatedCode")
    @Bean(name = "hibernateBridge.valueCodecs")
    public List<ValueCodec> valueCodecs() {
        List<ValueCodec> bean = new ArrayList<>();
        bean.add(new BooleanValueCodec());
        bean.add(new ByteValueCodec());
        bean.add(new ShortValueCodec());
        bean.add(new IntegerValueCodec());
        bean.add(new LongValueCodec());
        bean.add(new FloatValueCodec());
        bean.add(new DoubleValueCodec());
        bean.add(new BigDecimalValueCodec());
        bean.add(new BigIntegerValueCodec());
        bean.add(new CharacterValueCodec());
        bean.add(new StringValueCodec());
        return bean;
    }

    @Bean(name = "hibernateBridge.valueCodingHandler")
    public ValueCodingHandler valueCodingHandler(
            @Qualifier("hibernateBridge.valueCodecs") List<ValueCodec> valueCodecs
    ) {
        ValueCodingConfig config = new ValueCodingConfig.Builder()
                .addCodecs(valueCodecs)
                .addPreCacheClasses(
                        valueCodecs.stream().map(ValueCodec::getTargetClass).collect(Collectors.toList())
                )
                .addPreCachePrefixes(
                        valueCodecs.stream().map(ValueCodec::getValuePrefix).collect(Collectors.toList())
                )
                .build();
        return new ValueCodingHandlerImpl(config);
    }

    @Bean(name = "hibernateBridge.flatDataCodec")
    public FlatDataCodec flatDataCodec() {
        return new FastJsonFlatDataCodec();
    }

    @Bean(name = "hibernateBridge.dataCodingHandler")
    public DataCodingHandler dataCodingHandler(
            @Qualifier("hibernateBridge.flatDataCodec") FlatDataCodec flatDataCodec,
            @Qualifier("hibernateBridge.valueCodingHandler") ValueCodingHandler valueCodingHandler
    ) {
        DataCodingConfig config = new DataCodingConfig.Builder()
                .setFlatDataCodec(flatDataCodec)
                .setValueCodingHandler(valueCodingHandler)
                .build();
        return new DataCodingHandlerImpl(config);
    }
}
