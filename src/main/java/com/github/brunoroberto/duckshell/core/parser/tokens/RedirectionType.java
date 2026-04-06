package com.github.brunoroberto.duckshell.core.parser.tokens;

public enum RedirectionType {

    STDOUT_OVERWRITE,
    STDOUT_APPEND,
    STDERR,
    STDERR_APPEND;

    public boolean isErrorRedirection() {
        return this == STDERR || this == STDERR_APPEND;
    }

    public boolean isAppendRedirection() {
        return this == STDOUT_APPEND || this == STDERR_APPEND;
    }


}
