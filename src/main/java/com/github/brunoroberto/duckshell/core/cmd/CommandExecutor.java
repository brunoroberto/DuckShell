package com.github.brunoroberto.duckshell.core.cmd;

import com.github.brunoroberto.duckshell.core.ShellContext;

public interface CommandExecutor<T extends Command> {

    CommandResult execute(ShellContext shellContext, T command);

}
