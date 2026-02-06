package com.dwarfeng.statistics.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.sdk.bean.key.FastJsonTagDefinitionKey;
import com.dwarfeng.statistics.stack.bean.entity.TagDefinition;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 标签定义。
 *
 * @author zhaofz
 * @since 1.4.0
 */
public class FastJsonTagDefinition implements Bean {

    private static final long serialVersionUID = -1678103627245514025L;

    public static FastJsonTagDefinition of(TagDefinition tagDefinition) {
        if (Objects.isNull(tagDefinition)) {
            return null;
        } else {
            return new FastJsonTagDefinition(
                    FastJsonTagDefinitionKey.of(tagDefinition.getKey()),
                    tagDefinition.getDescription(),
                    tagDefinition.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonTagDefinitionKey key;

    @JSONField(name = "description", ordinal = 2)
    private String description;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    public FastJsonTagDefinition() {
    }

    public FastJsonTagDefinition(FastJsonTagDefinitionKey key, String description, String remark) {
        this.key = key;
        this.description = description;
        this.remark = remark;
    }

    public FastJsonTagDefinitionKey getKey() {
        return key;
    }

    public void setKey(FastJsonTagDefinitionKey key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "FastJsonTagDefinition{" +
                "key=" + key +
                ", description='" + description + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
