package com.dwarfeng.statistics.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.statistics.sdk.util.Constraints;
import com.dwarfeng.statistics.stack.bean.key.BridgeDataKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 桥接器数据键。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class WebInputBridgeDataKey implements Key {

    private static final long serialVersionUID = -6128562121687481129L;

    public static BridgeDataKey toStackBean(WebInputBridgeDataKey webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new BridgeDataKey(
                    webInput.getStatisticsSettingLongId(),
                    webInput.getTag()
            );
        }
    }

    @JSONField(name = "statistics_setting_long_id")
    @NotNull
    private Long statisticsSettingLongId;

    @JSONField(name = "tag")
    @NotNull
    @Length(max = Constraints.LENGTH_TAG)
    private String tag;

    public WebInputBridgeDataKey() {
    }

    public Long getStatisticsSettingLongId() {
        return statisticsSettingLongId;
    }

    public void setStatisticsSettingLongId(Long statisticsSettingLongId) {
        this.statisticsSettingLongId = statisticsSettingLongId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebInputBridgeDataKey that = (WebInputBridgeDataKey) o;
        return Objects.equals(statisticsSettingLongId, that.statisticsSettingLongId) && Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(statisticsSettingLongId);
        result = 31 * result + Objects.hashCode(tag);
        return result;
    }

    @Override
    public String toString() {
        return "WebInputBridgeDataKey{" +
                "statisticsSettingLongId=" + statisticsSettingLongId +
                ", tag='" + tag + '\'' +
                '}';
    }
}
