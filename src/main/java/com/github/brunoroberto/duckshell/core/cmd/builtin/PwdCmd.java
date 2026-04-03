package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.cmd.CommandResult;
import com.github.brunoroberto.duckshell.core.cmd.Result;
import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;

import java.util.Objects;

public class PwdCmd implements ShellCommand {

    private final CommandNode commandNode;

    public PwdCmd(CommandNode commandNode) {
        this.commandNode = commandNode;
    }

    @Override
    public Result execute(Context context) {
        Objects.requireNonNull(context, "shellContext must not be null");
        return new CommandResult(context.getCurrentWorkingDirectoryAsString(), this.commandNode.redirections());
    }
}
