package com.dwarfeng.statistics.impl.service.telqos;

import com.dwarfeng.springtelqos.node.config.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import com.dwarfeng.statistics.stack.service.ReceiveQosService;
import com.dwarfeng.statistics.stack.struct.ConsumerStatus;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledFuture;

@TelqosCommand
public class ConsumeCommand extends CliCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumeCommand.class);

    private static final String COMMAND_OPTION_LOOKUP = "l";
    private static final String COMMAND_OPTION_SET = "s";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LOOKUP,
            COMMAND_OPTION_SET
    };

    private static final String COMMAND_OPTION_HOLD = "h";
    private static final String COMMAND_OPTION_BATCH = "b";
    private static final String COMMAND_OPTION_THREAD = "t";

    private static final String IDENTITY = "csu";
    private static final String DESCRIPTION = "消费者操作";

    private static final String CMD_LINE_SYNTAX_LOOKUP = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_HOLD) + "]";
    private static final String CMD_LINE_SYNTAX_SET = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_SET) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_BATCH) + " val] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_THREAD) + " val]";

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_LOOKUP,
            CMD_LINE_SYNTAX_SET
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final ReceiveQosService receiveQosService;

    private final ThreadPoolTaskScheduler scheduler;

    public ConsumeCommand(
            ReceiveQosService receiveQosService,
            ThreadPoolTaskScheduler scheduler
    ) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.receiveQosService = receiveQosService;
        this.scheduler = scheduler;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_LOOKUP).optionalArg(true).hasArg(false).desc("查看消费者状态").build());
        list.add(Option.builder(COMMAND_OPTION_HOLD).desc("持续输出").build());
        list.add(Option.builder(COMMAND_OPTION_SET).optionalArg(true).hasArg(false).desc("设置消费者参数").build());
        list.add(Option.builder(COMMAND_OPTION_BATCH).optionalArg(true).hasArg(true).type(Number.class)
                .argName("buffer-size").desc("缓冲器的大小").build());
        list.add(Option.builder(COMMAND_OPTION_THREAD).optionalArg(true).hasArg(true).type(Number.class)
                .argName("thread").desc("消费者的线程数量").build());
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
                case COMMAND_OPTION_LOOKUP:
                    handleLookup(context, cmd);
                    break;
                case COMMAND_OPTION_SET:
                    handleSet(context, cmd);
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void handleLookup(Context context, CommandLine cmd) throws Exception {
        // 如果命令行中包含 COMMAND_OPTION_HOLD 选项，则持续输出。
        if (cmd.hasOption(COMMAND_OPTION_HOLD)) {
            ScheduledFuture<?> future = scheduler.scheduleWithFixedDelay(
                    () -> {
                        try {
                            printConsumerStatus(context);
                        } catch (Exception e) {
                            LOGGER.warn("持续输出消费者状态时发生异常, 异常信息如下: ", e);
                        }
                    },
                    1000
            );
            // 等待用户输入任意字符，然后停止持续输出。
            context.sendMessage("输入任意字符停止持续输出");
            context.receiveMessage();
            future.cancel(true);
        } else {
            printConsumerStatus(context);
        }
    }

    private void handleSet(Context context, CommandLine cmd) throws Exception {
        Integer newBufferSize = null;
        Integer newThread = null;
        try {
            if (cmd.hasOption(COMMAND_OPTION_BATCH)) newBufferSize = Integer.parseInt(cmd.getOptionValue("b"));
            if (cmd.hasOption(COMMAND_OPTION_THREAD)) newThread = Integer.parseInt(cmd.getOptionValue("t"));
        } catch (Exception e) {
            LOGGER.warn("解析命令选项时发生异常，异常信息如下", e);
            context.sendMessage("命令行格式错误，正确的格式为: " + CMD_LINE_SYNTAX_SET);
            context.sendMessage(
                    "请留意选项 " + COMMAND_OPTION_BATCH + ", " + COMMAND_OPTION_THREAD + " 后接参数的类型应该是数字 "
            );
            return;
        }
        ConsumerStatus consumerStatus = receiveQosService.getConsumerStatus();
        int bufferSize = Objects.nonNull(newBufferSize) ? newBufferSize : consumerStatus.getBufferSize();
        int thread = Objects.nonNull(newThread) ? newThread : consumerStatus.getThread();
        receiveQosService.setConsumerParameters(bufferSize, thread);
        context.sendMessage("设置完成，消费者新的参数为: ");
        printConsumerStatus(context);
    }

    private void printConsumerStatus(Context context) throws ServiceException, TelqosException {
        ConsumerStatus consumerStatus = receiveQosService.getConsumerStatus();
        context.sendMessage(String.format("buffered-size:%-7d buffer-size:%-7d thread:%-3d idle:%b",
                consumerStatus.getBufferedSize(), consumerStatus.getBufferSize(), consumerStatus.getThread(),
                consumerStatus.isIdle())
        );
    }
}
