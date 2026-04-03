package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.ShellContext;
import com.github.brunoroberto.duckshell.core.cmd.CommandResult;
import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;

import java.util.Objects;

public class PwdCmd implements ShellCommand {

    private final CommandNode commandNode;

    public PwdCmd(CommandNode commandNode) {
        this.commandNode = commandNode;
    }

    @Override
    public CommandResult execute(ShellContext shellContext) {
        Objects.requireNonNull(shellContext, "shellContext must not be null");
        return new CommandResult(shellContext.getCurrentWorkingDirectoryAsString(), true, true, this.commandNode.redirections());
    }
}
