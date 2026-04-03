package com.github.brunoroberto.duckshell.core.cmd;

import com.github.brunoroberto.duckshell.core.cmd.builtin.*;
import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;

public class CommandResolver {

    public Command resolve(CommandNode commandNode) {
        var cmdName = CommandNames.of(commandNode.command());
        return switch (cmdName) {
            case CommandNames.EXIT -> new ExitCmd();
            case CommandNames.ECHO -> new EchoCmd(commandNode);
            case CommandNames.PWD -> new PwdCmd(commandNode);
            case CommandNames.CD -> new CdCmd(commandNode);
            case CommandNames.TYPE ->  new TypeCmd(commandNode);
            default -> null;
        };
    }
}
