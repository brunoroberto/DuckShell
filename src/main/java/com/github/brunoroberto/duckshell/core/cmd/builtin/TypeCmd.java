package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.cmd.CommandNames;
import com.github.brunoroberto.duckshell.core.cmd.CommandResult;
import com.github.brunoroberto.duckshell.core.cmd.EmptyResult;
import com.github.brunoroberto.duckshell.core.cmd.Result;
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
        if (hasNoArguments()) {
            return new EmptyResult();
        }

        String targetCmd = this.commandNode.arguments().getFirst();
        CommandNames commandName = CommandNames.of(targetCmd);

        if (isBuiltIn(commandName)) {
            return builtInResult(targetCmd);
        }

        Path externalPath = context.getOsPath().findExecutableCommandPath(targetCmd);
        if (externalPath != null) {
            return externalResult(targetCmd, externalPath);
        }

        return commandNotFoundResult(targetCmd);
    }

    private boolean hasNoArguments() {
        return this.commandNode.arguments() == null || this.commandNode.arguments().isEmpty();
    }

    private boolean isBuiltIn(CommandNames commandName) {
        return commandName != CommandNames.INVALID_CMD;
    }

    private CommandResult builtInResult(String targetCmd) {
        return new CommandResult(
                String.format(FOUND_BUILT_IN_CMD, targetCmd),
                this.commandNode.redirections()
        );
    }

    private CommandResult externalResult(String targetCmd, Path externalPath) {
        return new CommandResult(
                String.format(FOUND_EXTERNAL_CMD, targetCmd, externalPath, targetCmd),
                this.commandNode.redirections()
        );
    }

    private CommandResult commandNotFoundResult(String targetCmd) {
        return new CommandResult(
                "",
                String.format(COMMAND_NOT_FOUND_ERROR, targetCmd),
                true,
                false,
                this.commandNode.redirections()
        );
    }
}
