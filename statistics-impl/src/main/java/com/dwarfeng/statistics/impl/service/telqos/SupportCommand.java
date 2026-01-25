package com.dwarfeng.statistics.impl.service.telqos;

import com.dwarfeng.springtelqos.node.config.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import com.dwarfeng.statistics.stack.service.SupportQosService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

@TelqosCommand
public class SupportCommand extends CliCommand {

    private static final String COMMAND_OPTION_RESET_DRIVER = "reset-driver";
    private static final String COMMAND_OPTION_RESET_FILTER = "reset-filter";
    private static final String COMMAND_OPTION_RESET_MAPPER = "reset-mapper";
    private static final String COMMAND_OPTION_RESET_PROVIDER = "reset-privider";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_RESET_DRIVER,
            COMMAND_OPTION_RESET_FILTER,
            COMMAND_OPTION_RESET_MAPPER,
            COMMAND_OPTION_RESET_PROVIDER
    };

    private static final String IDENTITY = "support";
    private static final String DESCRIPTION = "支持操作";

    private static final String CMD_LINE_SYNTAX_RESET_DRIVER = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_RESET_DRIVER);
    private static final String CMD_LINE_SYNTAX_RESET_FILTER = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_RESET_FILTER);
    private static final String CMD_LINE_SYNTAX_RESET_MAPPER = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_RESET_MAPPER);
    private static final String CMD_LINE_SYNTAX_RESET_PROVIDER = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_RESET_PROVIDER);

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_RESET_DRIVER,
            CMD_LINE_SYNTAX_RESET_FILTER,
            CMD_LINE_SYNTAX_RESET_MAPPER,
            CMD_LINE_SYNTAX_RESET_PROVIDER
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final SupportQosService supportQosService;

    public SupportCommand(SupportQosService supportQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.supportQosService = supportQosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder().longOpt(COMMAND_OPTION_RESET_DRIVER).desc("重置驱动器支持").build());
        list.add(Option.builder().longOpt(COMMAND_OPTION_RESET_FILTER).desc("重置过滤器支持").build());
        list.add(Option.builder().longOpt(COMMAND_OPTION_RESET_MAPPER).desc("重置映射器支持").build());
        list.add(Option.builder().longOpt(COMMAND_OPTION_RESET_PROVIDER).desc("重置提供器支持").build());
        return list;
    }

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
                case COMMAND_OPTION_RESET_DRIVER:
                    supportQosService.resetDriver();
                    context.sendMessage("重置驱动器支持。");
                    break;
                case COMMAND_OPTION_RESET_FILTER:
                    supportQosService.resetFilter();
                    context.sendMessage("重置过滤器支持。");
                    break;
                case COMMAND_OPTION_RESET_MAPPER:
                    supportQosService.resetMapper();
                    context.sendMessage("重置映射器支持。");
                    break;
                case COMMAND_OPTION_RESET_PROVIDER:
                    supportQosService.resetProvider();
                    context.sendMessage("重置提供器支持。");
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }
}
