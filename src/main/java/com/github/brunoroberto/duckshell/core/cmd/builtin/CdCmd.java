package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.ErrorCmdResult;
import com.github.brunoroberto.duckshell.core.cmd.EmptyCmdResult;
import com.github.brunoroberto.duckshell.core.cmd.Result;
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
    public Result execute(Context context) {
        Objects.requireNonNull(context, "shellContext must not be null");
        var targetDir = getTargetDir(this.commandNode.arguments());
        try {
            context.updateCurrentWorkingDirectory(targetDir);
        } catch (FileNotFoundException e) {
            return new ErrorCmdResult(e.getMessage());
        }
        return new EmptyCmdResult();
    }

    private String getTargetDir(List<String> arguments) {
        var fallbackDir = System.getenv("HOME");
        if (arguments == null || arguments.isEmpty() || arguments.contains("~")) {
            return fallbackDir;
        }
        return arguments.getFirst();
    }
}
