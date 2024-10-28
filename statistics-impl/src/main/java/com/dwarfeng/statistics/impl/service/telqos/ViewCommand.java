package com.dwarfeng.statistics.impl.service.telqos;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.dutil.basic.io.StringOutputStream;
import com.dwarfeng.dutil.basic.mea.TimeMeasurer;
import com.dwarfeng.springtelqos.node.config.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import com.dwarfeng.statistics.sdk.bean.dto.WebInputLookupInfo;
import com.dwarfeng.statistics.sdk.bean.dto.WebInputNativeQueryInfo;
import com.dwarfeng.statistics.sdk.bean.dto.WebInputQueryInfo;
import com.dwarfeng.statistics.stack.bean.dto.*;
import com.dwarfeng.statistics.stack.service.ViewQosService;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
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

    private static final String COMMAND_OPTION_JSON = "json";
    private static final String COMMAND_OPTION_JSON_FILE = "jf";
    private static final String COMMAND_OPTION_JSON_FILE_LONG_OPT = "json-file";

    private static final String IDENTITY = "view";
    private static final String DESCRIPTION = "观察指令";

    private static final String CMD_LINE_SYNTAX_LATEST = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_LATEST) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";
    private static final String CMD_LINE_SYNTAX_LOOKUP = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";
    private static final String CMD_LINE_SYNTAX_NATIVE_QUERY = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_NATIVE_QUERY) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";
    private static final String CMD_LINE_SYNTAX_QUERY = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_QUERY) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_LATEST,
            CMD_LINE_SYNTAX_LOOKUP,
            CMD_LINE_SYNTAX_NATIVE_QUERY,
            CMD_LINE_SYNTAX_QUERY
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    protected final ViewQosService viewQosService;

    public ViewCommand(ViewQosService viewQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.viewQosService = viewQosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_LATEST).desc("最新数据指令").build());
        list.add(Option.builder(COMMAND_OPTION_LOOKUP).desc("查看指令").build());
        list.add(
                Option.builder(COMMAND_OPTION_NATIVE_QUERY).longOpt(COMMAND_OPTION_NATIVE_QUERY_LONG_OPT)
                        .desc("原生查询指令").build()
        );
        list.add(Option.builder(COMMAND_OPTION_QUERY).desc("查询指令").build());
        list.add(
                Option.builder(COMMAND_OPTION_JSON).desc("JSON字符串").hasArg().type(String.class).build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_JSON_FILE).longOpt(COMMAND_OPTION_JSON_FILE_LONG_OPT).desc("JSON文件")
                        .hasArg().type(File.class).build()
        );
        return list;
    }

    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            Pair<String, Integer> pair = CommandUtil.analyseCommand(cmd, COMMAND_OPTION_ARRAY);
            if (pair.getRight() != 1) {
                context.sendMessage(CommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
                context.sendMessage(super.cmdLineSyntax);
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
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleLatest(Context context, CommandLine cmd) throws Exception {
        List<LongIdKey> statisticsSettingKeys;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 statisticsSettingKeys。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            statisticsSettingKeys = JSON.parseArray(json, WebInputLongIdKey.class).stream()
                    .map(WebInputLongIdKey::toStackBean).collect(Collectors.toList());
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 statisticsSettingKeys。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (
                    FileInputStream in = new FileInputStream(jsonFile);
                    StringOutputStream out = new StringOutputStream()
            ) {
                IOUtil.trans(in, out, 4096);
                out.flush();
                String json = out.toString();
                statisticsSettingKeys = JSON.parseArray(json, WebInputLongIdKey.class).stream()
                        .map(WebInputLongIdKey::toStackBean).collect(Collectors.toList());
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 查询数据，并计时。
        TimeMeasurer tm = new TimeMeasurer();
        tm.start();
        List<BridgeData> bridgeDatas = viewQosService.latest(statisticsSettingKeys);
        tm.stop();

        // 输出执行时间。
        context.sendMessage("");
        context.sendMessage("执行时间：" + tm.getTimeMs() + "ms");
        context.sendMessage("");

        // 输出数据。
        while (true) {
            CropResult cropResult = cropLatestData(bridgeDatas, context, "输入 q 退出查询");
            if (cropResult.exitFlag) {
                break;
            }
            context.sendMessage("");
            for (int i = cropResult.beginIndex; i < cropResult.endIndex; i++) {
                BridgeData bridgeData = bridgeDatas.get(i);
                printBridgeData(i, cropResult.endIndex, bridgeData, context);
            }
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleLookup(Context context, CommandLine cmd) throws Exception {
        LookupInfo lookupInfo;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 lookupInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            lookupInfo = WebInputLookupInfo.toStackBean(JSON.parseObject(json, WebInputLookupInfo.class));
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 lookupInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                lookupInfo = WebInputLookupInfo.toStackBean(JSON.parseObject(in, WebInputLookupInfo.class));
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 查询数据，并计时。
        TimeMeasurer tm = new TimeMeasurer();
        tm.start();
        LookupResult lookupResult = viewQosService.query(lookupInfo);
        tm.stop();
        List<BridgeData> bridgeDatas = lookupResult.getBridgeDatas();
        int currentPage = lookupResult.getCurrentPage();
        int totalPages = lookupResult.getTotalPages();

        // 输出执行时间。
        context.sendMessage("");
        context.sendMessage("执行时间：" + tm.getTimeMs() + "ms");
        context.sendMessage("");

        // 输出数据。
        while (true) {
            CropResult cropResult = cropLookupData(bridgeDatas, currentPage, totalPages, context, "输入 q 退出查询");
            if (cropResult.exitFlag) {
                break;
            }
            context.sendMessage("");
            for (int i = cropResult.beginIndex; i < cropResult.endIndex; i++) {
                BridgeData bridgeData = bridgeDatas.get(i);
                printBridgeData(i, cropResult.endIndex, bridgeData, context);
            }
        }
    }

    private void handleQuery(Context context, CommandLine cmd) throws Exception {
        QueryInfo queryInfo;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 queryInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            queryInfo = WebInputQueryInfo.toStackBean(JSON.parseObject(json, WebInputQueryInfo.class));
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 queryInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                queryInfo = WebInputQueryInfo.toStackBean(JSON.parseObject(in, WebInputQueryInfo.class));
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 查询数据，并计时。
        TimeMeasurer tm = new TimeMeasurer();
        tm.start();
        QueryResult queryResult = viewQosService.lookup(queryInfo);
        tm.stop();
        List<QueryResult.Sequence> sequences = queryResult.getSequences();

        // 输出执行时间。
        context.sendMessage("");
        context.sendMessage("执行时间：" + tm.getTimeMs() + "ms");
        context.sendMessage("");

        processQueryResultSequence(context, sequences);
    }

    private void handleNativeQuery(Context context, CommandLine cmd) throws Exception {
        NativeQueryInfo nativeQueryInfo;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 queryInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            nativeQueryInfo = WebInputNativeQueryInfo.toStackBean(
                    JSON.parseObject(json, WebInputNativeQueryInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 queryInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                nativeQueryInfo = WebInputNativeQueryInfo.toStackBean(
                        JSON.parseObject(in, WebInputNativeQueryInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 查询数据，并计时。
        TimeMeasurer tm = new TimeMeasurer();
        tm.start();
        QueryResult queryResult = viewQosService.nativeQuery(nativeQueryInfo);
        tm.stop();
        List<QueryResult.Sequence> sequences = queryResult.getSequences();

        // 输出执行时间。
        context.sendMessage("");
        context.sendMessage("执行时间：" + tm.getTimeMs() + "ms");
        context.sendMessage("");

        // 输出数据。
        processQueryResultSequence(context, sequences);
    }

    private void printBridgeData(int i, int endIndex, BridgeData bridgeData, Context context) throws TelqosException {
        context.sendMessage(String.format(
                "索引: %d/%d",
                i, endIndex
        ));
        if (Objects.isNull(bridgeData)) {
            context.sendMessage("  null");
        } else {
            context.sendMessage(String.format(
                    "  statisticsSettingId: %s",
                    bridgeData.getStatisticsSettingKey().getLongId()
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
    private void processQueryResultSequence(Context context, List<QueryResult.Sequence> sequences) throws Exception {
        // 输出数据。
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
            String sequenceFormat = "statisticsSettingId: %1$s    " +
                    "startDate: %2$tY-%2$tm-%2$td %2$tH:%2$tM:%2$tS.%2$tL    " +
                    "endDate: %3$tY-%3$tm-%3$td %3$tH:%3$tM:%3$tS.%3$tL";
            context.sendMessage(String.format(
                    sequenceFormat, sequence.getStatisticsSettingKey().getLongId(),
                    sequence.getStartDate(),
                    sequence.getEndDate()
            ));

            List<BridgeData> bridgeDatas = sequence.getBridgeDatas();

            while (true) {
                CropResult cropResult = cropQueryData(bridgeDatas, context, "输入 q 返回至序列选择");
                if (cropResult.exitFlag) {
                    break;
                }
                context.sendMessage("");
                for (int i = cropResult.beginIndex; i < cropResult.endIndex; i++) {
                    BridgeData bridgeData = bridgeDatas.get(i);
                    printBridgeData(i, cropResult.endIndex, bridgeData, context);
                }
            }
        }
    }

    // 为了程序的扩展性，忽略 quitPrompt 字段的 SameParameterValue 警告。
    @SuppressWarnings("SameParameterValue")
    private CropResult cropLatestData(List<BridgeData> bridgeDatas, Context context, String quitPrompt)
            throws Exception {
        String bannerMessage = "数据总数: " + bridgeDatas.size();
        return cropData(bridgeDatas, bannerMessage, context, quitPrompt);
    }

    // 为了程序的扩展性，忽略 quitPrompt 字段的 SameParameterValue 警告。
    @SuppressWarnings("SameParameterValue")
    private <T> CropResult cropLookupData(
            List<T> originData, int currentPage, int totalPages, Context context, String quitPrompt
    ) throws Exception {
        String bannerMessage = "当前页数据总数: " + originData.size() + "    当前页数: " + currentPage + "    " +
                "总页数: " + totalPages;
        return cropData(originData, bannerMessage, context, quitPrompt);
    }

    // 为了程序的扩展性，忽略 quitPrompt 字段的 SameParameterValue 警告。
    @SuppressWarnings("SameParameterValue")
    private <T> CropResult cropQueryData(List<T> originData, Context context, String quitPrompt) throws Exception {
        String bannerMessage = "数据总数: " + originData.size();
        return cropData(originData, bannerMessage, context, quitPrompt);
    }

    private <T> CropResult cropData(List<T> originData, String bannerMessage, Context context, String quitPrompt)
            throws Exception {
        int beginIndex;
        int endIndex;

        while (true) {
            context.sendMessage(bannerMessage);
            context.sendMessage("");
            context.sendMessage("输入 all 查看所有数据");
            context.sendMessage("输入 begin-end 查看指定范围的数据(开始于 0)");
            context.sendMessage(quitPrompt);
            context.sendMessage("");

            String message = context.receiveMessage();

            if (message.equalsIgnoreCase("q")) {
                return new CropResult(-1, -1, true);
            } else if (message.equalsIgnoreCase("all")) {
                beginIndex = 0;
                endIndex = originData.size();
            } else {
                String[] split = message.split("-");
                if (split.length != 2) {
                    context.sendMessage("输入格式错误");
                    context.sendMessage("");
                    continue;
                }
                try {
                    beginIndex = Integer.parseInt(split[0]);
                    endIndex = Integer.parseInt(split[1]);
                } catch (NumberFormatException e) {
                    context.sendMessage("输入格式错误");
                    context.sendMessage("");
                    continue;
                }
                if (beginIndex < 0 || endIndex > originData.size() || beginIndex >= endIndex) {
                    String errorMessage = "输入范围错误，begin 和 end 均应介于 [0, " + originData.size() + "] 之间，" +
                            "且 begin 应小于 end";
                    context.sendMessage(errorMessage);
                    context.sendMessage("");
                    continue;
                }
            }
            break;
        }

        return new CropResult(beginIndex, endIndex, false);
    }

    private static final class CropResult {

        private final int beginIndex;
        private final int endIndex;
        private final boolean exitFlag;

        public CropResult(int beginIndex, int endIndex, boolean exitFlag) {
            this.beginIndex = beginIndex;
            this.endIndex = endIndex;
            this.exitFlag = exitFlag;
        }

        public int getBeginIndex() {
            return beginIndex;
        }

        public int getEndIndex() {
            return endIndex;
        }

        public boolean isExitFlag() {
            return exitFlag;
        }

        @Override
        public String toString() {
            return "CropResult{" +
                    "beginIndex=" + beginIndex +
                    ", endIndex=" + endIndex +
                    ", exitFlag=" + exitFlag +
                    '}';
        }
    }
}
