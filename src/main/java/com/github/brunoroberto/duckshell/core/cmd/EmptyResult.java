package com.github.brunoroberto.duckshell.core.cmd;

import com.github.brunoroberto.duckshell.core.parser.tokens.RedirectionNode;

import java.util.List;

public record EmptyResult() implements Result {

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public boolean shouldPrint() {
        return false;
    }

    @Override
    public String getResult() {
        return null;
    }

    @Override
    public boolean hasRedirection() {
        return false;
    }

    @Override
    public List<RedirectionNode> getRedirections() {
        return List.of();
    }
}
