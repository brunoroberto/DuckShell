package com.github.brunoroberto.duckshell.core.cmd;

import com.github.brunoroberto.duckshell.core.cmd.builtin.EchoCmd;
import com.github.brunoroberto.duckshell.core.cmd.builtin.ExitCmd;
import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;

public class CommandResolver {

    public Command resolve(CommandNode commandNode) {
        var cmdName = CommandNames.of(commandNode.command());
        return switch (cmdName) {
            case CommandNames.ECHO -> new EchoCmd(commandNode);
            case CommandNames.EXIT -> new ExitCmd();
            default -> null;
        };
    }
}
