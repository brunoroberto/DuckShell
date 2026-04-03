package com.github.brunoroberto.duckshell.core.commands;

import com.github.brunoroberto.duckshell.core.parser.tokens.RedirectionNode;

import java.util.List;

public record CommandResult(String output, boolean shouldPrint, boolean success, List<RedirectionNode> redirections) {
}
