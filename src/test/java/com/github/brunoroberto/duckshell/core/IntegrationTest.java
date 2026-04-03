package com.github.brunoroberto.duckshell.core;

import com.github.brunoroberto.duckshell.core.cmd.CommandExecutorFactory;
import com.github.brunoroberto.duckshell.core.cmd.CommandResolver;
import com.github.brunoroberto.duckshell.core.parser.DuckParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationTest {

    private DuckParser parser;
    private CommandResolver resolver;
    private Context context;

    @BeforeEach
    void setUp() {
        parser = new DuckParser();
        resolver = new CommandResolver();
        context = new Context(content -> {}, new OSPath());
    }

    @Test
    void fullPipelineEchoSimple() {
        var node = parser.parse("echo hello");
        var command = resolver.resolve(node);
        var executor = CommandExecutorFactory.create(command);
        var result = executor.execute(context, command);

        assertEquals("hello", result.output());
        assertTrue(result.shouldPrint());
        assertTrue(result.success());
    }

    @Test
    void fullPipelineEchoMultipleArgs() {
        var node = parser.parse("echo hello beautiful world");
        var command = resolver.resolve(node);
        var executor = CommandExecutorFactory.create(command);
        var result = executor.execute(context, command);

        assertEquals("hello beautiful world", result.output());
    }

    @Test
    void fullPipelineEchoQuotedString() {
        var node = parser.parse("echo \"hello world\"");
        var command = resolver.resolve(node);
        var executor = CommandExecutorFactory.create(command);
        var result = executor.execute(context, command);

        assertEquals("hello world", result.output());
    }

    @Test
    void fullPipelineEchoWithRedirection() {
        var node = parser.parse("echo hello > out.txt");
        var command = resolver.resolve(node);
        var executor = CommandExecutorFactory.create(command);
        var result = executor.execute(context, command);

        assertEquals("hello", result.output());
        assertEquals(1, result.redirections().size());
        assertEquals("out.txt", result.redirections().get(0).target());
    }

    @Test
    void fullPipelineEchoNoArgs() {
        var node = parser.parse("echo");
        var command = resolver.resolve(node);
        var executor = CommandExecutorFactory.create(command);
        var result = executor.execute(context, command);

        assertNull(result.output());
        assertFalse(result.shouldPrint());
        assertTrue(result.success());
    }

    @Test
    void unknownCommandResolvesToNull() {
        var node = parser.parse("ls -la");
        var command = resolver.resolve(node);
        assertNull(command);
    }
}
