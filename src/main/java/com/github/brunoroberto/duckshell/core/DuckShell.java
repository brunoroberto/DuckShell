package com.github.brunoroberto.duckshell.core;

import com.github.brunoroberto.duckshell.core.cmd.CommandExecutorFactory;
import com.github.brunoroberto.duckshell.core.cmd.CommandResolver;
import com.github.brunoroberto.duckshell.core.io.ConsoleOutput;
import com.github.brunoroberto.duckshell.core.io.ResultOutput;
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
            var commandNode = shellParser.parse(rawInput);
            var command = commandResolver.resolve(this.context, commandNode);
            var executor = CommandExecutorFactory.create(command);
            var result = executor.execute(this.context, command);
            var resultOutput = new ResultOutput(this.context, new ConsoleOutput())
                    .withRedirections(commandNode.redirections());
            resultOutput.write(result);
        }
    }

    public String prompt() {
        var prompt = String.format(PROMPT_SYMBOL, this.context.getCurrentWorkingDirectoryAsString());
        return IO.readln(prompt);
    }
}
