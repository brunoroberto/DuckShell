package com.github.brunoroberto.duckshell.core;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OSPath {

    private static final String PATH_VAR = "PATH";
    private static final String PATH_SEPARATOR = ":";

    private final List<Path> paths = new ArrayList<>();

    public OSPath() {
        var dirs = System.getenv(PATH_VAR).split(PATH_SEPARATOR);
        for (var dir : dirs) {
            this.paths.add(Path.of(dir));
        }
    }

    public Path findExecutableCommandPath(String commandName) {
        for (var currentPath : this.paths) {
            try {
                var commandPath = currentPath.resolve(commandName);
                if (Files.isRegularFile(commandPath) && Files.isExecutable(commandPath)) {
                    return currentPath;
                }

            } catch (InvalidPathException e) {
                // if something happens here it's fine, we want to continue keep looking
            }
        }
        return null;
    }

}
