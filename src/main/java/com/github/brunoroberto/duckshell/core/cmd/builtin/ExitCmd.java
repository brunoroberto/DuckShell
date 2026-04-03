package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.cmd.CommandResult;

public class ExitCmd implements ShellCommand {

    @Override
    public CommandResult execute(Context context) {
        System.exit(0);
        return new CommandResult(null, false, true, null);
    }
}
