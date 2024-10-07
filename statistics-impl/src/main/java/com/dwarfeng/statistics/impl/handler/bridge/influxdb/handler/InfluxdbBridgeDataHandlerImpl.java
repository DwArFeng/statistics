package com.dwarfeng.statistics.impl.handler.bridge.influxdb.handler;

import com.dwarfeng.statistics.impl.handler.bridge.influxdb.bean.dto.*;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApi;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class InfluxdbBridgeDataHandlerImpl implements InfluxdbBridgeDataHandler {

    private final WriteApi writeApi;
    private final QueryApi queryApi;

    @Value("${bridge.influxdb.bucket}")
    private String bucket;

    @Value("${bridge.influxdb.organization}")
    private String organization;

    public InfluxdbBridgeDataHandlerImpl(
            @Qualifier("influxdbBridge.writeApi") WriteApi writeApi,
            @Qualifier("influxdbBridge.queryApi") QueryApi queryApi
    ) {
        this.writeApi = writeApi;
        this.queryApi = queryApi;
    }

    @Override
    public void write(Point point) throws HandlerException {
        try {
            writeApi.writePoint(bucket, organization, point);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public void write(List<Point> points) throws HandlerException {
        try {
            writeApi.writePoints(bucket, organization, points);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public InfluxdbBridgeLookupResult lookup(InfluxdbBridgeLookupInfo lookupInfo) throws HandlerException {
        try {
            // 构造数据查询语句模板。
            String dataFluxFormat = "from(bucket: \"%1$s\")\n" +
                    " |> range(start: %2$s, stop: %3$s)\n" +
                    " |> filter(fn: (r) => r[\"_measurement\"] == \"%4$s\")\n" +
                    " |> limit(n: %5$d, offset: %6$d)" +
                    " |> pivot(rowKey: [\"_time\"], columnKey: [\"_field\"], valueColumn: \"_value\")";

            // 根据 page 和 rows 确定 limitNumber 和 limitOffset。
            int limitNumber = lookupInfo.getRows();
            int limitOffset = lookupInfo.getPage() * lookupInfo.getRows();

            // 格式化查询语句。
            SimpleDateFormat dataSsimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            String dataFlux = String.format(dataFluxFormat,
                    bucket,
                    dataSsimpleDateFormat.format(lookupInfo.getRangeStart()),
                    dataSsimpleDateFormat.format(lookupInfo.getRangeStop()),
                    lookupInfo.getMeasurement(),
                    limitNumber,
                    limitOffset
            );

            // 查询数据。
            // 基于本项目的数据结构，返回的 dataFluxTables 不多于 1 个。
            List<FluxTable> dataFluxTables = queryApi.query(dataFlux, organization);

            // 转换数据并返回。
            List<InfluxdbBridgeLookupResult.Item> items = new ArrayList<>();
            if (!dataFluxTables.isEmpty()) {
                for (FluxRecord record : dataFluxTables.get(0).getRecords()) {
                    items.add(new InfluxdbBridgeLookupResult.Item(
                            lookupInfo.getMeasurement(),
                            record.getValues(),
                            record.getTime()
                    ));
                }
            }

            // 构造总数查询语句模板。
            String countFluxFormat = "from(bucket: \"%1$s\")\n" +
                    " |> range(start: %2$s, stop: %3$s)\n" +
                    " |> filter(fn: (r) => r[\"_measurement\"] == \"%4$s\")\n" +
                    " |> count()";

            // 格式化查询语句。
            SimpleDateFormat countSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            String countFlux = String.format(countFluxFormat,
                    bucket,
                    countSimpleDateFormat.format(lookupInfo.getRangeStart()),
                    countSimpleDateFormat.format(lookupInfo.getRangeStop()),
                    lookupInfo.getMeasurement()
            );

            // 查询总数。
            // 基于本项目的数据结构，返回的 dataFluxTables 不多于 1 个。
            List<FluxTable> countFluxTables = queryApi.query(countFlux, organization);

            // 转换总数并返回。
            long total = 0;
            if (!countFluxTables.isEmpty()) {
                // 通过 flux 语法可以保证返回的 countFluxTables 中只有一条记录，且 _value 类型为 long。
                @SuppressWarnings("DataFlowIssue")
                long totalDejaVu = (long) countFluxTables.get(0).getRecords().get(0).getValueByKey("_value");
                total = totalDejaVu;
            }

            // 返回结果。
            return new InfluxdbBridgeLookupResult(lookupInfo.getMeasurement(), items, total);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public InfluxdbBridgeQueryResult defaultQuery(InfluxdbBridgeDefaultQueryInfo queryInfo) throws HandlerException {
        try {
            // 构造查询语句模板。
            String fluxFormat = "from(bucket: \"%1$s\")\n" +
                    " |> range(start: %2$s, stop: %3$s)\n" +
                    " |> filter(fn: (r) => %4$s)\n" +
                    " |> filter(fn: (r) => r[\"_field\"] == \"value\")\n" +
                    " |> aggregateWindow(every: %5$dms, offset: %6$dms, fn:%7$s)";

            // 格式化查询语句。
            String measurementPattern = generateMeasurementPattern(queryInfo.getMeasurements());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            String flux = String.format(fluxFormat,
                    bucket,
                    simpleDateFormat.format(queryInfo.getRangeStart()),
                    simpleDateFormat.format(queryInfo.getRangeStop()),
                    measurementPattern,
                    queryInfo.getAggregateWindowEvery(),
                    queryInfo.getAggregateWindowOffset(),
                    queryInfo.getAggregateWindowFn()
            );

            // 查询数据。
            List<FluxTable> fluxTables = queryApi.query(flux, organization);

            // 转换数据并返回。
            return fluxTable2QueryResult(fluxTables);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public InfluxdbBridgeQueryResult customQuery(InfluxdbBridgeCustomQueryInfo queryInfo) throws HandlerException {
        try {
            // 构造查询语句模板。
            String fluxFormat = "from(bucket: \"%1$s\")\n" +
                    " |> range(start: %2$s, stop: %3$s)\n" +
                    " |> filter(fn: (r) => %4$s)\n" +
                    " |> filter(fn: (r) => r[\"_field\"] == \"value\")\n" +
                    "%5$s";

            // 格式化查询语句。
            String measurementPattern = generateMeasurementPattern(queryInfo.getMeasurements());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            String flux = String.format(fluxFormat,
                    bucket,
                    simpleDateFormat.format(queryInfo.getRangeStart()),
                    simpleDateFormat.format(queryInfo.getRangeStop()),
                    measurementPattern,
                    queryInfo.getFluxFragment()
            );

            // 查询数据。
            List<FluxTable> fluxTables = queryApi.query(flux, organization);

            // 转换数据并返回。
            return fluxTable2QueryResult(fluxTables);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    private String generateMeasurementPattern(List<String> measurements) {
        // 特殊情况：如果 measurements 为空，返回预设的查询模式文本，以便于快速返回空结果。
        if (measurements.isEmpty()) {
            return "r[\"_measurement\"] == \"-12450\" and r[\"_measurement\"] == \"-114514\"";
        }

        String singlePatternFormat = "r[\"_measurement\"] == \"%s\"";
        List<String> patterns = new ArrayList<>(measurements.size());
        for (String measurement : measurements) {
            patterns.add(String.format(singlePatternFormat, measurement));
        }
        return String.join(" or ", patterns);
    }

    private InfluxdbBridgeQueryResult fluxTable2QueryResult(List<FluxTable> fluxTables) {
        List<InfluxdbBridgeQueryResult.InfluxdbBridgeSequence> sequences = new ArrayList<>(fluxTables.size());
        for (FluxTable fluxTable : fluxTables) {
            List<FluxRecord> records = fluxTable.getRecords();
            List<InfluxdbBridgeQueryResult.InfluxdbBridgeItem> items = new ArrayList<>(records.size());

            // 需要保证 fluxTable 中至少有一条记录，否则跳过。
            if (records.isEmpty()) {
                continue;
            }
            FluxRecord firstRecord = records.get(0);
            String measurement = firstRecord.getMeasurement();
            Instant start = firstRecord.getStart();
            Instant stop = firstRecord.getStop();

            for (FluxRecord record : records) {
                Object value = record.getValue();
                items.add(new InfluxdbBridgeQueryResult.InfluxdbBridgeItem(
                        record.getMeasurement(), value, record.getTime()
                ));
            }
            sequences.add(new InfluxdbBridgeQueryResult.InfluxdbBridgeSequence(measurement, items, start, stop));
        }
        return new InfluxdbBridgeQueryResult(sequences);
    }

    @Override
    public String toString() {
        return "InfluxdbBridgeDataHandlerImpl{" +
                "writeApi=" + writeApi +
                ", queryApi=" + queryApi +
                ", bucket='" + bucket + '\'' +
                ", organization='" + organization + '\'' +
                '}';
    }
}
