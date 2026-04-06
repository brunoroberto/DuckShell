package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.ErrorCmdResult;
import com.github.brunoroberto.duckshell.core.cmd.CommandNames;
import com.github.brunoroberto.duckshell.core.cmd.EmptyCmdResult;
import com.github.brunoroberto.duckshell.core.cmd.Result;
import com.github.brunoroberto.duckshell.core.cmd.SuccessCmdResult;
import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;

import java.nio.file.Path;
import java.util.Objects;

public class TypeCmd implements ShellCommand{

    private static final String FOUND_BUILT_IN_CMD = "%s is a shell builtin";
    private static final String FOUND_EXTERNAL_CMD = "%s is %s/%s";
    private static final String COMMAND_NOT_FOUND_ERROR = "%s: not found";

    private final CommandNode commandNode;

    public TypeCmd(CommandNode commandNode) {
        Objects.requireNonNull(commandNode, "commandNode must not be null");
        this.commandNode = commandNode;
    }

    @Override
    public Result execute(Context context) {
        if (!this.commandNode.hasArguments()) {
            return new EmptyCmdResult();
        }

        String targetCmd = this.commandNode.arguments().getFirst();
        CommandNames commandName = CommandNames.of(targetCmd);

        if (commandName.isBuiltIn()) {
            return new SuccessCmdResult(String.format(FOUND_BUILT_IN_CMD, targetCmd));
        }

        Path externalPath = context.getOsPath().findExecutableCommandPath(targetCmd);
        if (externalPath != null) {
            return new SuccessCmdResult(String.format(FOUND_EXTERNAL_CMD, targetCmd, externalPath, targetCmd));
        }

        return new ErrorCmdResult(String.format(COMMAND_NOT_FOUND_ERROR, targetCmd));
    }

}
