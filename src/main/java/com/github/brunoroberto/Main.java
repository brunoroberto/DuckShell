package com.github.brunoroberto;

import com.github.brunoroberto.duckshell.core.DuckShell;
import com.github.brunoroberto.duckshell.core.ShellContext;
import com.github.brunoroberto.duckshell.core.cmd.CommandResolver;
import com.github.brunoroberto.duckshell.core.io.ConsoleOutput;
import com.github.brunoroberto.duckshell.core.parser.DuckParser;

public class Main {

    static void main() {
        var shell = new DuckShell(new ShellContext(new ConsoleOutput()), new DuckParser(), new CommandResolver());
        shell.run();
    }
}
