package com.github.brunoroberto.duckshell.core.commands;

import com.github.brunoroberto.duckshell.core.ShellContext;
import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;
import com.github.brunoroberto.duckshell.core.parser.tokens.RedirectionNode;

import java.util.List;

public interface Command {

    CommandResult execute(ShellContext shellContext);

}
