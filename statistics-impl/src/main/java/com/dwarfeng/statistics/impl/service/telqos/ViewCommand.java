package com.dwarfeng.statistics.impl.service.telqos;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.dutil.basic.io.StringOutputStream;
import com.dwarfeng.dutil.basic.mea.TimeMeasurer;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import com.dwarfeng.statistics.sdk.bean.dto.WebInputLookupInfo;
import com.dwarfeng.statistics.sdk.bean.dto.WebInputNativeQueryInfo;
import com.dwarfeng.statistics.sdk.bean.dto.WebInputQueryInfo;
import com.dwarfeng.statistics.sdk.bean.key.WebInputBridgeDataKey;
import com.dwarfeng.statistics.stack.bean.dto.*;
import com.dwarfeng.statistics.stack.bean.key.BridgeDataKey;
import com.dwarfeng.statistics.stack.service.ViewQosService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@TelqosCommand
public class ViewCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "view";

    // region 指令选项

    private static final String COMMAND_OPTION_LATEST = "latest";
    private static final String COMMAND_OPTION_LOOKUP = "lookup";
    private static final String COMMAND_OPTION_NATIVE_QUERY = "nquery";
    private static final String COMMAND_OPTION_NATIVE_QUERY_LONG_OPT = "native-query";
    private static final String COMMAND_OPTION_QUERY = "query";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LATEST,
            COMMAND_OPTION_LOOKUP,
            COMMAND_OPTION_NATIVE_QUERY,
            COMMAND_OPTION_QUERY,
    };

    private static final String COMMAND_SUB_OPTION_JSON = "json";
    private static final String COMMAND_SUB_OPTION_JSON_FILE = "jf";
    private static final String COMMAND_SUB_OPTION_JSON_FILE_LONG_OPT = "json-file";

    // endregion

    private final ViewQosService viewQosService;

    public ViewCommand(ViewQosService viewQosService) {
        super(IDENTITY);
        this.viewQosService = viewQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "观察指令";
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String jsonSegment = " [" + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON) + " json-string] " +
                "[" + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file]";
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_LATEST) + jsonSegment,
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + jsonSegment,
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_NATIVE_QUERY) + jsonSegment,
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_QUERY) + jsonSegment
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_LATEST).optionalArg(true).hasArg(false).desc("最新数据指令").build());
        list.add(Option.builder(COMMAND_OPTION_LOOKUP).optionalArg(true).hasArg(false).desc("查看指令").build());
        list.add(
                Option.builder(COMMAND_OPTION_NATIVE_QUERY).longOpt(COMMAND_OPTION_NATIVE_QUERY_LONG_OPT)
                        .optionalArg(true).hasArg(false).desc("原生查询指令").build()
        );
        list.add(Option.builder(COMMAND_OPTION_QUERY).optionalArg(true).hasArg(false).desc("查询指令").build());
        list.add(
                Option.builder(COMMAND_SUB_OPTION_JSON).hasArg(true).type(String.class).desc("JSON 字符串").build()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_JSON_FILE).longOpt(COMMAND_SUB_OPTION_JSON_FILE_LONG_OPT)
                        .hasArg(true).type(File.class).desc("JSON 文件").build()
        );
        return list;
    }

    @Override
    protected void executeWithCmd(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        Pair<String, Integer> pair = CliCommandUtil.analyseCommand(cmd, COMMAND_OPTION_ARRAY);
        if (pair.getRight() != 1) {
            context.sendMessage(CliCommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
            context.sendMessage(context.getCommandManual(context.getRuntimeIdentity()));
            return;
        }
        switch (pair.getLeft()) {
            case COMMAND_OPTION_LATEST:
                handleLatest(context, cmd);
                break;
            case COMMAND_OPTION_LOOKUP:
                handleLookup(context, cmd);
                break;
            case COMMAND_OPTION_NATIVE_QUERY:
                handleNativeQuery(context, cmd);
                break;
            case COMMAND_OPTION_QUERY:
                handleQuery(context, cmd);
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleLatest(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        List<BridgeDataKey> bridgeDataKeys;

        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON);
            bridgeDataKeys = JSON.parseArray(json, WebInputBridgeDataKey.class).stream()
                    .map(WebInputBridgeDataKey::toStackBean).collect(Collectors.toList());
        } else if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON_FILE);
            try (
                    FileInputStream in = new FileInputStream(jsonFile);
                    StringOutputStream out = new StringOutputStream()
            ) {
                IOUtil.trans(in, out, 4096);
                out.flush();
                String json = out.toString();
                bridgeDataKeys = JSON.parseArray(json, WebInputBridgeDataKey.class).stream()
                        .map(WebInputBridgeDataKey::toStackBean).collect(Collectors.toList());
            }
        } else {
            throw new UnsupportedOperationException("not supported yet");
        }

        TimeMeasurer tm = new TimeMeasurer();
        tm.start();
        List<BridgeData> bridgeDatas = viewQosService.latest(bridgeDataKeys);
        tm.stop();

        context.sendMessage("");
        context.sendMessage("执行时间：" + tm.getTimeMs() + "ms");
        context.sendMessage("");

        while (true) {
            CliCommandUtil.CropResult cropResult = cropLatestData(bridgeDatas, context);
            if (cropResult.isExitFlag()) {
                break;
            }
            context.sendMessage("");
            for (int i = cropResult.getBeginIndex(); i < cropResult.getEndIndex(); i++) {
                BridgeData bridgeData = bridgeDatas.get(i);
                printBridgeData(i, cropResult.getEndIndex(), bridgeData, context);
            }
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleLookup(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        LookupInfo lookupInfo;

        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON);
            lookupInfo = WebInputLookupInfo.toStackBean(JSON.parseObject(json, WebInputLookupInfo.class));
        } else if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                lookupInfo = WebInputLookupInfo.toStackBean(JSON.parseObject(in, WebInputLookupInfo.class));
            }
        } else {
            throw new UnsupportedOperationException("not supported yet");
        }

        TimeMeasurer tm = new TimeMeasurer();
        tm.start();
        LookupResult lookupResult = viewQosService.query(lookupInfo);
        tm.stop();
        List<BridgeData> bridgeDatas = lookupResult.getBridgeDatas();
        int currentPage = lookupResult.getCurrentPage();
        int totalPages = lookupResult.getTotalPages();

        context.sendMessage("");
        context.sendMessage("执行时间：" + tm.getTimeMs() + "ms");
        context.sendMessage("");

        while (true) {
            CliCommandUtil.CropResult cropResult = cropLookupData(bridgeDatas, currentPage, totalPages, context);
            if (cropResult.isExitFlag()) {
                break;
            }
            context.sendMessage("");
            for (int i = cropResult.getBeginIndex(); i < cropResult.getEndIndex(); i++) {
                BridgeData bridgeData = bridgeDatas.get(i);
                printBridgeData(i, cropResult.getEndIndex(), bridgeData, context);
            }
        }
    }

    private void handleQuery(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        QueryInfo queryInfo;

        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON);
            queryInfo = WebInputQueryInfo.toStackBean(JSON.parseObject(json, WebInputQueryInfo.class));
        } else if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                queryInfo = WebInputQueryInfo.toStackBean(JSON.parseObject(in, WebInputQueryInfo.class));
            }
        } else {
            throw new UnsupportedOperationException("not supported yet");
        }

        TimeMeasurer tm = new TimeMeasurer();
        tm.start();
        QueryResult queryResult = viewQosService.lookup(queryInfo);
        tm.stop();
        List<QueryResult.Sequence> sequences = queryResult.getSequences();

        context.sendMessage("");
        context.sendMessage("执行时间：" + tm.getTimeMs() + "ms");
        context.sendMessage("");

        processQueryResultSequence(context, sequences);
    }

    private void handleNativeQuery(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        NativeQueryInfo nativeQueryInfo;

        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON);
            nativeQueryInfo = WebInputNativeQueryInfo.toStackBean(
                    JSON.parseObject(json, WebInputNativeQueryInfo.class)
            );
        } else if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                nativeQueryInfo = WebInputNativeQueryInfo.toStackBean(
                        JSON.parseObject(in, WebInputNativeQueryInfo.class)
                );
            }
        } else {
            throw new UnsupportedOperationException("not supported yet");
        }

        TimeMeasurer tm = new TimeMeasurer();
        tm.start();
        QueryResult queryResult = viewQosService.nativeQuery(nativeQueryInfo);
        tm.stop();
        List<QueryResult.Sequence> sequences = queryResult.getSequences();

        context.sendMessage("");
        context.sendMessage("执行时间：" + tm.getTimeMs() + "ms");
        context.sendMessage("");

        processQueryResultSequence(context, sequences);
    }

    private void printBridgeData(
            int i, int endIndex, BridgeData bridgeData, CommandExecutor.Context context
    ) throws Exception {
        context.sendMessage(String.format(
                "索引: %d/%d",
                i, endIndex
        ));
        if (Objects.isNull(bridgeData)) {
            context.sendMessage("  null");
        } else {
            context.sendMessage(String.format(
                    "  statisticsSettingLongId: %s",
                    bridgeData.getKey().getStatisticsSettingLongId()
            ));
            context.sendMessage(String.format(
                    "  tag: %s",
                    bridgeData.getKey().getTag()
            ));
            context.sendMessage(String.format(
                    "  valueClass: %s",
                    Objects.isNull(bridgeData.getValue()) ? "null" : bridgeData.getValue().getClass().getCanonicalName()
            ));
            context.sendMessage(String.format(
                    "  value: %s",
                    bridgeData.getValue()
            ));
            context.sendMessage(String.format(
                    "  happenedDate: %1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL",
                    bridgeData.getHappenedDate()
            ));
        }
        context.sendMessage("");
    }

    @SuppressWarnings("DuplicatedCode")
    private void processQueryResultSequence(CommandExecutor.Context context, List<QueryResult.Sequence> sequences)
            throws Exception {
        int sequenceIndex;
        while (true) {
            context.sendMessage("序列总数: " + sequences.size());
            context.sendMessage("");
            context.sendMessage("输入序列索引（开始于 0）");
            context.sendMessage("输入 q 退出查询");
            context.sendMessage("");

            String message = context.receiveMessage();

            if (message.equalsIgnoreCase("q")) {
                break;
            } else {
                try {
                    sequenceIndex = Integer.parseInt(message);
                } catch (NumberFormatException e) {
                    context.sendMessage("输入格式错误");
                    context.sendMessage("");
                    continue;
                }
                if (sequenceIndex < 0 || sequenceIndex >= sequences.size()) {
                    context.sendMessage("输入范围错误，应介于 [0, " + (sequences.size() - 1) + "] 之间");
                    context.sendMessage("");
                    continue;
                }
            }

            QueryResult.Sequence sequence = sequences.get(sequenceIndex);

            context.sendMessage("");
            context.sendMessage("序列信息: ");
            String sequenceFormat = "statisticsSettingLongId: %1$s    " +
                    "tag: %2$s    " +
                    "startDate: %3$tY-%3$tm-%3$td %3$tH:%3$tM:%3$tS.%3$tL    " +
                    "endDate: %4$tY-%4$tm-%4$td %4$tH:%4$tM:%4$tS.%4$tL";
            context.sendMessage(String.format(
                    sequenceFormat,
                    sequence.getBridgeDataKey().getStatisticsSettingLongId(),
                    sequence.getBridgeDataKey().getTag(),
                    sequence.getStartDate(),
                    sequence.getEndDate()
            ));

            List<BridgeData> bridgeDatas = sequence.getBridgeDatas();

            while (true) {
                CliCommandUtil.CropResult cropResult = cropQueryData(bridgeDatas, context);
                if (cropResult.isExitFlag()) {
                    break;
                }
                context.sendMessage("");
                for (int i = cropResult.getBeginIndex(); i < cropResult.getEndIndex(); i++) {
                    BridgeData bridgeData = bridgeDatas.get(i);
                    printBridgeData(i, cropResult.getEndIndex(), bridgeData, context);
                }
            }
        }
    }

    private CliCommandUtil.CropResult cropLatestData(List<BridgeData> bridgeDatas, CommandExecutor.Context context)
            throws Exception {
        return CliCommandUtil.cropData(
                context, bridgeDatas, "数据总数: " + bridgeDatas.size(),
                command -> "输入 q 退出查询"
        );
    }

    private <T> CliCommandUtil.CropResult cropLookupData(
            List<T> originData, int currentPage, int totalPages, CommandExecutor.Context context
    ) throws Exception {
        String bannerMessage = "当前页数据总数: " + originData.size() + "    当前页数: " + currentPage + "    " +
                "总页数: " + totalPages;
        return CliCommandUtil.cropData(context, originData, bannerMessage, command -> "输入 q 退出查询");
    }

    private <T> CliCommandUtil.CropResult cropQueryData(List<T> originData, CommandExecutor.Context context)
            throws Exception {
        return CliCommandUtil.cropData(
                context, originData, "数据总数: " + originData.size(),
                command -> "输入 q 返回至序列选择"
        );
    }
}
