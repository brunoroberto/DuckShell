package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.cmd.EmptyCmdResult;
import com.github.brunoroberto.duckshell.core.cmd.Result;
import com.github.brunoroberto.duckshell.core.cmd.SuccessCmdResult;
import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;

import java.util.Objects;

public class EchoCmd implements ShellCommand {

    private final CommandNode commandNode;

    public EchoCmd(CommandNode commandNode) {
        this.commandNode = Objects.requireNonNull(commandNode, "commandNode must not be null");
    }

    @Override
    public Result execute(Context context) {
        Objects.requireNonNull(context, "shell context must not be null");
        if (this.commandNode.hasArguments()) {
            var content = String.join(" ", this.commandNode.arguments());
            return new SuccessCmdResult(content);
        }
        return new EmptyCmdResult();
    }
}
