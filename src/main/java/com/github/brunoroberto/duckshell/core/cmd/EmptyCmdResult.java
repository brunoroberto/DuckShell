package com.github.brunoroberto.duckshell.core.cmd;

public record EmptyCmdResult() implements Result {

    @Override
    public String getStdOut() {
        return null;
    }

    @Override
    public String getStdErr() {
        return null;
    }
}
