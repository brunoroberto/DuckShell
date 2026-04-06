package com.github.brunoroberto.duckshell.core.io;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.cmd.Result;
import com.github.brunoroberto.duckshell.core.parser.tokens.RedirectionNode;
import com.github.brunoroberto.duckshell.core.parser.tokens.RedirectionType;

import java.util.List;
import java.util.Objects;

public class ResultOutput implements ShellOutput {

    private final Context context;
    private final ConsoleOutput consoleOutput;

    private List<RedirectionNode> redirections;

    public ResultOutput(Context context, ConsoleOutput consoleOutput) {
        this.context = Objects.requireNonNull(context,  "context must not be null");
        this.consoleOutput = Objects.requireNonNull(consoleOutput, "consoleOutput must not be null");
    }

    public ResultOutput withRedirections(List<RedirectionNode> redirections) {
        this.redirections = redirections;
        return this;
    }

    @Override
    public void write(Result result) {
        if (hasRedirections()) {
            var stdOutRedirect = getAnyRedirection(List.of(RedirectionType.STDOUT_OVERWRITE, RedirectionType.STDOUT_APPEND));
            if (stdOutRedirect != null) {
                new FileOutput(context, stdOutRedirect).write(result);
                return;
            }

            var errorRedirect = getAnyRedirection(List.of(RedirectionType.STDERR, RedirectionType.STDERR_APPEND));
            if (errorRedirect != null) {
                new FileOutput(context, errorRedirect).write(result);
            }
        }
        this.consoleOutput.write(result);
    }

    private boolean hasRedirections() {
        return redirections != null && !redirections.isEmpty();
    }

    private RedirectionNode getAnyRedirection(List<RedirectionType> types) {
        return redirections.stream()
                .filter(r -> types.contains(r.type()))
                .findFirst()
                .orElse(null);
    }

}
