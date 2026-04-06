package com.github.brunoroberto.duckshell.core.io;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.OSPath;
import com.github.brunoroberto.duckshell.core.cmd.SuccessCmdResult;
import com.github.brunoroberto.duckshell.core.parser.tokens.RedirectionNode;
import com.github.brunoroberto.duckshell.core.parser.tokens.RedirectionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileOutputTest {

    @Test
    void writesStdoutToFileWithOverwrite(@TempDir Path tempDir) throws Exception {
        var context = createContextWithCwd(tempDir);
        var redirection = new RedirectionNode(RedirectionType.STDOUT_OVERWRITE, "output.txt");

        new FileOutput(context, redirection).write(new SuccessCmdResult("hello"));

        var content = Files.readString(tempDir.resolve("output.txt"));
        assertEquals("hello" + System.lineSeparator(), content);
    }

    @Test
    void overwriteReplacesExistingContent(@TempDir Path tempDir) throws Exception {
        var context = createContextWithCwd(tempDir);
        var redirection = new RedirectionNode(RedirectionType.STDOUT_OVERWRITE, "output.txt");

        new FileOutput(context, redirection).write(new SuccessCmdResult("first"));
        new FileOutput(context, redirection).write(new SuccessCmdResult("second"));

        var content = Files.readString(tempDir.resolve("output.txt"));
        assertEquals("second" + System.lineSeparator(), content);
    }

    @Test
    void appendAddsToExistingContent(@TempDir Path tempDir) throws Exception {
        var context = createContextWithCwd(tempDir);
        var redirection = new RedirectionNode(RedirectionType.STDOUT_APPEND, "output.txt");

        new FileOutput(context, redirection).write(new SuccessCmdResult("first"));
        new FileOutput(context, redirection).write(new SuccessCmdResult("second"));

        var content = Files.readString(tempDir.resolve("output.txt"));
        var expected = "first" + System.lineSeparator() + "second" + System.lineSeparator();
        assertEquals(expected, content);
    }

    @Test
    void stderrRedirectionWritesStderrContent(@TempDir Path tempDir) throws Exception {
        var context = createContextWithCwd(tempDir);
        var redirection = new RedirectionNode(RedirectionType.STDERR, "error.txt");

        new FileOutput(context, redirection).write(new SuccessCmdResult(null, "error message"));

        var content = Files.readString(tempDir.resolve("error.txt"));
        assertEquals("error message" + System.lineSeparator(), content);
    }

    @Test
    void stderrAppendRedirectionAppendsContent(@TempDir Path tempDir) throws Exception {
        var context = createContextWithCwd(tempDir);
        var redirection = new RedirectionNode(RedirectionType.STDERR_APPEND, "error.txt");

        new FileOutput(context, redirection).write(new SuccessCmdResult(null, "error1"));
        new FileOutput(context, redirection).write(new SuccessCmdResult(null, "error2"));

        var content = Files.readString(tempDir.resolve("error.txt"));
        var expected = "error1" + System.lineSeparator() + "error2" + System.lineSeparator();
        assertEquals(expected, content);
    }

    private Context createContextWithCwd(Path dir) throws FileNotFoundException {
        var context = new Context(content -> {}, new OSPath());
        context.updateCurrentWorkingDirectory(dir.toString());
        return context;
    }
}
