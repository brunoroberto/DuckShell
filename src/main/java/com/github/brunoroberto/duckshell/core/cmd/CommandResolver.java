package com.github.brunoroberto.duckshell.core.cmd;

import com.github.brunoroberto.duckshell.core.cmd.builtin.CdCmd;
import com.github.brunoroberto.duckshell.core.cmd.builtin.EchoCmd;
import com.github.brunoroberto.duckshell.core.cmd.builtin.ExitCmd;
import com.github.brunoroberto.duckshell.core.cmd.builtin.PwdCmd;
import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;

public class CommandResolver {

    public Command resolve(CommandNode commandNode) {
        var cmdName = CommandNames.of(commandNode.command());
        return switch (cmdName) {
            case CommandNames.EXIT -> new ExitCmd();
            case CommandNames.ECHO -> new EchoCmd(commandNode);
            case CommandNames.PWD -> new PwdCmd(commandNode);
            case CommandNames.CD -> new CdCmd(commandNode);
            default -> null;
        };
    }
}
