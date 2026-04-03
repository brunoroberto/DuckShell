package com.github.brunoroberto.duckshell.core.cmd;

import com.github.brunoroberto.duckshell.core.cmd.builtin.EchoCmd;
import com.github.brunoroberto.duckshell.core.cmd.builtin.ShellCommandExecutor;
import com.github.brunoroberto.duckshell.core.cmd.ext.ExternalCommand;
import com.github.brunoroberto.duckshell.core.cmd.ext.ExternalCommandExecutor;
import com.github.brunoroberto.duckshell.core.ShellContext;
import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommandExecutorFactoryTest {

    @Test
    void createsShellCommandExecutorForBuiltins() {
        var echoCmd = new EchoCmd(new CommandNode("echo", List.of("hi"), List.of()));
        var executor = CommandExecutorFactory.create(echoCmd);
        assertInstanceOf(ShellCommandExecutor.class, executor);
    }

    @Test
    void createsExternalCommandExecutorForExternalCommands() {
        ExternalCommand externalCmd = shellContext -> null;
        var executor = CommandExecutorFactory.create(externalCmd);
        assertInstanceOf(ExternalCommandExecutor.class, executor);
    }

    @Test
    void throwsForUnknownCommandType() {
        Command unknownCmd = shellContext -> null;
        assertThrows(IllegalArgumentException.class, () ->
                CommandExecutorFactory.create(unknownCmd));
    }
}
