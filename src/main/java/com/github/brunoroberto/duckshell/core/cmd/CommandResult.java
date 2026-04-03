package com.github.brunoroberto.duckshell.core.cmd;

import com.github.brunoroberto.duckshell.core.parser.tokens.RedirectionNode;

import java.util.Collections;
import java.util.List;

public record CommandResult(String output, boolean shouldPrint, boolean success, List<RedirectionNode> redirections) implements Result{

    public CommandResult(String output, List<RedirectionNode> redirections) {
        this(output, true, true, redirections);
    }

    public CommandResult(boolean success) {
        this("", false, success, Collections.emptyList());
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public String getResult() {
        return output;
    }

    @Override
    public boolean hasRedirection() {
        return redirections != null && !redirections.isEmpty();
    }

    @Override
    public List<RedirectionNode> getRedirections() {
        return redirections;
    }
}
