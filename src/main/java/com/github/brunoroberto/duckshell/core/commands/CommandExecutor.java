package com.github.brunoroberto.duckshell.core.commands;

import com.github.brunoroberto.duckshell.core.ShellContext;

public interface CommandExecutor<T extends Command> {

    CommandResult execute(ShellContext shellContext, T command);

}
