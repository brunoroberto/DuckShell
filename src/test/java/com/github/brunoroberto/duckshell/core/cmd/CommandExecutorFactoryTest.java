package com.github.brunoroberto.duckshell.core.cmd;

import com.github.brunoroberto.duckshell.core.cmd.builtin.EchoCmd;
import com.github.brunoroberto.duckshell.core.cmd.builtin.ShellCommandExecutor;
import com.github.brunoroberto.duckshell.core.cmd.ext.ExternalCommand;
import com.github.brunoroberto.duckshell.core.cmd.ext.ExternalCommandExecutor;
import com.github.brunoroberto.duckshell.core.parser.tokens.CommandNode;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandExecutorFactoryTest {

    @Test
    void createsShellCommandExecutorForBuiltins() {
        var echoCmd = new EchoCmd(new CommandNode("echo", List.of("hi"), List.of()));
        var executor = CommandExecutorFactory.create(echoCmd);
        assertInstanceOf(ShellCommandExecutor.class, executor);
    }

    @Test
    void createsExternalCommandExecutorForExternalCommands() {
        var externalCommandNode = new CommandNode("cat", List.of("file.txt"), List.of());
        ExternalCommand externalCmd = new ExternalCommand(externalCommandNode, Mockito.mock(Path.class));
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
