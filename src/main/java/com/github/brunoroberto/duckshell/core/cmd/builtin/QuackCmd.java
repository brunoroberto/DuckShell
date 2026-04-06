package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.cmd.Result;
import com.github.brunoroberto.duckshell.core.cmd.SuccessCmdResult;

public class QuackCmd implements ShellCommand{

    private static final String DUCK_MESSAGE = "\uD83E\uDD86 Quack!";

    @Override
    public Result execute(Context context) {
        return new SuccessCmdResult(DUCK_MESSAGE);
    }
}
