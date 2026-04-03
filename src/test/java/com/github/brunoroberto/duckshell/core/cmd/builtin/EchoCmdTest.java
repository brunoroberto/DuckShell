package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.OSPath;
import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;
import com.github.brunoroberto.duckshell.core.parser.tokens.RedirectionNode;
import com.github.brunoroberto.duckshell.core.parser.tokens.RedirectionType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EchoCmdTest {

    private final Context context = new Context(content -> {}, new OSPath());

    @Test
    void echoSingleArgument() {
        var cmd = new EchoCmd(new CommandNode("echo", List.of("hello"), List.of()));
        var result = cmd.execute(context);
        assertEquals("hello", result.getStdOut());
        assertTrue(result.shouldPrint());
        assertTrue(result.isSuccess());
    }

    @Test
    void echoMultipleArgumentsJoinedWithSpaces() {
        var cmd = new EchoCmd(new CommandNode("echo", List.of("hello", "world"), List.of()));
        var result = cmd.execute(context);
        assertEquals("hello world", result.getStdOut());
        assertTrue(result.shouldPrint());
    }

    @Test
    void echoNoArgumentsReturnsNullOutput() {
        var cmd = new EchoCmd(new CommandNode("echo", List.of(), List.of()));
        var result = cmd.execute(context);
        assertNull(result.getStdOut());
        assertFalse(result.shouldPrint());
        assertTrue(result.isSuccess());
    }

    @Test
    void echoPreservesRedirections() {
        var redirections = List.of(new RedirectionNode(RedirectionType.STDOUT_OVERWRITE, "out.txt"));
        var cmd = new EchoCmd(new CommandNode("echo", List.of("hello"), redirections));
        var result = cmd.execute(context);
        assertTrue(result.hasRedirection());
        assertEquals("out.txt", result.getRedirections().getFirst().target());
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
