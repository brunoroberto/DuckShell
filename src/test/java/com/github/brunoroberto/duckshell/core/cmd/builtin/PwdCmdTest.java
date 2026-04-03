package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.OSPath;
import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;
import com.github.brunoroberto.duckshell.core.parser.tokens.RedirectionNode;
import com.github.brunoroberto.duckshell.core.parser.tokens.RedirectionType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PwdCmdTest {

    private final Context context = new Context(content -> {}, new OSPath());

    @Test
    void pwdReturnsCurrentWorkingDirectory() {
        var cmd = new PwdCmd(new CommandNode("pwd", List.of(), List.of()));
        var result = cmd.execute(context);
        assertEquals(System.getProperty("user.dir"), result.getResult());
        assertTrue(result.shouldPrint());
        assertTrue(result.isSuccess());
    }

    @Test
    void pwdPreservesRedirections() {
        var redirections = List.of(new RedirectionNode(RedirectionType.STDOUT_OVERWRITE, "out.txt"));
        var cmd = new PwdCmd(new CommandNode("pwd", List.of(), redirections));
        var result = cmd.execute(context);
        assertTrue(result.hasRedirection());
        assertEquals("out.txt", result.getRedirections().getFirst().target());
    }

    @Test
    void pwdWithNullContextThrows() {
        var cmd = new PwdCmd(new CommandNode("pwd", List.of(), List.of()));
        assertThrows(NullPointerException.class, () -> cmd.execute(null));
    }
}
