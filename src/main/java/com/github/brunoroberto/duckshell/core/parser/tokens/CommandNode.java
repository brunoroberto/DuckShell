package com.github.brunoroberto.duckshell.core.parser.tokens;

import java.util.List;

public record CommandNode(String command, List<String> arguments, List<RedirectionNode> redirections) {
}
