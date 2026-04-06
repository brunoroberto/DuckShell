package com.github.brunoroberto.duckshell.core.io;

import com.github.brunoroberto.duckshell.core.cmd.Result;

import java.util.Objects;

public class ConsoleOutput implements ShellOutput {

    @Override
    public void write(Result result) {
        Objects.requireNonNull(result, "Result must not be null");
        if (result.getStdOut() != null && !result.getStdOut().isBlank()) {
            IO.println(result.getStdOut());
        }
        if (result.getStdErr() != null && !result.getStdErr().isBlank()) {
            IO.println(result.getStdErr());
        }
    }
}
