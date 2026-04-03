package com.github.brunoroberto.duckshell.core;

import com.github.brunoroberto.duckshell.core.cmd.CommandExecutorFactory;
import com.github.brunoroberto.duckshell.core.cmd.CommandResolver;
import com.github.brunoroberto.duckshell.core.cmd.Result;
import com.github.brunoroberto.duckshell.core.parser.ShellParser;

public class DuckShell implements Shell {

    private static final String PROMPT_SYMBOL = "%s | quack > ";

    private final Context context;
    private final ShellParser shellParser;
    private final CommandResolver commandResolver;

    private boolean running;

    public DuckShell(Context context, ShellParser shellParser, CommandResolver commandResolver) {
        this.context = context;
        this.shellParser = shellParser;
        this.commandResolver = commandResolver;
    }

    @Override
    public void run() {
        this.running = true;
        while (running) {
            var rawInput = prompt();
            var command = commandResolver.resolve(this.context, this.shellParser.parse(rawInput));
            var executor = CommandExecutorFactory.create(command);
            var result = executor.execute(this.context, command);
            printResult(result);
        }
    }

    public String prompt() {
        var prompt = String.format(PROMPT_SYMBOL, this.context.getCurrentWorkingDirectoryAsString());
        return IO.readln(prompt);
    }

    private void printResult(Result result) {
        if (result.shouldPrint()) {
            if (result.isSuccess()) {
                IO.println(result.getStdOut());
            } else {
                IO.println(result.getStdErr());
            }
        }
    }
}
