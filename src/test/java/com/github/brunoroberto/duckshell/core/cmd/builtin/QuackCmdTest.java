package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.OSPath;
import com.github.brunoroberto.duckshell.core.cmd.SuccessCmdResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuackCmdTest {

    private final Context context = new Context(content -> {}, new OSPath());

    @Test
    void quackReturnsDuckMessage() {
        var cmd = new QuackCmd();
        var result = cmd.execute(context);
        assertInstanceOf(SuccessCmdResult.class, result);
        assertEquals("\uD83E\uDD86 Quack!", result.getStdOut());
        assertNull(result.getStdErr());
    }
}
