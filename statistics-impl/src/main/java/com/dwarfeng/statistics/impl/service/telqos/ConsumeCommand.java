package com.dwarfeng.statistics.impl.service.telqos;

import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import com.dwarfeng.statistics.stack.service.ReceiveQosService;
import com.dwarfeng.statistics.stack.struct.ConsumerStatus;
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

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "csu";

    // region 指令选项

    private static final String COMMAND_OPTION_LOOKUP = "l";
    private static final String COMMAND_OPTION_SET = "s";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LOOKUP,
            COMMAND_OPTION_SET
    };

    private static final String COMMAND_SUB_OPTION_HOLD = "h";
    private static final String COMMAND_SUB_OPTION_BATCH = "b";
    private static final String COMMAND_SUB_OPTION_THREAD = "t";

    // endregion

    private final ReceiveQosService receiveQosService;

    private final ThreadPoolTaskScheduler scheduler;

    public ConsumeCommand(
            ReceiveQosService receiveQosService,
            ThreadPoolTaskScheduler scheduler
    ) {
        super(IDENTITY);
        this.receiveQosService = receiveQosService;
        this.scheduler = scheduler;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "消费者操作";
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_HOLD) + "]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_SET) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_BATCH) + " val] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_THREAD) + " val]"
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_LOOKUP).optionalArg(true).hasArg(false).desc("查看消费者状态").build());
        list.add(Option.builder(COMMAND_SUB_OPTION_HOLD).desc("持续输出").build());
        list.add(Option.builder(COMMAND_OPTION_SET).optionalArg(true).hasArg(false).desc("设置消费者参数").build());
        list.add(Option.builder(COMMAND_SUB_OPTION_BATCH).optionalArg(true).hasArg(true).type(Number.class)
                .argName("buffer-size").desc("缓冲器的大小").build());
        list.add(Option.builder(COMMAND_SUB_OPTION_THREAD).optionalArg(true).hasArg(true).type(Number.class)
                .argName("thread").desc("消费者的线程数量").build());
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
            case COMMAND_OPTION_LOOKUP:
                handleLookup(context, cmd);
                break;
            case COMMAND_OPTION_SET:
                handleSet(context, cmd);
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }
    }

    private void handleLookup(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        if (cmd.hasOption(COMMAND_SUB_OPTION_HOLD)) {
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
            context.sendMessage("输入任意字符停止持续输出");
            context.receiveMessage();
            future.cancel(true);
        } else {
            printConsumerStatus(context);
        }
    }

    private void handleSet(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        Integer newBufferSize = null;
        Integer newThread = null;
        try {
            if (cmd.hasOption(COMMAND_SUB_OPTION_BATCH)) {
                newBufferSize = Integer.parseInt(cmd.getOptionValue(COMMAND_SUB_OPTION_BATCH));
            }
            if (cmd.hasOption(COMMAND_SUB_OPTION_THREAD)) {
                newThread = Integer.parseInt(cmd.getOptionValue(COMMAND_SUB_OPTION_THREAD));
            }
        } catch (Exception e) {
            LOGGER.warn("解析命令选项时发生异常，异常信息如下", e);
            context.sendMessage("命令行格式错误，请检查指令选项");
            context.sendMessage(context.getCommandManual(context.getRuntimeIdentity()));
            context.sendMessage(
                    "请留意选项 " + COMMAND_SUB_OPTION_BATCH + ", " + COMMAND_SUB_OPTION_THREAD +
                            " 后接参数的类型应该是数字 "
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

    private void printConsumerStatus(CommandExecutor.Context context) throws Exception {
        ConsumerStatus consumerStatus = receiveQosService.getConsumerStatus();
        context.sendMessage(String.format("buffered-size:%-7d buffer-size:%-7d thread:%-3d idle:%b",
                consumerStatus.getBufferedSize(), consumerStatus.getBufferSize(), consumerStatus.getThread(),
                consumerStatus.isIdle())
        );
    }
}
