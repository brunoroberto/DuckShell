package com.github.brunoroberto.duckshell.core;

import com.github.brunoroberto.duckshell.core.io.ShellOutput;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class ShellContext {

    private final ShellOutput defaultOutput;

    private Path currentWorkingDirectory;

    public ShellContext(ShellOutput defaultOutput) {
        Objects.requireNonNull(defaultOutput, "defaultOutput must not be null");
        this.defaultOutput = defaultOutput;
        this.currentWorkingDirectory = Paths.get(System.getProperty("user.dir"));
    }

    public String getCurrentWorkingDirectoryAsString() {
        return currentWorkingDirectory.toString();
    }

    public Path getCurrentWorkingDirectory() {
        return this.currentWorkingDirectory;
    }

    public ShellOutput getDefaultOutput() {
        return this.defaultOutput;
    }

}
