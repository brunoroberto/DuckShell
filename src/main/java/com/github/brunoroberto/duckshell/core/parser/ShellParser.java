package com.github.brunoroberto.duckshell.core.parser;

import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;

public interface ShellParser {

    CommandNode parse(String input);

}
