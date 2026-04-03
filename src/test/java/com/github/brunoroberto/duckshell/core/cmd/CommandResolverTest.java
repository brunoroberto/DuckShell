package com.github.brunoroberto.duckshell.core.cmd;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.cmd.builtin.EchoCmd;
import com.github.brunoroberto.duckshell.core.cmd.builtin.ExitCmd;
import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommandResolverTest {

    private CommandResolver resolver;
    private Context context;

    @BeforeEach
    void setUp() {
        resolver = new CommandResolver();
        context = null;
    }

    @Test
    void resolvesEchoCommand() {
        var node = new CommandNode("echo", List.of("hello"), List.of());
        var command = resolver.resolve(context, node);
        assertInstanceOf(EchoCmd.class, command);
    }

    @Test
    void resolvesExitCommand() {
        var node = new CommandNode("exit", List.of(), List.of());
        var command = resolver.resolve(context, node);
        assertInstanceOf(ExitCmd.class, command);
    }

    @Test
    void unknownCommandReturnsNull() {
        var node = new CommandNode("ls", List.of(), List.of());
        var command = resolver.resolve(context, node);
        assertNull(command);
    }
}
