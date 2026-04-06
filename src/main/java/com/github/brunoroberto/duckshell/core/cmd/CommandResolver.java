package com.github.brunoroberto.duckshell.core.cmd;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.cmd.builtin.*;
import com.github.brunoroberto.duckshell.core.cmd.ext.ExternalCommand;
import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;

import static com.github.brunoroberto.duckshell.core.cmd.CommandNames.*;

public class CommandResolver {

    public Command resolve(Context context, CommandNode commandNode) {
        var cmdName = of(commandNode.command());
        return switch (cmdName) {
            case EXIT -> new ExitCmd();
            case ECHO -> new EchoCmd(commandNode);
            case PWD -> new PwdCmd();
            case CD -> new CdCmd(commandNode);
            case TYPE ->  new TypeCmd(commandNode);
            case QUACK -> new QuackCmd();
            case CLEAR -> new ClearCmd();
            case INVALID_CMD -> buildExternalOrInvalidCommand(context, commandNode);
        };
    }

    private Command buildExternalOrInvalidCommand(Context context, CommandNode commandNode) {
        var externalCommandPath = context.getOsPath().findExecutableCommandPath(commandNode.command());
        if (externalCommandPath != null) {
            return new ExternalCommand(commandNode, externalCommandPath);
        }
        return new InvalidCmd(commandNode);
    }
}
