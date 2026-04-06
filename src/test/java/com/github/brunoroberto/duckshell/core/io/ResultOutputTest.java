package com.github.brunoroberto.duckshell.core.io;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.OSPath;
import com.github.brunoroberto.duckshell.core.cmd.EmptyCmdResult;
import com.github.brunoroberto.duckshell.core.cmd.SuccessCmdResult;
import com.github.brunoroberto.duckshell.core.parser.tokens.RedirectionNode;
import com.github.brunoroberto.duckshell.core.parser.tokens.RedirectionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResultOutputTest {

    private final List<String> consoleCapture = new ArrayList<>();
    private Context context;
    private ConsoleOutput consoleOutput;

    @BeforeEach
    void setUp() {
        consoleCapture.clear();
        context = new Context(content -> {}, new OSPath());
        consoleOutput = new ConsoleOutput();
    }

    @Test
    void writeWithNoRedirectionsGoesToConsole() {
        var output = new ResultOutput(context, consoleOutput);
        // Should not throw — routes to console
        assertDoesNotThrow(() -> output.write(new EmptyCmdResult()));
    }

    @Test
    void writeWithStdoutRedirectionWritesToFile(@TempDir Path tempDir) throws FileNotFoundException {
        context.updateCurrentWorkingDirectory(tempDir.toString());

        var redirections = List.of(new RedirectionNode(RedirectionType.STDOUT_OVERWRITE, "out.txt"));
        var output = new ResultOutput(context, consoleOutput).withRedirections(redirections);

        output.write(new SuccessCmdResult("hello"));

        var file = tempDir.resolve("out.txt");
        assertTrue(Files.exists(file));
    }

    @Test
    void writeWithAppendRedirectionWritesToFile(@TempDir Path tempDir) throws Exception {
        context.updateCurrentWorkingDirectory(tempDir.toString());

        var redirections = List.of(new RedirectionNode(RedirectionType.STDOUT_APPEND, "out.txt"));
        var output = new ResultOutput(context, consoleOutput).withRedirections(redirections);

        output.write(new SuccessCmdResult("first"));
        output.write(new SuccessCmdResult("second"));

        var file = tempDir.resolve("out.txt");
        var content = Files.readString(file);
        assertTrue(content.contains("first"));
        assertTrue(content.contains("second"));
    }

    @Test
    void constructorWithNullContextThrows() {
        assertThrows(NullPointerException.class, () -> new ResultOutput(null, consoleOutput));
    }

    @Test
    void constructorWithNullConsoleOutputThrows() {
        assertThrows(NullPointerException.class, () -> new ResultOutput(context, null));
    }
}
