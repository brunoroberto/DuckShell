package com.github.brunoroberto.duckshell.core.commands;

import com.github.brunoroberto.duckshell.core.ShellContext;
import com.github.brunoroberto.duckshell.core.parser.tokens.RedirectionNode;

import java.util.List;

public interface ShellCmd {

    void execute(ShellContext shellContext, List<String> params, List<RedirectionNode> redirections);

}
