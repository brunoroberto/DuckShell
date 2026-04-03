package com.github.brunoroberto.duckshell.core.cmd;

import com.github.brunoroberto.duckshell.core.parser.tokens.RedirectionNode;

import java.util.Collections;
import java.util.List;

public record CommandResult(String output, boolean shouldPrint, boolean success, List<RedirectionNode> redirections) {

    public CommandResult(String output, List<RedirectionNode> redirections) {
        this(output, true, true, redirections);
    }

    public CommandResult(boolean success) {
        this("", false, success, Collections.emptyList());
    }

}
