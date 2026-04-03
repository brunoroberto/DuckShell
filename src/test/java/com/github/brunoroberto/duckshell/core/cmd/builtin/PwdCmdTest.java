package com.github.brunoroberto.duckshell.core.cmd.builtin;

import com.github.brunoroberto.duckshell.core.ShellContext;
import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;
import com.github.brunoroberto.duckshell.core.parser.tokens.RedirectionNode;
import com.github.brunoroberto.duckshell.core.parser.tokens.RedirectionType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PwdCmdTest {

    private final ShellContext context = new ShellContext(content -> {});

    @Test
    void pwdReturnsCurrentWorkingDirectory() {
        var cmd = new PwdCmd(new CommandNode("pwd", List.of(), List.of()));
        var result = cmd.execute(context);
        assertEquals(System.getProperty("user.dir"), result.output());
        assertTrue(result.shouldPrint());
        assertTrue(result.success());
    }

    @Test
    void pwdPreservesRedirections() {
        var redirections = List.of(new RedirectionNode(RedirectionType.STDOUT_OVERWRITE, "out.txt"));
        var cmd = new PwdCmd(new CommandNode("pwd", List.of(), redirections));
        var result = cmd.execute(context);
        assertEquals(1, result.redirections().size());
        assertEquals("out.txt", result.redirections().get(0).target());
    }

    @Test
    void pwdWithNullContextThrows() {
        var cmd = new PwdCmd(new CommandNode("pwd", List.of(), List.of()));
        assertThrows(NullPointerException.class, () -> cmd.execute(null));
    }
}
