package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.cmd.EmptyResult;
import com.github.brunoroberto.duckshell.core.cmd.Result;

public class ExitCmd implements ShellCommand {

    @Override
    public Result execute(Context context) {
        System.exit(0);
        return new EmptyResult();
    }
}
