package com.github.brunoroberto.duckshell.core.parser;

import com.github.brunoroberto.duckshell.core.parser.tokens.RedirectionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DuckParserTest {

    private DuckParser parser;

    @BeforeEach
    void setUp() {
        parser = new DuckParser();
    }

    @Test
    void parseSimpleCommand() {
        var node = parser.parse("echo");
        assertEquals("echo", node.command());
        assertTrue(node.arguments().isEmpty());
        assertTrue(node.redirections().isEmpty());
    }

    @Test
    void parseCommandWithArguments() {
        var node = parser.parse("echo hello world");
        assertEquals("echo", node.command());
        assertEquals(2, node.arguments().size());
        assertEquals("hello", node.arguments().get(0));
        assertEquals("world", node.arguments().get(1));
    }

    @Test
    void parseCommandWithQuotedArgument() {
        var node = parser.parse("echo \"hello world\"");
        assertEquals("echo", node.command());
        assertEquals(1, node.arguments().size());
        assertEquals("hello world", node.arguments().get(0));
    }

    @Test
    void parseCommandWithMixedArguments() {
        var node = parser.parse("echo hello \"big world\" foo");
        assertEquals("echo", node.command());
        assertEquals(3, node.arguments().size());
        assertEquals("hello", node.arguments().get(0));
        assertEquals("big world", node.arguments().get(1));
        assertEquals("foo", node.arguments().get(2));
    }

    @Test
    void parseStdoutRedirect() {
        var node = parser.parse("echo hello > stdout.txt");
        assertEquals("echo", node.command());
        assertEquals(1, node.arguments().size());
        assertEquals(1, node.redirections().size());
        assertEquals(RedirectionType.STDOUT_OVERWRITE, node.redirections().get(0).type());
        assertEquals("stdout.txt", node.redirections().get(0).target());
    }

    @Test
    void parseAppendRedirect() {
        var node = parser.parse("echo hello >> stdout.txt");
        assertEquals(1, node.redirections().size());
        assertEquals(RedirectionType.STDOUT_APPEND, node.redirections().get(0).type());
        assertEquals("stdout.txt", node.redirections().get(0).target());
    }

    @Test
    void parseRedirectWithQuotedTarget() {
        var node = parser.parse("echo hello > \"my file.txt\"");
        assertEquals(1, node.redirections().size());
        assertEquals("my file.txt", node.redirections().get(0).target());
    }

    @Test
    void emptyReturnsEmptyCommand() {
        var emptyCommandNode = parser.parse("");
        assertNotNull(emptyCommandNode);
        assertTrue(emptyCommandNode.command().isEmpty());
        assertTrue(emptyCommandNode.arguments().isEmpty());
        assertTrue(emptyCommandNode.redirections().isEmpty());
    }

    @Test
    void parseRedirectMissingTargetThrows() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("echo hello >"));
    }

    @Test
    void parserIsReusable() {
        var node1 = parser.parse("echo first");
        var node2 = parser.parse("exit");
        assertEquals("echo", node1.command());
        assertEquals("exit", node2.command());
    }
}
