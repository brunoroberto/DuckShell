package com.github.brunoroberto.duckshell.core.cmd;

import com.github.brunoroberto.duckshell.core.Context;

public interface Command {

    CommandResult execute(Context context);

}
