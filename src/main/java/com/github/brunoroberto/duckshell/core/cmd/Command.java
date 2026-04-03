package com.github.brunoroberto.duckshell.core.cmd;

import com.github.brunoroberto.duckshell.core.ShellContext;

public interface Command {

    CommandResult execute(ShellContext shellContext);

}
