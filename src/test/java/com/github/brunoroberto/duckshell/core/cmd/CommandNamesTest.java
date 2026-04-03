package com.github.brunoroberto.duckshell.core.cmd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandNamesTest {

    @Test
    void echoResolvesCorrectly() {
        assertEquals(CommandNames.ECHO, CommandNames.of("echo"));
    }

    @Test
    void exitResolvesCorrectly() {
        assertEquals(CommandNames.EXIT, CommandNames.of("exit"));
    }

    @Test
    void unknownCommandResolvesToInvalidCmd() {
        assertEquals(CommandNames.INVALID_CMD, CommandNames.of("unknown"));
    }

    @Test
    void emptyStringResolvesToInvalidCmd() {
        assertEquals(CommandNames.INVALID_CMD, CommandNames.of(""));
    }

    @Test
    void getNameReturnsStringValue() {
        assertEquals("echo", CommandNames.ECHO.getName());
        assertEquals("exit", CommandNames.EXIT.getName());
        assertEquals("invalid_cmd", CommandNames.INVALID_CMD.getName());
    }

    @Test
    void lookupIsCaseSensitive() {
        assertEquals(CommandNames.INVALID_CMD, CommandNames.of("ECHO"));
        assertEquals(CommandNames.INVALID_CMD, CommandNames.of("Echo"));
    }
}
