package com.dwarfeng.statistics.impl.handler.provider.mock;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Random;

/**
 * 随机数生成器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class MockProviderRandomGenerator {

    private final Random random;

    public MockProviderRandomGenerator(Random random) {
        this.random = random;
    }

    public LongIdKey nextLongIdKey() {
        return new LongIdKey(Math.abs(random.nextLong()));
    }

    @RequiredBridgeDataValueType("int")
    public int nextInt() {
        return random.nextInt();
    }

    @RequiredBridgeDataValueType("long")
    public long nextLong() {
        return random.nextLong();
    }

    @RequiredBridgeDataValueType("float")
    public float nextFloat() {
        return random.nextFloat();
    }

    @RequiredBridgeDataValueType("double")
    public double nextDouble() {
        return random.nextDouble();
    }

    @RequiredBridgeDataValueType("gaussian")
    public double nextGaussian() {
        return random.nextGaussian();
    }

    @RequiredBridgeDataValueType("boolean")
    public boolean nextBoolean() {
        return random.nextBoolean();
    }

    @RequiredBridgeDataValueType("string")
    public String nextString() {
        long value = Math.abs(nextLong());
        return Long.toString(value, Character.MAX_RADIX);
    }

    @RequiredBridgeDataValueType("int_string")
    public String nextIntString() {
        int value = nextInt();
        return Integer.toString(value);
    }

    @RequiredBridgeDataValueType("long_string")
    public String nextLongString() {
        long value = nextLong();
        return Long.toString(value);
    }

    @RequiredBridgeDataValueType("float_string")
    public String nextFloatString() {
        float value = nextFloat();
        return Float.toString(value);
    }

    @RequiredBridgeDataValueType("double_string")
    public String nextDoubleString() {
        double value = nextDouble();
        return Double.toString(value);
    }

    @RequiredBridgeDataValueType("gaussian_string")
    public String nextGaussianString() {
        double value = nextGaussian();
        return Double.toString(value);
    }

    @Override
    public String toString() {
        return "MockProviderRandomGenerator{" +
                "random=" + random +
                '}';
    }
}
