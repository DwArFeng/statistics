package com.dwarfeng.statistics.impl.handler.provider.mock;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.stack.bean.Bean;

/**
 * 配置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class Config implements Bean {

    private static final long serialVersionUID = -3325095051825831539L;

    @JSONField(name = "#random_seed", ordinal = 1, deserialize = false)
    private String randomSeedRem = "随机种子, 用于生成随机数据, 可以为 null。";

    @JSONField(name = "random_seed", ordinal = 2)
    private Long randomSeed;

    @JSONField(name = "#data_size", ordinal = 3, deserialize = false)
    private String dataSizeRem = "数据大小, 用于指定每次生成的数据的数量。";

    @JSONField(name = "data_size", ordinal = 4)
    private int dataSize;

    @JSONField(name = "#data_size", ordinal = 5, deserialize = false)
    private String dataTypeRem = "数据类型, 用于指定生成的数据的类型。" +
            "可选值有：int - 整型；long - 长整型；float - 单精度浮点数；double - 双精度浮点数；" +
            "gaussian - 高斯分布随机数；boolean - 布尔值；string - 字符串；int_string - 整型字符串；" +
            "long_string - 长整型字符串；float_string - 单精度浮点数字符串；double_string - 双精度浮点数字符串；" +
            "gaussian_string - 高斯分布随机数字符串。";

    @JSONField(name = "data_type", ordinal = 6)
    private String dataType;

    @JSONField(name = "#last_provided_date_variable_id", ordinal = 7, deserialize = false)
    private String lastProvidedDateVariableIdRem = "最后提供数据的日期变量的 ID。";

    @JSONField(name = "last_provided_date_variable_id", ordinal = 8)
    private String lastProvidedDateVariableId;

    @JSONField(name = "#tag", ordinal = 9, deserialize = false)
    private String tagRem = "生成的数据的标签。";

    @JSONField(name = "tag", ordinal = 10)
    private String tag;

    @JSONField(name = "#delay", ordinal = 11, deserialize = false)
    private String delayRem = "延迟时间，用于指定生成数据的延迟时间。" +
            "单位为毫秒，可以为 null。" +
            "如果值为 null 或小于等于 0，则立即生成数据。";

    @JSONField(name = "delay", ordinal = 12)
    private Long delay;

    public Config() {
    }

    public Config(
            Long randomSeed, int dataSize, String dataType, String lastProvidedDateVariableId, String tag, Long delay
    ) {
        this.randomSeed = randomSeed;
        this.dataSize = dataSize;
        this.dataType = dataType;
        this.lastProvidedDateVariableId = lastProvidedDateVariableId;
        this.tag = tag;
        this.delay = delay;
    }

    public String getRandomSeedRem() {
        return randomSeedRem;
    }

    public void setRandomSeedRem(String randomSeedRem) {
        this.randomSeedRem = randomSeedRem;
    }

    public Long getRandomSeed() {
        return randomSeed;
    }

    public void setRandomSeed(Long randomSeed) {
        this.randomSeed = randomSeed;
    }

    public String getDataSizeRem() {
        return dataSizeRem;
    }

    public void setDataSizeRem(String dataSizeRem) {
        this.dataSizeRem = dataSizeRem;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public String getDataTypeRem() {
        return dataTypeRem;
    }

    public void setDataTypeRem(String dataTypeRem) {
        this.dataTypeRem = dataTypeRem;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getLastProvidedDateVariableIdRem() {
        return lastProvidedDateVariableIdRem;
    }

    public void setLastProvidedDateVariableIdRem(String lastProvidedDateVariableIdRem) {
        this.lastProvidedDateVariableIdRem = lastProvidedDateVariableIdRem;
    }

    public String getLastProvidedDateVariableId() {
        return lastProvidedDateVariableId;
    }

    public void setLastProvidedDateVariableId(String lastProvidedDateVariableId) {
        this.lastProvidedDateVariableId = lastProvidedDateVariableId;
    }

    public String getTagRem() {
        return tagRem;
    }

    public void setTagRem(String tagRem) {
        this.tagRem = tagRem;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDelayRem() {
        return delayRem;
    }

    public void setDelayRem(String delayRem) {
        this.delayRem = delayRem;
    }

    public Long getDelay() {
        return delay;
    }

    public void setDelay(Long delay) {
        this.delay = delay;
    }

    @Override
    public String toString() {
        return "Config{" +
                "randomSeedRem='" + randomSeedRem + '\'' +
                ", randomSeed=" + randomSeed +
                ", dataSizeRem='" + dataSizeRem + '\'' +
                ", dataSize=" + dataSize +
                ", dataTypeRem='" + dataTypeRem + '\'' +
                ", dataType='" + dataType + '\'' +
                ", lastProvidedDateVariableIdRem='" + lastProvidedDateVariableIdRem + '\'' +
                ", lastProvidedDateVariableId='" + lastProvidedDateVariableId + '\'' +
                ", tagRem='" + tagRem + '\'' +
                ", tag='" + tag + '\'' +
                ", delayRem='" + delayRem + '\'' +
                ", delay=" + delay +
                '}';
    }
}
