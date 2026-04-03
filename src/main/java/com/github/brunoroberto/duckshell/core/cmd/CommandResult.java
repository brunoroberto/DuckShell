package com.github.brunoroberto.duckshell.core.cmd;

import com.github.brunoroberto.duckshell.core.parser.tokens.RedirectionNode;

import java.util.List;

public record CommandResult(String output, boolean shouldPrint, boolean success, List<RedirectionNode> redirections) {
}
