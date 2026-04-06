package com.github.brunoroberto;

import com.github.brunoroberto.duckshell.core.Context;
import com.github.brunoroberto.duckshell.core.DuckShell;
import com.github.brunoroberto.duckshell.core.OSPath;
import com.github.brunoroberto.duckshell.core.cmd.CommandResolver;
import com.github.brunoroberto.duckshell.core.io.ConsoleOutput;
import com.github.brunoroberto.duckshell.core.parser.DuckParser;

public class Main {

    static void main() {
        var shellContext = new Context(new ConsoleOutput(), new OSPath());
        var shell = new DuckShell(shellContext, new DuckParser(), new CommandResolver());
        shell.run();
    }
}
