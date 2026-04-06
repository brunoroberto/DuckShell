package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.OSPath;
import com.github.brunoroberto.duckshell.core.cmd.SuccessCmdResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClearCmdTest {

    private final Context context = new Context(content -> {}, new OSPath());

    @Test
    void clearReturnsAnsiClearSequence() {
        var cmd = new ClearCmd();
        var result = cmd.execute(context);
        assertInstanceOf(SuccessCmdResult.class, result);
        assertEquals("\u001B[H\u001B[2J", result.getStdOut());
        assertNull(result.getStdErr());
    }
}
