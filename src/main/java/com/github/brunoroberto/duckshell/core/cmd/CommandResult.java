package com.github.brunoroberto.duckshell.core.cmd;

import com.github.brunoroberto.duckshell.core.parser.tokens.RedirectionNode;

import java.util.Collections;
import java.util.List;

public record CommandResult(String stdout, String stderr, boolean shouldPrint, boolean success, List<RedirectionNode> redirections) implements Result {

    public CommandResult(String stdout, List<RedirectionNode> redirections) {
        this(stdout, "", true, true, redirections);
    }

    public CommandResult(boolean success) {
        this("", "", false, success, Collections.emptyList());
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public String getStdOut() {
        return stdout;
    }

    @Override
    public String getStdErr() {
        return stderr;
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
