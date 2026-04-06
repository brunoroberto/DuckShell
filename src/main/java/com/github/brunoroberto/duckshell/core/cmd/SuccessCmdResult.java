package com.github.brunoroberto.duckshell.core.cmd;

public record SuccessCmdResult(String stdout, String stderr) implements Result {

    public SuccessCmdResult(String stdout) {
        this(stdout, null);
    }

    @Override
    public String getStdOut() {
        return stdout;
    }

    @Override
    public String getStdErr() {
        return stderr;
    }

}
