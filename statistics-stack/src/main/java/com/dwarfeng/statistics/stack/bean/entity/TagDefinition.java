package com.dwarfeng.statistics.stack.bean.entity;

import com.dwarfeng.statistics.stack.bean.key.TagDefinitionKey;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;

/**
 * 标记定义。
 *
 * @author zhaofzg
 * @since 1.4.0
 */
public class TagDefinition implements Entity<TagDefinitionKey> {

    private static final long serialVersionUID = -8723689093356962999L;

    private TagDefinitionKey key;
    private String description;
    private String remark;

    public TagDefinition() {
    }

    public TagDefinition(TagDefinitionKey key, String description, String remark) {
        this.key = key;
        this.description = description;
        this.remark = remark;
    }

    @Override
    public TagDefinitionKey getKey() {
        return key;
    }

    @Override
    public void setKey(TagDefinitionKey key) {
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
        return "TagDefinition{" +
                "key=" + key +
                ", description='" + description + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
