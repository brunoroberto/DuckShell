package com.github.brunoroberto.duckshell.core.cmd;

public enum CommandNames {

    ECHO("echo"),
    EXIT("exit"),
    INVALID_CMD("invalid_cmd");

    private final String name;

    CommandNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CommandNames of(String name) {
        for (CommandNames cmdNames : values()) {
            if (cmdNames.getName().equals(name)) {
                return cmdNames;
            }
        }
        return INVALID_CMD;
    }
}
