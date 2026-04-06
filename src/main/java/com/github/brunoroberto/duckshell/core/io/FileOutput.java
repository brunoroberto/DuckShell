package com.github.brunoroberto.duckshell.core.io;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.cmd.Result;
import com.github.brunoroberto.duckshell.core.parser.tokens.RedirectionNode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileOutput implements ShellOutput {

    private final Context context;
    private final RedirectionNode redirection;

    public FileOutput(Context context, RedirectionNode redirection) {
        this.context = context;
        this.redirection = redirection;
    }

    @Override
    public void write(Result result) {
        var targetPath = this.context.getCurrentWorkingDirectory().resolve(this.redirection.target());
        createOutputFile(targetPath, buildResultContent(result), this.redirection.type().isAppendRedirection());
    }

    private String buildResultContent(Result result) {
        var content = this.redirection.type().isErrorRedirection() ? result.getStdErr() : result.getStdOut();
        return content + System.lineSeparator();
    }

    private void createOutputFile(Path target, String content, boolean append) {
        try {
            var options = List.of(StandardOpenOption.CREATE, (append ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING));
            Files.write(target, content.getBytes(StandardCharsets.UTF_8), options.toArray(new StandardOpenOption[0]));
        } catch (IOException e) {
            throw new RuntimeException("Could not create output file redirection", e);
        }
    }

}
