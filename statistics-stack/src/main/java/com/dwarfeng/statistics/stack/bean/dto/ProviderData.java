package com.dwarfeng.statistics.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;

/**
 * 提供器数据。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ProviderData implements Dto {

    private static final long serialVersionUID = 5389525436981426176L;

    /**
     * 统计数据的标签。
     *
     * <p>
     * 该值不能为 <code>null</code>。
     *
     * @since 1.1.0
     */
    private String tag;

    private Object value;
    private Date happenedDate;

    public ProviderData() {
    }

    public ProviderData(String tag, Object value, Date happenedDate) {
        this.tag = tag;
        this.value = value;
        this.happenedDate = happenedDate;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    @Override
    public String toString() {
        return "ProviderData{" +
                "tag='" + tag + '\'' +
                ", value=" + value +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
