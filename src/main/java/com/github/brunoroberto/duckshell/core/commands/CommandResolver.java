package com.github.brunoroberto.duckshell.core.commands;

import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;

public class CommandResolver {

    public ShellCmd resolve(CommandNode commandNode) {
        var cmdName = CmdNames.of(commandNode.command());
        return switch (cmdName) {
            case CmdNames.ECHO -> new EchoCmd();
            default -> null;
        };
    }
}
