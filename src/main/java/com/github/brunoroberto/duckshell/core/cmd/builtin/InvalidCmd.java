package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.cmd.CommandResult;
import com.github.brunoroberto.duckshell.core.cmd.EmptyResult;
import com.github.brunoroberto.duckshell.core.cmd.Result;
import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;

import java.util.Objects;

public class InvalidCmd implements ShellCommand {

    private static final String COMMAND_NOT_FOUND_ERROR = "%s: command not found";

    private final CommandNode commandNode;

    public InvalidCmd(CommandNode commandNode) {
        Objects.requireNonNull(commandNode, "command cannot be null");
        this.commandNode = commandNode;
    }

    @Override
    public Result execute(Context context) {
        Objects.requireNonNull(context, "context cannot be null");
        var command = commandNode.command();
        if (command == null || command.isBlank()) {
            return new EmptyResult();
        }
        return new CommandResult("", String.format(COMMAND_NOT_FOUND_ERROR, command), true, false, commandNode.redirections());
    }
}
