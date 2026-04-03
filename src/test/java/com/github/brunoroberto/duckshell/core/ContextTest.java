package com.github.brunoroberto.duckshell.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContextTest {

    @Test
    void workingDirectoryDefaultsToUserDir() {
        var context = new Context(content -> {}, new OSPath());
        assertEquals(System.getProperty("user.dir"), context.getCurrentWorkingDirectoryAsString());
    }

    @Test
    void getCurrentWorkingDirectoryReturnsPath() {
        var context = new Context(content -> {}, new OSPath());
        assertNotNull(context.getCurrentWorkingDirectory());
        assertEquals(context.getCurrentWorkingDirectoryAsString(),
                context.getCurrentWorkingDirectory().toString());
    }

    @Test
    void defaultOutputIsStored() {
        var context = new Context(content -> {}, new OSPath());
        assertNotNull(context.getDefaultOutput());
    }

    @Test
    void nullOutputThrows() {
        assertThrows(NullPointerException.class, () -> new Context(null, null));
    }
}
