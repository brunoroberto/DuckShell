package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.OSPath;
import com.github.brunoroberto.duckshell.core.cmd.EmptyCmdResult;
import com.github.brunoroberto.duckshell.core.cmd.SuccessCmdResult;
import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EchoCmdTest {

    private final Context context = new Context(content -> {}, new OSPath());

    @Test
    void echoSingleArgument() {
        var cmd = new EchoCmd(new CommandNode("echo", List.of("hello"), List.of()));
        var result = cmd.execute(context);
        assertInstanceOf(SuccessCmdResult.class, result);
        assertEquals("hello", result.getStdOut());
        assertNull(result.getStdErr());
    }

    @Test
    void echoMultipleArgumentsJoinedWithSpaces() {
        var cmd = new EchoCmd(new CommandNode("echo", List.of("hello", "world"), List.of()));
        var result = cmd.execute(context);
        assertInstanceOf(SuccessCmdResult.class, result);
        assertEquals("hello world", result.getStdOut());
        assertNull(result.getStdErr());
    }

    @Test
    void echoNoArgumentsReturnsNullOutput() {
        var cmd = new EchoCmd(new CommandNode("echo", List.of(), List.of()));
        var result = cmd.execute(context);
        assertInstanceOf(EmptyCmdResult.class, result);
        assertNull(result.getStdOut());
        assertNull(result.getStdErr());
    }

    @Test
    void echoWithNullContextThrows() {
        var cmd = new EchoCmd(new CommandNode("echo", List.of("hello"), List.of()));
        assertThrows(NullPointerException.class, () -> cmd.execute(null));
    }

    @Test
    void constructorWithNullCommandNodeThrows() {
        assertThrows(NullPointerException.class, () -> new EchoCmd(null));
    }
}
