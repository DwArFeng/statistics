package com.dwarfeng.statistics.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.sdk.bean.key.JSFixedFastJsonTagDefinitionKey;
import com.dwarfeng.statistics.stack.bean.entity.TagDefinition;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * JSFixed FastJson 变量。
 *
 * @author zhaofz
 * @since 1.4.0
 */
public class JSFixedFastJsonTagDefinition implements Bean {

    private static final long serialVersionUID = 706674300820062308L;

    public static JSFixedFastJsonTagDefinition of(TagDefinition tagDefinition) {
        if (Objects.isNull(tagDefinition)) {
            return null;
        } else {
            return new JSFixedFastJsonTagDefinition(
                    JSFixedFastJsonTagDefinitionKey.of(tagDefinition.getKey()),
                    tagDefinition.getDescription(),
                    tagDefinition.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonTagDefinitionKey key;

    @JSONField(name = "description", ordinal = 2)
    private String description;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    public JSFixedFastJsonTagDefinition() {
    }

    public JSFixedFastJsonTagDefinition(JSFixedFastJsonTagDefinitionKey key, String description, String remark) {
        this.key = key;
        this.description = description;
        this.remark = remark;
    }

    public JSFixedFastJsonTagDefinitionKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonTagDefinitionKey key) {
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
        return "JSFixedFastJsonTagDefinition{" +
                "key=" + key +
                ", description=" + description +
                ", remark='" + remark + '\'' +
                '}';
    }
}
