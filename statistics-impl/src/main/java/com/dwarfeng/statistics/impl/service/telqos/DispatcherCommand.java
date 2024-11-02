package com.dwarfeng.statistics.impl.service.telqos;

import com.dwarfeng.springtelqos.node.config.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import com.dwarfeng.statistics.stack.handler.Dispatcher;
import com.dwarfeng.statistics.stack.service.DispatchQosService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

@TelqosCommand
public class DispatcherCommand extends CliCommand {

    private static final String COMMAND_OPTION_CURRENT = "current";
    private static final String COMMAND_OPTION_ALL = "all";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_CURRENT,
            COMMAND_OPTION_ALL
    };

    private static final String IDENTITY = "dispatcher";
    private static final String DESCRIPTION = "调度器处理器操作/查看";

    private static final String CMD_LINE_SYNTAX_STATUS = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_CURRENT);
    private static final String CMD_LINE_SYNTAX_ALL = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_ALL);

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_STATUS,
            CMD_LINE_SYNTAX_ALL
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final DispatchQosService dispatchQosService;

    public DispatcherCommand(DispatchQosService dispatchQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.dispatchQosService = dispatchQosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_CURRENT).desc("查看当前调度器").build());
        list.add(Option.builder(COMMAND_OPTION_ALL).desc("查看全部调度器").build());
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
                case COMMAND_OPTION_CURRENT:
                    printCurrent(context);
                    break;
                case COMMAND_OPTION_ALL:
                    printAll(context);
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void printCurrent(Context context) throws Exception {
        Dispatcher currentDispatcher = dispatchQosService.currentDispatcher();
        context.sendMessage("current dispatcher:");
        context.sendMessage(String.format("  %s", currentDispatcher));
    }

    private void printAll(Context context) throws Exception {
        List<Dispatcher> dispatchers = dispatchQosService.allDispatchers();
        context.sendMessage("all dispatchers:");
        int index = 0;
        int total = dispatchers.size();
        for (Dispatcher dispatcher : dispatchers) {
            context.sendMessage(String.format("  %d/%d: %s", index++, total, dispatcher));
        }
    }
}
