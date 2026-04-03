package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.OSPath;
import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;
import com.github.brunoroberto.duckshell.core.parser.tokens.RedirectionNode;
import com.github.brunoroberto.duckshell.core.parser.tokens.RedirectionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CdCmdTest {

    private final Context context = new Context(content -> {}, new OSPath());

    @Test
    void cdToExistingDirectory(@TempDir Path tempDir) {
        var cmd = new CdCmd(new CommandNode("cd", List.of(tempDir.toString()), List.of()));
        var result = cmd.execute(context);
        assertEquals("", result.output());
        assertFalse(result.shouldPrint());
        assertTrue(result.success());
        assertEquals(tempDir.toString(), context.getCurrentWorkingDirectoryAsString());
    }

    @Test
    void cdToNonExistentDirectoryReturnsError() {
        var cmd = new CdCmd(new CommandNode("cd", List.of("/nonexistent/path/xyz"), List.of()));
        var result = cmd.execute(context);
        assertTrue(result.output().contains("does not exist"));
        assertTrue(result.shouldPrint());
        assertFalse(result.success());
    }

    @Test
    void cdWithNoArgumentsGoesToHome() {
        var cmd = new CdCmd(new CommandNode("cd", List.of(), List.of()));
        var result = cmd.execute(context);
        assertTrue(result.success());
        assertEquals(System.getenv("HOME"), context.getCurrentWorkingDirectoryAsString());
    }

    @Test
    void cdWithTildeGoesToHome() {
        var cmd = new CdCmd(new CommandNode("cd", List.of("~"), List.of()));
        var result = cmd.execute(context);
        assertTrue(result.success());
        assertEquals(System.getenv("HOME"), context.getCurrentWorkingDirectoryAsString());
    }

    @Test
    void cdToRelativeDirectory(@TempDir Path tempDir) throws Exception {
        var subDir = tempDir.resolve("subdir");
        subDir.toFile().mkdir();

        // First cd to tempDir
        var cmd1 = new CdCmd(new CommandNode("cd", List.of(tempDir.toString()), List.of()));
        cmd1.execute(context);

        // Then cd to relative subdir
        var cmd2 = new CdCmd(new CommandNode("cd", List.of("subdir"), List.of()));
        var result = cmd2.execute(context);
        assertTrue(result.success());
        assertEquals(subDir.toString(), context.getCurrentWorkingDirectoryAsString());
    }

    @Test
    void cdPreservesRedirections(@TempDir Path tempDir) {
        var redirections = List.of(new RedirectionNode(RedirectionType.STDOUT_OVERWRITE, "out.txt"));
        var cmd = new CdCmd(new CommandNode("cd", List.of(tempDir.toString()), redirections));
        var result = cmd.execute(context);
        assertEquals(1, result.redirections().size());
        assertEquals("out.txt", result.redirections().get(0).target());
    }

    @Test
    void cdWithNullContextThrows() {
        var cmd = new CdCmd(new CommandNode("cd", List.of("/tmp"), List.of()));
        assertThrows(NullPointerException.class, () -> cmd.execute(null));
    }

    @Test
    void constructorWithNullCommandNodeThrows() {
        assertThrows(NullPointerException.class, () -> new CdCmd(null));
    }
}
