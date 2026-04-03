package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.cmd.CommandResult;
import com.github.brunoroberto.duckshell.core.cmd.Result;
import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;

import java.util.Objects;

public class QuackCmd implements ShellCommand{

    private static final String DUCK_MESSAGE = "\uD83E\uDD86 Quack!";

    private final CommandNode commandNode;

    public QuackCmd(CommandNode commandNode) {
        this.commandNode = Objects.requireNonNull(commandNode, "commandNode must not be null");
    }

    @Override
    public Result execute(Context context) {
        return new CommandResult(DUCK_MESSAGE, this.commandNode.redirections());
    }
}
