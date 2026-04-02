package com.github.brunoroberto.duckshell.core.io;

public class ConsoleOutput implements ShellOutput {

    @Override
    public void print(String content) {
        IO.println(content);
    }
}
