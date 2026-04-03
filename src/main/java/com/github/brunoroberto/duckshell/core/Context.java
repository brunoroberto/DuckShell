package com.github.brunoroberto.duckshell.core;

import com.github.brunoroberto.duckshell.core.io.ShellOutput;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Context {

    private final ShellOutput defaultOutput;
    private final OSPath osPath;

    private Path currentWorkingDirectory;

    public Context(ShellOutput defaultOutput,  OSPath osPath) {
        Objects.requireNonNull(defaultOutput, "defaultOutput must not be null");
        this.defaultOutput = defaultOutput;
        this.currentWorkingDirectory = Paths.get(System.getProperty("user.dir"));
        this.osPath = osPath;
    }

    public OSPath getOsPath() {
        return osPath;
    }

    public ShellOutput getDefaultOutput() {
        return this.defaultOutput;
    }

    public String getCurrentWorkingDirectoryAsString() {
        return currentWorkingDirectory.toString();
    }

    public Path getCurrentWorkingDirectory() {
        return this.currentWorkingDirectory;
    }

    public void updateCurrentWorkingDirectory(String targetDir) throws FileNotFoundException {
        var newWorkingDir = this.currentWorkingDirectory.resolve(targetDir).normalize();
        if (!Files.exists(newWorkingDir)) {
            throw new FileNotFoundException(String.format("Target directory '%s' does not exist", newWorkingDir));
        }
        this.currentWorkingDirectory = newWorkingDir;
    }
}
