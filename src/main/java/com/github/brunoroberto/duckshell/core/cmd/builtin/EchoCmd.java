package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.ShellContext;
import com.github.brunoroberto.duckshell.core.cmd.CommandResult;
import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;

import java.util.Objects;

public class EchoCmd implements ShellCommand {

    private final CommandNode commandNode;

    public EchoCmd(CommandNode commandNode) {
        this.commandNode = Objects.requireNonNull(commandNode, "commandNode must not be null");
    }

    @Override
    public CommandResult execute(ShellContext shellContext) {
        Objects.requireNonNull(shellContext, "shell context must not be null");
        var params = this.commandNode.arguments();
        if (params != null && !params.isEmpty()) {
            var content = String.join(" ", params);
            return new CommandResult(content, true, true, commandNode.redirections());
        }
        return new CommandResult(null, false, true, commandNode.redirections());
    }
}
