package com.github.brunoroberto.duckshell.core.commands;

public enum CmdNames {

    ECHO("echo"),
    INVALID_CMD("invalid_cmd");

    private final String name;

    CmdNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CmdNames of(String name) {
        for (CmdNames cmdNames : values()) {
            if (cmdNames.getName().equals(name)) {
                return cmdNames;
            }
        }
        return INVALID_CMD;
    }
}
