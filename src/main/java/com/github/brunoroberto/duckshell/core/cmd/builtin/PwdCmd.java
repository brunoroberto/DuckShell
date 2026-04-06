package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.cmd.Result;
import com.github.brunoroberto.duckshell.core.cmd.SuccessCmdResult;

import java.util.Objects;

public class PwdCmd implements ShellCommand {

    @Override
    public Result execute(Context context) {
        Objects.requireNonNull(context, "shellContext must not be null");
        return new SuccessCmdResult(context.getCurrentWorkingDirectoryAsString());
    }
}
