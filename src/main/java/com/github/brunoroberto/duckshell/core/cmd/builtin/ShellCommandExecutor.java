package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.ShellContext;
import com.github.brunoroberto.duckshell.core.cmd.CommandExecutor;
import com.github.brunoroberto.duckshell.core.cmd.CommandResult;

public class ShellCommandExecutor implements CommandExecutor<ShellCommand> {

    @Override
    public CommandResult execute(ShellContext shellContext, ShellCommand command) {
        return command.execute(shellContext);
    }
}
