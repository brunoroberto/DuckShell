package com.github.brunoroberto.duckshell.core.commands;

import com.github.brunoroberto.duckshell.core.ShellContext;

public class ExitCmd implements ShellCommand{

    @Override
    public CommandResult execute(ShellContext shellContext) {
        System.exit(0);
        return new CommandResult(null, false, true, null);
    }
}
