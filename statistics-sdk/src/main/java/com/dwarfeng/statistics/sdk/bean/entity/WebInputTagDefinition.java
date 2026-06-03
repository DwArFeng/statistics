package com.dwarfeng.statistics.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.sdk.bean.key.WebInputTagDefinitionKey;
import com.dwarfeng.statistics.sdk.util.Constraints;
import com.dwarfeng.statistics.stack.bean.entity.TagDefinition;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 标记定义。
 *
 * @author DwArFeng
 * @since 1.5.1
 */
public class WebInputTagDefinition implements Bean {

    private static final long serialVersionUID = -641803518769621275L;

    public static TagDefinition toStackBean(WebInputTagDefinition webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new TagDefinition(
                    WebInputTagDefinitionKey.toStackBean(webInput.getKey()),
                    webInput.getDescription(),
                    webInput.getRemark()
            );
        }
    }

    @JSONField(name = "key")
    @NotNull
    @Valid
    private WebInputTagDefinitionKey key;

    @JSONField(name = "description")
    @Length(max = Constraints.LENGTH_REMARK)
    private String description;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    public WebInputTagDefinition() {
    }

    public WebInputTagDefinitionKey getKey() {
        return key;
    }

    public void setKey(WebInputTagDefinitionKey key) {
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
        return "WebInputTagDefinition{" +
                "key=" + key +
                ", description='" + description + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
