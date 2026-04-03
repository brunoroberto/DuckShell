package com.github.brunoroberto.duckshell.core.cmd;

import com.github.brunoroberto.duckshell.core.parser.tokens.RedirectionNode;

import java.util.List;

public interface Result {

    boolean isSuccess();

    boolean shouldPrint();

    String getResult();

    boolean hasRedirection();

    List<RedirectionNode> getRedirections();

}
