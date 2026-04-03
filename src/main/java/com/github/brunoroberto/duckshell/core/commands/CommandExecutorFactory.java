package com.github.brunoroberto.duckshell.core.commands;

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
