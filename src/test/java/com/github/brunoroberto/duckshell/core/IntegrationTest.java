package com.github.brunoroberto.duckshell.core;

import com.github.brunoroberto.duckshell.core.cmd.CommandExecutorFactory;
import com.github.brunoroberto.duckshell.core.cmd.CommandResolver;
import com.github.brunoroberto.duckshell.core.cmd.EmptyCmdResult;
import com.github.brunoroberto.duckshell.core.cmd.SuccessCmdResult;
import com.github.brunoroberto.duckshell.core.cmd.builtin.InvalidCmd;
import com.github.brunoroberto.duckshell.core.ErrorCmdResult;
import com.github.brunoroberto.duckshell.core.parser.DuckParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IntegrationTest {

    private DuckParser parser;
    private CommandResolver resolver;
    private Context context;

    @BeforeEach
    void setUp() {
        parser = new DuckParser();
        resolver = new CommandResolver();
        context = new Context(content -> {}, new OSPath());
    }

    @Test
    void fullPipelineEchoSimple() {
        var node = parser.parse("echo hello");
        var command = resolver.resolve(context, node);
        var executor = CommandExecutorFactory.create(command);
        var result = executor.execute(context, command);

        assertInstanceOf(SuccessCmdResult.class, result);
        assertEquals("hello", result.getStdOut());
        assertNull(result.getStdErr());
    }

    @Test
    void fullPipelineEchoMultipleArgs() {
        var node = parser.parse("echo hello beautiful world");
        var command = resolver.resolve(context, node);
        var executor = CommandExecutorFactory.create(command);
        var result = executor.execute(context, command);

        assertInstanceOf(SuccessCmdResult.class, result);
        assertEquals("hello beautiful world", result.getStdOut());
        assertNull(result.getStdErr());
    }

    @Test
    void fullPipelineEchoQuotedString() {
        var node = parser.parse("echo \"hello world\"");
        var command = resolver.resolve(context, node);
        var executor = CommandExecutorFactory.create(command);
        var result = executor.execute(context, command);

        assertInstanceOf(SuccessCmdResult.class, result);
        assertEquals("hello world", result.getStdOut());
        assertNull(result.getStdErr());
    }

    @Test
    void fullPipelineEchoWithRedirection() {
        var node = parser.parse("echo hello > out.txt");
        var command = resolver.resolve(context, node);
        var executor = CommandExecutorFactory.create(command);
        var result = executor.execute(context, command);

        assertInstanceOf(SuccessCmdResult.class, result);
        assertEquals("hello", result.getStdOut());
        assertNull(result.getStdErr());
    }

    @Test
    void fullPipelineEchoNoArgs() {
        var node = parser.parse("echo");
        var command = resolver.resolve(context, node);
        var executor = CommandExecutorFactory.create(command);
        var result = executor.execute(context, command);

        assertInstanceOf(EmptyCmdResult.class, result);
        assertNull(result.getStdOut());
        assertNull(result.getStdErr());
    }

    @Test
    void fullPipelinePwd() {
        var node = parser.parse("pwd");
        var command = resolver.resolve(context, node);
        var executor = CommandExecutorFactory.create(command);
        var result = executor.execute(context, command);

        assertInstanceOf(SuccessCmdResult.class, result);
        assertEquals(System.getProperty("user.dir"), result.getStdOut());
    }

    @Test
    void fullPipelineCdToHome() {
        var node = parser.parse("cd ~");
        var command = resolver.resolve(context, node);
        var executor = CommandExecutorFactory.create(command);
        var result = executor.execute(context, command);

        assertInstanceOf(EmptyCmdResult.class, result);
        assertEquals(System.getenv("HOME"), context.getCurrentWorkingDirectoryAsString());
    }

    @Test
    void fullPipelineTypeBuiltin() {
        var node = parser.parse("type echo");
        var command = resolver.resolve(context, node);
        var executor = CommandExecutorFactory.create(command);
        var result = executor.execute(context, command);

        assertInstanceOf(SuccessCmdResult.class, result);
        assertEquals("echo is a shell builtin", result.getStdOut());
    }

    @Test
    void fullPipelineQuack() {
        var node = parser.parse("quack");
        var command = resolver.resolve(context, node);
        var executor = CommandExecutorFactory.create(command);
        var result = executor.execute(context, command);

        assertInstanceOf(SuccessCmdResult.class, result);
        assertEquals("\uD83E\uDD86 Quack!", result.getStdOut());
    }

    @Test
    void fullPipelineInvalidCommand() {
        var node = parser.parse("nonexistent_xyz");
        var command = resolver.resolve(context, node);
        assertInstanceOf(InvalidCmd.class, command);

        var executor = CommandExecutorFactory.create(command);
        var result = executor.execute(context, command);

        assertInstanceOf(ErrorCmdResult.class, result);
        assertTrue(result.getStdErr().contains("command not found"));
    }

    @Test
    void unknownCommandResolvesToNull() {
        var node = parser.parse("unknown-command");
        var command = resolver.resolve(context, node);
        assertInstanceOf(InvalidCmd.class, command);
    }
}
