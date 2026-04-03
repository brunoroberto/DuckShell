package com.github.brunoroberto.duckshell.core.commands;

import com.github.brunoroberto.duckshell.core.ShellContext;

public class ShellCommandExecutor implements CommandExecutor<ShellCommand> {

    @Override
    public CommandResult execute(ShellContext shellContext, ShellCommand command) {
        return command.execute(shellContext);
    }
}
