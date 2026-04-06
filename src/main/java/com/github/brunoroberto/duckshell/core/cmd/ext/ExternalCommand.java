package com.github.brunoroberto.duckshell.core.cmd.ext;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.cmd.Command;
import com.github.brunoroberto.duckshell.core.cmd.Result;
import com.github.brunoroberto.duckshell.core.cmd.SuccessCmdResult;
import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExternalCommand implements Command {

    private final CommandNode commandNode;
    private final Path externalCommandPath;

    public ExternalCommand(CommandNode commandNode, Path externalCommandPath) {
        this.commandNode = Objects.requireNonNull(commandNode, "commandNode cannot be null");
        this.externalCommandPath = Objects.requireNonNull(externalCommandPath, "externalCommandPath cannot be null");
    }

    @Override
    public Result execute(Context context) {
        Objects.requireNonNull(context, "context must not be null");

        List<String> inputs = new ArrayList<>();
        inputs.add(commandNode.command());
        inputs.addAll(commandNode.arguments());

        var executor = Executors.newFixedThreadPool(2);
        try {
            var processBuilder = new ProcessBuilder(inputs);
            processBuilder.directory(context.getCurrentWorkingDirectory().toFile());

            var process = processBuilder.start();

            Future<String> stdoutFuture = executor.submit(readStream(process.getInputStream()));
            Future<String> stderrFuture = executor.submit(readStream(process.getErrorStream()));

            process.getOutputStream().close();

            String stdout = stdoutFuture.get();
            String stderr = stderrFuture.get();

            return new SuccessCmdResult(stdout, stderr);

        } catch (IOException e) {
            throw new RuntimeException("Failed to start process", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Process execution interrupted", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Failed while reading process stdout", e);
        } finally {
            executor.shutdown();
        }
    }

    private Callable<String> readStream(InputStream inputStream) {
        return () -> {
            var output = new StringBuffer();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                boolean first = true;
                while ((line = reader.readLine()) != null) {
                    if (!first) {
                        output.append(System.lineSeparator());
                    }
                    output.append(line);
                    first = false;
                }
            }
            return output.toString();
        };
    }

}
