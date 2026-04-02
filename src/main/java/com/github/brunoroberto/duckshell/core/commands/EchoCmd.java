package com.github.brunoroberto.duckshell.core.commands;

import com.github.brunoroberto.duckshell.core.ShellContext;
import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;
import com.github.brunoroberto.duckshell.core.parser.tokens.RedirectionNode;

import java.util.List;
import java.util.Objects;

public class EchoCmd implements ShellCmd {

    @Override
    public void execute(ShellContext shellContext, List<String> params, List<RedirectionNode> redirections) {
        Objects.requireNonNull(shellContext, "shell context must not be null");
        Objects.requireNonNull(params, "params must not be null");
        Objects.requireNonNull(redirections, "redirections must not be null");

    }
}
