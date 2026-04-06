package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.ErrorCmdResult;
import com.github.brunoroberto.duckshell.core.OSPath;
import com.github.brunoroberto.duckshell.core.cmd.EmptyCmdResult;
import com.github.brunoroberto.duckshell.core.cmd.SuccessCmdResult;
import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TypeCmdTest {

    private final Context context = new Context(content -> {}, new OSPath());

    @Test
    void typeBuiltinCommandReturnsShellBuiltin() {
        var cmd = new TypeCmd(new CommandNode("type", List.of("echo"), List.of()));
        var result = cmd.execute(context);
        assertInstanceOf(SuccessCmdResult.class, result);
        assertEquals("echo is a shell builtin", result.getStdOut());
        assertNull(result.getStdErr());
    }

    @Test
    void typeAnotherBuiltinCommand() {
        var cmd = new TypeCmd(new CommandNode("type", List.of("cd"), List.of()));
        var result = cmd.execute(context);
        assertInstanceOf(SuccessCmdResult.class, result);
        assertEquals("cd is a shell builtin", result.getStdOut());
    }

    @Test
    void typeUnknownCommandReturnsNotFound() {
        var cmd = new TypeCmd(new CommandNode("type", List.of("nonexistent_cmd_xyz"), List.of()));
        var result = cmd.execute(context);
        assertInstanceOf(ErrorCmdResult.class, result);
        assertNull(result.getStdOut());
        assertTrue(result.getStdErr().contains("not found"));
    }

    @Test
    void typeNoArgumentsReturnsEmpty() {
        var cmd = new TypeCmd(new CommandNode("type", List.of(), List.of()));
        var result = cmd.execute(context);
        assertInstanceOf(EmptyCmdResult.class, result);
        assertNull(result.getStdOut());
        assertNull(result.getStdErr());
    }

    @Test
    void constructorWithNullCommandNodeThrows() {
        assertThrows(NullPointerException.class, () -> new TypeCmd(null));
    }
}
