package com.github.brunoroberto.duckshell.core.cmd;

import com.github.brunoroberto.duckshell.core.cmd.builtin.ShellCommand;
import com.github.brunoroberto.duckshell.core.cmd.ext.ExternalCommand;
import com.github.brunoroberto.duckshell.core.cmd.ext.ExternalCommandExecutor;
import com.github.brunoroberto.duckshell.core.cmd.builtin.ShellCommandExecutor;

public class CommandExecutorFactory {

    public static CommandExecutor create(Command command) {
        if (command instanceof ExternalCommand) {
            return new ExternalCommandExecutor();
        } else if (command instanceof ShellCommand) {
            return new ShellCommandExecutor();
        }
        throw new IllegalArgumentException("Unknown command: " + command);
    }
}
