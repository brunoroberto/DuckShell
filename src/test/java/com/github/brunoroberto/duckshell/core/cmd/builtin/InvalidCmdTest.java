package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.ErrorCmdResult;
import com.github.brunoroberto.duckshell.core.OSPath;
import com.github.brunoroberto.duckshell.core.cmd.EmptyCmdResult;
import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InvalidCmdTest {

    private final Context context = new Context(content -> {}, new OSPath());

    @Test
    void invalidCommandReturnsErrorWithCommandName() {
        var cmd = new InvalidCmd(new CommandNode("foobar", List.of(), List.of()));
        var result = cmd.execute(context);
        assertInstanceOf(ErrorCmdResult.class, result);
        assertNull(result.getStdOut());
        assertEquals("foobar: command not found", result.getStdErr());
    }

    @Test
    void invalidCommandWithBlankNameReturnsEmpty() {
        var cmd = new InvalidCmd(new CommandNode("", List.of(), List.of()));
        var result = cmd.execute(context);
        assertInstanceOf(EmptyCmdResult.class, result);
        assertNull(result.getStdOut());
        assertNull(result.getStdErr());
    }

    @Test
    void invalidCommandWithNullNameReturnsEmpty() {
        var cmd = new InvalidCmd(new CommandNode(null, List.of(), List.of()));
        var result = cmd.execute(context);
        assertInstanceOf(EmptyCmdResult.class, result);
    }

    @Test
    void invalidCommandWithNullContextThrows() {
        var cmd = new InvalidCmd(new CommandNode("foobar", List.of(), List.of()));
        assertThrows(NullPointerException.class, () -> cmd.execute(null));
    }

    @Test
    void constructorWithNullCommandNodeThrows() {
        assertThrows(NullPointerException.class, () -> new InvalidCmd(null));
    }
}
