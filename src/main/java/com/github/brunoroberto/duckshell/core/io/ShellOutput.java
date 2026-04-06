package com.github.brunoroberto.duckshell.core.io;

import com.github.brunoroberto.duckshell.core.cmd.Result;

public interface ShellOutput {

    void write(Result result);

}
