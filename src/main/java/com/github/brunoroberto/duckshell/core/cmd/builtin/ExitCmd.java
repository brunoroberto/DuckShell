package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.ShellContext;
import com.github.brunoroberto.duckshell.core.cmd.CommandResult;

public class ExitCmd implements ShellCommand {

    @Override
    public CommandResult execute(ShellContext shellContext) {
        System.exit(0);
        return new CommandResult(null, false, true, null);
    }
}
