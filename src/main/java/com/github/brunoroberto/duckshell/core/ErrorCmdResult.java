package com.github.brunoroberto.duckshell.core;

import com.github.brunoroberto.duckshell.core.cmd.Result;

public record ErrorCmdResult(String stderr) implements Result {

    @Override
    public String getStdOut() {
        return null;
    }

    @Override
    public String getStdErr() {
        return stderr;
    }
}
