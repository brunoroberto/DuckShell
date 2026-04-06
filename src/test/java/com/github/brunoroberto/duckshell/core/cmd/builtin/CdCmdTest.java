package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.ErrorCmdResult;
import com.github.brunoroberto.duckshell.core.OSPath;
import com.github.brunoroberto.duckshell.core.cmd.EmptyCmdResult;
import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;
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
        assertInstanceOf(EmptyCmdResult.class, result);
        assertNull(result.getStdOut());
        assertNull(result.getStdErr());
        assertEquals(tempDir.toString(), context.getCurrentWorkingDirectoryAsString());
    }

    @Test
    void cdToNonExistentDirectoryReturnsError() {
        var cmd = new CdCmd(new CommandNode("cd", List.of("/nonexistent/path/xyz"), List.of()));
        var result = cmd.execute(context);
        assertInstanceOf(ErrorCmdResult.class, result);
        assertNull(result.getStdOut());
        assertTrue(result.getStdErr().contains("does not exist"));
    }

    @Test
    void cdWithNoArgumentsGoesToHome() {
        var cmd = new CdCmd(new CommandNode("cd", List.of(), List.of()));
        var result = cmd.execute(context);
        assertInstanceOf(EmptyCmdResult.class, result);
        assertNull(result.getStdOut());
        assertNull(result.getStdErr());
        assertEquals(System.getenv("HOME"), context.getCurrentWorkingDirectoryAsString());
    }

    @Test
    void cdWithTildeGoesToHome() {
        var cmd = new CdCmd(new CommandNode("cd", List.of("~"), List.of()));
        var result = cmd.execute(context);
        assertInstanceOf(EmptyCmdResult.class, result);
        assertNull(result.getStdOut());
        assertNull(result.getStdErr());
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
        assertInstanceOf(EmptyCmdResult.class, result);
        assertNull(result.getStdOut());
        assertNull(result.getStdErr());
        assertEquals(subDir.toString(), context.getCurrentWorkingDirectoryAsString());
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
