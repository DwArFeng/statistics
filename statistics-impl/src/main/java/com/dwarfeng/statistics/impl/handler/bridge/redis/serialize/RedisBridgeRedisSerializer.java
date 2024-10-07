package com.dwarfeng.statistics.impl.handler.bridge.redis.serialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Redis 桥接 Redis 序列化器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class RedisBridgeRedisSerializer implements RedisSerializer<Object> {

    private final ParserConfig parserConfig;

    public RedisBridgeRedisSerializer(
            @Qualifier("redisBridge.parserConfig") ParserConfig parserConfig
    ) {
        this.parserConfig = parserConfig;
    }

    @Override
    public byte[] serialize(Object o) {
        if (Objects.isNull(o)) {
            return new byte[0];
        }
        return JSON.toJSONString(o, SerializerFeature.WriteClassName).getBytes();
    }

    @Override
    public Object deserialize(byte[] bytes) {
        if (Objects.isNull(bytes) || bytes.length == 0) {
            return null;
        }
        String str = new String(bytes);
        return JSON.parseObject(str, Object.class, parserConfig);
    }

    @Override
    public String toString() {
        return "RedisBridgeRedisSerializer{" +
                "parserConfig=" + parserConfig +
                '}';
    }
}
