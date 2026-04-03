package com.github.brunoroberto.duckshell.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShellContextTest {

    @Test
    void workingDirectoryDefaultsToUserDir() {
        var context = new ShellContext(content -> {});
        assertEquals(System.getProperty("user.dir"), context.getCurrentWorkingDirectoryAsString());
    }

    @Test
    void getCurrentWorkingDirectoryReturnsPath() {
        var context = new ShellContext(content -> {});
        assertNotNull(context.getCurrentWorkingDirectory());
        assertEquals(context.getCurrentWorkingDirectoryAsString(),
                context.getCurrentWorkingDirectory().toString());
    }

    @Test
    void defaultOutputIsStored() {
        var context = new ShellContext(content -> {});
        assertNotNull(context.getDefaultOutput());
    }

    @Test
    void nullOutputThrows() {
        assertThrows(NullPointerException.class, () -> new ShellContext(null));
    }
}
