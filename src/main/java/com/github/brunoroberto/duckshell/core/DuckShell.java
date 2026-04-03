package com.github.brunoroberto.duckshell.core;

import com.github.brunoroberto.duckshell.core.commands.CommandExecutorFactory;
import com.github.brunoroberto.duckshell.core.commands.CommandResolver;
import com.github.brunoroberto.duckshell.core.commands.CommandResult;
import com.github.brunoroberto.duckshell.core.parser.ShellParser;

public class DuckShell implements Shell {

    private static final String PROMPT_SYMBOL = "%s |quack > ";

    private final ShellContext shellContext;
    private final ShellParser shellParser;
    private final CommandResolver commandResolver;

    private boolean running;

    public DuckShell(ShellContext shellContext, ShellParser shellParser, CommandResolver commandResolver) {
        this.shellContext = shellContext;
        this.shellParser = shellParser;
        this.commandResolver = commandResolver;
    }

    @Override
    public void run() {
        this.running = true;
        while (running) {
            var rawInput = prompt();
            var command = commandResolver.resolve(this.shellParser.parse(rawInput));
            var executor = CommandExecutorFactory.create(command);
            var result = executor.execute(shellContext, command);
            printOutput(result);
        }
    }

    public String prompt() {
        var prompt = String.format(PROMPT_SYMBOL, this.shellContext.getCurrentWorkingDirectoryAsString());
        return IO.readln(prompt);
    }

    private void printOutput(CommandResult result) {
        if (result.shouldPrint()) {
            IO.println(result.output());
        }
    }
}
