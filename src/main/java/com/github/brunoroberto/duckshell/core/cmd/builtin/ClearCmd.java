package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.cmd.SuccessCmdResult;
import com.github.brunoroberto.duckshell.core.cmd.Result;

public class ClearCmd implements ShellCommand {

    @Override
    public Result execute(Context context) {
        var clearSequence = "\u001B[H\u001B[2J";;
        return new SuccessCmdResult(clearSequence, null);
    }
}
