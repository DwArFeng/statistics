package com.dwarfeng.statistics.impl.service.telqos;

import com.dwarfeng.springtelqos.node.config.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import com.dwarfeng.statistics.stack.bean.entity.FilterInfo;
import com.dwarfeng.statistics.stack.bean.entity.ProviderInfo;
import com.dwarfeng.statistics.stack.handler.Filter;
import com.dwarfeng.statistics.stack.handler.Provider;
import com.dwarfeng.statistics.stack.service.ExecuteQosService;
import com.dwarfeng.statistics.stack.struct.ExecuteInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@TelqosCommand
public class ExecuteLocalCacheCommand extends CliCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteLocalCacheCommand.class);

    private static final String COMMAND_OPTION_LOOKUP = "l";
    private static final String COMMAND_OPTION_CLEAR = "c";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LOOKUP,
            COMMAND_OPTION_CLEAR
    };

    private static final String IDENTITY = "elc";
    private static final String DESCRIPTION = "执行器本地缓存操作";

    private static final String CMD_LINE_SYNTAX_LOOKUP = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " id";
    private static final String CMD_LINE_SYNTAX_CLEAR = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_CLEAR);

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_LOOKUP,
            CMD_LINE_SYNTAX_CLEAR
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final ExecuteQosService executeQosService;

    public ExecuteLocalCacheCommand(ExecuteQosService executeQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.executeQosService = executeQosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_LOOKUP).desc("查询执行本地缓存").hasArg().type(Number.class).build());
        list.add(Option.builder(COMMAND_OPTION_CLEAR).desc("清除执行本地缓存").build());
        return list;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            Pair<String, Integer> pair = CommandUtil.analyseCommand(cmd, COMMAND_OPTION_ARRAY);
            if (pair.getRight() != 1) {
                context.sendMessage(CommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
                context.sendMessage(CMD_LINE_SYNTAX);
                return;
            }
            switch (pair.getLeft()) {
                case COMMAND_OPTION_LOOKUP:
                    handleLookup(context, cmd);
                    break;
                case COMMAND_OPTION_CLEAR:
                    executeQosService.clearLocalCache();
                    context.sendMessage("本地缓存已清除");
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleLookup(Context context, CommandLine cmd) throws Exception {
        long statisticsSettingId;
        try {
            statisticsSettingId = ((Number) cmd.getParsedOptionValue(COMMAND_OPTION_LOOKUP)).longValue();
        } catch (ParseException e) {
            LOGGER.warn("解析命令选项时发生异常，异常信息如下", e);
            context.sendMessage("命令行格式错误，正确的格式为: " + CMD_LINE_SYNTAX_LOOKUP);
            context.sendMessage("请留意选项 " + COMMAND_OPTION_LOOKUP + " 后接参数的类型应该是数字 ");
            return;
        }
        ExecuteInfo executeInfo = executeQosService.getExecuteInfo(new LongIdKey(statisticsSettingId));
        if (Objects.isNull(executeInfo)) {
            context.sendMessage("not exists!");
            return;
        }
        context.sendMessage(String.format("statisticsSetting: %s", executeInfo.getStatisticsSetting().toString()));
        context.sendMessage("");
        context.sendMessage("providers:");
        int index = 0;
        for (LongIdKey executorKey : executeInfo.getProviderMap().keySet()) {
            if (index != 0) {
                context.sendMessage("");
            }
            index++;
            ProviderInfo providerInfo = executeInfo.getProviderInfoMap().get(executorKey);
            Provider provider = executeInfo.getProviderMap().get(executorKey);
            context.sendMessage(String.format("  %-3d %s", index, providerInfo));
            context.sendMessage(String.format("  %-3d %s", index, provider));
        }
        index = 0;
        for (LongIdKey filterChainKey : executeInfo.getFilterChainKeys()) {
            if (index != 0) {
                context.sendMessage("");
            }
            index++;
            FilterInfo filterInfo = executeInfo.getFilterInfoMap().get(filterChainKey);
            Filter filter = executeInfo.getFilterMap().get(filterChainKey);
            context.sendMessage(String.format("  %-3d %s", index, filterInfo));
            context.sendMessage(String.format("  %-3d %s", index, filter));
        }
    }
}
