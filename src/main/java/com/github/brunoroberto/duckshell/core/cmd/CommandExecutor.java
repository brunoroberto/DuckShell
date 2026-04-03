package com.github.brunoroberto.duckshell.core.cmd;

import com.github.brunoroberto.duckshell.core.Context;

public interface CommandExecutor<T extends Command> {

    CommandResult execute(Context context, T command);

}
