package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.OSPath;
import com.github.brunoroberto.duckshell.core.cmd.SuccessCmdResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PwdCmdTest {

    private final Context context = new Context(content -> {}, new OSPath());

    @Test
    void pwdReturnsCurrentWorkingDirectory() {
        var cmd = new PwdCmd();
        var result = cmd.execute(context);
        assertInstanceOf(SuccessCmdResult.class, result);
        assertEquals(System.getProperty("user.dir"), result.getStdOut());
    }

    @Test
    void pwdWithNullContextThrows() {
        var cmd = new PwdCmd();
        assertThrows(NullPointerException.class, () -> cmd.execute(null));
    }
}
