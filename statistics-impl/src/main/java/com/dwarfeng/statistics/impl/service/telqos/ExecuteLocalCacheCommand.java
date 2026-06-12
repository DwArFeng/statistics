package com.dwarfeng.statistics.impl.service.telqos;

import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
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

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "elc";

    // region 指令选项

    private static final String COMMAND_OPTION_LOOKUP = "l";
    private static final String COMMAND_OPTION_CLEAR = "c";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LOOKUP,
            COMMAND_OPTION_CLEAR
    };

    // endregion

    private final ExecuteQosService executeQosService;

    public ExecuteLocalCacheCommand(ExecuteQosService executeQosService) {
        super(IDENTITY);
        this.executeQosService = executeQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "执行器本地缓存操作";
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " id",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_CLEAR)
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(
                Option.builder(COMMAND_OPTION_LOOKUP).optionalArg(true).hasArg(true).type(Number.class)
                        .desc("查询执行本地缓存").build()
        );
        list.add(Option.builder(COMMAND_OPTION_CLEAR).optionalArg(true).hasArg(false).desc("清除执行本地缓存").build());
        return list;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void executeWithCmd(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        Pair<String, Integer> pair = CliCommandUtil.analyseCommand(cmd, COMMAND_OPTION_ARRAY);
        if (pair.getRight() != 1) {
            context.sendMessage(CliCommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
            context.sendMessage(context.getCommandManual(context.getRuntimeIdentity()));
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
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleLookup(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        long statisticsSettingId;
        try {
            statisticsSettingId = ((Number) cmd.getParsedOptionValue(COMMAND_OPTION_LOOKUP)).longValue();
        } catch (ParseException e) {
            LOGGER.warn("解析命令选项时发生异常，异常信息如下", e);
            context.sendMessage("命令行格式错误，请检查指令选项");
            context.sendMessage(context.getCommandManual(context.getRuntimeIdentity()));
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
