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

    private static final long serialVersionUID = -7562649311250980791L;

    private Object value;
    private Date happenedDate;

    public ProviderData() {
    }

    public ProviderData(Object value, Date happenedDate) {
        this.value = value;
        this.happenedDate = happenedDate;
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
                "value=" + value +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
