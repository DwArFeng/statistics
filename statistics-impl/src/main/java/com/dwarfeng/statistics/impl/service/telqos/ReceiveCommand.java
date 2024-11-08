package com.dwarfeng.statistics.impl.service.telqos;

import com.dwarfeng.springtelqos.node.config.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import com.dwarfeng.statistics.stack.service.ReceiveQosService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

@TelqosCommand
public class ReceiveCommand extends CliCommand {

    private static final String COMMAND_OPTION_STATUS = "status";
    private static final String COMMAND_OPTION_ONLINE = "online";
    private static final String COMMAND_OPTION_OFFLINE = "offline";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_STATUS,
            COMMAND_OPTION_ONLINE,
            COMMAND_OPTION_OFFLINE
    };

    private static final String IDENTITY = "receive";
    private static final String DESCRIPTION = "接收功能上线/下线";

    private static final String CMD_LINE_SYNTAX_STATUS = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_STATUS);
    private static final String CMD_LINE_SYNTAX_ONLINE = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_ONLINE);
    private static final String CMD_LINE_SYNTAX_OFFLINE = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_OFFLINE);

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_STATUS,
            CMD_LINE_SYNTAX_ONLINE,
            CMD_LINE_SYNTAX_OFFLINE
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final ReceiveQosService receiveQosService;

    public ReceiveCommand(ReceiveQosService receiveQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.receiveQosService = receiveQosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_STATUS).desc("查看接收功能当前状态").build());
        list.add(Option.builder(COMMAND_OPTION_ONLINE).desc("上线服务").build());
        list.add(Option.builder(COMMAND_OPTION_OFFLINE).desc("下线服务").build());
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
                case COMMAND_OPTION_STATUS:
                    context.sendMessage("接收功能当前状态: " + (receiveQosService.isStarted() ? "上线" : "下线"));
                    break;
                case COMMAND_OPTION_ONLINE:
                    receiveQosService.start();
                    context.sendMessage("接收功能已上线!");
                    break;
                case COMMAND_OPTION_OFFLINE:
                    receiveQosService.stop();
                    context.sendMessage("接收功能已下线!");
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }
}
