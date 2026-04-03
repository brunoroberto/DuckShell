package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.cmd.CommandExecutor;
import com.github.brunoroberto.duckshell.core.cmd.CommandResult;

public class ShellCommandExecutor implements CommandExecutor<ShellCommand> {

    @Override
    public CommandResult execute(Context context, ShellCommand command) {
        return command.execute(context);
    }
}
