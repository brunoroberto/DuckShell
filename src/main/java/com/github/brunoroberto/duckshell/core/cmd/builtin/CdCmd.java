package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.cmd.CommandResult;
import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;

public class CdCmd implements ShellCommand{

    private final CommandNode commandNode;

    public CdCmd(CommandNode commandNode) {
        Objects.requireNonNull(commandNode, "commandNode must not be null");
        this.commandNode = commandNode;
    }

    @Override
    public CommandResult execute(Context context) {
        Objects.requireNonNull(context, "shellContext must not be null");
        var targetDir = getTargetDir(this.commandNode.arguments());
        try {
            context.updateCurrentWorkingDirectory(targetDir);
        } catch (FileNotFoundException e) {
            return new CommandResult(e.getMessage(), true, false, commandNode.redirections());
        }
        return new CommandResult("", false, true, commandNode.redirections());
    }

    private String getTargetDir(List<String> arguments) {
        var fallbackDir = System.getenv("HOME");
        if (arguments == null || arguments.isEmpty() || arguments.contains("~")) {
            return fallbackDir;
        }
        return arguments.getFirst();
    }
}
