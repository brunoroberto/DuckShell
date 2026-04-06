package com.github.brunoroberto.duckshell.core.parser.tokens;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedirectionTypeTest {

    @Test
    void stdoutOverwriteIsNotErrorRedirection() {
        assertFalse(RedirectionType.STDOUT_OVERWRITE.isErrorRedirection());
    }

    @Test
    void stdoutOverwriteIsNotAppendRedirection() {
        assertFalse(RedirectionType.STDOUT_OVERWRITE.isAppendRedirection());
    }

    @Test
    void stdoutAppendIsNotErrorRedirection() {
        assertFalse(RedirectionType.STDOUT_APPEND.isErrorRedirection());
    }

    @Test
    void stdoutAppendIsAppendRedirection() {
        assertTrue(RedirectionType.STDOUT_APPEND.isAppendRedirection());
    }

    @Test
    void stderrIsErrorRedirection() {
        assertTrue(RedirectionType.STDERR.isErrorRedirection());
    }

    @Test
    void stderrIsNotAppendRedirection() {
        assertFalse(RedirectionType.STDERR.isAppendRedirection());
    }

    @Test
    void stderrAppendIsErrorRedirection() {
        assertTrue(RedirectionType.STDERR_APPEND.isErrorRedirection());
    }

    @Test
    void stderrAppendIsAppendRedirection() {
        assertTrue(RedirectionType.STDERR_APPEND.isAppendRedirection());
    }
}
